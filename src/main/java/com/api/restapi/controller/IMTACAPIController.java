package com.api.restapi.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import org.hibernate.type.LongType;

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
	@ResponseBody String getIMTACData(HttpServletRequest request){
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
			String startdate = tanggalpulang+start_time;
			String enddate = tanggalpulang+end_time;
			log.error("[INFO] :::::::::START OPEN CONNECTION::::::::::");
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();
			log.error("[INFO] :::::::::API START::::::::::");
			log.error("[INFO|tanggalpulang] :::::::::"+tanggalpulang+"::::::::::");
			log.error("[INFO|start_time] :::::::::"+startdate+"::::::::::");
			log.error("[INFO|end_time] :::::::::"+enddate+"::::::::::");
			log.error("[INFO|branchid] :::::::::"+branchid+"::::::::::");
			List<Map<String, Object>> listResponseHeader = new LinkedList<Map<String, Object>>();
			SQLQuery<Object[]> branch = (SQLQuery<Object[]>) session.getNamedQuery("loadBranchDetailsForIMTAC")
                    .setParameter("branchid", branchid);
			List branchList = branch.list();
			String branchId = branchList.get(0).toString();
			if(branchId.isEmpty()){
				return buildErrorResponse(202, "Branch details not found, Branch ID :" + branchid);
			}
			SQLQuery<Long> patientData = (SQLQuery<Long>) session.getNamedQuery("getImctacEncounterList")
					.setParameter("startdate", startdate).setParameter("enddate", enddate).setParameter("branchid", branchId);
			List <Long> patient = patientData.list();
			for (Long encounterId : patient) {
				try {
//				Map<String, Object> listResponse = new LinkedHashMap<String, Object>();
//				Map<String, Object> imtacResponse = new LinkedHashMap<String, Object>();
//				List<Map<String, Object>> DataEncounter = new LinkedList<Map<String, Object>>();
				/*Data Encounter*/
			    SQLQuery<Object[]> EncounterData = (SQLQuery<Object[]>) session.getNamedQuery("getEncounterIMTAC").setParameter("encounterId", encounterId.toString());
				List <Object[]> Encounter = EncounterData.list();
				for (Object[] m : Encounter) {
			        LocalDateTime currentDateTime = LocalDateTime.now();
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			        String waktu = currentDateTime.format(formatter);
					Map<String, Object> dataEncounter = new LinkedHashMap<String, Object>();
					dataEncounter.put("kodebooking",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataEncounter.put("taskid",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataEncounter.put("waktu", waktu);
					listResponseHeader.add(dataEncounter);
				}
				/*End Encounter*/
			} catch (Exception e) {
				log.error("[ERROR] "+e.getMessage());
		        continue; // Melanjutkan ke iterasi berikutnya
		      }
			}
			Map<String, Object> response = new HashMap<>();
			response.put("response", listResponseHeader);
			Map<String, Object> metadata = new HashMap<>();
			metadata.put("code", "200");
			metadata.put("message", "SUCCESS");
			response.put("metadata", metadata);
			log.error("[INFO] :::::::::"+listResponseHeader+"::::::::::");
			log.error("[INFO|API] :::::::::"+metadata+"::::::::::");
			log.error("[INFO] :::::::::API END::::::::::");
			return gson.toJson(response);
		}catch(Exception e) {
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    e.printStackTrace(pw);
		    log.error("[ERROR] "+e.getMessage() + "\n" + sw.toString());
//		    return buildErrorResponse(500, e.getMessage() + "\n" + sw.toString());
			return buildErrorResponse(500, "error");
		}
	}
}
