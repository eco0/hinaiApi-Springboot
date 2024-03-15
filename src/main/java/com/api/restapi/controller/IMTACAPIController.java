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
	
//	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	
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
	@RequestMapping (value = "/SatuSehat/Info", method = RequestMethod.GET,  produces = "application/json; charset=utf-8")
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
			
			SQLQuery<Object[]> patientIpData = (SQLQuery<Object[]>) session.getNamedQuery("getPatientDischargeIPSatuSehat")
					.setParameter("startdate", startdate).setParameter("enddate", enddate).setParameter("branchid", branchId);
			List <Object[]> patientIp = patientIpData.list();
			for (Object[] ptb : patient) {
				try {
				Map<String, Object> listResponse = new LinkedHashMap<String, Object>();
				Map<String, Object> dataPenunjangMedis = new LinkedHashMap<String, Object>();
				Map<String, Object> dataPemeriksaanMedis = new LinkedHashMap<String, Object>();
				List<Map<String, String>> DataPasien = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataEncounter = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataMedis = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataBilling = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> MedicationTanggal = new LinkedList<Map<String, Object>>();
				List<Map<String, String>> DataMedicationService = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> LabResult = new LinkedList<Map<String, Object>>();
				List<Map<String, String>> RadiologyResult = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> ListDatavitalSign = new LinkedList<Map<String, Object>>();
				List<Map<String, String>> DataAllergy = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataDiet = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataClinicalNote = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> DataConsultantNote = new LinkedList<Map<String, Object>>();
				List<Map<String, Object>> DataDischargeSummary = new LinkedList<Map<String, Object>>();
			    /*Patient*/
				SQLQuery<Object[]> PatientData = (SQLQuery<Object[]>) session.getNamedQuery("getPatientSatuSehat")
						.setParameter("patientId", ptb[1].toString());
				List <Object[]> Patient = PatientData.list();
			    for (Object[] m : Patient) {
			    	Map<String, String> dataPasien = new LinkedHashMap<String, String>();
			        dataPasien.put("Patient_Name", !Objects.isNull(m[2]) ? m[2].toString() : "");
			        dataPasien.put("MRN", !Objects.isNull(m[3]) ? m[3].toString() : "");
			        dataPasien.put("Patient_Gender", !Objects.isNull(m[4]) ? m[4].toString() : "");
			        dataPasien.put("Patient_DOB", !Objects.isNull(m[5]) ?  m[5].toString() : "");
			        dataPasien.put("Patient_NIK", !Objects.isNull(m[6]) ? m[6].toString() : "");
			        dataPasien.put("Patient_BPJS_Card_Number", !Objects.isNull(m[7]) ? m[7].toString() : "");
			        dataPasien.put("Patient_Phone_Number", !Objects.isNull(m[8]) ? m[8].toString() : "");
			        dataPasien.put("Marital_Status", !Objects.isNull(m[9]) ? m[9].toString() : "");
			        dataPasien.put("Adress", !Objects.isNull(m[10]) ? m[10].toString() : "");
			        dataPasien.put("State", !Objects.isNull(m[11]) ? m[11].toString() : "");
			        dataPasien.put("District", !Objects.isNull(m[12]) ? m[12].toString() : "");
			        dataPasien.put("City", !Objects.isNull(m[13]) ? m[13].toString() : "");
			        dataPasien.put("Village", !Objects.isNull(m[14]) ? m[14].toString() : "");
			        dataPasien.put("Zip_Code", !Objects.isNull(m[15]) ? m[15].toString() : "");
			        DataPasien.add(dataPasien);
			    }
			    /*End Patient*/
			    
				/*Data Encounter*/
			    SQLQuery<Object[]> EncounterData = (SQLQuery<Object[]>) session.getNamedQuery("getEncounterSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> Encounter = EncounterData.list();
				for (Object[] m : Encounter) {
					Map<String, String> dataEncounter = new LinkedHashMap<String, String>();
					dataEncounter.put("encounter_branch_id",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataEncounter.put("MRN",!Objects.isNull(m[13]) ? m[13].toString() : "" );
					dataEncounter.put("visit_type",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataEncounter.put("visite_date",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataEncounter.put("encounter_id",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					dataEncounter.put("encounter_type",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					dataEncounter.put("encounter_time",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					dataEncounter.put("start_consult_time",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					dataEncounter.put("end_consult_time",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					dataEncounter.put("department_name",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					dataEncounter.put("department_code",!Objects.isNull(m[10]) ? m[10].toString() : "" );
					dataEncounter.put("unit_name",!Objects.isNull(m[11]) ? m[11].toString() : "" );
					dataEncounter.put("unit_id",!Objects.isNull(m[12]) ? m[12].toString() : "" );
					dataEncounter.put("nosep",!Objects.isNull(m[14]) ? m[14].toString() : "" );
					DataEncounter.add(dataEncounter);
				}
				/*End Encounter*/
				
				/*Data Medis*/
				SQLQuery<Object[]> MedisData = (SQLQuery<Object[]>) session.getNamedQuery("getMedisSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> Medis = MedisData.list();
				for (Object[] m : Medis) {
					Map<String, String> dataMedis = new LinkedHashMap<String, String>();
					dataMedis.put("encounter_id",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataMedis.put("consultant_name",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataMedis.put("consultant_NIK",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataMedis.put("consultant_employee_code",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					dataMedis.put("diagnosis_icd10_code",!Objects.isNull(m[4]) ? m[4].toString() : "-" );
					if (!(m[5].toString().equals("-"))) {
						dataMedis.put("primary", !Objects.isNull(m[5]) ? m[5].toString() : "-" );
					}
					if (!(m[6].toString().equals("-"))) {
						dataMedis.put("secondary", !Objects.isNull(m[6]) ? m[6].toString() : "-" );
					}
					if (!(m[7].toString().equals("-"))) {
						dataMedis.put("co-morbid", !Objects.isNull(m[7]) ? m[7].toString() : "-" );
					}
					dataMedis.put("diagnosis_icd10_description",!Objects.isNull(m[8]) ? m[8].toString() : "-" );
					if (!(m[9].toString().equals("-"))) {
						dataMedis.put("primary_desc", !Objects.isNull(m[9]) ? m[9].toString() : "-" );
					}
					if (!(m[10].toString().equals("-"))) {
						dataMedis.put("secondary_desc", !Objects.isNull(m[10]) ? m[10].toString() : "-" );
					}
					if (!(m[11].toString().equals("-"))) {
						dataMedis.put("co-morbid_desc", !Objects.isNull(m[11]) ? m[11].toString() : "-" );
					}
					DataMedis.add(dataMedis);
				}
				/*End Medis*/
				
				/*Data Billing*/
				SQLQuery<Object[]> BillingData = (SQLQuery<Object[]>) session.getNamedQuery("getBillingSatuSehat")
						.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> Billing = BillingData.list();
				for (Object[] m : Billing) {
					Map<String, String> dataBilling = new LinkedHashMap<String, String>();
					dataBilling.put("encounter_id",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					dataBilling.put("no_Invoice",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataBilling.put("datetime",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataBilling.put("group_id",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					dataBilling.put("group_code",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					dataBilling.put("billing_group_name",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					dataBilling.put("service_code",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					dataBilling.put("service_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataBilling.put("order_no",!Objects.isNull(m[10]) ? m[10].toString() : "" );
					dataBilling.put("tariff",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					dataBilling.put("plan",!Objects.isNull(m[4]) ? m[4].toString() : "");
					DataBilling.add(dataBilling);
				}
				/*End Billing*/
				
				/*Penunjang Medis*/
				SQLQuery<Object[]> ListMedicationData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationSatuSehat")
						.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> ListMedication = ListMedicationData.list();
				SQLQuery<Object[]> ListMedicationServiceData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServiceSatuSehat")
						.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> ListMedicationService = ListMedicationServiceData.list();
				SQLQuery<Object[]> ListLabResultData = (SQLQuery<Object[]>) session.getNamedQuery("getListLabResultSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> ListLabResult = ListLabResultData.list();
				SQLQuery<Object[]> ListRadiologyResultData = (SQLQuery<Object[]>) session.getNamedQuery("getListRadiologyResultSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> ListRadiologyResult = ListRadiologyResultData.list();
				
				//Medication
				for (Object[] m : ListMedication) {
					Map<String, Object> medicationTanggal = new LinkedHashMap<String, Object>();
					medicationTanggal.put("encounter_id",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					medicationTanggal.put("resep_no",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					medicationTanggal.put("tanggal",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					medicationTanggal.put("obat_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					medicationTanggal.put("obat_code",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					medicationTanggal.put("price",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					medicationTanggal.put("qty",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					medicationTanggal.put("instruction",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					medicationTanggal.put("mixture",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					
					SQLQuery<Object[]> ListMixtureData = (SQLQuery<Object[]>) session.getNamedQuery("getListMixtureSatuSehat")
							.setParameter("ordersId", m[5].toString());
					List <Object[]> ListMixture = ListMixtureData.list();
					List<Map<String, String>> DataMedicationMixture = new LinkedList<Map<String, String>>();
					if (m[6].toString().equals("Y")) {
						for (Object[] lm : ListMixture) {
							Map<String, String> medicationMixture = new LinkedHashMap<String, String>();
							medicationMixture.put("obat_name",!Objects.isNull(lm[0]) ? lm[0].toString() : "" );
							medicationMixture.put("obat_code",!Objects.isNull(lm[3]) ?lm[3].toString() : "" );
							medicationMixture.put("price",!Objects.isNull(lm[4]) ?lm[4].toString() : "" );
							medicationMixture.put("qty",!Objects.isNull(lm[1]) ? lm[1].toString() : "" );
							medicationMixture.put("instruction",!Objects.isNull(lm[2]) ? lm[2].toString() : "" );
							DataMedicationMixture.add(medicationMixture);
						}
					}
					medicationTanggal.put("component", DataMedicationMixture);
					MedicationTanggal.add(medicationTanggal);
				}
				//End
			
				//Medication Service
				for (Object[] m : ListMedicationService) {
					Map<String, String> medicationService = new LinkedHashMap<String, String>();
					medicationService.put("encounter_id",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					medicationService.put("tanggal",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					medicationService.put("service_code",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					medicationService.put("service_name",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					medicationService.put("order_no",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					medicationService.put("tariff",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					DataMedicationService.add(medicationService);
				}
				//End
				
				//Lab Result
				for (Object[] m : ListLabResult) {
					Map<String, Object> labResultPemeriksaan = new LinkedHashMap<String, Object>();
					labResultPemeriksaan.put("encounter_id",!Objects.isNull(m[10]) ? m[10].toString() : "" );
					labResultPemeriksaan.put("order_no",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					labResultPemeriksaan.put("service_code",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					labResultPemeriksaan.put("price",!Objects.isNull(m[11]) ? m[11].toString() : "" );
					labResultPemeriksaan.put("tanggal",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					labResultPemeriksaan.put("service_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					labResultPemeriksaan.put("nilai_normal",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					labResultPemeriksaan.put("result",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					labResultPemeriksaan.put("satuan",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					labResultPemeriksaan.put("Tanggal_Certified",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					labResultPemeriksaan.put("Certifiying_Consultant_BPJS_Code",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					labResultPemeriksaan.put("Certifiying_Consultant_Name",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					labResultPemeriksaan.put("Certifiying_Employee_Code",!Objects.isNull(m[14]) ? m[14].toString() : "" );
					labResultPemeriksaan.put("Specimen_code",!Objects.isNull(m[12]) ? m[12].toString() : "" );
					labResultPemeriksaan.put("Specimen_name",!Objects.isNull(m[13]) ? m[13].toString() : "" );
					labResultPemeriksaan.put("Parameter",!Objects.isNull(m[15]) ? m[15].toString() : "" );
					/*Parameter List*/
					SQLQuery<Object[]> labResultParameterData = (SQLQuery<Object[]>) session.getNamedQuery("getLabResultParameterSatuSehat")
							.setParameter("labresultId", m[16].toString());
					List <Object[]> labResultParameter = labResultParameterData.list();
					List<Map<String, String>> DatalabResultParameter = new LinkedList<Map<String, String>>();
					for (Object[] lrp : labResultParameter) {
						Map<String, String> labParameter = new LinkedHashMap<String, String>();
						labParameter.put("parameter_name", !Objects.isNull(lrp[0]) ? lrp[0].toString() : "");
						labParameter.put("unit", !Objects.isNull(lrp[1]) ? lrp[1].toString() : "");
						labParameter.put("result_range", !Objects.isNull(lrp[2]) ? lrp[2].toString() : "");
						labParameter.put("result_value", !Objects.isNull(lrp[3]) ? lrp[3].toString() : "");
						DatalabResultParameter.add(labParameter);
					}/*End Parameter List*/
					labResultPemeriksaan.put("Parameter_List", DatalabResultParameter );
					LabResult.add(labResultPemeriksaan);
				}//End
				
				//Radiology Result
				for (Object[] m : ListRadiologyResult) {
					Map<String, String> radiologyResultPemeriksaan = new LinkedHashMap<String, String>();
					radiologyResultPemeriksaan.put("encounter_id",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					radiologyResultPemeriksaan.put("order_no",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					radiologyResultPemeriksaan.put("service_code",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					radiologyResultPemeriksaan.put("price",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					radiologyResultPemeriksaan.put("tanggal",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					radiologyResultPemeriksaan.put("service_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					radiologyResultPemeriksaan.put("result",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					radiologyResultPemeriksaan.put("Certifiying_Consultant_BPJS_Code",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					radiologyResultPemeriksaan.put("Certifiying_Consultant_Name",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					radiologyResultPemeriksaan.put("Certifiying_Employee_Code",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					RadiologyResult.add(radiologyResultPemeriksaan);
				}//End
				
				/*Data Vital Sign*/
				Map<String, Object> datavitalSign = new LinkedHashMap<String, Object>();
				List<Map<String, String>> DataVitalSign = new LinkedList<Map<String, String>>();
				datavitalSign.put("encounter_id",!Objects.isNull(ptb[0].toString()) ? ptb[0].toString() : "" );
				SQLQuery<Object[]> dataVitalSignData = (SQLQuery<Object[]>) session.getNamedQuery("getvitalSignSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> dataVitalSign = dataVitalSignData.list();
				for (Object[] vs : dataVitalSign) {
					Map<String, String> mapDataVitalSign = new LinkedHashMap<String, String>();
					mapDataVitalSign.put("code_vitalsign",!Objects.isNull(vs[1]) ? vs[1].toString() : "" );
					mapDataVitalSign.put("description",!Objects.isNull(vs[0]) ?vs[0].toString() : "" );
					mapDataVitalSign.put("value_unit",!Objects.isNull(vs[2]) ?vs[2].toString() : "" );
					mapDataVitalSign.put("value",!Objects.isNull(vs[3]) ? vs[3].toString() : "" );
					mapDataVitalSign.put("employee_code",!Objects.isNull(vs[4]) ? vs[4].toString() : "" );
					mapDataVitalSign.put("employee_name",!Objects.isNull(vs[5]) ? vs[5].toString() : "" );
					mapDataVitalSign.put("time_check_vitalsign",!Objects.isNull(vs[6]) ? vs[6].toString() : "" );
					DataVitalSign.add(mapDataVitalSign);
				}
				datavitalSign.put("detail_vitalsign", DataVitalSign);
				ListDatavitalSign.add(datavitalSign);
				/*End Data Vital Sign*/
				
				/*Data Allergi*/
				SQLQuery<Object[]> AllergiData = (SQLQuery<Object[]>) session.getNamedQuery("getAllergiSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> Allergi = AllergiData.list();
				for (Object[] m : Allergi) {
					Map<String, String> dataAllergi = new LinkedHashMap<String, String>();
					dataAllergi.put("encounter_id", !Objects.isNull(m[0]) ? m[0].toString() : "");
					dataAllergi.put("allergi_code", !Objects.isNull(m[3]) ? m[3].toString() : "");
					dataAllergi.put("allergi_name", !Objects.isNull(m[4]) ? m[4].toString() : "");
					dataAllergi.put("allergi_category", !Objects.isNull(m[5]) ? m[5].toString() : "");
					DataAllergy.add(dataAllergi);
				}/*End Allergi*/
				
				/*Data Diet*/
				SQLQuery<Object[]> DietiData = (SQLQuery<Object[]>) session.getNamedQuery("getDietSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString());
				List <Object[]> Diet = DietiData.list();
				if(!Objects.isNull(Diet)){
					for (Object[] m : Diet) {
						Map<String, String> dataDiet = new LinkedHashMap<String, String>();
						dataDiet.put("encounter_id", !Objects.isNull( ptb[0].toString()) ?  ptb[0].toString() : "" );
						dataDiet.put("diet_note",!Objects.isNull(m[0]) ? m[0].toString() : "" );
						dataDiet.put("datetimeinput",!Objects.isNull(m[1]) ? m[1].toString() : "" );
						DataDiet.add(dataDiet);
					}
				}else{
					Map<String, String> dataDiet = new LinkedHashMap<String, String>();
					dataDiet.put("encounter_id", !Objects.isNull( ptb[0].toString()) ?  ptb[0].toString() : "" );
					dataDiet.put("diet_note", "" );
					dataDiet.put("datetimeinput", "" );
					DataDiet.add(dataDiet);
				}/*End Diet*/
				
				/*Data Clinical Note*/
				SQLQuery<Object[]> clinicalNoteData = (SQLQuery<Object[]>) session.getNamedQuery("getclinicalNoteSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
				List <Object[]> clinicalNote = clinicalNoteData.list();
				if(!Objects.isNull(clinicalNote)){
					for (Object[] m : clinicalNote) {
						Map<String, String> dataclinicalNote = new LinkedHashMap<String, String>();
						dataclinicalNote.put("encounter_id", !Objects.isNull(m[0]) ? m[0].toString() : "" );
						dataclinicalNote.put("subjective", !Objects.isNull(m[1]) ? m[1].toString() : "" );
						dataclinicalNote.put("objective", "" );
						dataclinicalNote.put("assessment", "" );
						dataclinicalNote.put("plan", "" );
						dataclinicalNote.put("datetimeinput",!Objects.isNull(m[9]) ? m[9].toString() : "" );
						DataClinicalNote.add(dataclinicalNote);
					}
				}else{
					Map<String, String> dataclinicalNote = new LinkedHashMap<String, String>();
					dataclinicalNote.put("encounter_id", !Objects.isNull( ptb[0].toString()) ?  ptb[0].toString() : "" );
					dataclinicalNote.put("subjective", "" );
					dataclinicalNote.put("objective", "" );
					dataclinicalNote.put("assessment", "" );
					dataclinicalNote.put("plan", "" );
					dataclinicalNote.put("datetimeinput", "" );
					DataClinicalNote.add(dataclinicalNote);
				}/*End Clinical Note*/
				
				
				/*Data Consultant Note*/
				SQLQuery<Object[]> consultantNoteData = (SQLQuery<Object[]>) session.getNamedQuery("getconsultantNoteSatuSehat")
						.setParameter("patientId", ptb[1].toString()).setParameter("visitId", ptb[2].toString());
				List <Object[]> consultantNote = consultantNoteData.list();
				for (Object[] m : consultantNote) {
					Map<String, Object> dataconsultantNote = new LinkedHashMap<String, Object>();
					dataconsultantNote.put("encounter_id", !Objects.isNull( ptb[0].toString()) ?  ptb[0].toString() : "" );
					dataconsultantNote.put("Nama Pasien", !Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataconsultantNote.put("No. Rekam Medis", !Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataconsultantNote.put("Nama Orang tua/Suami/Istri", !Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataconsultantNote.put("Tanggal Lahir", !Objects.isNull(m[3]) ? m[3].toString() : "" );
					dataconsultantNote.put("Jenis Kelamin", !Objects.isNull(m[4]) ? m[4].toString() : "" );
					dataconsultantNote.put("Tanggal Masuk RS", !Objects.isNull(m[5]) ? m[5].toString() : "" );
					dataconsultantNote.put("Kelas Kamar", !Objects.isNull(m[6]) ? m[6].toString() : "" );
					dataconsultantNote.put("Waktu tanggal,jam pasien tiba di ruangan",  "" );
					dataconsultantNote.put("Waktu tanggal,jam pasien mulai assesmen",  "" );
					//Keluhan Utama 
					SQLQuery<Object[]> keluhanUtamaData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitDahuludanSekarangSatuSehat")
							.setParameter("patientId", ptb[1].toString()).setParameter("visitId", ptb[2].toString());
					List <Object[]> keluhanUtama = keluhanUtamaData.list();
					List<Map<String, String>> DatariwayatKeluhan = new LinkedList<Map<String, String>>();
					for (Object[] rd : keluhanUtama) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("keluhan_utama",!Objects.isNull(rd[0]) ? rd[0].toString() : "" );
						DatariwayatKeluhan.add(medicationService);
					}
					//End Keluhan Utama
					dataconsultantNote.put("Keluhan Utama", DatariwayatKeluhan);
					//Riwayat Sekarang 
					SQLQuery<Object[]> riwayatSekarangData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitDahuludanSekarangSatuSehat")
							.setParameter("patientId", ptb[1].toString()).setParameter("visitId", ptb[2].toString());
					List <Object[]> riwayatSekarang = riwayatSekarangData.list();
					List<Map<String, String>> DatariwayatSekarang = new LinkedList<Map<String, String>>();
					for (Object[] rs : riwayatSekarang) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("riwayat_penyakit_sekarang",!Objects.isNull(rs[1]) ? rs[1].toString() : "" );
						DatariwayatSekarang.add(medicationService);
					}
					//End Riwayat Sekarang
					dataconsultantNote.put("Riwayat penyakit sekarang", DatariwayatSekarang);
					//Riwayat Keluarga 
					SQLQuery<Object[]> riwayatKeluargaData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitKeluargaSatuSehat")
							.setParameter("patientId", ptb[1].toString()).setParameter("visitId", ptb[2].toString());
					List <Object[]> riwayatKeluarga = riwayatKeluargaData.list();
					List<Map<String, String>> DatariwayatKeluarga = new LinkedList<Map<String, String>>();
					for (Object[] rk : riwayatKeluarga) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("relasi",!Objects.isNull(rk[0]) ? rk[0].toString() : "" );
						medicationService.put("riwayat_penyakit_keluarga",!Objects.isNull(rk[1]) ? rk[1].toString() : "" );
						DatariwayatKeluarga.add(medicationService);
					}
					//End Riwayat Keluarga
					dataconsultantNote.put("Riwayat penyakit keluarga", DatariwayatKeluarga);
					//Riwayat Dahulu 
					SQLQuery<Object[]> riwayatDahuluData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitDahuluSatuSehat")
							.setParameter("patientId", ptb[1].toString()).setParameter("visitId", ptb[2].toString());
					List <Object[]> riwayatDahulu = riwayatDahuluData.list();
					List<Map<String, String>> DatariwayatDahulu = new LinkedList<Map<String, String>>();
					for (Object[] rd : riwayatDahulu) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("riwayat_penyakit_dahulu",!Objects.isNull(rd[0]) ? rd[0].toString() : "" );
						DatariwayatDahulu.add(medicationService);
					}
					//End Riwayat Dahulu
					dataconsultantNote.put("Riwayat penyakit dahulu", DatariwayatDahulu);
					dataconsultantNote.put("Riwayat Pekerjaan", !Objects.isNull(m[13]) ? m[13].toString() : "" );
					/*Riwayat Allergi*/
					/*SQLQuery<Object[]> riwayatAllergiData = (SQLQuery<Object[]>) session.getNamedQuery("getAllergiSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
					List <Object[]> riwayatAllergi = riwayatAllergiData.list();
					List<Map<String, String>> DataRiwayatAllergi = new LinkedList<Map<String, String>>();
					for (Object[] ra : riwayatAllergi) {
						Map<String, String> dataAllergi = new LinkedHashMap<String, String>();
						dataAllergi.put("encounter_id", !Objects.isNull(ra[0]) ? ra[0].toString() : "");
						dataAllergi.put("allergi_code", !Objects.isNull(ra[3]) ? ra[3].toString() : "");
						dataAllergi.put("allergi_name", !Objects.isNull(ra[4]) ? ra[4].toString() : "");
						dataAllergi.put("allergi_category", !Objects.isNull(ra[5]) ? ra[5].toString() : "");
						DataRiwayatAllergi.add(dataAllergi);
					}
					dataconsultantNote.put("Riwayat Alergi", DataRiwayatAllergi );*//*End Riwayat Alergi*/
					dataconsultantNote.put("Riwayat Minum Obat", !Objects.isNull(m[15]) ? m[15].toString() : "" );
					dataconsultantNote.put("Obat yang sedang dikonsumsi", !Objects.isNull(m[16]) ? m[16].toString() : "" );
					dataconsultantNote.put("Keadaan Umum", !Objects.isNull(m[17]) ? m[17].toString() : "" );
					dataconsultantNote.put("Kesadaran", !Objects.isNull(m[18]) ? m[18].toString() : "" );
					dataconsultantNote.put("GCS", !Objects.isNull(m[19]) ? m[19].toString() : "" );
					/*Tanda Vital Remove*/
					/*SQLQuery<Object[]> dataVitalSignCNData = (SQLQuery<Object[]>) session.getNamedQuery("getvitalSignSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
					List <Object[]> dataVitalSignCN = dataVitalSignCNData.list();
					List<Map<String, String>> DataVitalSignCN = new LinkedList<Map<String, String>>();
					for (Object[] vs : dataVitalSignCN) {
						Map<String, String> mapDataVitalSign = new LinkedHashMap<String, String>();
						mapDataVitalSign.put("encounter_id", !Objects.isNull(vs[0]) ? vs[0].toString() : "");
						mapDataVitalSign.put("code_vitalsign",!Objects.isNull(vs[1]) ? vs[1].toString() : "" );
						mapDataVitalSign.put("description",!Objects.isNull(vs[0]) ?vs[0].toString() : "" );
						mapDataVitalSign.put("value_unit",!Objects.isNull(vs[2]) ?vs[2].toString() : "" );
						mapDataVitalSign.put("value",!Objects.isNull(vs[3]) ? vs[3].toString() : "" );
						mapDataVitalSign.put("employee_code",!Objects.isNull(vs[4]) ? vs[4].toString() : "" );
						mapDataVitalSign.put("employee_name",!Objects.isNull(vs[5]) ? vs[5].toString() : "" );
						mapDataVitalSign.put("time_check_vitalsign",!Objects.isNull(vs[6]) ? vs[6].toString() : "" );
						DataVitalSignCN.add(mapDataVitalSign);
					}
					dataconsultantNote.put("Tanda Vital", DataVitalSignCN);*//*End Tanda Vital*/
					//Pemeriksaan Status Generalis
					SQLQuery<Object[]> pemeriksaanStatusGeneralisData = (SQLQuery<Object[]>) session.getNamedQuery("getListExaminationSatuSehat")
							.setParameter("patientId", ptb[1].toString()).setParameter("visitId", ptb[2].toString());
					List <Object[]> DatapemeriksaanStatusGeneralisData = pemeriksaanStatusGeneralisData.list();
					List<Map<String, String>> PemeriksaanStatusGeneralis = new LinkedList<Map<String, String>>();
					for (Object[] ex : DatapemeriksaanStatusGeneralisData) {
						Map<String, String> datapemeriksaanStatusGeneralis = new LinkedHashMap<String, String>();
						datapemeriksaanStatusGeneralis.put("Bagian Tubuh",!Objects.isNull(ex) ? ex[0].toString() : "" );
						datapemeriksaanStatusGeneralis.put("Keterangan",!Objects.isNull(ex[1]) ? ex[1].toString() : "" );
						PemeriksaanStatusGeneralis.add(datapemeriksaanStatusGeneralis);
					}
					//End Pemeriksaan Status Generalis
					dataconsultantNote.put("Pemeriksaan Status Generalis", PemeriksaanStatusGeneralis );
					//Pemeriksaan Penunjang 
					SQLQuery<Object[]> pemeriksaanPenunjangData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServicePemeriksaanPenunjangSatuSehat")
							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
					List <Object[]> pemeriksaanPenunjang = pemeriksaanPenunjangData.list();
					List<Map<String, String>> DatapemeriksaanPenunjang = new LinkedList<Map<String, String>>();
					for (Object[] pp : pemeriksaanPenunjang) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("service_name",!Objects.isNull(pp[1]) ? pp[1].toString() : "" );
						DatapemeriksaanPenunjang.add(medicationService);
					}
					//End Pemeriksaan Penunjang
					dataconsultantNote.put("Pemeriksaan Penunjang", DatapemeriksaanPenunjang );
					/*Data Diagnosis Kerja*/
					SQLQuery<Object[]> diagnosisKerjaData = (SQLQuery<Object[]>) session.getNamedQuery("getMedisPrimarySatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
					List <Object[]> diagnosisKerja = diagnosisKerjaData.list();
					List<Map<String, String>> DatadiagnosisKerja = new LinkedList<Map<String, String>>();
					for (Object[] dk : diagnosisKerja) {
						Map<String, String> dataMedis = new LinkedHashMap<String, String>();
						dataMedis.put("primary_desc", !Objects.isNull(dk[8]) ? dk[8].toString() : "-" );
						dataMedis.put("primary", !Objects.isNull(dk[4]) ? dk[4].toString() : "-" );
						DatadiagnosisKerja.add(dataMedis);
					}
					/*End Diagnosis Kerja*/
					dataconsultantNote.put("Diagnosis Kerja", DatadiagnosisKerja );
					/*Data Diagnosis Banding*/
					SQLQuery<Object[]> diagnosisBandingData = (SQLQuery<Object[]>) session.getNamedQuery("getMedisSecondarySatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
					List <Object[]> diagnosisBanding = diagnosisBandingData.list();
					List<Map<String, String>> DatadiagnosisBanding = new LinkedList<Map<String, String>>();
					for (Object[] db : diagnosisBanding) {
						Map<String, String> dataMedis = new LinkedHashMap<String, String>();
						dataMedis.put("secondary_desc", !Objects.isNull(db[8]) ? db[8].toString() : "-" );
						dataMedis.put("secondary", !Objects.isNull(db[4]) ? db[4].toString() : "-" );
						DatadiagnosisBanding.add(dataMedis);
					}
					/*End Diagnosis Banding*/
					dataconsultantNote.put("Diagnosis Banding", DatadiagnosisBanding);
					dataconsultantNote.put("Permasalahan Medis", !Objects.isNull(m[25]) ? m[25].toString() : "" );
					dataconsultantNote.put("Rencana Asuhan dan Terapi (Standing order )", !Objects.isNull(m[26]) ? m[26].toString() : "" );
					dataconsultantNote.put("Rencana Asuhan dan Terapi (Standing order ) Kolaborasi/konsultasi", !Objects.isNull(m[27]) ? m[27].toString() : "" );
					dataconsultantNote.put("Jenis Terapi", !Objects.isNull(m[28]) ? m[28].toString() : "" );
					//Terapi
					SQLQuery<Object[]> dataTerapiData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationSatuSehat")
							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
					List <Object[]> dataTerapi = dataTerapiData.list();
					List<Map<String, Object>> DataTerapi = new LinkedList<Map<String, Object>>();
					for (Object[] dt : dataTerapi) {
						Map<String, Object> medicationTanggal = new LinkedHashMap<String, Object>();
						medicationTanggal.put("obat_name",!Objects.isNull(dt[2]) ? dt[2].toString() : "" );
						DataTerapi.add(medicationTanggal);
					}
					//End Terapi
					dataconsultantNote.put("Terapi", DataTerapi );
					//Tindakan 
					SQLQuery<Object[]> dataTindakanData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServiceTindakanSatuSehat")
							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
					List <Object[]> dataTindakan = dataTindakanData.list();
					List<Map<String, String>> DataTindakan = new LinkedList<Map<String, String>>();
					for (Object[] pp : dataTindakan) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("service_name",!Objects.isNull(pp[1]) ? pp[1].toString() : "" );
						DataTindakan.add(medicationService);
					}
					//End Tindakan
					dataconsultantNote.put("Tindakan", DataTindakan);
					//Konsultasi
					SQLQuery<Object[]> dataKonsultasiData = (SQLQuery<Object[]>) session.getNamedQuery("getListCrossConsultationSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("visitId", ptb[2].toString());
					List <Object[]> dataKonsultasi = dataKonsultasiData.list();
					List<Map<String, String>> DataKonsultasi = new LinkedList<Map<String, String>>();
					for (Object[] dk : dataKonsultasi) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("consultation_type",!Objects.isNull(dk[0]) ? dk[0].toString() : "" );
						medicationService.put("doctor_name",!Objects.isNull(dk[1]) ? dk[1].toString() : "" );
						DataKonsultasi.add(medicationService);
					}
					//End Konsultasi
					dataconsultantNote.put("Penunjang", DataKonsultasi);
					//Pemeriksaan Penunjang Lanjutan
//					SQLQuery<Object[]> pemeriksaanPenunjangLanjutanData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServiceSatuSehat")
//							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptb[1].toString()).setParameter("encounterId", ptb[0].toString());
//					List <Object[]> pemeriksaanPenunjangLanjutan = pemeriksaanPenunjangLanjutanData.list();
						List<Map<String, String>> DatapemeriksaanPenunjangLanjutan = new LinkedList<Map<String, String>>();
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("service_name", "" );
						DatapemeriksaanPenunjangLanjutan.add(medicationService);
					//End Pemeriksaan Penunjang Lanjutan
					dataconsultantNote.put("Pemeriksaan Penunjang Lanjutan", DatapemeriksaanPenunjangLanjutan);
					dataconsultantNote.put("Hasil yang diharapkan / Sasaran Rencana Asuhan", !Objects.isNull(m[33]) ? m[33].toString() : "" );
					dataconsultantNote.put("Edukasi awal, tentang diagnosis, rencana, tujuan terapi kepada- pasien - keluarga pasien", !Objects.isNull(m[34]) ? m[34].toString() : "" );
					dataconsultantNote.put("Rencana Pulang", !Objects.isNull(m[35]) ? m[35].toString() : "" );
					dataconsultantNote.put("Tanggal dan waktu selesai assesmen", !Objects.isNull(m[36]) ? m[36].toString() : "" );
					DataConsultantNote.add(dataconsultantNote);
				}/*End Consultant Note*/
				
				/*Data Discharge Summary*/
//				List<Object[]> dischargeSummary = baseApplicationService.find("getdischargeSummarySatuSehat", new Object[]{ptb[1].toString(), ptb[2].toString()});	
//				for (Object[] m : dischargeSummary) {
					Map<String, Object> datadischargeSummary = new LinkedHashMap<String, Object>();
					datadischargeSummary.put("encounter_id",  !Objects.isNull( ptb[0].toString()) ?  ptb[0].toString() : "" );
					datadischargeSummary.put("Indikasi_Rawat_Inap", "" );
					datadischargeSummary.put("obat_dibawa_pulang","");
					datadischargeSummary.put("kondisi_pasien",  "" );
					datadischargeSummary.put("mobilisasi_saat_pulang", "" );
					datadischargeSummary.put("alat_kesehatan_yang_terpasang",  "" );
					datadischargeSummary.put("instruksi_tindak_lanjut",  "" );
					datadischargeSummary.put("rencana_kontrol",  "" );
					datadischargeSummary.put("informasi_Perawatan_dirumah",  "" );
					datadischargeSummary.put("rencana_pemeriksaan_penunjang",  "" );
					datadischargeSummary.put("kebutuhan_edukasi","" );
					datadischargeSummary.put("pertolongan_mendesak",  "" );
					datadischargeSummary.put("remarks", "" );
					datadischargeSummary.put("penyakit_berhubungan_dengan", "" );
					DataDischargeSummary.add(datadischargeSummary);
//				}/*End Discharge Summary*/
				dataPenunjangMedis.put("Data_Medication", MedicationTanggal);
				dataPenunjangMedis.put("Medication_Service", DataMedicationService);
				dataPemeriksaanMedis.put("Lab_Result", LabResult);
				dataPemeriksaanMedis.put("Radiology_Result", RadiologyResult);
				listResponse.put("Data_Pasien", DataPasien);
				listResponse.put("Data_Encounter", DataEncounter);
				listResponse.put("Data_Medis", DataMedis);
				listResponse.put("Data_Billing", DataBilling);
				listResponse.put("Penunjang_Medis", dataPenunjangMedis);
				listResponse.put("Pemeriksaan", dataPemeriksaanMedis);
				listResponse.put("Vital_Sign", ListDatavitalSign);
				listResponse.put("Allergi", DataAllergy);
				listResponse.put("Diet", DataDiet);
				listResponse.put("Clinical_Note", DataClinicalNote);
				listResponse.put("Consultation_Note", DataConsultantNote);
				listResponse.put("Discharge_Summary", DataDischargeSummary);
				/*End Penunjang Medis*/
			listResponseHeader.add(listResponse);
			} catch (Exception e) {
		            continue; // Melanjutkan ke iterasi berikutnya
		        }
			}
			
			for (Object[] ptbip : patientIp) {
				try {
				Map<String, Object> listResponse = new LinkedHashMap<String, Object>();
				Map<String, Object> dataPenunjangMedis = new LinkedHashMap<String, Object>();
				Map<String, Object> dataPemeriksaanMedis = new LinkedHashMap<String, Object>();
//				Map<String, Object> medication = new LinkedHashMap<String, Object>();
				List<Map<String, String>> DataPasien = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataEncounter = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataMedis = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataBilling = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> MedicationTanggal = new LinkedList<Map<String, Object>>();
				List<Map<String, String>> DataMedicationService = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> LabResult = new LinkedList<Map<String, Object>>();
				List<Map<String, String>> RadiologyResult = new LinkedList<Map<String, String>>();
//				List<Map<String, String>> DatavitalSign = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> ListDatavitalSign = new LinkedList<Map<String, Object>>();
				List<Map<String, String>> DataAllergy = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataDiet = new LinkedList<Map<String, String>>();
				List<Map<String, String>> DataClinicalNote = new LinkedList<Map<String, String>>();
				List<Map<String, Object>> DataConsultantNote = new LinkedList<Map<String, Object>>();
				List<Map<String, Object>> DataDischargeSummary = new LinkedList<Map<String, Object>>();
			    /*Patient*/
				SQLQuery<Object[]> PatientData = (SQLQuery<Object[]>) session.getNamedQuery("getPatientIPSatuSehat")
						.setParameter("patientId", ptbip[1].toString());
				List <Object[]> Patient = PatientData.list();
			    for (Object[] m : Patient) {
			    	Map<String, String> dataPasien = new LinkedHashMap<String, String>();
			    	dataPasien.put("Patient_Name", !Objects.isNull(m[2]) ? m[2].toString() : "");
			        dataPasien.put("MRN", !Objects.isNull(m[3]) ? m[3].toString() : "");
			        dataPasien.put("Patient_Gender", !Objects.isNull(m[4]) ? m[4].toString() : "");
			        dataPasien.put("Patient_DOB", !Objects.isNull(m[5]) ?  m[5].toString() : "");
			        dataPasien.put("Patient_NIK", !Objects.isNull(m[6]) ? m[6].toString() : "");
			        dataPasien.put("Patient_BPJS_Card_Number", !Objects.isNull(m[7]) ? m[7].toString() : "");
			        dataPasien.put("Patient_Phone_Number", !Objects.isNull(m[8]) ? m[8].toString() : "");
			        dataPasien.put("Marital_Status", !Objects.isNull(m[9]) ? m[9].toString() : "");
			        dataPasien.put("Adress", !Objects.isNull(m[10]) ? m[10].toString() : "");
			        dataPasien.put("State", !Objects.isNull(m[11]) ? m[11].toString() : "");
			        dataPasien.put("District", !Objects.isNull(m[12]) ? m[12].toString() : "");
			        dataPasien.put("City", !Objects.isNull(m[13]) ? m[13].toString() : "");
			        dataPasien.put("Village", !Objects.isNull(m[14]) ? m[14].toString() : "");
			        dataPasien.put("Zip_Code", !Objects.isNull(m[15]) ? m[15].toString() : "");
			        DataPasien.add(dataPasien);
			    }
			    /*End Patient*/
			    
				/*Data Encounter*/
			    SQLQuery<Object[]> EncounterData = (SQLQuery<Object[]>) session.getNamedQuery("getEncounterIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> Encounter = EncounterData.list();
				for (Object[] m : Encounter) {
					Map<String, String> dataEncounter = new LinkedHashMap<String, String>();
					dataEncounter.put("encounter_branch_id",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataEncounter.put("MRN",!Objects.isNull(m[13]) ? m[13].toString() : "" );
					dataEncounter.put("visit_type",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataEncounter.put("visite_date",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataEncounter.put("encounter_id",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					dataEncounter.put("encounter_type",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					dataEncounter.put("encounter_time",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					dataEncounter.put("start_consult_time",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					dataEncounter.put("end_consult_time",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					dataEncounter.put("department_name",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					dataEncounter.put("department_code",!Objects.isNull(m[10]) ? m[10].toString() : "" );
					dataEncounter.put("unit_name",!Objects.isNull(m[11]) ? m[11].toString() : "" );
					dataEncounter.put("unit_id",!Objects.isNull(m[12]) ? m[12].toString() : "" );
					dataEncounter.put("nosep",!Objects.isNull(m[14]) ? m[14].toString() : "" );
					DataEncounter.add(dataEncounter);
				}
				/*End Encounter*/
				
				/*Data Medis*/
				SQLQuery<Object[]> MedisData = (SQLQuery<Object[]>) session.getNamedQuery("getMedisIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> Medis = MedisData.list();
				for (Object[] m : Medis) {
					Map<String, String> dataMedis = new LinkedHashMap<String, String>();
					dataMedis.put("encounter_id",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataMedis.put("consultant_name",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataMedis.put("consultant_NIK",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataMedis.put("consultant_employee_code",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					dataMedis.put("diagnosis_icd10_code",!Objects.isNull(m[4]) ? m[4].toString() : "-" );
					if (!(m[5].toString().equals("-"))) {
						dataMedis.put("primary", !Objects.isNull(m[5]) ? m[5].toString() : "-" );
					}
					if (!(m[6].toString().equals("-"))) {
						dataMedis.put("secondary", !Objects.isNull(m[6]) ? m[6].toString() : "-" );
					}
					if (!(m[7].toString().equals("-"))) {
						dataMedis.put("co-morbid", !Objects.isNull(m[7]) ? m[7].toString() : "-" );
					}
					dataMedis.put("diagnosis_icd10_description",!Objects.isNull(m[8]) ? m[8].toString() : "-" );
					if (!(m[9].toString().equals("-"))) {
						dataMedis.put("primary_desc", !Objects.isNull(m[9]) ? m[9].toString() : "-" );
					}
					if (!(m[10].toString().equals("-"))) {
						dataMedis.put("secondary_desc", !Objects.isNull(m[10]) ? m[10].toString() : "-" );
					}
					if (!(m[11].toString().equals("-"))) {
						dataMedis.put("co-morbid_desc", !Objects.isNull(m[11]) ? m[11].toString() : "-" );
					}
					DataMedis.add(dataMedis);
				}
				/*End Medis*/
				
				/*Data Billing*/
				SQLQuery<Object[]> BillingData = (SQLQuery<Object[]>) session.getNamedQuery("getBillingIPSatuSehat")
						.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> Billing = BillingData.list();
				for (Object[] m : Billing) {
					Map<String, String> dataBilling = new LinkedHashMap<String, String>();
					dataBilling.put("encounter_id",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					dataBilling.put("no_Invoice",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataBilling.put("datetime",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataBilling.put("group_id",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					dataBilling.put("group_code",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					dataBilling.put("billing_group_name",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					dataBilling.put("service_code",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					dataBilling.put("service_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataBilling.put("order_no",!Objects.isNull(m[10]) ? m[10].toString() : "" );
					dataBilling.put("tariff",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					dataBilling.put("plan",!Objects.isNull(m[4]) ? m[4].toString() : "");
					DataBilling.add(dataBilling);
				}
				/*End Billing*/
				
				/*Penunjang Medis*/
				SQLQuery<Object[]> ListMedicationData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationIPSatuSehat")
						.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> ListMedication = ListMedicationData.list();
				SQLQuery<Object[]> ListMedicationServiceData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServiceIPSatuSehat")
						.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> ListMedicationService = ListMedicationServiceData.list();
				SQLQuery<Object[]> ListLabResultData = (SQLQuery<Object[]>) session.getNamedQuery("getListLabResultIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> ListLabResult = ListLabResultData.list();
				SQLQuery<Object[]> ListRadiologyResultData = (SQLQuery<Object[]>) session.getNamedQuery("getListRadiologyResultIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> ListRadiologyResult = ListRadiologyResultData.list();
								
				//Medication
				for (Object[] m : ListMedication) {
					Map<String, Object> medicationTanggal = new LinkedHashMap<String, Object>();
					medicationTanggal.put("encounter_id",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					medicationTanggal.put("resep_no",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					medicationTanggal.put("tanggal",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					medicationTanggal.put("obat_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					medicationTanggal.put("obat_code",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					medicationTanggal.put("price",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					medicationTanggal.put("qty",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					medicationTanggal.put("instruction",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					medicationTanggal.put("mixture",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					
					SQLQuery<Object[]> ListMixtureData = (SQLQuery<Object[]>) session.getNamedQuery("getListMixtureIPSatuSehat")
							.setParameter("ordersId", m[5].toString());
					List <Object[]> ListMixture = ListMixtureData.list();
					List<Map<String, String>> DataMedicationMixture = new LinkedList<Map<String, String>>();
					if (m[6].toString().equals("Y")) {
						for (Object[] lm : ListMixture) {
							Map<String, String> medicationMixture = new LinkedHashMap<String, String>();
							medicationMixture.put("obat_name",!Objects.isNull(lm[0]) ? lm[0].toString() : "" );
							medicationMixture.put("obat_code",!Objects.isNull(lm[3]) ?lm[3].toString() : "" );
							medicationMixture.put("price",!Objects.isNull(lm[4]) ?lm[4].toString() : "" );
							medicationMixture.put("qty",!Objects.isNull(lm[1]) ? lm[1].toString() : "" );
							medicationMixture.put("instruction",!Objects.isNull(lm[2]) ? lm[2].toString() : "" );
							DataMedicationMixture.add(medicationMixture);
						}
					}
					medicationTanggal.put("component", DataMedicationMixture);
					MedicationTanggal.add(medicationTanggal);
				}
				//End
			
				//Medication Service
				for (Object[] m : ListMedicationService) {
					Map<String, String> medicationService = new LinkedHashMap<String, String>();
					medicationService.put("encounter_id",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					medicationService.put("tanggal",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					medicationService.put("service_code",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					medicationService.put("service_name",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					medicationService.put("order_no",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					medicationService.put("tariff",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					DataMedicationService.add(medicationService);
				}
				//End
				
				//Lab Result
				for (Object[] m : ListLabResult) {
					Map<String, Object> labResultPemeriksaan = new LinkedHashMap<String, Object>();
					labResultPemeriksaan.put("encounter_id",!Objects.isNull(m[10]) ? m[10].toString() : "" );
					labResultPemeriksaan.put("order_no",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					labResultPemeriksaan.put("service_code",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					labResultPemeriksaan.put("price",!Objects.isNull(m[11]) ? m[11].toString() : "" );
					labResultPemeriksaan.put("tanggal",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					labResultPemeriksaan.put("service_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					labResultPemeriksaan.put("nilai_normal",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					labResultPemeriksaan.put("result",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					labResultPemeriksaan.put("satuan",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					labResultPemeriksaan.put("Tanggal_Certified",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					labResultPemeriksaan.put("Certifiying_Consultant_BPJS_Code",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					labResultPemeriksaan.put("Certifiying_Consultant_Name",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					labResultPemeriksaan.put("Certifiying_Employee_Code",!Objects.isNull(m[14]) ? m[14].toString() : "" );
					labResultPemeriksaan.put("Specimen_code",!Objects.isNull(m[12]) ? m[12].toString() : "" );
					labResultPemeriksaan.put("Specimen_name",!Objects.isNull(m[13]) ? m[13].toString() : "" );
					labResultPemeriksaan.put("Parameter",!Objects.isNull(m[15]) ? m[15].toString() : "" );
					/*Parameter List*/
					SQLQuery<Object[]> labResultParameterData = (SQLQuery<Object[]>) session.getNamedQuery("getLabResultParameterIPSatuSehat")
							.setParameter("labresultId", m[16].toString());
					List <Object[]> labResultParameter = labResultParameterData.list();
					List<Map<String, String>> DatalabResultParameter = new LinkedList<Map<String, String>>();
					for (Object[] lrp : labResultParameter) {
						Map<String, String> labParameter = new LinkedHashMap<String, String>();
						labParameter.put("parameter_name", !Objects.isNull(lrp[0]) ? lrp[0].toString() : "");
						labParameter.put("unit", !Objects.isNull(lrp[1]) ? lrp[1].toString() : "");
						labParameter.put("result_range", !Objects.isNull(lrp[2]) ? lrp[2].toString() : "");
						labParameter.put("result_value", !Objects.isNull(lrp[3]) ? lrp[3].toString() : "");
						DatalabResultParameter.add(labParameter);
					}/*End Parameter List*/
					labResultPemeriksaan.put("Parameter_List", DatalabResultParameter );
					LabResult.add(labResultPemeriksaan);
				}//End
			
				//Radiology Result
				for (Object[] m : ListRadiologyResult) {
					Map<String, String> radiologyResultPemeriksaan = new LinkedHashMap<String, String>();
					radiologyResultPemeriksaan.put("encounter_id",!Objects.isNull(m[7]) ? m[7].toString() : "" );
					radiologyResultPemeriksaan.put("order_no",!Objects.isNull(m[0]) ? m[0].toString() : "" );
					radiologyResultPemeriksaan.put("service_code",!Objects.isNull(m[6]) ? m[6].toString() : "" );
					radiologyResultPemeriksaan.put("price",!Objects.isNull(m[8]) ? m[8].toString() : "" );
					radiologyResultPemeriksaan.put("tanggal",!Objects.isNull(m[1]) ? m[1].toString() : "" );
					radiologyResultPemeriksaan.put("service_name",!Objects.isNull(m[2]) ? m[2].toString() : "" );
					radiologyResultPemeriksaan.put("result",!Objects.isNull(m[3]) ? m[3].toString() : "" );
					radiologyResultPemeriksaan.put("Certifiying_Consultant_BPJS_Code",!Objects.isNull(m[4]) ? m[4].toString() : "" );
					radiologyResultPemeriksaan.put("Certifiying_Consultant_Name",!Objects.isNull(m[5]) ? m[5].toString() : "" );
					radiologyResultPemeriksaan.put("Certifiying_Employee_Code",!Objects.isNull(m[9]) ? m[9].toString() : "" );
					RadiologyResult.add(radiologyResultPemeriksaan);
				}//End
				
				/*Data Vital Sign*/
				Map<String, Object> datavitalSign = new LinkedHashMap<String, Object>();
				List<Map<String, String>> DataVitalSign = new LinkedList<Map<String, String>>();
				datavitalSign.put("encounter_id",!Objects.isNull(ptbip[0].toString()) ? ptbip[0].toString() : "" );
				SQLQuery<Object[]> dataVitalSignData = (SQLQuery<Object[]>) session.getNamedQuery("getvitalSignIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> dataVitalSign = dataVitalSignData.list();
				for (Object[] vs : dataVitalSign) {
					Map<String, String> mapDataVitalSign = new LinkedHashMap<String, String>();
					mapDataVitalSign.put("code_vitalsign",!Objects.isNull(vs[1]) ? vs[1].toString() : "" );
					mapDataVitalSign.put("description",!Objects.isNull(vs[0]) ?vs[0].toString() : "" );
					mapDataVitalSign.put("value_unit",!Objects.isNull(vs[2]) ?vs[2].toString() : "" );
					mapDataVitalSign.put("value",!Objects.isNull(vs[3]) ? vs[3].toString() : "" );
					mapDataVitalSign.put("employee_code",!Objects.isNull(vs[4]) ? vs[4].toString() : "" );
					mapDataVitalSign.put("employee_name",!Objects.isNull(vs[5]) ? vs[5].toString() : "" );
					mapDataVitalSign.put("time_check_vitalsign",!Objects.isNull(vs[6]) ? vs[6].toString() : "" );
					DataVitalSign.add(mapDataVitalSign);
				}
				datavitalSign.put("detail_vitalsign", DataVitalSign);
				ListDatavitalSign.add(datavitalSign);
				/*End Data Vital Sign*/
				
				/*Data Allergi*/
				SQLQuery<Object[]> AllergiData = (SQLQuery<Object[]>) session.getNamedQuery("getAllergiIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> Allergi = AllergiData.list();
				for (Object[] m : Allergi) {
					Map<String, String> dataAllergi = new LinkedHashMap<String, String>();
					dataAllergi.put("encounter_id", !Objects.isNull(m[0]) ? m[0].toString() : "");
					dataAllergi.put("allergi_code", !Objects.isNull(m[3]) ? m[3].toString() : "");
					dataAllergi.put("allergi_name", !Objects.isNull(m[4]) ? m[4].toString() : "");
					dataAllergi.put("allergi_category", !Objects.isNull(m[5]) ? m[5].toString() : "");
					DataAllergy.add(dataAllergi);
				}/*End Allergi*/
				
				/*Data Diet*/
				SQLQuery<Object[]> DietiData = (SQLQuery<Object[]>) session.getNamedQuery("getDietIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString());
				List <Object[]> Diet = DietiData.list();
				if(!Objects.isNull(Diet)){
					for (Object[] m : Diet) {
						Map<String, String> dataDiet = new LinkedHashMap<String, String>();
						dataDiet.put("encounter_id", !Objects.isNull( ptbip[0].toString()) ?  ptbip[0].toString() : "" );
						dataDiet.put("diet_note",!Objects.isNull(m[0]) ? m[0].toString() : "" );
						dataDiet.put("datetimeinput",!Objects.isNull(m[1]) ? m[1].toString() : "" );
						DataDiet.add(dataDiet);
					}
				}else{
					Map<String, String> dataDiet = new LinkedHashMap<String, String>();
					dataDiet.put("encounter_id", !Objects.isNull( ptbip[0].toString()) ?  ptbip[0].toString() : "" );
					dataDiet.put("diet_note", "" );
					dataDiet.put("datetimeinput", "" );
					DataDiet.add(dataDiet);
				}/*End Diet*/
				
				/*Data Clinical Note*/
				SQLQuery<Object[]> clinicalNoteData = (SQLQuery<Object[]>) session.getNamedQuery("getclinicalNoteIPSatuSehat")
						.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
				List <Object[]> clinicalNote = clinicalNoteData.list();
				if(!Objects.isNull(clinicalNote)){
					for (Object[] m : clinicalNote) {
						Map<String, String> dataclinicalNote = new LinkedHashMap<String, String>();
						dataclinicalNote.put("encounter_id", !Objects.isNull(m[0]) ? m[0].toString() : "" );
						dataclinicalNote.put("subjective", !Objects.isNull(m[1]) ? m[1].toString() : "" );
						dataclinicalNote.put("objective", "" );
						dataclinicalNote.put("assessment", "" );
						dataclinicalNote.put("plan", "" );
						dataclinicalNote.put("datetimeinput",!Objects.isNull(m[9]) ? m[9].toString() : "" );
						DataClinicalNote.add(dataclinicalNote);
					}
				}else{
					Map<String, String> dataclinicalNote = new LinkedHashMap<String, String>();
					dataclinicalNote.put("encounter_id", !Objects.isNull( ptbip[0].toString()) ?  ptbip[0].toString() : "" );
					dataclinicalNote.put("subjective", "" );
					dataclinicalNote.put("objective", "" );
					dataclinicalNote.put("assessment", "" );
					dataclinicalNote.put("plan", "" );
					dataclinicalNote.put("datetimeinput", "" );
					DataClinicalNote.add(dataclinicalNote);
				}/*End Clinical Note*/
				
				/*Data Consultant Note*/
				SQLQuery<Object[]> consultantNoteData = (SQLQuery<Object[]>) session.getNamedQuery("getconsultantNoteIPSatuSehat")
						.setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
				List <Object[]> consultantNote = consultantNoteData.list();
				for (Object[] m : consultantNote) {
					Map<String, Object> dataconsultantNote = new LinkedHashMap<String, Object>();
					dataconsultantNote.put("encounter_id", !Objects.isNull( ptbip[0].toString()) ?  ptbip[0].toString() : "" );
					dataconsultantNote.put("Nama Pasien", !Objects.isNull(m[0]) ? m[0].toString() : "" );
					dataconsultantNote.put("No. Rekam Medis", !Objects.isNull(m[1]) ? m[1].toString() : "" );
					dataconsultantNote.put("Nama Orang tua/Suami/Istri", !Objects.isNull(m[2]) ? m[2].toString() : "" );
					dataconsultantNote.put("Tanggal Lahir", !Objects.isNull(m[3]) ? m[3].toString() : "" );
					dataconsultantNote.put("Jenis Kelamin", !Objects.isNull(m[4]) ? m[4].toString() : "" );
					dataconsultantNote.put("Tanggal Masuk RS", !Objects.isNull(m[5]) ? m[5].toString() : "" );
					dataconsultantNote.put("Kelas Kamar", !Objects.isNull(m[6]) ? m[6].toString() : "" );
					dataconsultantNote.put("Waktu tanggal,jam pasien tiba di ruangan", !Objects.isNull(m[7]) ? m[7].toString() : "" );
					dataconsultantNote.put("Waktu tanggal,jam pasien mulai assesmen", !Objects.isNull(m[8]) ? m[8].toString() : "" );
					//Keluhan Utama 
					SQLQuery<Object[]> keluhanUtamaData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitDahuludanSekarangIPSatuSehat")
							.setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
					List <Object[]> keluhanUtama = keluhanUtamaData.list();
					List<Map<String, String>> DatariwayatKeluhan = new LinkedList<Map<String, String>>();
					for (Object[] rd : keluhanUtama) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("keluhan_utama",!Objects.isNull(rd[0]) ? rd[0].toString() : "" );
						DatariwayatKeluhan.add(medicationService);
					}
					//End Keluhan Utama
					dataconsultantNote.put("Keluhan Utama", DatariwayatKeluhan);
					//Riwayat Sekarang 
					SQLQuery<Object[]> riwayatSekarangData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitDahuludanSekarangIPSatuSehat")
							.setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
					List <Object[]> riwayatSekarang = riwayatSekarangData.list();
					List<Map<String, String>> DatariwayatSekarang = new LinkedList<Map<String, String>>();
					for (Object[] rs : riwayatSekarang) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("riwayat_penyakit_sekarang",!Objects.isNull(rs[1]) ? rs[1].toString() : "" );
						DatariwayatSekarang.add(medicationService);
					}
					//End Riwayat Sekarang
					dataconsultantNote.put("Riwayat penyakit sekarang", DatariwayatSekarang);
					//Riwayat Keluarga 
					SQLQuery<Object[]> riwayatKeluargaData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitKeluargaIPSatuSehat")
							.setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
					List <Object[]> riwayatKeluarga = riwayatKeluargaData.list();
					List<Map<String, String>> DatariwayatKeluarga = new LinkedList<Map<String, String>>();
					for (Object[] rk : riwayatKeluarga) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("relasi",!Objects.isNull(rk[0]) ? rk[0].toString() : "" );
						medicationService.put("riwayat_penyakit_keluarga",!Objects.isNull(rk[1]) ? rk[1].toString() : "" );
						DatariwayatKeluarga.add(medicationService);
					}
					//End Riwayat Keluarga
					dataconsultantNote.put("Riwayat penyakit keluarga", DatariwayatKeluarga);
					//Riwayat Dahulu 
					SQLQuery<Object[]> riwayatDahuluData = (SQLQuery<Object[]>) session.getNamedQuery("getListRiwayatPenyakitDahuluIPSatuSehat")
							.setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
					List <Object[]> riwayatDahulu = riwayatDahuluData.list();
					List<Map<String, String>> DatariwayatDahulu = new LinkedList<Map<String, String>>();
					for (Object[] rd : riwayatDahulu) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("riwayat_penyakit_dahulu",!Objects.isNull(rd[0]) ? rd[0].toString() : "" );
						DatariwayatDahulu.add(medicationService);
					}
					//End Riwayat Dahulu
					dataconsultantNote.put("Riwayat penyakit dahulu", DatariwayatDahulu);
					dataconsultantNote.put("Riwayat Pekerjaan", !Objects.isNull(m[13]) ? m[13].toString() : "" );
					/*Riwayat Allergi*/
					/*SQLQuery<Object[]> riwayatAllergiData = (SQLQuery<Object[]>) session.getNamedQuery("getAllergiIPSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
					List <Object[]> riwayatAllergi = riwayatAllergiData.list();
					List<Map<String, String>> DataRiwayatAllergi = new LinkedList<Map<String, String>>();
					for (Object[] ra : riwayatAllergi) {
						Map<String, String> dataAllergi = new LinkedHashMap<String, String>();
						dataAllergi.put("encounter_id", !Objects.isNull(ra[0]) ? ra[0].toString() : "");
						dataAllergi.put("allergi_code", !Objects.isNull(ra[3]) ? ra[3].toString() : "");
						dataAllergi.put("allergi_name", !Objects.isNull(ra[4]) ? ra[4].toString() : "");
						dataAllergi.put("allergi_category", !Objects.isNull(ra[5]) ? ra[5].toString() : "");
						DataRiwayatAllergi.add(dataAllergi);
					}
					dataconsultantNote.put("Riwayat Alergi", DataRiwayatAllergi );*//*End Riwayat Alergi*/
					dataconsultantNote.put("Riwayat Minum Obat", !Objects.isNull(m[15]) ? m[15].toString() : "" );
					dataconsultantNote.put("Obat yang sedang dikonsumsi", !Objects.isNull(m[16]) ? m[16].toString() : "" );
					dataconsultantNote.put("Keadaan Umum", !Objects.isNull(m[17]) ? m[17].toString() : "" );
					dataconsultantNote.put("Kesadaran", !Objects.isNull(m[18]) ? m[18].toString() : "" );
					dataconsultantNote.put("GCS", !Objects.isNull(m[19]) ? m[19].toString() : "" );
					/*Tanda Vital Remove*/
					/*SQLQuery<Object[]> dataVitalSignCNData = (SQLQuery<Object[]>) session.getNamedQuery("getvitalSignIPSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
					List <Object[]> dataVitalSignCN = dataVitalSignCNData.list();
					List<Map<String, String>> DataVitalSignCN = new LinkedList<Map<String, String>>();
					for (Object[] vs : dataVitalSignCN) {
						Map<String, String> mapDataVitalSign = new LinkedHashMap<String, String>();
						mapDataVitalSign.put("encounter_id", !Objects.isNull(vs[0]) ? vs[0].toString() : "");
						mapDataVitalSign.put("code_vitalsign",!Objects.isNull(vs[1]) ? vs[1].toString() : "" );
						mapDataVitalSign.put("description",!Objects.isNull(vs[0]) ?vs[0].toString() : "" );
						mapDataVitalSign.put("value_unit",!Objects.isNull(vs[2]) ?vs[2].toString() : "" );
						mapDataVitalSign.put("value",!Objects.isNull(vs[3]) ? vs[3].toString() : "" );
						mapDataVitalSign.put("employee_code",!Objects.isNull(vs[4]) ? vs[4].toString() : "" );
						mapDataVitalSign.put("employee_name",!Objects.isNull(vs[5]) ? vs[5].toString() : "" );
						mapDataVitalSign.put("time_check_vitalsign",!Objects.isNull(vs[6]) ? vs[6].toString() : "" );
						DataVitalSignCN.add(mapDataVitalSign);
					}
					dataconsultantNote.put("Tanda Vital", DataVitalSignCN);*//*End Tanda Vital*/
					//Pemeriksaan Status Generalis
					SQLQuery<Object[]> pemeriksaanStatusGeneralisData = (SQLQuery<Object[]>) session.getNamedQuery("getListExaminationIPSatuSehat")
							.setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
					List <Object[]> DatapemeriksaanStatusGeneralisData = pemeriksaanStatusGeneralisData.list();
					List<Map<String, String>> PemeriksaanStatusGeneralis = new LinkedList<Map<String, String>>();
					for (Object[] ex : DatapemeriksaanStatusGeneralisData) {
						Map<String, String> datapemeriksaanStatusGeneralis = new LinkedHashMap<String, String>();
						datapemeriksaanStatusGeneralis.put("Bagian Tubuh",!Objects.isNull(ex) ? ex[0].toString() : "" );
						datapemeriksaanStatusGeneralis.put("Keterangan",!Objects.isNull(ex[1]) ? ex[1].toString() : "" );
						PemeriksaanStatusGeneralis.add(datapemeriksaanStatusGeneralis);
					}
					//End Pemeriksaan Status Generalis
					dataconsultantNote.put("Pemeriksaan Status Generalis", PemeriksaanStatusGeneralis );
					//Pemeriksaan Penunjang 
					SQLQuery<Object[]> pemeriksaanPenunjangData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServiceIPPemeriksaanPenunjangSatuSehat")
							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
					List <Object[]> pemeriksaanPenunjang = pemeriksaanPenunjangData.list();
					List<Map<String, String>> DatapemeriksaanPenunjang = new LinkedList<Map<String, String>>();
					for (Object[] pp : pemeriksaanPenunjang) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("service_name",!Objects.isNull(pp[1]) ? pp[1].toString() : "" );
						DatapemeriksaanPenunjang.add(medicationService);
					}
					//End Pemeriksaan Penunjang
					dataconsultantNote.put("Pemeriksaan Penunjang", DatapemeriksaanPenunjang );
					/*Data Diagnosis Kerja*/
					SQLQuery<Object[]> diagnosisKerjaData = (SQLQuery<Object[]>) session.getNamedQuery("getMedisPrimaryIPSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
					List <Object[]> diagnosisKerja = diagnosisKerjaData.list();
					List<Map<String, String>> DatadiagnosisKerja = new LinkedList<Map<String, String>>();
					for (Object[] dk : diagnosisKerja) {
						Map<String, String> dataMedis = new LinkedHashMap<String, String>();
						dataMedis.put("primary_desc", !Objects.isNull(dk[8]) ? dk[8].toString() : "-" );
						dataMedis.put("primary", !Objects.isNull(dk[4]) ? dk[4].toString() : "-" );
						DatadiagnosisKerja.add(dataMedis);
					}
					/*End Diagnosis Kerja*/
					dataconsultantNote.put("Diagnosis Kerja", DatadiagnosisKerja );
					/*Data Diagnosis Banding*/
					SQLQuery<Object[]> diagnosisBandingData = (SQLQuery<Object[]>) session.getNamedQuery("getMedisSecondaryIPSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
					List <Object[]> diagnosisBanding = diagnosisBandingData.list();
					List<Map<String, String>> DatadiagnosisBanding = new LinkedList<Map<String, String>>();
					for (Object[] db : diagnosisBanding) {
						Map<String, String> dataMedis = new LinkedHashMap<String, String>();
						dataMedis.put("secondary_desc", !Objects.isNull(db[8]) ? db[8].toString() : "-" );
						dataMedis.put("secondary", !Objects.isNull(db[4]) ? db[4].toString() : "-" );
						DatadiagnosisBanding.add(dataMedis);
					}
					/*End Diagnosis Banding*/
					dataconsultantNote.put("Diagnosis Banding", DatadiagnosisBanding);
					dataconsultantNote.put("Permasalahan Medis", !Objects.isNull(m[25]) ? m[25].toString() : "" );
					dataconsultantNote.put("Rencana Asuhan dan Terapi (Standing order )", !Objects.isNull(m[26]) ? m[26].toString() : "" );
					dataconsultantNote.put("Rencana Asuhan dan Terapi (Standing order ) Kolaborasi/konsultasi", !Objects.isNull(m[27]) ? m[27].toString() : "" );
					dataconsultantNote.put("Jenis Terapi", !Objects.isNull(m[28]) ? m[28].toString() : "" );
					//Terapi
					SQLQuery<Object[]> dataTerapiData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationIPSatuSehat")
							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
					List <Object[]> dataTerapi = dataTerapiData.list();
					List<Map<String, Object>> DataTerapi = new LinkedList<Map<String, Object>>();
					for (Object[] dt : dataTerapi) {
						Map<String, Object> medicationTanggal = new LinkedHashMap<String, Object>();
						medicationTanggal.put("obat_name",!Objects.isNull(dt[2]) ? dt[2].toString() : "" );
						DataTerapi.add(medicationTanggal);
					}
					//End Terapi
					dataconsultantNote.put("Terapi", DataTerapi );
					//Tindakan 
					SQLQuery<Object[]> dataTindakanData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServiceTindakanIPSatuSehat")
							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
					List <Object[]> dataTindakan = dataTindakanData.list();
					List<Map<String, String>> DataTindakan = new LinkedList<Map<String, String>>();
					for (Object[] pp : dataTindakan) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("service_name",!Objects.isNull(pp[1]) ? pp[1].toString() : "" );
						DataTindakan.add(medicationService);
					}
					//End Tindakan
					dataconsultantNote.put("Tindakan", DataTindakan);
					//Konsultasi
					SQLQuery<Object[]> dataKonsultasiData = (SQLQuery<Object[]>) session.getNamedQuery("getListCrossConsultationIPSatuSehat")
							.setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
					List <Object[]> dataKonsultasi = dataKonsultasiData.list();
					List<Map<String, String>> DataKonsultasi = new LinkedList<Map<String, String>>();
					for (Object[] dk : dataKonsultasi) {
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("consultation_type",!Objects.isNull(dk[0]) ? dk[0].toString() : "" );
						medicationService.put("doctor_name",!Objects.isNull(dk[1]) ? dk[1].toString() : "" );
						DataKonsultasi.add(medicationService);
					}
					//End Konsultasi
					dataconsultantNote.put("Penunjang", DataKonsultasi);
					//Pemeriksaan Penunjang Lanjutan
//					SQLQuery<Object[]> pemeriksaanPenunjangLanjutanData = (SQLQuery<Object[]>) session.getNamedQuery("getListMedicationServiceIPSatuSehat")
//							.setParameter("tanggalpulang", tanggalpulang).setParameter("branchId", branchId).setParameter("patientId", ptbip[1].toString()).setParameter("encounterId", ptbip[0].toString());
//					List <Object[]> pemeriksaanPenunjangLanjutan = pemeriksaanPenunjangLanjutanData.list();
						List<Map<String, String>> DatapemeriksaanPenunjangLanjutan = new LinkedList<Map<String, String>>();
						Map<String, String> medicationService = new LinkedHashMap<String, String>();
						medicationService.put("service_name", "" );
						DatapemeriksaanPenunjangLanjutan.add(medicationService);
					//End Pemeriksaan Penunjang Lanjutan
					dataconsultantNote.put("Pemeriksaan Penunjang Lanjutan", DatapemeriksaanPenunjangLanjutan);
					dataconsultantNote.put("Hasil yang diharapkan / Sasaran Rencana Asuhan", !Objects.isNull(m[33]) ? m[33].toString() : "" );
					dataconsultantNote.put("Edukasi awal, tentang diagnosis, rencana, tujuan terapi kepada- pasien - keluarga pasien", !Objects.isNull(m[34]) ? m[34].toString() : "" );
					dataconsultantNote.put("Rencana Pulang", !Objects.isNull(m[35]) ? m[35].toString() : "" );
					dataconsultantNote.put("Tanggal dan waktu selesai assesmen", !Objects.isNull(m[36]) ? m[36].toString() : "" );
					DataConsultantNote.add(dataconsultantNote);
				}/*End Consultant Note*/
				
				/*Data Discharge Summary*/
				SQLQuery<Object[]> dischargeSummaryData = (SQLQuery<Object[]>) session.getNamedQuery("getdischargeSummaryIPSatuSehat")
						.setParameter("patientId", ptbip[1].toString()).setParameter("visitId", ptbip[2].toString());
				List <Object[]> dischargeSummary = dischargeSummaryData.list();
				for (Object[] m : dischargeSummary) {
					Map<String, Object> datadischargeSummary = new LinkedHashMap<String, Object>();
					datadischargeSummary.put("encounter_id", !Objects.isNull( ptbip[0].toString()) ?  ptbip[0].toString() : "" );
					//Indikasi Rawat Inap
					SQLQuery<Object[]> indikasiRawatInapData = (SQLQuery<Object[]>) session.getNamedQuery("getListindikasiRawatInapIPSatuSehat")
							.setParameter("patientId", m[2].toString()).setParameter("visitId", m[0].toString());
					List <Object[]> indikasiRawatInap = indikasiRawatInapData.list();
					List<Map<String, Object>> IndikasiRawatInap = new LinkedList<Map<String, Object>>();
					for (Object[] dt : indikasiRawatInap) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[2]) ? dt[2].toString() : "" );
						IndikasiRawatInap.add(mapData);
					}
					//End Indikasi Rawat Inap
					datadischargeSummary.put("Indikasi_Rawat_Inap",IndikasiRawatInap);
					//Obat Dibawa Pulang
					SQLQuery<Object[]> obatDibawaPulangData = (SQLQuery<Object[]>) session.getNamedQuery("getListobatDibawaPulangIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> obatDibawaPulang = obatDibawaPulangData.list();
					List<Map<String, Object>> ObatDibawaPulang = new LinkedList<Map<String, Object>>();
					for (Object[] dt : obatDibawaPulang) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						ObatDibawaPulang.add(mapData);
					}
					//End Obat Dibawa Pulang
					datadischargeSummary.put("obat_dibawa_pulang",ObatDibawaPulang);
					//Kondisi Pasien
					SQLQuery<Object[]> kondisiPasienData = (SQLQuery<Object[]>) session.getNamedQuery("getListkondisiPasienIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> kondisiPasien = kondisiPasienData.list();
					List<Map<String, Object>> KondisiPasien = new LinkedList<Map<String, Object>>();
					for (Object[] dt : kondisiPasien) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						KondisiPasien.add(mapData);
					}
					//End Kondisi Pasien
					datadischargeSummary.put("kondisi_pasien", KondisiPasien);
					//mobilisasi_saat_pulang
					SQLQuery<Object[]> mobilisasiSaatPulangData = (SQLQuery<Object[]>) session.getNamedQuery("getListmobilisasiSaatPulangIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> mobilisasiSaatPulang = mobilisasiSaatPulangData.list();
					List<Map<String, Object>> MobilisasiSaatPulang = new LinkedList<Map<String, Object>>();
					for (Object[] dt : mobilisasiSaatPulang) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						MobilisasiSaatPulang.add(mapData);
					}
					//End mobilisasi_saat_pulang
					datadischargeSummary.put("mobilisasi_saat_pulang",MobilisasiSaatPulang);
					//alat_kesehatan_yang_terpasang
					SQLQuery<Object[]> alatKesehatanyangTerpasangData = (SQLQuery<Object[]>) session.getNamedQuery("getListalatKesehatanyangTerpasangIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> alatKesehatanyangTerpasang = alatKesehatanyangTerpasangData.list();
					List<Map<String, Object>> AlatKesehatanyangTerpasang = new LinkedList<Map<String, Object>>();
					for (Object[] dt : alatKesehatanyangTerpasang) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						AlatKesehatanyangTerpasang.add(mapData);
					}
					//End alat_kesehatan_yang_terpasang
					datadischargeSummary.put("alat_kesehatan_yang_terpasang", AlatKesehatanyangTerpasang);
					datadischargeSummary.put("instruksi_tindak_lanjut", "" );
					//rencana_kontrol
					SQLQuery<Object[]> rencanaKontrolData = (SQLQuery<Object[]>) session.getNamedQuery("getListrencanaKontrolIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> rencanaKontrol = rencanaKontrolData.list();
					List<Map<String, Object>> RencanaKontrol = new LinkedList<Map<String, Object>>();
					for (Object[] dt : rencanaKontrol) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						RencanaKontrol.add(mapData);
					}
					//End rencana_kontrol
					datadischargeSummary.put("rencana_kontrol", RencanaKontrol);
					//informasi_Perawatan_dirumah
					SQLQuery<Object[]> informasiPerawatandirumahData = (SQLQuery<Object[]>) session.getNamedQuery("getListinformasiPerawatandirumahIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> informasiPerawatandirumah = informasiPerawatandirumahData.list();
					List<Map<String, Object>> InformasiPerawatandirumah = new LinkedList<Map<String, Object>>();
					for (Object[] dt : informasiPerawatandirumah) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						InformasiPerawatandirumah.add(mapData);
					}
					//End informasi_Perawatan_dirumah
					datadischargeSummary.put("informasi_Perawatan_dirumah", InformasiPerawatandirumah );
					//rencana_pemeriksaan_penunjang
					SQLQuery<Object[]> rencanaPemeriksaanPenunjangData = (SQLQuery<Object[]>) session.getNamedQuery("getListrencanaPemeriksaanPenunjangIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> rencanaPemeriksaanPenunjang = rencanaPemeriksaanPenunjangData.list();
					List<Map<String, Object>> RencanaPemeriksaanPenunjang = new LinkedList<Map<String, Object>>();
					for (Object[] dt : rencanaPemeriksaanPenunjang) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						RencanaPemeriksaanPenunjang.add(mapData);
					}
					//End rencana_pemeriksaan_penunjang
					datadischargeSummary.put("rencana_pemeriksaan_penunjang", RencanaPemeriksaanPenunjang );
					//kebutuhan_edukasi
					SQLQuery<Object[]> kebutuhanEdukasiData = (SQLQuery<Object[]>) session.getNamedQuery("getListkebutuhanEdukasiIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> kebutuhanEdukasi = kebutuhanEdukasiData.list();
					List<Map<String, Object>> KebutuhanEdukasi = new LinkedList<Map<String, Object>>();
					for (Object[] dt : kebutuhanEdukasi) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						KebutuhanEdukasi.add(mapData);
					}
					//End kebutuhan_edukasi
					datadischargeSummary.put("kebutuhan_edukasi", KebutuhanEdukasi );
					//pertolongan_mendesak
					SQLQuery<Object[]> pertolonganMendesakData = (SQLQuery<Object[]>) session.getNamedQuery("getListpertolonganMendesakIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> pertolonganMendesak = pertolonganMendesakData.list();
					List<Map<String, Object>> PertolonganMendesak = new LinkedList<Map<String, Object>>();
					for (Object[] dt : pertolonganMendesak) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						PertolonganMendesak.add(mapData);
					}
					//End pertolongan_mendesak
					datadischargeSummary.put("pertolongan_mendesak", PertolonganMendesak);
					datadischargeSummary.put("remarks", "" );
					//penyakit_berhubungan_dengan
					SQLQuery<Object[]> penyakitBerhubunganDenganData = (SQLQuery<Object[]>) session.getNamedQuery("getListpenyakitBerhubunganDenganIPSatuSehat")
							.setParameter("visitId", m[0].toString()).setParameter("templateId", m[1].toString());
					List <Object[]> penyakitBerhubunganDengan = penyakitBerhubunganDenganData.list();
					List<Map<String, Object>> PenyakitBerhubunganDengan = new LinkedList<Map<String, Object>>();
					for (Object[] dt : penyakitBerhubunganDengan) {
						Map<String, Object> mapData = new LinkedHashMap<String, Object>();
						mapData.put("result",!Objects.isNull(dt[4]) ? dt[4].toString() : "" );
						PenyakitBerhubunganDengan.add(mapData);
					}
					//End penyakit_berhubungan_dengan
					datadischargeSummary.put("penyakit_berhubungan_dengan", PenyakitBerhubunganDengan);
					DataDischargeSummary.add(datadischargeSummary);
				}/*End Discharge Summary*/
				dataPenunjangMedis.put("Data_Medication", MedicationTanggal);
				dataPenunjangMedis.put("Medication_Service", DataMedicationService);
				dataPemeriksaanMedis.put("Lab_Result", LabResult);
				dataPemeriksaanMedis.put("Radiology_Result", RadiologyResult);
				listResponse.put("Data_Pasien", DataPasien);
				listResponse.put("Data_Encounter", DataEncounter);
				listResponse.put("Data_Medis", DataMedis);
				listResponse.put("Data_Billing", DataBilling);
				listResponse.put("Penunjang_Medis", dataPenunjangMedis);
				listResponse.put("Pemeriksaan", dataPemeriksaanMedis);
				listResponse.put("Vital_Sign", ListDatavitalSign);
				listResponse.put("Allergi", DataAllergy);
				listResponse.put("Diet", DataDiet);
				listResponse.put("Clinical_Note", DataClinicalNote);
				listResponse.put("Consultation_Note", DataConsultantNote);
				listResponse.put("Discharge_Summary", DataDischargeSummary);
				/*End Penunjang Medis*/
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
