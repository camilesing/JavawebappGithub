package com.chinapay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.MD5;
import com.alipay.util.AlipayCore;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.authority.common.utils.WebUtils;



public class ChinaPayService {
	
	private static final String BASE_URL = "http://bianmin-test.chinapay.com:9080/witsingletran/WitSinTranServlet";
	
	public static void main(String[] args) {
		 chinapay.PrivateKey key=new chinapay.PrivateKey(); 

	     chinapay.SecureLink t; 

	     boolean flag; 
	     String plainData, ChkValue2 ; 
	     String MId= "808080001000157";

	     flag=key.buildKey(MId,0,"E:\\BCKF\\Project\\java\\Workspaces\\JavawebappGithub\\src\\config\\others\\MerPrk.key"); 

	     if (flag==false) 
	     {
	    	 System.out.println("build key error!"); 
	     return; 
	     } else{
	    	 System.out.println("build key success");
	    	 
	    	 WebUtils web = new WebUtils();
	    	 try {
	 			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
	 			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
	 			//设置编码集
	 	        request.setCharset(AlipayConfig.input_charset);
	 	        
	 	        String to = "18857846128";
	 	        String yzm = String.valueOf(Integer.parseInt(to.substring(to.length()-4))*3+3206);
	 	        String tjpc = "1";
	 	        
	 	        //发送支付请求
		    	 Map<String,String> Param = new HashMap<String, String>();
		    	 
		    	 Param.put("merId", "808080001000157");
		    	 Param.put("transDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		    	 Param.put("orderNo", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		    	 Param.put("transType", "0003");
		    	 Param.put("openBankId", "0102");
		    	 Param.put("cardType", "0");
		    	 Param.put("cardNo", "6212261203005120596");
		    	 Param.put("usrName", web.chinaToUnicode("陈锋"));
		    	 Param.put("certType", "01");
		    	 Param.put("certId", "33038119801273670");
		    	 Param.put("curyId", "156");
		    	 Param.put("transAmt", "1");
		    	 Param.put("purpose", "");
		    	 Param.put("priv1", "");
		    	 Param.put("version", "20100831");
		    	 Param.put("gateId", "7008");
		    	 Param.put("chkValue", "");
		    	 
		    	//t.Sign(MsgBody);
	 	        
	 	        request.setParameters(generatNameValuePair(Param));
	 	        request.setUrl(BASE_URL);
	 	        HttpResponse response;
	 			response = httpProtocolHandler.execute(request,"","");
	 			String strResult ="";
	 	        if (response == null) {
	 	        	System.out.println("response is null");
	 	        }else{
	 	        	strResult = response.getStringResult();
	 	        	System.out.println("strResult:"+strResult);
	 	        }
	 		} catch (Exception e) {
	 			
	 			// TODO: handle exception
	 		}


	     }
		
		
	}
	

	public String witsingletran(Map<String,Object> Param){
		String interfaceurl ="",Type="";
		
		try {
			
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			//设置编码集
	        request.setCharset(AlipayConfig.input_charset);
	        HttpResponse response;
	        Map<String, String> paramMap = new HashMap<String, String>();
	        Map<String, String> sPara = buildRequestPara(paramMap);
			
	        request.setParameters(generatNameValuePair(sPara));
	    	request.setUrl(interfaceurl+"/"+Type.toUpperCase());
	    	response = httpProtocolHandler.execute(request,"","");
	    	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	

	/**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.sign_type.equals("MD5") ) {
        	mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        //mysign ="gur8bebpnew403u7lnan1jtfux9smtva";
        return mysign;
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
