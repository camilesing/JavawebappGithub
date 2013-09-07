/**
 * 
 */
package com.chinamobile.openmas.client;

import org.apache.axis2.AxisFault;
import java.rmi.RemoteException;
import org.tempuri.SystemImplementationStub;
import com.chinamobile.openmas.entity.*;

/**
 * @author OpenMas
 *
 */
public class SystemService
{
	private SystemImplementationStub stub = null;
	public SystemService(String serviceUrl) throws AxisFault
	{
		stub = new SystemImplementationStub(serviceUrl);
	}
	
	public SystemServiceInfo[] GetSystemService(String applicationId, String password)
	{
		SystemImplementationStub.GetSystemService request = new SystemImplementationStub.GetSystemService();
		request.setApplicationId(applicationId);
		request.setPassword(password);
		SystemImplementationStub.GetSystemServiceResponse response;
		try{
			response=stub.GetSystemService(request);
			if(response == null)
				return null;
		
		}catch(RemoteException ex)
		{
			System.err.println(ex);
			return null;
		}
		SystemImplementationStub.SystemServiceInfo[] serviceInfo = response.getGetSystemServiceResult().getSystemServiceInfo();
		if(serviceInfo ==null)
			return null;
		SystemServiceInfo[] infos = new SystemServiceInfo[serviceInfo.length];
		for(int i=0;i<serviceInfo.length;i++)
		{
			SystemServiceInfo info = new SystemServiceInfo();
			info.setCpuPercent(serviceInfo[i].get_cpuPercent());
			info.setCreateTime(serviceInfo[i].get_createTime());
			info.setMemorySize(serviceInfo[i].get_memorySize());
			info.setServerName(serviceInfo[i].get_serverName());
			info.setServiceName(serviceInfo[i].get_serviceName());
			if(serviceInfo[i].get_serviceThreads() !=null)
			{
				ThreadEntity[] threads = new ThreadEntity[serviceInfo[i].get_serviceThreads().getThreadEntity().length];
				for(int j=0;j<serviceInfo[i].get_serviceThreads().getThreadEntity().length;j++)
				{
					ThreadEntity entity = new ThreadEntity();
					entity.setRunning(serviceInfo[i].get_serviceThreads().getThreadEntity()[j].get_isRunning());
					entity.setThreadName(serviceInfo[i].get_serviceThreads().getThreadEntity()[j].get_threadName());
					threads[j]=entity;
				}
				info.setServiceThreads(threads);
			}
			infos[i]=info;
		}
		return infos;
	}
}
