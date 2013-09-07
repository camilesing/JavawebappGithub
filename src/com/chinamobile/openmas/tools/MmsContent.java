/**
 * 
 */
package com.chinamobile.openmas.tools;

import java.io.*;
/**
 * @author OpenMas
 *
 */
public class MmsContent 
{
	private MmsContentType ContentType;
    private String ContentID;
    private String ContentLocation;
    private String Charset;
    private ByteArrayOutputStream byteOutput;
    
    private MmsContent()
    {
    	ContentType = new MmsContentType();
        ContentID = "";
        ContentLocation = "";
        Charset = "gb2312";
        byteOutput = new ByteArrayOutputStream();
    }
    /**
     * �ڲ����췽��
     * @param content
     */
    private MmsContent(byte[] content)
    {
    	ContentID = "";
        ContentLocation = "";
        Charset = "gb2312";
    	byteOutput = new ByteArrayOutputStream();
        try
        {
            byteOutput.write(content);
        }catch(IOException e)
        {
            System.err.println(e);
        }
    }
	/**
	 * @return charset
	 */
	public String getCharset() {
		return Charset;
	}
	/**
	 * @param charset Ҫ���õ� ����
	 */
	public void setCharset(String charset) {
		Charset = charset;
	}
	/**
	 * @return contentID
	 */
	public String getContentID() {
		return ContentID;
	}
	/**
	 * @param contentID Ҫ���õ� contentID
	 */
	public void setContentID(String contentID) {
		ContentID = contentID;
	}
	/**
	 * @return contentLocation
	 */
	public String getContentLocation() {
		return ContentLocation;
	}
	/**
	 * @param contentLocation Ҫ���õ� contentLocation
	 */
	public void setContentLocation(String contentLocation) {
		ContentLocation = contentLocation;
	}
	/**
	 * @return contentType
	 */
	public MmsContentType getContentType() {
		return ContentType;
	}
	/**
	 * @param contentType Ҫ���õ� contentType
	 */
	public void setContentType(MmsContentType contentType) {
		ContentType = contentType;
	}
	/**
	 * ����ý�����ݵĴ�С
	 * @return ��Ϣ��С
	 */
	public int getSize()
	{
		return(byteOutput.toByteArray().length);
	}
	/**
	 * �Զ����Ʒ�ʽ��õ�����
	 * @return  ��Ϣ����
	 */
	public byte[] getContent()
	{
		return(byteOutput.toByteArray());
	}
	/**
	 * 
	 * @param content
	 */
	public void setContent(byte[] content)
	{
		byteOutput = new ByteArrayOutputStream();
        try
        {
            byteOutput.write(content);
        }catch(IOException e)
        {
            System.err.println(e);
        }
	}
	/**
	 * ��String��ʽ�������
	 * @return ��Ϣ����
	 */
	public String getContentAsString()
	{
		String charset = getCharset();
	    if(charset == null || charset.equals(""))
	      charset = "UTF-8";
	    try
	    {
	      return (byteOutput.toString(charset));
	    }catch(IOException ioe)
	    {
	      System.err.println(ioe);
	      return null;
	    }
	}
	public String toString()
    {
		StringBuffer sb = new StringBuffer();
        sb.append("ContentType=" + ContentType+"\n");
        sb.append("ContentID=" + ContentID+"\n");
        sb.append("ContentLocation=" + ContentLocation+"\n");
        sb.append("Charset=" + Charset+"\n");
        sb.append("byteOutput=" + getContentAsString()+"\n");
        return sb.toString();

    }
	/**
	 * ͨ������byte[]���ͽ�bMmsContent
	 * @param data
	 * @return MMContent ����
	 */
	public static MmsContent CreateFromBytes(byte[] data)
    {
        MmsContent mmsContent = new MmsContent(data);
        return (mmsContent);
    }
	/**
	 * ͨ�������ļ��ľ��·����bMmsContent
	 * @param fileName
	 * @return MMContent ����
	 */
	public static MmsContent CreateFromFile(String fileName)
	{
		try{
		      DataInputStream input = new DataInputStream(new FileInputStream(fileName));
		      ByteArrayOutputStream output = new ByteArrayOutputStream();
		      byte[] data = null;
		      while (input.available() != 0) {
		        output.write(input.readByte());
		      }
		      data = output.toByteArray();
		      MmsContent mmsContent = new MmsContent(data);
		      String name = new File(fileName).getName();
		      String fileExtension = "";
		      int i = name.lastIndexOf('.');
		      if(i >-1 && i < (name.length() - 1))
		      {
		    	  fileExtension = name.substring(i+1);
		      }
		      if (fileExtension.equalsIgnoreCase("GIF"))
                  mmsContent.setContentType(MmsConst.GIF);
              else if (fileExtension.equalsIgnoreCase("AMR"))
                  mmsContent.setContentType(MmsConst.AMR);
              else if (fileExtension.equalsIgnoreCase("JPEG"))
                  mmsContent.setContentType(MmsConst.JPEG);
              else if (fileExtension.equalsIgnoreCase("JPG"))
                  mmsContent.setContentType(MmsConst.JPEG);
              else if (fileExtension.equalsIgnoreCase("MID"))
                  mmsContent.setContentType(MmsConst.MIDI);
              else if (fileExtension.equalsIgnoreCase("MIDI"))
                  mmsContent.setContentType(MmsConst.MIDI);
              else if (fileExtension.equalsIgnoreCase("PNG"))
                  mmsContent.setContentType(MmsConst.PNG);
              else if (fileExtension.equalsIgnoreCase("SMIL"))
                  mmsContent.setContentType(MmsConst.SMIL);
              else if (fileExtension.equalsIgnoreCase("TXT"))
                  mmsContent.setContentType(MmsConst.TEXT);
              else if (fileExtension.equalsIgnoreCase("TEXT"))
                  mmsContent.setContentType(MmsConst.TEXT);
              else if (fileExtension.equalsIgnoreCase("WBMP"))
                  mmsContent.setContentType(MmsConst.WBMP);
		      mmsContent.setContentID(name);
		      return (mmsContent);
		    }catch(IOException ioe){
		      System.err.println(ioe);
		      return null;
		    }
	}
	/**
	 * ͨ������InputStream���ͽ�bMmsContent
	 * @param in
	 * @return MMContent ����
	 */
	public static MmsContent createFromStream(InputStream in)
	{
		DataInputStream input = new DataInputStream(in);
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] data = null;
	    try{
	      while (input.available() != 0) {
	        output.write(input.readByte());
	      }
	      data = output.toByteArray();
	    }catch(IOException ioe){
	      System.err.println(ioe);
	    }
	    MmsContent mmsContent = new MmsContent(data);
	    return(mmsContent);
	}
	/**
	 * ͨ������InputStream���ͺ�length��bMmsContent
	 * @param in
	 * @param length
	 * @return MMContent ����
	 */
	public static MmsContent createFromStream(InputStream in, int length)
	{
		DataInputStream input = new DataInputStream(in);
	    ByteArrayOutputStream byteout = new ByteArrayOutputStream();
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] data = null;
	    try{
	      while (input.available() != 0) {
	        byteout.write(input.readByte());
	      }
	      data = byteout.toByteArray();
	      output.write(data,0,length);
	    }catch(IOException ioe){
	      System.err.println(ioe);
	    }
	    data = output.toByteArray();
	    MmsContent mmsContent = new MmsContent(data);
	    return(mmsContent);
	}
	/**
	 * ͨ������base64������string ���ͽ�bMMContent
	 * @param content
	 * @param charset
	 * @return MMContent ����
	 */
	public static MmsContent CreateFromBase64String(String content, String charset)
	{
		try
		{
			MmsContent mmsContent = new MmsContent(new sun.misc.BASE64Decoder().decodeBuffer(content));
			mmsContent.setCharset(charset);
			return mmsContent;
		}catch(IOException iex)
		{
			System.err.println(iex);
			return null;
		}
	}
	/**
	 * ͨ������string ���ͽ�bMMContent
	 * @param content
	 * @return MMContent ����
	 */
	public static MmsContent CreateFromstring(String content)
	{
		try
		{
			MmsContent mmsContent = new MmsContent(content.getBytes());
			return mmsContent;
		}catch(Exception ex)
		{
			System.err.println(ex);
			return null;
		}
	}
	/**
	 * ͨ������string ���ͽ�bMMContent
	 * @param content
	 * @param charset
	 * @return MMContent ����
	 */
	public static MmsContent CreateFromstring(String content, String charset)
	{
		try
		{
			MmsContent mmsContent = new MmsContent(content.getBytes(charset));
			mmsContent.setCharset(charset);
			return mmsContent;
		}catch(Exception ex)
		{
			try
			{
				MmsContent mmsContent = new MmsContent(content.getBytes());
				return mmsContent;
			}catch(Exception e)
			{
				return null;
			}
		}
	}
	
	
}