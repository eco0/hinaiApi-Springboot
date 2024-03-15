////package com.api.restapi.service;
////
////public class BaseApplicationService {
////
////}
//package com.api.restapi.service;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.annotation.Resource;
//
//import org.hibernate.transform.ResultTransformer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.api.restapi.service.IBaseRepository;
//import com.api.restapi.service.IBaseApplicationService;
//
//@Service
//public class BaseApplicationService implements IBaseApplicationService {
//
//	@Resource(name = "applicationMap")
//	protected Map applicationMap;
//
//	@Resource(name = "baseRepository")
//	private IBaseRepository baseRepository;
//
//	
//
//	protected IBaseRepository getBaseRepository() {
//		return baseRepository;
//	}
//
//
//	public List find(String queryKey, Object[] object) {
//		return baseRepository.find(queryKey, object);
//	}
//
//
//
//
//}
