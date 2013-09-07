/**
 * 
 */
package com.chinamobile.openmas.tools;

/**
 * @author OpenMas
 *
 */
public class MmsContentType {
	private String strPrimaryType;
	private String strSubType;
    /**
     * 构造方法
     *
     */
    public MmsContentType()
    {
    	strPrimaryType = "";
        strSubType = "";
    }
    /**
     * 构造方法
     * @param type 类型
     */
    public MmsContentType(String type) 
    {
    	strPrimaryType = "";
        strSubType = "";
        int index = type.indexOf("/");
        if(index>0)
        {
          String strPraType = type.substring(0,index);
          String strSubType = type.substring(index+1);
          setPrimaryType(strPraType);
          if(strSubType.indexOf(";")>0)
            strSubType = strSubType.substring(0,strSubType.indexOf(";"));
          setSubType(strSubType);
        }
        else
            System.err.println("该类型不是标准类型！type=" + type);
    }
    /**
     * 
     * @return 获得主类型
     */
    public String getPrimaryType()
    {
      return(this.strPrimaryType);
    }
    /**
     * 
     * @param primaryType 设置主类型
     */
    public void setPrimaryType(String primaryType)
    {
      this.strPrimaryType = primaryType;
    }
    /**
     * 
     * @param subType 设置子类型
     */
    public void setSubType(String subType)
    {
      this.strSubType = subType;
    }
    /**
     * 
     * @return  获得子类型
     */
    public String getSubType()
    {
      return(this.strSubType);
    }
    
    /**
     * 比较主类型与子类型是否一致
     * @param type 
     * @return true or false
     */
    public boolean match(MmsContentType type)
    {
      String strPrimaryType = type.getPrimaryType().trim();
      String strSubType = type.getSubType().trim();
      if(strPrimaryType.equals(strSubType))
        return true;
      else
        return false;
    }
    /**
     * 比较主类型与子类型是否一致
     * @param type
     * @return true or false
     */
    public boolean match(String type)
    {
      int index = type.indexOf("/");
      if(index>0)
      {
        String strParType = type.substring(0,index);
        String strSubType = type.substring(index+1);
        if(strParType.trim().equals(strPrimaryType))
        {
          if(strSubType.trim().equals(strSubType))
            return true;
          else
            return false;
        }
        else
          return false;
      }
      else{
        System.err.println("该类型不是标准类型！type="+type);
        return (false);
      }
    }
    /**
     * 返回对象的文本表示
     */
    public String toString()
    {
        return strPrimaryType + "/" + strSubType;
    }
}
