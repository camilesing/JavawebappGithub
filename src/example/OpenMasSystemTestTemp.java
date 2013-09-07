package example;

import com.chinamobile.openmas.client.*;
import com.chinamobile.openmas.entity.*;

public class OpenMasSystemTestTemp {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try
		{
			SystemService sys = new SystemService("http://192.168.0.4:7070/openmasservice");
			SystemServiceInfo[] infos=sys.GetSystemService("default","default");
			if(infos != null)
			{
				for(SystemServiceInfo info : infos)
				{
					System.out.println("CpuPercent:"+info.getCpuPercent());
					System.out.println("CreateTime:"+info.getCreateTime());
					System.out.println("MemorySize:"+info.getMemorySize());
					System.out.println("ServerName:"+info.getServerName());
					System.out.println("ServiceName:"+info.getServiceName());
					ThreadEntity[] entities = info.getServiceThreads();
					if(entities != null)
					{
						for(ThreadEntity entity: entities)
						{
							System.out.println(entity.getThreadName()+":"+"isRunning="+entity.isRunning());
							
						}
					}
					System.out.println();
				}
			}
			
		}catch(Exception ex)
		{
			System.err.println(ex);
		}
		System.exit(0);
	}

}
