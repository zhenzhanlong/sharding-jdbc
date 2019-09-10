package complex.complexBootWeb.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import complex.complexBootWeb.model.vo.common.ExcelTitleVO;
import net.sf.json.JSONObject;

/****
 * 将传入的properties文件解析为Map数据
 * 
 * @author lenovo
 *
 */
public class PropertiesUtils {

	private static Logger log = LoggerFactory.getLogger(PropertiesUtils.class);
	/** 编码 **/
	private static final String CHARSET_NAME = "utf-8";
	private static Properties prop = new Properties();

	private PropertiesUtils() {
	}

	//// 根据路径解析文件成Map
	public static Map<String, String> proToMap(String path) {
		if (StringUtils.isBlank(path)) {
			return null;
		}
		Map<String, String> map = new HashMap<>();
		Properties properties = new Properties();
		try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(path)) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, CHARSET_NAME));
			properties.load(bf);
		} catch (Exception e) {
			log.error("解析properties文件异常error={}", e);
		}

		Enumeration<?> enumPro = properties.keys();
		while (enumPro.hasMoreElements()) {
			Object obj = enumPro.nextElement();
			map.put(obj.toString(), properties.getProperty(obj.toString()));
		}
		return map;
	}

	//// 根据路径解析文件成List<Map<>>
	public static List<Map<String, String>> proToMaps(String path) {
		if (StringUtils.isBlank(path)) {
			return null;
		}
		List<Map<String, String>> mapList = new ArrayList<>(2);
		// 中，英文对应关系
		Map<String, String> map1 = new HashMap<>();
		// 英文与数值类型对应关系
		Map<String, String> map2 = new HashMap<>();
		Properties properties = new Properties();
		try {
			InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(path);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, CHARSET_NAME));
			properties.load(bf);
			inputStream.close(); // 关闭流
		} catch (Exception e) {
			log.error("解析数据异常", e);
		}

		Enumeration<?> enumPro = properties.keys();
		while (enumPro.hasMoreElements()) {
			Object obj = enumPro.nextElement();
			String[] values = properties.getProperty(obj.toString()).split(",");
			map1.put(obj.toString(), values[0]);
			map2.put(values[0], values[1]);
		}
		mapList.add(map1);
		mapList.add(map2);
		return mapList;
	}

	/**
	 * 将json字符串转换为Map对象
	 * 
	 * @param json
	 * @return map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) {
		if (json == null) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<>();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		Object value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.get(key);
			resultMap.put(key, value);
		}
		return resultMap;
	}

//	static {
//		try (InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("/application.properties")) {
//			prop.load(in);
//		} catch (Exception e) {
//			log.error("解析application.properties文件异常error={}", e);
//		}
//	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	//// 根据路径解析文件成List<Map<>>
	public static List<ExcelTitleVO> proToExcelTitleList(String path) {
		if (StringUtils.isBlank(path)) {
			log.error("路径参数为空path={}", path);
			return new ArrayList<>(0);
		}
		List<ExcelTitleVO> titleList = new ArrayList<>();

		Properties properties = new Properties();
		try {
			InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(path);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, CHARSET_NAME));
			properties.load(bf);
			inputStream.close(); // 关闭流
		} catch (Exception e) {
			log.error("解析数据异常", e);
			return new ArrayList<>(0);
		}
		Enumeration<?> enumPro = properties.keys();
		while (enumPro.hasMoreElements()) {
			Object obj = enumPro.nextElement();
			String[] values = properties.getProperty(obj.toString()).split(",");
			ExcelTitleVO titleVO = new ExcelTitleVO();
			// 中文标题
			titleVO.setChineseTitle(values[0]);
			if (null != values[1]) {
				titleVO.setSno(Integer.parseInt(values[1]));
			}
			if(null!=values[2]) {
				titleVO.setDataType(values[2]);
			}
			// 英文标题
			titleVO.setEnglishTitle(obj.toString());
			// 加入集合
			titleList.add(titleVO);
		}
		return titleList;
	}
}
