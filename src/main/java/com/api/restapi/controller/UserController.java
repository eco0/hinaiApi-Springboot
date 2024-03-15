//package com.api.restapi.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.api.restapi.model.User;
//import com.api.restapi.model.ResponseUser;
//import com.api.restapi.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.json.JsonMapper;
//
//
//
//
//@RestController
//public class UserController {
//	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	UserRepository userRepo;
//	
//	
//	//Select All Data
//	@GetMapping(value = "/api/users")
//	public List<User> all(){
//		return userRepo.findAll();
//	}
//	
//	//Save Data User
//	@PostMapping(value = "/api/save")
//	public void saveUser(@RequestBody String user , HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		
//		log.info("[START|DIRECT_REGIST] Registration Start.");
//		ResponseUser responseData = new ResponseUser();
//		
//		User requestRegistrationAPI = null;
//		Object mapper = new JsonMapper();
//		
//		try {
//			log.info("[REQUEST|DIRECT_REGIST] Registration Origin Request : " + user);
//			requestRegistrationAPI = ((ObjectMapper) mapper).readValue(user, User.class);
//			
//			if (requestRegistrationAPI==null) {
//				
//				log.error("[ERROR|DIRECT_REGIST] Registration Request Parameter Null");
//				responseData = new ResponseUser();
//				responseData.setResultCd("01");
//				responseData.setResultMsg("Parameter Is Null");
//
//				// Processing Result
//				String resultJsonStr = ((ObjectMapper) mapper).writeValueAsString(responseData);
//				response.setContentType("application/json");
//				response.setCharacterEncoding("UTF-8");
//				response.getWriter().write(resultJsonStr);
//				
//				log.error("[ERROR|REGIST] Registration Request End");
//				return;
//			}
//			
//			else {
//				log.error("[REQUEST|PARAM] Registration Request Parameter is Success");
//				responseData = new ResponseUser();
//				responseData.setResultCd("200");
//				responseData.setResultMsg("Save Data is Successfully");
//				
//				//save data
//				userRepo.save(requestRegistrationAPI);
//
//				// Processing Result
//				String resultJsonStr = ((ObjectMapper) mapper).writeValueAsString(responseData);
//				response.setContentType("application/json");
//				response.setCharacterEncoding("UTF-8");
//				response.getWriter().write(resultJsonStr);
//				
//				log.info("[REQUEST|PARAM] Response Api : " + resultJsonStr);
//				log.info("[REQUEST|PARAM] Registration Request End");
//				return;
//			}
//		} catch (Exception e) {
//			
//			log.error("[ERROR|DIRECT_REGIST] Registration Request Parameter Null");
//			responseData = new ResponseUser();
//			responseData.setResultCd("01");
//			responseData.setResultMsg("Parameter Is Fail");
//
//			// Processing Result
//			String resultJsonStr = ((ObjectMapper) mapper).writeValueAsString(responseData);
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write(resultJsonStr);
//
//			log.error("[ERROR|PARAM] Registration Request End");
//			return;
//		}
//	}
//}
