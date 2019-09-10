package complex.complexBootWeb.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.common.ExcelTitleVO;
import complex.complexBootWeb.model.vo.common.MsgVO;


/**
 * excel导出帮助类
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class ExcelExportUtil<T> {

	public final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

	/** excel列默认宽度 **/
	public final int EXCEL_COLUMN_DEFAULT_WIDTH = 2540;
	/** 中文字符串 **/
	public final String CHNIESE_STR = "chniese";
	/** 英文字符串 **/
	public final String ENGLISH_STR = "english";
	/** 数字字符串 **/
	public final String NUMBER_STR = "number";
	/** 导出的数据 **/
	private List<T> dataList;
	/** 元数据 **/
	private MetaClass metaClass;
	/** 标题信息 **/
	private List<ExcelTitleVO> titleList;
	/** 输出流 **/
	private HttpServletResponse response;
	/** 导出excel标题 **/
	private String excelTitle;
	/** 导出标题模板路径 **/
	private String templatePath;

	// 反射执行，无参数
	private final Object[] NO_ARGUMENTS = new Object[0];

	public ExcelExportUtil() {
	}

	public ExcelExportUtil(List<T> dataList, HttpServletResponse response, String templatePath) {
		this.dataList = dataList;
		this.response = response;
		this.templatePath = templatePath;
	}

	public ExcelExportUtil(List<T> dataList, HttpServletResponse response) {
		this.dataList = dataList;
		this.response = response;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getExcelTitle() {
		return excelTitle;
	}

	public void setExcelTitle(String excelTitle) {
		this.excelTitle = excelTitle;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public List<ExcelTitleVO> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<ExcelTitleVO> titleList) {
		this.titleList = titleList;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public MetaClass getMetaClass() {
		return metaClass;
	}

	public void setMetaClass(MetaClass metaClass) {
		this.metaClass = metaClass;
	}

	public Logger getLogger() {
		return logger;
	}

	public int getEXCEL_COLUMN_DEFAULT_WIDTH() {
		return EXCEL_COLUMN_DEFAULT_WIDTH;
	}

	public String getCHNIESE_STR() {
		return CHNIESE_STR;
	}

	public String getENGLISH_STR() {
		return ENGLISH_STR;
	}

	public String getNUMBER_STR() {
		return NUMBER_STR;
	}

	public Object[] getNO_ARGUMENTS() {
		return NO_ARGUMENTS;
	}

	public void writeExcel(Workbook work) throws IOException {
		try (OutputStream out = response.getOutputStream()) {
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(excelTitle + ".xlsx", "UTF-8"))));
			work.write(out);
			out.flush();
		} catch (IOException e) {
			logger.error("文件输入流错误", e);
		}
	}

	/** 设置列的宽度 **/
	public void setColumnWidth(Sheet sheet, Map<Integer, Integer> widthMap) {
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
	public CellStyle createCellStyle(Workbook workbook, short align) {
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
	public void calculateColumnWidth(Map<Integer, Integer> widthMap, int columnNum, ExcelTitleVO titleVO) {
		calculateColumnWidth(widthMap, columnNum, titleVO.getChineseTitle(), titleVO);
	}

	/** 根据字符串计算列宽 **/
	public void calculateColumnWidth(Map<Integer, Integer> widthMap, int columnNum, String cellValue, ExcelTitleVO titleVO) {
		// 为空不许要计算
		if (StringUtils.isBlank(cellValue)) {
			return;
		}
		// 默认宽度是2540
		Integer width = widthMap.get(columnNum);
		if (null == width) {
			width = EXCEL_COLUMN_DEFAULT_WIDTH;
		}
		//
		switch (titleVO.getDataType()) {
		case CHNIESE_STR:// 一个汉子宽度大概为630
			if (cellValue.length() * 600 > width) {
				widthMap.put(columnNum, cellValue.length() * 600);
			}
			break;
		case ENGLISH_STR:// 一个字母宽度大概为630
			if (cellValue.length() * 300 > width) {
				widthMap.put(columnNum, cellValue.length() * 300);
			}
			break;
		case NUMBER_STR:// 一个数字宽度大概为630
			if (cellValue.length() * 300 > width) {
				widthMap.put(columnNum, cellValue.length() * 300);
			}
			break;
		default:
			if (cellValue.length() * 400 > width) {
				widthMap.put(columnNum, cellValue.length() * 320);
			}
			break;
		}
	}

	/**
	 * 数据解析为 excel
	 * 
	 * @param dataList
	 *            数据集合
	 * @param templatePath
	 *            excel 列配置路径
	 */
	public ResultVO<?> parse() {
		ResultVO<?> result = new ResultVO<>();
		if (null == dataList || dataList.isEmpty()) {
			logger.debug("没有需要导出的数据dataList={}", dataList);
			result.setFailMsg("没有需要导出的数据");
			return result;
		}
		if (null == response) {
			logger.debug("导出流为空response={}", response);
			result.setFailMsg("导出流为空");
			return result;
		}
		// 所有参数进入 Properties，解析xml文件使用
		MetaClass metaClassOriginal = MetaClass.forClass(dataList.get(0).getClass(), new DefaultReflectorFactory());
		this.setMetaClass(metaClassOriginal);
		if (null == titleList && StringUtils.isBlank(templatePath)) {
			logger.debug("标题信息为空templatePath={},titleList={}", templatePath, titleList);
			result.setFailMsg("标题信息为空");
			return result;
		} else if (null == titleList) {
			// 先解析列，看T中是否有 需要个get方法
			List<ExcelTitleVO> titleVOList = PropertiesUtils.proToExcelTitleList(templatePath);
			List<ExcelTitleVO> titleSnoList = titleVOList.stream().sorted(Comparator.comparing(ExcelTitleVO::getSno)).collect(Collectors.toList());
			this.setTitleList(titleSnoList);
		}
		// 先验证标题
		ResultVO<List<ExcelTitleVO>> resultTitle = checkTitle();
		if (!resultTitle.isSuccess()) {
			logger.error("标题验证未通过msg={}", resultTitle.getMsg());
			return resultTitle;
		}
		return result;
	}

	// 生成数据
	public void exportExcel() {
		createData();
	}

	/** 匹配excel标题行 **/
	public ResultVO<List<ExcelTitleVO>> checkTitle() {
		// 获取所有的get方法
		MsgVO msg = new MsgVO();
		titleList.stream().forEach(title -> hasKey(title, msg));
		ResultVO<List<ExcelTitleVO>> result = new ResultVO<>();
		if (msg.getError().length() > 0) {
			logger.error("解析excel导入标题失败msg={}", msg.getError());
			result.setFailMsg(msg.getError());
		}
		result.setData(titleList);
		return result;
	}

	/**
	 * 在线下载excel 从数据库里面检索出来，List<Map>,及显示字段即Map里面的key。来导出excel
	 * 
	 * @param fieldList
	 *            字段字段集合
	 * @param dataMap
	 *            数据结合
	 * @param out
	 * @return
	 */
	public ResultVO<?> createData() {
		logger.info("批量转账excel导出dataList={}", dataList);
		ResultVO<?> result = new ResultVO<>();
		// 计算列宽使用，（外国人的名字比较长，支行名字比较长）
		Map<Integer, Integer> widthMap = new HashMap<>();
		try {
			Workbook workbook = new XSSFWorkbook();
			// 在Excel 工作簿中建一工作表
			Sheet sheet = workbook.createSheet("Sheet1");
			// 创建标题行
			createTitleCommon(workbook, sheet, widthMap);
			// 创建数据
			createDataCommon(workbook, sheet, widthMap);
			// 设置列的宽度
			setColumnWidth(sheet, widthMap);
			// 导入excel
			writeExcel(workbook);

		} catch (IOException e) {
			logger.error("生成下载的excel文件时出错error={}", e);
		}
		return result;
	}

	/** 判断类是否包含列 **/
	public void hasKey(ExcelTitleVO titleVO, MsgVO msg) {
		try {
			metaClass.getGetInvoker(titleVO.getEnglishTitle());
		} catch (ReflectionException e) {
			logger.error("解析excel列异常error={}", e);
			msg.appendDirectly(titleVO.getChineseTitle()).appendDirectly(":字段不存在\n");
		}
	}

	/** 创建数据 **/
	public void createDataCommon(Workbook workbook, Sheet sheet, Map<Integer, Integer> widthMap) {
		AtomicInteger rowNum = new AtomicInteger(1);
		dataList.stream().forEach(data -> {
			Row dataRow = sheet.createRow(rowNum.getAndIncrement());
			// cel拉个屎
			CellStyle cellStyle = createCellStyle(workbook, CellStyle.ALIGN_LEFT);
			// vo 生成cell数据
			dataToRow(dataRow, cellStyle, data, widthMap);
		});
	}

	/** 创建标题 common **/
	public void createTitleCommon(Workbook workbook, Sheet sheet, Map<Integer, Integer> widthMap) {
		CellStyle cellStyleTitle = createCellStyle(workbook, CellStyle.ALIGN_LEFT);
		// 标题行 标题
		Row rowZero = sheet.createRow((short) 0);
		rowZero.setHeight((short) 500);
		AtomicInteger num = new AtomicInteger(0);
		titleList.stream().forEach(titleVO -> {
			Cell cell1 = rowZero.createCell(num.get());
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			cell1.setCellStyle(cellStyleTitle);
			cell1.setCellValue(titleVO.getChineseTitle());
			// 计算列宽度
			// if (titleVO.getChineseTitle().length() > 4) {
			calculateColumnWidth(widthMap, num.getAndIncrement(), titleVO);
			// }
		});

	}

	/** 数据转换为 excel的row **/
	public void dataToRow(Row dataRow, CellStyle cellStyle, T data, Map<Integer, Integer> widthMap) {
		AtomicInteger cellNum = new AtomicInteger(0);
		for (ExcelTitleVO titleVO : titleList) {
			Cell dataCell1 = dataRow.createCell(cellNum.get());
			dataCell1.setCellStyle(cellStyle);
			dataCell1.setCellValue(cellValue(data, titleVO));
			// 计算列宽度
			calculateColumnWidth(widthMap, cellNum.getAndIncrement(), dataCell1.getStringCellValue(), titleVO);
		}
	}

	/** 根据 英文字段获取 属性值 **/
	public String cellValue(T data, ExcelTitleVO titleVO) {
		Invoker method = metaClass.getGetInvoker(titleVO.getEnglishTitle());
		if (null != method) {
			try {
				return method.invoke(data, NO_ARGUMENTS).toString();
			} catch (IllegalAccessException e) {
				logger.error("反射获取数据失败error1={}", e);
			} catch (InvocationTargetException e) {
				logger.error("反射获取数据失败error2={}", e);
			}
		}
		return null;
	}
}
