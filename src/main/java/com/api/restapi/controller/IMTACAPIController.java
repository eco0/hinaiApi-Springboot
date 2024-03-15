package com.api.restapi.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings("deprecation")
@Controller
public class IMTACAPIController {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@PersistenceContext
    private EntityManager entityManager;
	
	private String buildErrorResponse(final int errorCode, final String errrorMessage) {
		Map<String, Object> responseMap      = new LinkedHashMap<String, Object>();
		Map<String, Object> metadataMapInner = new LinkedHashMap<String, Object>();
		metadataMapInner.put("message", errrorMessage);
		metadataMapInner.put("code", errorCode);
		responseMap.put("Metadata", metadataMapInner);
		return new GsonBuilder().create().toJson(responseMap);
	}
	@SuppressWarnings({ "unchecked" })
	@RequestMapping (value = "/Imtac/Notification", method = RequestMethod.GET,  produces = "application/json; charset=utf-8")
	@ResponseBody String getSatuSehatData(HttpServletRequest request){
		Gson gson = new GsonBuilder().create();
		try {
			String tanggalpulang = request.getParameter("tanggalpulang");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String branchid = request.getParameter("branchid");
			if(tanggalpulang.isEmpty()){
				return buildErrorResponse(202, "parameter tanggalpulang must be filled.");
			}
			if(start_time.isEmpty()){
				return buildErrorResponse(202, "parameter start_time must be filled.");
			}
			if(end_time.isEmpty()){
				return buildErrorResponse(202, "parameter end_time must be filled.");
			}
			if(branchid.isEmpty()){
				return buildErrorResponse(202, "parameter branchid must be filled.");
			}
			log.error("[INFO|PARAM] API Start . .");
			String startdate = tanggalpulang+start_time;
			String enddate = tanggalpulang+end_time;
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			List<Map<String, Object>> listResponseHeader = new LinkedList<Map<String, Object>>();
			SQLQuery<Object[]> branch = (SQLQuery<Object[]>) session.getNamedQuery("loadBranchDetailsForSatuSehat")
                    .setParameter("branchid", branchid);
			List branchList = branch.list();
			String branchId = branchList.get(0).toString();
			if(branchId.isEmpty()){
				return buildErrorResponse(202, "Branch details not found, Branch ID :" + branchid);
			}
			SQLQuery<Object[]> patientData = (SQLQuery<Object[]>) session.getNamedQuery("getPatientDischargeSatuSehat")
					.setParameter("startdate", startdate).setParameter("enddate", enddate).setParameter("branchid", branchId);
			List <Object[]> patient = patientData.list();
			for (Object[] ptb : patient) {
				try {
				Map<String, Object> listResponse = new LinkedHashMap<String, Object>();
				List<Map<String, String>> DataEncounter = new LinkedList<Map<String, String>>();
				/*Data Encounter*/
			    SQLQuery<Object[]> EncounterData = (SQLQuery<Object[]>) session.getNamedQuery("getEncounterSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> Encounter = EncounterData.list();
				for (Object[] m : Encounter) {
					Map<String, String> dataEncounter = new LinkedHashMap<String, String>();
					dataEncounter.put("kodebooking",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataEncounter.put("taskid",!Objects.isNull(m[13]) ? m[13].toString() : "" );
					dataEncounter.put("waktu",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					DataEncounter.add(dataEncounter);
				}
				/*End Encounter*/
			listResponseHeader.add(listResponse);
			} catch (Exception e) {
		        continue; // Melanjutkan ke iterasi berikutnya
		      }
			}
			Map<String, Object> response = new HashMap<>();
			response.put("Response", listResponseHeader);
			Map<String, Object> metadata = new HashMap<>();
			metadata.put("code", "200");
			metadata.put("message", "SUCCESS");
			response.put("Metadata", metadata);
			return gson.toJson(response);
		}catch(Exception e) {
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
//		    return buildErrorResponse(500, e.getMessage() + "\n" + sw.toString());
			return buildErrorResponse(500, "error");
		}
	}
}
