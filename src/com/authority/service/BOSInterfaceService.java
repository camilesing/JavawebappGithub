package com.authority.service;

import java.util.List;
import java.util.Map;

public interface BOSInterfaceService {
	 
	String M_SALE(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account);
	
	String M_RETAIL(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account);
	
	String M_RET_SALE(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account);
	
	String M_RET_PUR(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account);
	
	String M_TRANSFER(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account);
	
	String M_OTHER_INOUT(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account);
	
	String M_INVENTORY(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account);
}
