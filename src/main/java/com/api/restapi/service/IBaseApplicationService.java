//package com.api.restapi.service;
//
//import java.util.List;
//
//public class IBaseApplicationService {
//
//	public List<Object[]> find(String string, Object[] objects) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
package com.api.restapi.service;

import java.util.List;

public interface IBaseApplicationService {

	List<Object[]> find(String queryKey, Object[] object);	
	
}

