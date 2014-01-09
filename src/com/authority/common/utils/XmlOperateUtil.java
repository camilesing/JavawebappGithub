package com.authority.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
 
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.VisitorSupport;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlOperateUtil {
	
	private File inputXml;
	
	public XmlOperateUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public XmlOperateUtil(File inputXml) {
		// TODO Auto-generated constructor stub
		this.inputXml = inputXml;
	}
	
	
	/**
	 * 生成 Document From String 
	 * @return
	 */
	public Document generateDocumentByString(String text ) {
       Document document = null;
       try {
           document = DocumentHelper.parseText(text);
       } catch (DocumentException e) {
           e.printStackTrace();
       }
       return document;
	}
	
	/**
	 * 保存到 xml 文件
	 * @param document
	 * @param outputXml
	 */
	public void saveDocument(Document document, File outputXml) {
       try {
           // 美化格式
           OutputFormat format = OutputFormat.createPrettyPrint();
           /*// 缩减格式
           OutputFormat format = OutputFormat.createCompactFormat();*/
           /*// 指定XML编码
            format.setEncoding("GBK");*/
           XMLWriter output = new XMLWriter(new FileWriter(outputXml), format);
           output.write(document);
           output.close();
       } catch (IOException e) {
           System.out.println(e.getMessage());
       }
	 }
	/**
	 * 转 xml 文件为 Document
	 * @return
	 */
	public Document getDocument() {
       SAXReader saxReader = new SAXReader();
       Document document = null;
       try {
           document = saxReader.read(inputXml);
       } catch (DocumentException e) {
           e.printStackTrace();
       }
       return document;
    }
	
	/**
	 * 得到根目录
	 * @return
	 */
	public Element getRootElement() {
       return getDocument().getRootElement();
    }
	
	public void traversalDocumentByIterator() {
       Element root = getRootElement();
       // 枚举根节点下所有子节点
       for (Iterator ie = root.elementIterator(); ie.hasNext();) {
           System.out.println("======");
           Element element = (Element) ie.next();
           System.out.println(element.getName());
 
           // 枚举属性
           for (Iterator ia = element.attributeIterator(); ia.hasNext();) {
              Attribute attribute = (Attribute) ia.next();
              System.out.println(attribute.getName() + ":"
                     + attribute.getData());
           }
           // 枚举当前节点下所有子节点
           for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
              Element elementSon = (Element) ieson.next();
              System.out.println(elementSon.getName() + ":"
                     + elementSon.getText());
           }
       }
    }
	
	
	public void traversalDocumentByVisitor() {
		getDocument().accept(new MyVisitor());
	}
	
	/**
     * 定义自己的访问者类
     */
    private static class MyVisitor extends VisitorSupport {
       /**
        * 对于属性节点，打印属性的名字和值
        */
       public void visit(Attribute node) {
           System.out.println("attribute : " + node.getName() + " = "
                  + node.getValue());
       }
 
       /**
        * 对于处理指令节点，打印处理指令目标和数据
        */
       public void visit(ProcessingInstruction node) {
           System.out.println("PI : " + node.getTarget() + " "
                  + node.getText());
       }
 
       /**
        * 对于元素节点，判断是否只包含文本内容，如是，则打印标记的名字和 元素的内容。如果不是，则只打印标记的名字
        */
       public void visit(Element node) {
           if (node.isTextOnly())
              System.out.println("element : " + node.getName() + " = "
                     + node.getText());
           else
              System.out.println("--------" + node.getName() + "--------");
       }
    }
	
}
