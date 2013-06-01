package com.authority.common.jackjson.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import com.authority.common.utils.ClassLoaderUtil;
import com.authority.common.utils.FileDigest;
import com.authority.common.utils.PropertiesHolder;

/**
 * 
 * 
 * @author chenxin
 * @date 2011-5-12 下午02:31:26
 */
public class MainTest {
	public static void main(String[] args) throws Exception {
		test1();
		//DigestUtils.md5Hex(DigestUtils.md5Hex(password){account});
		String str = "";
		System.out.println(DigestUtils.md5Hex("123456"));
		System.out.println(DigestUtils.shaHex(str));
		System.out.println(DigestUtils.sha256Hex(str));
		System.out.println(DigestUtils.sha384Hex(str));
		System.out.println(DigestUtils.sha512Hex(str));
		
		long start=System.currentTimeMillis();
		System.out.println(FileDigest.getFileMD5(new File("D:\\Personal\\Music\\崔子格 - 皇上吉祥.mp3")));
		System.out.println("耗时(毫秒)："+(System.currentTimeMillis()-start));
		Map<String, String> maps=FileDigest.getDirMD5(new File("E:\\BCKF\\apache-tomcat-6.0.29\\logs\\javawebapp"), true);
		for (Entry<String, String> entry : maps.entrySet()) {
			String key = entry.getKey();
			String md5 = entry.getValue();
			System.out.println(md5+" "+key);
		}
	}

	@SuppressWarnings({ "deprecation", "unused" })
	private static void test1() throws JsonGenerationException, JsonMappingException, IOException {
		User user = new User();
		user.setAge(23);
		user.setName("cx");
		user.setPassword("123456");
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.serializeAllExcept("password"));
		// SimpleBeanPropertyFilter.filterOutAllExcept("password"));
		// and then serialize using that filter provider:
		String json = mapper.filteredWriter(filters).writeValueAsString(user);
		System.out.println(json);
		
		//反序列化
		User user_ = mapper.readValue(json, User.class);
		System.out.println(user_.getName());
		
		InputStreamReader reader = null;
		Properties properties = new Properties();
		InputStream is = ClassLoaderUtil.getResourceAsStream("config/others/config.properties", MainTest.class);
		if (null != is) {
			reader = new InputStreamReader(is, "UTF-8");
			properties.load(reader);
		}
		PropertiesHolder p = new PropertiesHolder();
		p.setProperties(properties);
		System.out.println(p.getProperty("system.url"));
	}
}
