package com.authority.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.authority.common.jackjson.JackJson;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.FileDigest;
import com.authority.common.utils.FileOperateUtil;
import com.authority.pojo.Criteria;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.Tree;
import com.authority.service.WebFilemanagerService;
import com.authority.web.interseptor.WebConstants;


@Controller
@RequestMapping("/filemanager")
public class WebFilemanagerController {
	private static final Logger logger = LoggerFactory.getLogger(WebFilemanagerController.class);
	
	@Autowired
	private WebFilemanagerService webfilemanagerservice;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * index
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String webfilemanager() {
		return "user/webfilemanager";
	}
	
	/**
	 * 文件夹目录
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value="/getdirectories",method = RequestMethod.POST)	
	public void getDirectories( PrintWriter writer,HttpSession session, HttpServletRequest request) throws IOException {
		String rootPath = request.getSession().getServletContext().getRealPath("/upload");
		File file = new File(rootPath);
		if(!file.exists()){
			file.mkdirs();
		}
		file = null;
		String node=request.getParameter("node");
		if(null==node||node.startsWith("xnode-"))
			node="";
		
		List list=webfilemanagerservice.listFiles(rootPath, node, true);
		int total = new File(rootPath + node).list().length;
		
		logger.debug("total:{}", total);
		
		String json = JackJson.fromObjectToJson(list);
		writer.write(json);
		writer.flush();
		writer.close();
		
	}
	
	/**
	 * 文件信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value="/getfiles",method = RequestMethod.POST)	
	@ResponseBody
	public Object getfiles( HttpSession session, HttpServletRequest request) throws IOException {
		String rootPath = request.getSession().getServletContext().getRealPath("/upload");
		File file = new File(rootPath);
		if(!file.exists()){
			file.mkdirs();
		}
		file = null;
		String node=request.getParameter("node");
		if(null==node||node.startsWith("xnode-"))
			node="";
		
		List list=webfilemanagerservice.listFiles(rootPath, node, false);
		int total = new File(rootPath + node).list().length;
		
		logger.debug("total:{}", total);
		return new ExtGridReturn(total, list);
	}
	
	@RequestMapping(value="/deleteFiles")
	@ResponseBody
	public Object deleteFiles( HttpSession session, HttpServletRequest request) throws IOException{		
		try {
			System.out.println("CURRENT_USER"+request.getSession().getAttribute(WebConstants.CURRENT_USER));
			String rootPath = request.getSession().getServletContext().getRealPath("/upload");			
			String[] paths =request.getParameterValues("paths");
			File file = new File(rootPath);
			if(!file.exists()){
				file.mkdirs();
			}
			file = null;
			boolean flag = false;			
			try {
				for (String path : paths) {
					file = new File(rootPath + path);
					flag = FileOperateUtil.delFiles(file);
					if (!flag) {
						break;
					}
				}
			} catch (RuntimeException e) {
				flag = false;
				e.printStackTrace();
			} finally {
				file = null;
			}
			
			if(flag)
				return new ExtReturn(flag, "删除成功!");
			else
				return new ExtReturn(flag, "删除失败!");
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	@RequestMapping(value="/uploadFiles")
	@ResponseBody
	public void uploadFiles(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response,
			PrintWriter writer) {
		try {
		//	System.out.println("CURRENT_USER"+request.getSession().getAttribute(WebConstants.CURRENT_USER));	
			//文件的MD5
			logger.info("start");
			String node=request.getParameter("node");			
			String fileMD5=FileDigest.getFileMD5(file.getInputStream());
			logger.info(fileMD5);
			// 保存的地址
			String savePath = request.getSession().getServletContext().getRealPath("/upload");
			// 上传的文件名 //需要保存
			String uploadFileName = file.getOriginalFilename();
			// 获取文件后缀名 //需要保存
			String fileType = StringUtils.substringAfterLast(uploadFileName, ".");
			logger.debug("文件的MD5：{},上传的文件名：{},文件后缀名：{},文件大小：{}",
					new Object[] {fileMD5, StringUtils.substringBeforeLast(uploadFileName, "."), fileType, file.getSize() });
			// 以年月/天的格式来存放
			String dataPath = DateFormatUtils.format(new Date(), "yyyy-MM" + File.separator + "dd");
			
			if(null==node||node.equals("")||node.startsWith("xnode-"))
				dataPath=File.separator+"";
			else
				dataPath=node;
			
			String finalPath=dataPath+File.separator+StringUtils.substringBeforeLast(uploadFileName, ".")+("".equals(fileType) ? "" : "." + fileType);
									
			logger.debug("savePath:{},finalPath:{}", new Object[] { savePath, finalPath });
			File saveFile_tmp = new File(savePath + finalPath);
			// 判断文件夹是否存在，不存在则创建
			if (!saveFile_tmp.getParentFile().exists()) {
				saveFile_tmp.getParentFile().mkdirs();
			}
			
			File saveFile = new File(saveFile_tmp.getParent()+File.separator+FileOperateUtil.checkFileName(saveFile_tmp.getName(),saveFile_tmp.getParent()));
			
			// 写入文件
			FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
			// 保存文件的基本信息到数据库
			// 上传的文件名（带不带后缀名？）；文件后缀名；存放的相对路径
			
//			System.out.println("getFreeSpace:"+saveFile.getFreeSpace());
//			System.out.println("getTotalSpace:"+saveFile.getTotalSpace());
//			System.out.println("getUsableSpace:"+saveFile.getUsableSpace());
			
			
			String returnMsg = JackJson.fromObjectToJson(new ExtReturn(true, "磁盘剩余空间："+(saveFile.getUsableSpace() / 1073741824f)+" GB"));
			logger.debug("{}", returnMsg);
			writer.print(returnMsg);
		} catch (Exception e) {
			logger.error("Exception: ", e);
		} finally {
			writer.flush();
			writer.close();
		}
	}
	
	@RequestMapping(value="/downloadFiles")
	@ResponseBody
	public void downloadFiles(HttpServletRequest request, HttpServletResponse response) {
		InputStream input = null;
		ServletOutputStream output = null;
		try {
			// 保存的地址
			String savePath = request.getSession().getServletContext().getRealPath("/upload");			
			
			String finalPath=new String(request.getParameter("path").getBytes("ISO-8859-1"),"UTF-8");
			
//			String url="%E6%96%B0%E5%BB%BA%E6%96%87%E6%9C%AC%E6%96%87%E6%A1%A3.txt";
//			System.out.println(java.net.URLDecoder.decode(url,"UTF-8"));

			File downloadFile = new File(savePath + finalPath);
			
			// 判断是否存在这个文件
			if (!downloadFile.isFile()) {
				// 创建一个
				FileUtils.touch(downloadFile);
			}
			
			response.setContentType("application/octet-stream ;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-disposition", "attachment; filename=" + new String(downloadFile.getName().getBytes("GBK"),"ISO8859_1"));
			input = new FileInputStream(downloadFile);
			output = response.getOutputStream();
			IOUtils.copy(input, output);
			output.flush();
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
		} finally {
			
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
	}
	
	@RequestMapping(value="/downloadFiles_Zip")
	@ResponseBody
	public void downloadFiles_Zip(HttpServletRequest request, HttpServletResponse response) {
		InputStream input = null;
		ServletOutputStream output = null;
		try {
			String rootPath = request.getSession().getServletContext().getRealPath("/upload");
			String[] paths =request.getParameterValues("paths");
			File file = new File(rootPath);
			if(!file.exists()){
				file.mkdirs();
			}
			file = null;
			
			synchronized (this) {
			
				for (String path : paths) {
					file = new File(rootPath + new String(path.getBytes("ISO-8859-1"),"UTF-8"));
					FileOutputStream fos=new FileOutputStream(rootPath+File.separator+"tmp.zip");		
					ZipOutputStream zosm = new ZipOutputStream(fos);
					FileOperateUtil.compressionFiles(zosm, file, "");		
					zosm.close();
					fos.close();
				}
				
				File downloadFile = new File(rootPath+File.separator+"tmp.zip");
				
				response.setContentType("application/octet-stream ;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("content-disposition", "attachment; filename=" + new String(downloadFile.getName().getBytes("GBK"),"ISO8859_1"));
				input = new FileInputStream(downloadFile);
				output = response.getOutputStream();
				IOUtils.copy(input, output);
				output.flush();
			}
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
		} finally {
			
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
	}
	
	@RequestMapping(value="/createFolder")
	@ResponseBody
	public Object createFolder( HttpSession session, HttpServletRequest request) throws IOException{
		
		try {
			String rootPath = request.getSession().getServletContext().getRealPath("/upload");			
			String node=request.getParameter("node");
			if(null==node||node.startsWith("xnode-"))
				node="";
			String folder=request.getParameter("folder");
			if(FileOperateUtil.mkDirectory(rootPath+node+File.separator+folder))
				return new ExtReturn(true, "创建成功!");
			else
				return new ExtReturn(false, "创建失败!");
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	@RequestMapping(value="/getSpaceInfo")
	@ResponseBody
	public Object getSpaceInfo( HttpSession session, HttpServletRequest request) throws IOException{		
		try {
			String rootPath = request.getSession().getServletContext().getRealPath("/upload");			
			
			File spacefile=new File(rootPath);
			
			Float UsableSpace=(float) Math.round(100*spacefile.getUsableSpace()/1073741824f);
			Float TotalSpace=(float) Math.round(100*spacefile.getTotalSpace()/1073741824f);
			
			return new ExtReturn(true, "总:"+TotalSpace/100+"GB 可用:"+UsableSpace/100+"GB");
			
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	@RequestMapping(value="/paste")
	@ResponseBody
	public Object paste( HttpSession session, HttpServletRequest request) throws IOException{		
		try {
			String rootPath = request.getSession().getServletContext().getRealPath("/upload");			
			String source = request.getParameter("source");
			String node=request.getParameter("node");
			if(null==node||node.startsWith("xnode-"))
				node="";
			String method =request.getParameter("method");
			String cover_tmp  =request.getParameter("cover");
			if(null==cover_tmp||cover_tmp.equals(""))
				cover_tmp="true";
			
			Boolean cover=new Boolean(cover_tmp);
			
			String target=node;
			
			File source_file=new File(rootPath+source);
			File target_file=new File(rootPath+target+File.separator+source_file.getName());
			FileOperateUtil fop=new FileOperateUtil();
			
			if(source_file.getParent().equals(target_file.getParent())) //不允许同文件夹操作 没意义
				return new ExtReturn(false, "源文件与目标文件相同");			
			
			if(method.equals("cut")){
				
				if(source_file.isDirectory()){ //如果是文件夹
					fop.moveFolder(source_file.getAbsolutePath(), target_file.getAbsolutePath(), cover);
				}
				else{
					fop.moveFile(source_file.getAbsolutePath(), target_file.getAbsolutePath(), cover);
				}
				return new ExtReturn(true, "剪切成功");
				
			}
			else if(method.equals("copy")){
				
				if(source_file.isDirectory()){ //如果是文件夹
					fop.copyFolder(source_file.getAbsolutePath(), target_file.getAbsolutePath(), cover);
				}
				else{
					fop.copyFile(source_file.getAbsolutePath(), target_file.getAbsolutePath(), cover);
				}
				
				return new ExtReturn(true, "复制成功");
			}
			
			return new ExtReturn(false, "文件操作方式丢失,请重新选择复制或剪切");
				
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	@RequestMapping(value="/rename")
	@ResponseBody
	public Object rename( HttpSession session, HttpServletRequest request) throws IOException{		
		try {
			String rootPath = request.getSession().getServletContext().getRealPath("/upload");			
			String filename = request.getParameter("filename");
			String newname  = request.getParameter("newname");
			
			File oldfile=new File(rootPath+filename);
			File newfile=new File(oldfile.getParent()+File.separator+newname);
			
			if(oldfile.exists()){
				if(newfile.exists())
					return new ExtReturn(false, "已包含同名文件");
				else{
					if(oldfile.renameTo(newfile))
						return new ExtReturn(true, "重命名成功");
					else
						return new ExtReturn(false, "重命名失败");
				}
			}
			else			
				return new ExtReturn(false, "源文件不存在");
				
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	
	
	public String compressionFiles() throws IOException {
		return null;
	}
	
	
	
	public String decompressionFiles() {
		return null;
	}
}
