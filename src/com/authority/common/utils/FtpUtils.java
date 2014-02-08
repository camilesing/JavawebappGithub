package com.authority.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils {
	
	/**
	  * 上传程序方法
	  * 
	  */
	 public boolean uploadFile(String url, int port, String username,String password, String path, String filename, InputStream input) {
	  // filename:要上传的文件
	  // path :上传的路径
	  // 初始表示上传失败
	  boolean success = false;
	  // 创建FTPClient对象
	  FTPClient ftp = new FTPClient();
	  try {
		  int reply;
		  // 连接FTP服务器
		  // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		  ftp.connect(url, port);
		  
		  // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
		  ftp.setControlEncoding("GBK");
		  FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		  conf.setServerLanguageCode("zh");
		  
		  // 登录ftp
		  ftp.login(username, password);
	
		  // 看返回的值是不是230，如果是，表示登陆成功
		  reply = ftp.getReplyCode();
	
		  // 以2开头的返回值就会为真
		  if (!FTPReply.isPositiveCompletion(reply)) {
			  ftp.disconnect();
			  System.out.println("连接服务器失败");
			  return success;
		  }
	
		  System.out.println("登陆服务器成功");
		  ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录
		  FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
		  System.out.println(fs.length);
		  System.out.println(filename);
		  
		  String filename1 = FtpUtils.changeName(filename, fs);
		  String filename2 = new String(filename1.getBytes("GBK"),"ISO-8859-1");
		  String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
	
		  // 转到指定上传目录
		  ftp.changeWorkingDirectory(path1);
	
		  // 将上传文件存储到指定目录
		  // ftp.appendFile(new String(filename.getBytes("GBK"),"ISO-8859-1"),input);
	
		  ftp.setFileType(FTP.BINARY_FILE_TYPE); 
		  //如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
		  ftp.storeFile(filename2, input);
		  // 关闭输入流
		  input.close();
		  // 退出ftp
		  ftp.logout();
		  // 表示上传成功
		  success = true;
		  System.out.println("上传成功。。。。。。");

	  } catch (IOException e) {
		   e.printStackTrace();
	  } finally {
		  if (ftp.isConnected()) {
			  try {
				  ftp.disconnect();
			  } catch (IOException ioe) {
				  
			  }
		  }
	  }
	  return success;
	 }

	 /**
	  * 删除程序
	  * 
	  */
	 public boolean deleteFile(String url, int port, String username,String password, String path, String filename) {
		 // filename:要上传的文件
		 // path :上传的路径
		 // 初始表示上传失败
		 boolean success = false;
		 // 创建FTPClient对象
		 FTPClient ftp = new FTPClient();
		 try {
			 int reply;
			 // 连接FTP服务器
			 // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			 ftp.connect(url, port);
			 // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			 ftp.setControlEncoding("GBK");
			 FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			 conf.setServerLanguageCode("zh");
			 // 登录ftp
			 ftp.login(username, password);
			 // 看返回的值是不是230，如果是，表示登陆成功
			 reply = ftp.getReplyCode();
			 // 以2开头的返回值就会为真
			 if (!FTPReply.isPositiveCompletion(reply)) {
				 ftp.disconnect();
				 System.out.println("连接服务器失败");
				 return success;
			 }

			 System.out.println("登陆服务器成功");
			 String filename2 = new String(filename.getBytes("GBK"),"ISO-8859-1");
			 String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
			 // 转到指定上传目录
			 ftp.changeWorkingDirectory(path1);
			 FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
			 ftp.deleteFile(filename2);
			 ftp.logout();
			 success=true;
		 } catch (IOException e) {
			 System.out.println(e);    
		 } finally {
			 if (ftp.isConnected()) {
				 try {
					 ftp.disconnect();
				 } catch (IOException ioe) {
				 
				 }
			 }
		 }
		 return success;
	 }
	 
	 /**
	  * 
	  * @param ip
	  * @param port
	  * @param username
	  * @param password
	  * @param remotePath
	  * @param fileName
	  * @param localPath
	  * @return
	  */
	 public static boolean downFile(String ip, int port, String username,String password, String remotePath, String fileName,String localPath){
		 boolean success = false;
		 FTPClient ftp = new FTPClient();
		 try {
			 int reply;
			 ftp.connect(ip, port);
				 
			 // 下面三行代码必须要，而且不能改变编码格式
			 ftp.setControlEncoding("GBK");
			 FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			 conf.setServerLanguageCode("zh");
			 
			 // 如果采用默认端口，可以使用ftp.connect(url) 的方式直接连接FTP服务器
			 ftp.login(username, password);// 登录
			 ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			 reply = ftp.getReplyCode();
			 if (!FTPReply.isPositiveCompletion(reply)) {
				 ftp.disconnect();
				 return success;
			 }
			 
			 System.out.println("ftp.printWorkingDirectory:"+ftp.printWorkingDirectory());
			
			 Boolean bool = ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			
			 FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
			 // System.out.println(fs.length);//打印列表长度
			 for (int i = 0; i < fs.length; i++) {
				FTPFile ff = fs[i];
				if (ff.getName().equals(fileName)) {
					String filename = fileName;
					File localFile = new File(localPath+"/"+ff.getName());
					OutputStream ops = new FileOutputStream(localFile);
					// 这个就就是弹出下载对话框的关键代码
					// 将文件保存到输出流outputStream中
					ftp.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), ops);
					ops.close();
					}
			 }
			 
			 ftp.logout();
			 success = true;
			 
		 } catch (IOException e) {
			 e.printStackTrace();
		 } finally {
			 if (ftp.isConnected()) {
				 try {
					 ftp.disconnect();
				 } catch (IOException ioe) {
		
				 }
			 }
		 }
		 return success;
	 }

	 /**
	  * 下载文件
	  * @param ip
	  * @param port
	  * @param username
	  * @param password
	  * @param remotePath
	  * @param fileName
	  * @param outputStream
	  * @param response
	  * @return
	  */
	 public static boolean downFile(String ip, int port, String username,String password, String remotePath, String fileName,OutputStream outputStream, HttpServletResponse response) {
		 boolean success = false;
		 FTPClient ftp = new FTPClient();
		 try {
			 int reply;
			 ftp.connect(ip, port);
			 
			 // 下面三行代码必须要，而且不能改变编码格式
			 ftp.setControlEncoding("GBK");
			 FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			 conf.setServerLanguageCode("zh");
			 
			 // 如果采用默认端口，可以使用ftp.connect(url) 的方式直接连接FTP服务器
			 ftp.login(username, password);// 登录
			 ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			 reply = ftp.getReplyCode();
			 if (!FTPReply.isPositiveCompletion(reply)) {
				 ftp.disconnect();
				 return success;
			 }
			 
			System.out.println("登陆成功。。。。");
			System.out.println("ftp.printWorkingDirectory:"+ftp.printWorkingDirectory());
			
			Boolean bool = ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
	
			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
			// System.out.println(fs.length);//打印列表长度
			for (int i = 0; i < fs.length; i++) {
				FTPFile ff = fs[i];
				if (ff.getName().equals(fileName)) {
					String filename = fileName;
					// 这个就就是弹出下载对话框的关键代码
					response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
					// 将文件保存到输出流outputStream中
					ftp.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), outputStream);
					outputStream.flush();
					outputStream.close();
				}
		   }
			
		ftp.logout();
	    success = true;
	  } catch (IOException e) {
		  e.printStackTrace();
	  } finally {
		 if (ftp.isConnected()) {
			 try {
				 ftp.disconnect();
			 } catch (IOException ioe) {

			 }
		 }
	  }
	  return success;
	  
	 }
	 
	 // 判断是否有重名方法
	 public static boolean isDirExist(String fileName, FTPFile[] fs) {
		  for (int i = 0; i < fs.length; i++) {
			  FTPFile ff = fs[i];
			  if (ff.getName().equals(fileName)) {
				  return true; // 如果存在返回 正确信号
			  }
		  }
		  return false; // 如果不存在返回错误信号
	 }
	 
	 
	 // 根据重名判断的结果 生成新的文件的名称
	 public static String changeName(String filename, FTPFile[] fs){
		  int n = 0;
		  // 创建一个可变的字符串对象 即StringBuffer对象，把filename值付给该对象
		  StringBuffer filename1 = new StringBuffer("");	
		  filename1 = filename1.append(filename);
	
		  System.out.println(filename1);
	
		  while (isDirExist(filename1.toString(), fs)) {
			   n++;	
			   String a = "[" + n + "]";		
			   System.out.println("字符串a的值是：" + a);		
			   int b = filename1.lastIndexOf(".");// 最后一出现小数点的位置
			   int c = filename1.lastIndexOf("[");// 最后一次"["出现的位置
			   if (c < 0) {
				   c = b;
			   }			   
			   StringBuffer name = new StringBuffer(filename1.substring(0, c));// 文件的名字		
			   StringBuffer suffix = new StringBuffer(filename1.substring(b + 1));// 后缀的名称		
			   filename1 = name.append(a).append(".").append(suffix);	
		  }

		  return filename1.toString();
	 }

	 /**
	  * @param args
	  * @throws FileNotFoundException
	  * 测试程序
	  */
	 public static void main(String[] args) throws FileNotFoundException {
		 /*String path = "";
		 File f1 = new File("D:\\download\\10201_database_win32.zip");
		 String filename = f1.getName();
		 System.out.println(filename);
		 InputStream input = new FileInputStream(f1);
		 FtpUtils a = new FtpUtils();
		 a.uploadFile("192.168.1.126", 21, "poeeva168", "314159", path, filename, input);*/		 
		 
		 /*String path ="Test"; 
		 File f2 = new File("D:\\data\\新建文本文档.txt"); 
		 String filename2= f2.getName();
		 
		 System.out.println(filename2);
		 FtpUtils a = new FtpUtils(); 
		 a.downFile("192.168.1.126", 21, "poeeva168", "314159", path,filename2,"D:/data");*/
		 
		 /*String path ="";
		 String filename = "新建文本文档.txt";
		 FtpUtils a = new FtpUtils();
		 a.deleteFile("192.168.1.126", 21, "poeeva168", "314159", path, filename);*/
	   
	 }

}
