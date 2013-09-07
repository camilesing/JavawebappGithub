/**
 * 
 */
package com.chinamobile.openmas.tools;

import java.util.*;
import org.w3c.dom.*; 
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import java.io.ByteArrayOutputStream;
import com.chinamobile.openmas.entity.*;
//import java.io.*;
/**
 * @author OpenMas
 *
 */
public class MmsBuilder 
{
	private LinkedList<MmsContent> mmsContentList = new LinkedList<MmsContent>();
	
	public void AddContent(MmsContent mmsContent) throws MessageException
    {
		if(mmsContent.getContentType() == null)
			throw new MessageException("ContentType ����Ϊ��");
		if(mmsContent.getContentID() == null || mmsContent.getContentID().trim().equals(""))
			throw new MessageException("ContentId ����Ϊ��");
        mmsContentList.add(mmsContent);
    }
	
	public String BuildContentToXml() throws MessageException
	{
		try
		{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.newDocument();
			Element root = doc.createElement("masmms");
			doc.appendChild(root);
			for(int i=0;i<mmsContentList.size();i++)
			{
				MmsContent mmsContent = mmsContentList.get(i);
				Element content = doc.createElement("content");
				content.setAttribute("contentType", mmsContent.getContentType().toString());
				content.setAttribute("charset",mmsContent.getCharset());
				content.setAttribute("contentId",mmsContent.getContentID());
				content.setAttribute("contentLocation", mmsContent.getContentLocation());
				System.out.println(new sun.misc.BASE64Encoder().encode(mmsContent.getContent()));
				//1tC5+g== 1tC5+g==

				content.appendChild(doc.createTextNode(new sun.misc.BASE64Encoder().encode(mmsContent.getContent())));
				root.appendChild(content);
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(doc);;
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
            return out.toString();
			
		}catch(Exception ex)
		{
			System.out.println(ex);
			return null;
		}
		
	}
	/**
	 * xml -> Object
	 * @param xml xml���
	 * @return MmsBuildFile���󼯺�
	 */
	public static List<MmsContent> BuildXmlToContent(String xml)
	{
		LinkedList<MmsContent> contentList = new LinkedList<MmsContent>();
		try
		{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc = builder.parse( new java.io.ByteArrayInputStream(xml.getBytes()));
			NodeList contents=doc.getElementsByTagName("content");
			for(int i=0;i<contents.getLength();i++)
			{
				Node content=contents.item(i);
				MmsContent mmsContent = MmsContent.CreateFromBytes(new sun.misc.BASE64Decoder().decodeBuffer(content.getFirstChild().getNodeValue()));
				if(content.hasAttributes())
				{
					NamedNodeMap attributes = content.getAttributes();
					for(int j=0;j<attributes.getLength();j++)
					{
						if(attributes.item(j).getNodeName().equalsIgnoreCase("contentType"))
						{
							mmsContent.setContentType(new MmsContentType(attributes.item(j).getNodeValue()));
						}
						else if(attributes.item(j).getNodeName().equalsIgnoreCase("charset"))
						{
							mmsContent.setCharset(attributes.item(j).getNodeValue());
						}
						else if(attributes.item(j).getNodeName().equalsIgnoreCase("contentId"))
						{
							mmsContent.setContentID(attributes.item(j).getNodeValue());
						}
						else if(attributes.item(j).getNodeName().equalsIgnoreCase("contentLocation"))
						{
							mmsContent.setContentLocation(attributes.item(j).getNodeValue());
						}
					}
				}
				contentList.add(mmsContent);
			}

			
		}catch(Exception ex)
		{
			
		}
		return contentList;
	}
	
	
}
