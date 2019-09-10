package complex.complexBootWeb.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.complex.practice.vo.ResultExcelVO;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ExcelUtils {
	public static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
	/** 中文字符串 **/
	public static final String CHNIESE_STR = "CHNIESE_STR";
	/** 英文字符串 **/
	public static final String ENGLISH_STR = "ENGLISH_STR";
	/** 数字字符串 **/
	public static final String NUMBER_STR = "NUMBER_STR";
	/** excel列默认宽度 **/
	public static final int EXCEL_COLUMN_DEFAULT_WIDTH = 2540;
	/** excel模板设置：标题 **/
	public static final String EXCEL_EXPORT_MOULD_TEXT_TEXT = "text";

	private ExcelUtils() {

	}

	/**
	 * 解析excle 生成数据集
	 * 
	 * @param in
	 *            数据流
	 * @param fieldMap
	 *            key:excle的列名字,value:数据库的列名字
	 * @param targetMap
	 *            key:数据库的列名字,value:列类型
	 * @return
	 */
	public static ResultExcelVO fileToExcel(MultipartFile file, Map<String, String> fieldMap, Map<String, String> targetMap) {

		ResultExcelVO result = new ResultExcelVO();
		if (file == null) {
			return null;
		}
		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			logger.error("生成输入流异常", e);
			result.setSuccess(false);
			result.setMsg("生成输入流异常");
			return result;
		}
		return fileToExcel(inputStream, fieldMap, targetMap);
	}

	/**
	 * 解析excle 生成数据集
	 * 
	 * @param in
	 *            数据流
	 * @param fieldMap
	 *            key:excle的列名字,value:数据库的列名字
	 * @param targetMap
	 *            key:数据库的列名字,value:列类型
	 * @return
	 */
	public static ResultExcelVO fileToExcel(InputStream in, Map<String, String> fieldMap, Map<String, String> targetMap) {
		ResultExcelVO result = new ResultExcelVO();
		if (in == null) {
			return null;
		}
		Workbook workbook = null;
		// / 数据集合
		List<Map<String, Object>> dataList = null;
		try {
			workbook = WorkbookFactory.create(in);
			// /只有一个sheet表
			Sheet sheet = workbook.getSheetAt(0);
			// 获取excel头
			Map<String, String> excelHeader = getExcelHeader(sheet);
			// 获取excel头内容
			Collection<String> header = excelHeader.values();
			// 校验表头
			ResultExcelVO checkExcleHead = checkExcleHead(fieldMap, header);
			if (checkExcleHead != null) {
				return checkExcleHead;
			}
			dataList = new ArrayList<>();
			result.setListMap(dataList);
			// /第一行是标题
			for (int rowNum = sheet.getFirstRowNum() + 1, rowLen = sheet.getLastRowNum(); rowNum <= rowLen; rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					break;
				}
				// 数据封装成map
				Map<String, Object> tempMap = new HashMap<>();
				// 数据所在excel行数
				tempMap.put("excelLineNum", rowNum);
				// 对一行进行列递归
				boolean boo = true;
				for (int i = 0; i < row.getLastCellNum(); i++) {
					// 判断当前列是不是要解析的列
					String colnumname = excelHeader.get(i + "");
					if (colnumname != null) {
						// 或者当前单元格的值
						String colnumdbname = fieldMap.get(colnumname);
						Object colnumvalue = getCellValue(row.getCell(i), targetMap.get(colnumdbname));
						logger.info("解析第{}行第{}列, colnumname={},colnumdbname={},colnumvalue={}", rowNum, i, colnumname, colnumdbname, colnumvalue);
						// 如果某行的第一列出现空值,解析结束
						if ((i < row.getFirstCellNum()) && boo) {
							// logger.error("解析列第{}行第{}列值为空,解析行结束,不会再解析后面的行", rowNum, i);
							return result;
						} else if (boo && (null == colnumvalue || "null".equals(colnumvalue))) {
							logger.error("解析列第{}行第{}列值为空,解析行结束,不会再解析后面的行", rowNum, i);
							return result;
						} else {
							boo = false;
						}
						tempMap.put(colnumdbname, colnumvalue);

					} else {
						logger.info("非解析第{}行第{}列,colnumname={}", rowNum, i, colnumname);
					}

				}
				dataList.add(tempMap);
				result.setSuccess(true);
			}

		} catch (Exception e) {
			logger.error("解析excle异常", e);
			result.setSuccess(false);
		}
		return result;
	}

	private static ResultExcelVO checkExcleHead(Map<String, String> fieldMap, Collection<String> header) {
		// 错误提示
		StringBuilder errorresult = new StringBuilder();
		// 验证表头，必须包含要求的列
		AtomicInteger i = new AtomicInteger(0);
		header.forEach(colname -> {
			if (null == fieldMap.get(colname)) {
				if (i.get() > 0) {
					errorresult.append(",").append(colname);
				} else {
					errorresult.append(colname);
				}
			}
			i.incrementAndGet();

		});

		if (errorresult.length() > 0) {
			errorresult.append(";这些字段系统不能解析");
			logger.error("字段不能解析:{}", errorresult.toString());
			return new ResultExcelVO(false, null, errorresult.toString());
		} else {
			return null;
		}
	}

	/**
	 * 返回excel的标题 1.遇到空的头就不继续解析 2.隐藏列不去解析
	 * 
	 * @param sheet
	 * @return Map<String, String> excle列标和列的值,方便行的解析
	 */
	private static Map<String, String> getExcelHeader(Sheet sheet) {

		Row row = sheet.getRow(sheet.getFirstRowNum());
		Map<String, String> headList = new HashMap<>();
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (sheet.isColumnHidden(i)) {
				logger.info("row{}是隐藏的", i);
				continue;
			}
			if (cell != null) {
				Object cellvalue = getCellValue(cell, "str");
				if (cellvalue != null) {
					headList.put(i + "", cellvalue.toString());
				} else {
					return headList;
				}
			} else {

				return headList;
			}
		}
		return headList;
	}

	/**
	 * 获取一个单元格的值
	 * 
	 * @param cell
	 *            单元格
	 * @param tragetType
	 *            目标类型 num,str
	 * @return
	 */
	private static Object getCellValue(Cell cell, String tragetType) {
		logger.info("将要解析cell={}", cell);
		Object returnObj = null;
		if (cell != null) {
			int cellType = cell.getCellType();
			logger.info("将要解析cell={},type={}", cell, cellType);
			switch (cellType) {
			case HSSFCell.CELL_TYPE_FORMULA:// 函数列
				returnObj = parseFunction(cell, tragetType);
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:// 如果是数字
				returnObj = parseNumeric(cell, tragetType);
				break;
			case HSSFCell.CELL_TYPE_STRING: // 如果是字符串
				returnObj = parseString(cell, tragetType);
				break;
			default:
				logger.error("未处理的类型cell={},type={}", cell, cellType);
				break;
			}

		}
		return returnObj;
	}

	/**
	 * 解析excle字符串
	 * 
	 * @param cell
	 *            单元格
	 * @param targetType
	 *            目标类型 num,str
	 * @return
	 */
	private static Object parseString(Cell cell, String targetType) {

		String str = cell.getStringCellValue();// ValidateUtil.trimAll(cell.getStringCellValue()); 要求去掉特殊处理
		switch (targetType) {
		case "num":
			return new BigDecimal(str);
		case "str":
			return str;// specialCharacter(str); 要求去掉特殊处理
		default:
			return null;
		}
	}

	private static String specialCharacter(String str) {

		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			return m.replaceAll("");
		} else {
			return str;
		}

	}

	/**
	 * 解析excle数值型
	 * 
	 * @param cell
	 * @param targetType
	 *            目标类型 num,str
	 * @return
	 */
	private static Object parseNumeric(Cell cell, String targetType) {

		BigDecimal doubleTemp = BigDecimal.valueOf(cell.getNumericCellValue());
		switch (targetType) {
		case "num":
			return doubleTemp;
		case "str":
			return doubleTemp.setScale(0, BigDecimal.ROUND_DOWN).toString();
		default:
			return null;
		}

	}

	/**
	 * 解析excle函数型
	 * 
	 * @param cell
	 * @param targetType
	 *            目标类型 num,str
	 * @return
	 */
	private static Object parseFunction(Cell cell, String targetType) {

		int calltype = cell.getCachedFormulaResultType();
		Object returnObj = null;
		switch (calltype) {
		case HSSFCell.CELL_TYPE_NUMERIC:// 如果是数字
			returnObj = parseNumeric(cell, targetType);
			break;
		case HSSFCell.CELL_TYPE_STRING: // 如果是字符串
			returnObj = parseString(cell, targetType);
			break;
		default:
			return returnObj;
		}
		return returnObj;

	}

	/**
	 * Map 数据转换为UserVO 对象
	 * 
	 * @param list
	 * @param cls
	 * @return
	 * 
	 * 		public static List<UserVO> dataToUser(List<Map<String,Object>>
	 *         list,Class<UserVO> cls) { List<UserVO> userList = new
	 *         ArrayList<UserVO>(); UserVO user = null; for(Map<String,Object>
	 *         map:list){ user = new UserVO(); try { BeanUtils.populate(user, map);
	 *         } catch (Exception e) { e.printStackTrace(); } userList.add(user); }
	 *         return userList; }
	 */
	public static Workbook readExcel(String filePath) {
		InputStream in = null;
		Workbook work = null;
		try {
			in = new FileInputStream(filePath);
			work = new HSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			logger.error("文件路径错误", e);
		} catch (IOException e) {
			logger.error("文件输入流错误", e);
		}
		return work;
	}

	public static void writeExcel(HttpServletResponse response, Workbook work, String fileName) throws IOException {
		try (OutputStream out = response.getOutputStream()) {
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName + ".xlsx", "UTF-8"))));
			work.write(out);
		} catch (IOException e) {
			logger.error("文件输入流错误", e);
		}
	}

	/** 生成标题 **/
	public static Map<String, String[]> createBankAccountNo(String titleStr) {
		Map<String, String[]> titleMap = new HashMap<>();
		String[] titleArray = titleStr.split(",");
		// 英文标题
		String[] englishTitle = new String[titleArray.length];
		// 中文标题
		String[] chineseTitle = new String[titleArray.length];
		// 生成标题数组
		for (int i = 0, l = titleArray.length; i < l; i++) {
			String[] tempArray = titleArray[i].split(":");
			englishTitle[i] = tempArray[0];
			chineseTitle[i] = tempArray[1];
		}
		titleMap.put("englishTitle", englishTitle);
		titleMap.put("chineseTitle", chineseTitle);
		return titleMap;
	}

	/** 设置列的宽度 **/
	public static void setColumnWidth(Sheet sheet, Map<Integer, Integer> widthMap) {
		// 设置列的列宽
		Set<Integer> set = widthMap.keySet();
		for (Integer key : set) {
			Integer width = widthMap.get(key);
			if (null != width) {
				sheet.setColumnWidth(key, width);
			} else {
				sheet.setColumnWidth(key, EXCEL_COLUMN_DEFAULT_WIDTH);
			}
		}
	}

	/** 设置单元格格式，添加边框，字体（居中，居左，居右） **/
	public static CellStyle createCellStyle(Workbook workbook, short align) {
		// 生成单元格格式类
		CellStyle cellStyle = workbook.createCellStyle();
		// /添加边框设置居中
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);// 文字自动换行
		cellStyle.setAlignment(align);// 左右对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直对齐
		return cellStyle;
	}

	/** 根据字符串计算列宽 **/
	public static void calculateColumnWidth(Map<Integer, Integer> widthMap, int columnNum, String value, String strType) {
		// 为空不许要计算
		if (StringUtils.isBlank(value)) {
			return;
		}
		// 默认宽度是2540
		Integer width = widthMap.get(columnNum);
		if (null == width) {
			width = EXCEL_COLUMN_DEFAULT_WIDTH;
		}
		//
		switch (strType) {
		case CHNIESE_STR:// 一个汉子宽度大概为630
			if (value.length() * 600 > width) {
				widthMap.put(columnNum, value.length() * 600);
			}
			break;
		case ENGLISH_STR:// 一个字母宽度大概为630
			if (value.length() * 300 > width) {
				widthMap.put(columnNum, value.length() * 300);
			}
			break;
		case NUMBER_STR:// 一个数字宽度大概为630
			if (value.length() * 300 > width) {
				widthMap.put(columnNum, value.length() * 300);
			}
			break;
		default:
			if (value.length() * 320 > width) {
				widthMap.put(columnNum, value.length() * 320);
			}
			break;
		}
	}

	/** 创建标题 common **/
	public static void createTitleCommon(Workbook workbook, Sheet sheet, Map<Integer, Integer> widthMap, String[] titles) {
		CellStyle cellStyleTitle = createCellStyle(workbook, CellStyle.ALIGN_LEFT);
		// 标题行 标题
		Row rowZero = sheet.createRow((short) 0);
		rowZero.setHeight((short) 500);
		for (int i = 0; i < titles.length; i++) {
			// 姓名
			Cell cell1 = rowZero.createCell(i);
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			cell1.setCellStyle(cellStyleTitle);
			cell1.setCellValue(titles[i]);
			// 计算列宽度
			if (titles[i].length() > 4) {
				calculateColumnWidth(widthMap, 1, titles[i], CHNIESE_STR);
			}
		}

	}

	/** 导入的文件，根据模板解析为 Map 数据 **/
	public static ResultExcelVO<Map<String, Object>> fileToData(MultipartFile file, String templatePath) {
		ResultExcelVO<Map<String, Object>> result = new ResultExcelVO<>();
		if (file == null) {
			logger.error("上传上来的文件为空file={}", file);
			result.setMsg("文件为空");
			result.setSuccess(false);
			return result;
		}
		logger.info("转账用户导入fileName={}", file.getName());
		String fileName = file.getOriginalFilename();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!("xlsx".equals(prefix))) {
			logger.error(" 文件格式不对,目前只支持2007及以上版本Excel!，只能验证xlsx prefix={}", prefix);
			result.setMsg(" 文件格式不对 prefix=" + prefix + "(目前只支持2007及以上版本Excel!，只能验证xlsx，)");
			result.setSuccess(false);
			return result;
		}
		try {
			List<Map<String, String>> listMap = PropertiesUtils.proToMaps(templatePath);
			result = ExcelUtils.fileToExcel(file, listMap.get(0), listMap.get(1));
		} catch (Exception e) {
			logger.error("解析excel异常error={}", e);
			result.setMsg("excel解析异常，导入数据失败！");
			result.setSuccess(false);
		}
		return result;
	}

}
