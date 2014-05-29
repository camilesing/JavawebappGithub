<%@ page language="java" 
import="
cn.com.burgeon.tasks.webpos.*,
nds.query.QueryEngine,
nds.util.Tools,
java.util.ArrayList,
java.util.Collection,
java.util.List,
java.security.MessageDigest"
pageEncoding="utf-8"%>
<%
	Collection params=new ArrayList();
	params.add("1");
	Object ob = QueryEngine.getInstance().executeStoredProcedure("datequeue",params,true);
	String is_y = ob.toString(); 
	if(is_y.equals("[Y]")){
		String store_str = String.valueOf(QueryEngine.getInstance().doQueryOne("select wm_concat(t.c_store_id) from b_datequeue t where date_status=2"));
		if(store_str.equals("") || store_str==null){
			String[] storeid_str = store_str.split(",");
			int[] storeIds = new int[storeid_str.length];
			for(int i=0;i<storeid_str.length;i++){
			   //DateGeneration2.getInstance().generationForDIS(Integer.parseInt(storeid_str[i]));
			   storeIds[i]=Integer.parseInt(storeid_str[i]);
			}
			DateGeneration2.getInstance().generationForDISes(storeIds);
		}

		String area_str = String.valueOf(QueryEngine.getInstance().doQueryOne("select wm_concat(t.C_PRICEAREA_ID) from b_datequeue t where date_status=2"));
		if(area_str.equals("") || area_str==null){
			String[] areaid_str = area_str.split(",");
			int[] areaIds = new int[areaid_str.length];
			for(int i=0;i<areaid_str.length;i++){
			   //DateGeneration2.getInstance().generationForDIS(Integer.parseInt(storeid_str[i]));
			   areaIds[i]=Integer.parseInt(areaid_str[i]);
			}
			DateGeneration2.getInstance().generationForADJUSTMENTs(areaIds);
			
			QueryEngine.getInstance().executeStoredProcedure("datequeue_end",params,true);
		}
		out.print("成功");
	}else{
		out.print("成功");
	}	

%>