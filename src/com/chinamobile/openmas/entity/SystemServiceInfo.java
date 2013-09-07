/**
 * 
 */
package com.chinamobile.openmas.entity;

/**
 * @author OpenMas
 *
 */
public class SystemServiceInfo 
{
	 private String ServerName;
     private String ServiceName; 
     private long MemorySize; 
     private String CreateTime;
     private int CpuPercent; 
     private long TotalCpuTime;
     private ThreadEntity[] ServiceThreads;
	/**
	 * @return cpuPercent CPU占用率
	 */
	public int getCpuPercent() {
		return CpuPercent;
	}
	/**
	 * @param cpuPercent CPU占用率
	 */
	public void setCpuPercent(int cpuPercent) {
		CpuPercent = cpuPercent;
	}
	/**
	 * @return createTime 创建时间
	 */
	public String getCreateTime() {
		return CreateTime;
	}
	/**
	 * @param createTime 创建时间
	 */
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	/**
	 * @return memorySize K为单位
	 */
	public long getMemorySize() {
		return MemorySize;
	}
	/**
	 * @param memorySize K为单位
	 */
	public void setMemorySize(long memorySize) {
		MemorySize = memorySize;
	}
	/**
	 * @return serverName 服务器名
	 */
	public String getServerName() {
		return ServerName;
	}
	/**
	 * @param serverName 服务器名
	 */
	public void setServerName(String serverName) {
		ServerName = serverName;
	}
	/**
	 * @return serviceName 服务名
	 */
	public String getServiceName() {
		return ServiceName;
	}
	/**
	 * @param serviceName 服务名
	 */
	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
	/**
	 * @return serviceThreads 服务线程
	 */
	public ThreadEntity[] getServiceThreads() {
		return ServiceThreads;
	}
	/**
	 * @param serviceThreads 服务线程
	 */
	public void setServiceThreads(ThreadEntity[] serviceThreads) {
		ServiceThreads = serviceThreads;
	}
	/**
	 * @return totalCpuTime cpu累计运行时间,秒为单位
	 */
	public long getTotalCpuTime() {
		return TotalCpuTime;
	}
	/**
	 * @param totalCpuTime cpu累计运行时间,秒为单位
	 */
	public void setTotalCpuTime(long totalCpuTime) {
		TotalCpuTime = totalCpuTime;
	}
     
}
