package test.controls;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.authority.service.HelloWorldWS;

public class HelloWorldWSClient {

	public static void main(String[] args){
		try{
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(HelloWorldWS.class);
			factory.setAddress("http://localhost:9000/Javawebapp/Openmas_Webservice/HelloWorldWS");
			HelloWorldWS helloworldws = (HelloWorldWS) factory.create();
			
			String result = helloworldws.SayHello("Test");
			System.out.println(result);
			
		}catch(Exception e){			
			System.out.println("异常信息："+e);
		}
		
	}
}
