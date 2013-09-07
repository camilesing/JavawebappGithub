/**
 * 
 */
package com.chinamobile.openmas.entity;

/**
 * @author OpenMas
 *
 */
public class PeriodValue
{
	private int month;
    private int day;
    private int week = 1;
    
	/**
	 * @return day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day 要设置的 day
	 */
	public void setDay(int day) throws MessageException
	{
		if (day >= 1 && day <= 31)
		{
			this.day = day;
		}
		else
			throw new MessageException("日(Day)必须在1~31之间。");
	}
	
	/**
	 * @return month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month 要设置的 month
	 */
	public void setMonth(int month)  throws MessageException
	{
		if (month >= 1 && month <= 12)
			this.month = month;
		else
			throw new MessageException("月份(Month)必须在1~12之间。");
	}
	/**
	 * @return week
	 */
	public int getWeek() 
	{
		return week;
	}
	/**
	 * @param week 要设置的 week
	 */
	public void setWeek(int week)  throws MessageException
	{
		if(week >= 1 && week <= 7)
			this.week = week;
		else
			throw new MessageException("星期(week)必须在1~7之间。");
	}
}
