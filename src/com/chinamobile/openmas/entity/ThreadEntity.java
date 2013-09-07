/**
 * 
 */
package com.chinamobile.openmas.entity;

/**
 * @author OpenMas
 *
 */
public class ThreadEntity 
{
	private boolean IsRunning = false;
    private String ThreadName;
	/**
	 * @return isRunning 是否正在运行
	 */
	public boolean isRunning() {
		return IsRunning;
	}
	/**
	 * @param isRunning 是否正在运行
	 */
	public void setRunning(boolean isRunning) {
		IsRunning = isRunning;
	}
	/**
	 * @return threadName 线程名称
	 */
	public String getThreadName() {
		return ThreadName;
	}
	/**
	 * @param threadName 线程名称
	 */
	public void setThreadName(String threadName) {
		ThreadName = threadName;
	}
}
