package complex.complexBootWeb.web.salary.excel.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.complex.practice.type.consumer.PaperType;
import com.complex.practice.type.excel.AuditStatus;
import com.complex.practice.type.excel.ExcelDataStatusType;
import com.complex.practice.type.excel.VerifyStatusType;
import com.complex.practice.util.DateUtil;
import com.complex.practice.vo.ResultExcelVO;

import complex.complexBootWeb.biz.salary.excel.IExcelOriginalDataBiz;
import complex.complexBootWeb.constants.ManageConstants;
import complex.complexBootWeb.model.form.salary.excel.ExcelOriginalDataForm;
import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.common.MImpExcelErrorVO;
import complex.complexBootWeb.model.vo.login.MConsumerLoginVO;
import complex.complexBootWeb.model.vo.salary.excel.ExcelOriginalDataVO;
import complex.complexBootWeb.util.ExcelUtils;
import complex.complexBootWeb.web.salary.excel.IExcelOriginalDataWebService;

/**
 * 导入发薪excel原数据表
 *
 * @author Administrator
 *
 */
@Service("excelOriginalDataWebServiceImpl")
public class ExcelOriginalDataWebServiceImpl implements IExcelOriginalDataWebService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 导入发薪excel原数据表
	@Resource(name = "excelOriginalDataBizImpl")
	private IExcelOriginalDataBiz excelOriginalDataBizImpl;


	@Override
	public ResultVO<ExcelOriginalDataForm> add(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("新增导入发薪excel原数据表form={},loginVO={}", paraForm, loginVO);
		return excelOriginalDataBizImpl.add(paraForm, loginVO);
	}

	@Override
	public ResultVO<?> update(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("修改导入发薪excel原数据表form={},loginVO={}", paraForm, loginVO);
		return excelOriginalDataBizImpl.update(paraForm, loginVO);
	}

	@Override
	public ResultVO<?> deleteById(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("删除导入发薪excel原数据表form={},loginVO={}", paraForm, loginVO);
		return excelOriginalDataBizImpl.deleteById(paraForm, loginVO);
	}

	@Override
	public ResultVO<ExcelOriginalDataVO> queryById(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("根据id查询导入发薪excel原数据表form={}", paraForm);
		return excelOriginalDataBizImpl.queryById(paraForm);
	}

	@Override
	public ResultVO<List<ExcelOriginalDataVO>> queryList(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("根据form查询导入发薪excel原数据表list,form={}", paraForm);
		return excelOriginalDataBizImpl.queryList(paraForm);
	}

	@Override
	public ResultVO<List<ExcelOriginalDataVO>> page(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("根据form查询导入发薪excel原数据表列表,form={}", paraForm);
		return excelOriginalDataBizImpl.page(paraForm, loginVO);
	}

	/**
	 * 停用、下架
	 *
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	@Override
	public ResultVO<?> disable(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("禁用导入发薪excel原数据表 paraForm={}", paraForm);
		ResultVO<?> result = new ResultVO<>();
		if (null == paraForm.getId()) {
			result.setSuccess(false);
			result.setMsg("参数为空");
			return result;
		}
		try {
			return excelOriginalDataBizImpl.editStatus(paraForm, loginVO);
		} catch (Exception e) {
			log.error("禁用导入发薪excel原数据表失败 error={}", e);
			result.setSuccess(false);
			result.setMsg("禁用失败");
			return result;
		}
	}

	/**
	 * 启用、上架
	 *
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	@Override
	public ResultVO<?> enable(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("启用导入发薪excel原数据表 paraForm={}", paraForm);
		ResultVO<?> result = new ResultVO<>();
		if (null == paraForm.getId()) {
			result.setSuccess(false);
			result.setMsg("参数为空");
			return result;
		}
		try {
			return excelOriginalDataBizImpl.editStatus(paraForm, loginVO);
		} catch (Exception e) {
			log.error("禁用导入发薪excel原数据表失败 error={}", e);
			result.setSuccess(false);
			result.setMsg("禁用失败");
			return result;
		}
	}

	/**
	 * 数据进入转账信息表
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ResultVO<?> inTransfer(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("数据进入转账信息表id={}", paraForm.getId());
		return null;
	}

	/**
	 * 数据进入转账信息表(批量)
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ResultVO<?> inTransferBatch(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("批量数据进入转账信息表id={}", paraForm.getId());
		return excelOriginalDataBizImpl.queryList(paraForm);
	}

	/**
	 * 数据进入转账信息表（一个批次）
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ResultVO<?> batchNoInTransferBatch(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("批量数据进入转账信息表id={}", paraForm.getId());
		return excelOriginalDataBizImpl.queryListByBatchNo(paraForm);
	}

	/**
	 * excel 数据导入
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<List<MImpExcelErrorVO>> excelImport(MultipartFile file, MConsumerLoginVO loginVO) {
		log.debug("导入excel，开始解析数据fileName={},loginVO={}", file.getName(), loginVO);
		ResultExcelVO<Map<String, Object>> resultExcel = ExcelUtils.fileToData(file, ManageConstants.PERSONAL_TRANSFER_IMPORT_FILE_PATH);
		if (!resultExcel.isSuccess()) {
			ResultVO<List<MImpExcelErrorVO>> result = new ResultVO<>(false, null, resultExcel.getMsg());
			result.setData(null);
			return result;
		}
		log.debug("信息开始检测、入库size={}", resultExcel.getListMap().size());
		return this.validateExcelData(resultExcel.getListMap(),loginVO);
	}
	/**
	 * 验证用户信息，生成入库信息
	 * 
	 * @param paraList
	 * @return
	 */
	public ResultVO<List<MImpExcelErrorVO>> validateExcelData(List<Map<String, Object>> paraList, MConsumerLoginVO loginVO) {
		ResultVO<List<MImpExcelErrorVO>> result = new ResultVO<>();
		List<MImpExcelErrorVO> errorList = new ArrayList<>();
		if (null == paraList || paraList.isEmpty()) {
			log.info("身份证集合为空，或长度为0 paraList={}", paraList);
			result.setFailMsg("身份证集合为空,或长度为0");
			// 错误信息集合
			MImpExcelErrorVO errorVO = new MImpExcelErrorVO();
			errorVO.appError("身份证集合为空,或长度为0");
			errorList.add(errorVO);
			result.setData(errorList);
			return result;
		}
		log.debug("开始验证用户信息size={}", paraList.size());
		List<String> lawNoList = new ArrayList<>(paraList.size());
		// 先验证格式
		validateDataFormat(paraList, lawNoList, errorList);
		if (!errorList.isEmpty()) {
			log.info("数据格式有错误{}，直接返回", errorList.size());
			result.setData(errorList);
			result.setSuccess(false);
			return result;
		}
		// 验证用户，开户、银行卡等信息
		ResultVO<List<ExcelOriginalDataForm>> resultPerson = createExcelOriginalData(paraList, lawNoList, errorList);
		if (!resultPerson.getData().isEmpty()) {
			log.debug("poList不为空，数据入库size={}", resultPerson.getData().size());
			this.excelOriginalDataBizImpl.addBatch(resultPerson.getData(),loginVO);
		}

		if (!errorList.isEmpty()) {
			log.info("数据格式有错误{}，直接返回", errorList.size());
			result.setData(errorList);
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	/** 验证数据格式 **/
	public void validateDataFormat(List<Map<String, Object>> paraList, List<String> lawNoList, List<MImpExcelErrorVO> errorList) {
		AtomicInteger line = new AtomicInteger(2);
		paraList.stream().forEach(map -> {
			MImpExcelErrorVO errorVO = new MImpExcelErrorVO();
			StringBuilder error = new StringBuilder();
			errorVO.getError().append("第").append(line.getAndIncrement()).append("行:").append("客户姓名：");
			boolean hasError = false;
			if (null == map.get("company_name")) {
				error.append("公司名称为空");
				hasError = true;
			}
			if (null == map.get("consumer_name")) {
				error.append("用户名称为空");
				errorVO.getError().append("");
				hasError = true;
			} else {
				errorVO.getError().append(map.get("consumer_name"));
			}
			errorVO.getError().append(",证件号码为：");

			if (null == map.get("law_type")) {
				error.append("证件类型为空");
				hasError = true;
			}
			if (null == map.get("law_no")) {
				error.append("证件号为空");
				errorVO.getError().append("");
				hasError = true;
			} else {
				errorVO.getError().append(map.get("law_no"));
			}
			if (null == map.get("money")) {
				error.append("金额为空");
				hasError = true;
			}
			if (null == map.get("withdraw_type")) {
				error.append("出金类型为空");
				hasError = true;
			}
			if (hasError) {
				errorVO.getError().append(",").append(error.toString());
				errorList.add(errorVO);
				return;
			}
			lawNoList.add(String.valueOf(map.get("law_no")));
		});
	}
	
	/** 根据身份证号，验证数据是否存在,没有错误，生成入库信息 **/
	private ResultVO<List<ExcelOriginalDataForm>> createExcelOriginalData(List<Map<String, Object>> paraList, List<String> lawNoList, List<MImpExcelErrorVO> errorList) {
		ResultVO<List<ExcelOriginalDataForm>> result = new ResultVO<>();
		// 查询出所有人的数据，进行比对。判错
		List<ExcelOriginalDataForm> transPOList = new ArrayList<>();
		// 生成本次数据批次号
		String batchNo = DateUtil.dateToStr(new Date(), DateUtil.TIMESTAMP_FORMAT_);
		AtomicInteger line = new AtomicInteger(2);
		paraList.stream().forEach(perMap -> {
			String lawNo = perMap.get("law_no").toString();
			MImpExcelErrorVO errorVO = new MImpExcelErrorVO();
			errorVO.getError().append("第").append(line.getAndIncrement()).append("行:").append("证件号：").append(lawNo).append(",客户姓名：").append(perMap.get("consumer_name").toString());
			
			ExcelOriginalDataForm original = new ExcelOriginalDataForm();
			original.setConsumer_id(System.currentTimeMillis());
			original.setCompany_name(perMap.get("company_name").toString());
			original.setConsumer_name(perMap.get("consumer_name").toString());
			original.setLaw_type_name(perMap.get("law_type").toString());
			if ("身份证".equals(original.getLaw_type_name())) {
				original.setLaw_type(PaperType.IDENTITYCARD);
			}

			original.setLaw_no(perMap.get("law_no").toString());
			original.setMoney((BigDecimal) perMap.get("money"));
			original.setStatus(ExcelDataStatusType.NEW_IMPORT);
			original.setCreate_time(new Date());
			original.setBatch_no(batchNo);
			original.setAudit_status(AuditStatus.AUDIT_STATUS_NO);
			original.setWithdraw_type(Integer.valueOf(perMap.get("withdraw_type").toString()));

			if (perMap.get("card_num") != null) {
				original.setCard_num(perMap.get("card_num").toString());
			}
			original.setAccount_no(String.valueOf(original.getConsumer_id()));
			original.setBank_account_no(String.valueOf(original.getConsumer_id()));
			original.setTradeMemCode(String.valueOf(original.getConsumer_id()));
			original.setVerify_status(VerifyStatusType.VERIFY_STATUS_SUCCEED);
			// 正常数据
			transPOList.add(original);
		});
		result.setData(transPOList);
		return result;
	}

}