/**
 * 
 */
package com.chinamobile.openmas.entity;

/**
 * @author OpenMas
 *
 */
public class PeriodTime 
{
	private int Hour;
    private int minute;
    private int second;
    
    /**
	 * @return hour
	 */
	public int getHour() {
		return Hour;
	}
	/**
	 * @param hour 要设置的 hour
	 */
	public void setHour(int hour)  throws MessageException
	{
		if (hour >= 0 && hour <= 23)
			this.Hour = hour;
		else
			throw new MessageException("小时(Hour)必须在0~23之间。");
	}
	/**
	 * @return minute
	 */
	public int getMinute() {
		return minute;
	}
	/**
	 * @param minute 要设置的 minute
	 */
	public void setMinute(int minute)  throws MessageException
	{
		if (minute >= 0 && minute <= 59)
			this.minute = minute;
		else
			throw new MessageException("分钟(Minute)必须在0~59之间。");
	}
	/**
	 * @return second
	 */
	public int getSecond() {
		return second;
	}
	/**
	 * @param second 要设置的 second
	 */
	public void setSecond(int second)  throws MessageException
	{
		if (second >= 0 && second <= 59)
			this.second = second;
		else
            throw new MessageException("秒(Second)必须在0~59之间。");
	}
}
