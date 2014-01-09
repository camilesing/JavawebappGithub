package test.controls;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.fastjson.JSON;
import com.alipay.config.AlipayConfig;
import com.alipay.sign.MD5;
import com.alipay.util.AlipayCore;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.authority.common.utils.FileOperateUtil;
import com.authority.common.utils.PoiHelper;

public class Httprequest_test {
	
	private static final String BASE_URL = "http://127.0.0.1:9000/Javawebapp/bosinterface/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Httprequest_test htp = new Httprequest_test();
			String classname = htp.getClass().getName();
			String classname_resource = "/" + classname.replace('.', '/') + ".class";
			URL url = htp.getClass().getResource(classname_resource);
			String urlPath = url.getPath();
			File classFile = new File(urlPath);
			String classPath = classFile.getPath();
			System.out.println(classname + "'s path is " + classPath);
			
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			//设置编码集
	        request.setCharset(AlipayConfig.input_charset);
	        
	        Map<String, String> paramTemp = new HashMap<String, String>();
	        paramTemp.put("CONDITION", "(DOCNO='SA1308250000002' )");
	        
	        request.setParameters(generatNameValuePair(paramTemp));
	        
	        request.setUrl(BASE_URL+"/M_SALE");

	        HttpResponse response;
			
			response = httpProtocolHandler.execute(request,"","");
			
			String strResult ="";
	        if (response == null) {
	        	System.out.println("response is null");
	        }else{
	        	strResult = response.getStringResult();
	        	//字符串转JSON
	        	Map<String,Object> RepMap = JSON.parseObject(strResult);
	        	if((Boolean) RepMap.get("success")){
	        		//结果为真 读取文件路径,再次发送请求 下载文件
	        		String fileURL = "http://127.0.0.1:9000/Javawebapp/"+RepMap.get("msg").toString();
	        		String saveURL = "D:\\Test";
	        		String filePath = FileOperateUtil.downloadfile(saveURL, fileURL);
	        		File NewFile = new File(filePath);
	        		if(NewFile.exists()){
	        			//后期从数据库中读取,源--目标
	        			Map<String,Object> fieldMap = new HashMap<String, Object>();
	        			fieldMap.put("MASTERID","DOCNO");
	        			fieldMap.put("SALETYPE","DOCTYPE");
	        			
	        			//读取Excel 内容 
	        			List<String[]> DataArray = PoiHelper.getData(NewFile, 0,0);
	        			//源Excel 字段解析 映射，并检测是否合规范
	        			Map<String,Object> fieldmatchMap =  new HashMap<String, Object>();
	        			//判断Excel 格式是否符合标准
	        			String[] DataArrayChild = DataArray.get(0);
	        			Set<String> keySet = fieldMap.keySet();
	        			boolean standard = true ;
	        			for(String key : keySet){
	        				boolean exists = false ;
	        				for (int i = 0; i < DataArrayChild.length; i++) {
								if(fieldMap.get(key).toString().toUpperCase().equals(DataArrayChild[i].toUpperCase())){
									fieldmatchMap.put(key, i);
									exists = true ;
									break;
								}
							}
	        				if(!exists){
	        					standard = false;
	        					break;
	        				}
	        			}
	        			
	        			if(!standard){
	        				System.out.println("文件不符合规范,请调整设置");
	        			}else{
	        				System.out.println("检测通过");
	        			}
	        			
	        		}
	        		
	        	}else{
	        		System.out.println("数据生成异常,请重新提交请求");
	        	}
	        	
	        }
	        
	        System.out.println("Response ：\n"+strResult);
	        
		}catch(Exception e){
			
			System.out.println(e);
			
		}
		/*HttpRequester req = new HttpRequester();
		String url="http://eai.tmall.com/api";
		try {			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("sign", "F4E1054967F086C42D51EE4ACA064EB2");
			map.put("app_key", "jip544720130515094913");
			map.put("format", "json");
			map.put("from_date", "2013-04-02 09:24:21");
			map.put("length", "0");
			map.put("method", "taobao.logistics.station.order.query");
			map.put("partner_id", "top-apitools-zhejiangshizu");
			map.put("service_provider_code", "xiaoyouju");
			map.put("session", "966d334ff66b64714dd168ed75b7b4a5aaf93dfc48a9e542f1102637c199df35");
			map.put("sign_method", "md5");
			map.put("start", "0");
			map.put("station_id", "7473");
			map.put("status", "2");
			map.put("timestamp", "13-7-2 9:23:55");
			map.put("to_date", "2013-07-02 09:24:21");
			map.put("user_id", "1704223042");
			map.put("v", "2.0");			
			HttpRespons rep =  req.sendPost(url, map);	
			
			System.out.println(rep.getContent());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }
   
}
