package complex.complexBootWeb.biz.salary.excel.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.complex.practice.type.excel.ExcelDataStatusType;

import complex.complexBootWeb.biz.salary.excel.IExcelOriginalDataBiz;
import complex.complexBootWeb.model.form.salary.excel.ExcelOriginalDataForm;
import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.login.MConsumerLoginVO;
import complex.complexBootWeb.model.vo.salary.excel.ExcelOriginalDataVO;
import complex.complexBootWeb.service.salary.excel.IExcelOriginalDataService;

/**
 * 导入发薪excel原数据表
 * 
 * @author Administrator
 *
 */
@Service("excelOriginalDataBizImpl")
public class ExcelOriginalDataBizImpl implements IExcelOriginalDataBiz {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 导入发薪excel原数据表
	@Resource(name = "excelOriginalDataServiceImpl")
	private IExcelOriginalDataService excelOriginalDataServiceImpl;

	@Override
	public ResultVO<ExcelOriginalDataForm> add(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("新增导入发薪excel原数据表form={},loginVO={}", paraForm, loginVO);
		ResultVO<ExcelOriginalDataForm> result = new ResultVO<>();
		if (null == paraForm) {
			log.debug("paraForm:{} 参数为空", paraForm);
			result.setMsg("参数为空");
			result.setSuccess(false);
			return result;
		}
		// 新增数据验证
		if (!paraForm.addValidate(result)) {
			log.info("新增项目验证数据失败msg={}", result.getMsg());
			return result;
		}
		return excelOriginalDataServiceImpl.add(paraForm);
	}

	@Override
	public ResultVO<?> addBatch(List<ExcelOriginalDataForm> paraFormList, MConsumerLoginVO loginVO) {
		log.debug("批量新增导入发薪excel原数据表paraFormList={},loginVO={}", paraFormList, loginVO);
		ResultVO<ExcelOriginalDataForm> result = new ResultVO<>();
		if (null == paraFormList || paraFormList.isEmpty()) {
			log.debug("paraFormList:{} 参数为空", paraFormList);
			result.setMsg("参数为空");
			result.setSuccess(false);
			return result;
		}
		// 验证数据格式
		paraFormList.stream().forEach(paraForm -> {
			paraForm.addValidate(result);
		});

		if (!result.isSuccess()) {
			log.info("批量新增excel原始数据数据验证失败msg={}", result.getMsg());
			return result;
		}
		return excelOriginalDataServiceImpl.addBatch(paraFormList);
	}

	@Override
	public ResultVO<?> update(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("修改导入发薪excel原数据表form={},loginVO={}", paraForm, loginVO);
		ResultVO<?> result = new ResultVO<>();
		if (null == paraForm || null == paraForm.getId()) {
			log.debug("paraForm:{} 参数为空", paraForm);
			result.setMsg("参数为空");
			result.setSuccess(false);
			return result;
		}
		return excelOriginalDataServiceImpl.update(paraForm);
	}

	@Override
	public ResultVO<?> deleteById(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("删除导入发薪excel原数据表form={},loginVO={}", paraForm, loginVO);
		ResultVO<?> result = new ResultVO<>();
		if (null == paraForm.getId()) {
			log.debug("id:{} 参数为空", paraForm.getId());
			result.setMsg("参数为空");
			result.setSuccess(false);
			return result;
		}
		return excelOriginalDataServiceImpl.deleteById(paraForm);
	}

	@Override
	public ResultVO<ExcelOriginalDataVO> queryById(ExcelOriginalDataForm paraForm) {
		log.debug("根据id查询导入发薪excel原数据表form={}", paraForm);
		ResultVO<ExcelOriginalDataVO> result = new ResultVO<>();
		if (null == paraForm.getId()) {
			log.debug("id:{} 参数为空", paraForm.getId());
			result.setMsg("参数为空");
			result.setSuccess(false);
			return result;
		}
		return excelOriginalDataServiceImpl.queryById(paraForm);
	}

	/**
	 * 根据id查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<ExcelOriginalDataVO> queryById(Long id) {
		log.debug("根据id：{}查询原数据", id);
		return queryById(new ExcelOriginalDataForm(id));
	}

	/**
	 * 根据id查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> queryByIds(ExcelOriginalDataForm paraForm) {
		log.debug("根据id：{}查询原数据", paraForm);
		if (null == paraForm.getIds_() || 0 == paraForm.getIds_().length) {
			log.debug("参数ids{}为空", paraForm);
			return new ResultVO<>();
		}
		return this.queryList(paraForm);
	}

	@Override
	public ResultVO<?> count(ExcelOriginalDataForm paraForm) {
		log.debug("根据form查询count,form={}", paraForm);
		return excelOriginalDataServiceImpl.count(paraForm);
	}

	@Override
	public ResultVO<List<ExcelOriginalDataVO>> queryListByBatchNo(ExcelOriginalDataForm paraForm) {
		log.debug("根据form查询导入发薪excel原数据表list,form={}", paraForm);
		return this.queryList(paraForm);
	}

	public ResultVO<List<ExcelOriginalDataVO>> queryList(ExcelOriginalDataForm paraForm) {
		log.debug("根据form查询导入发薪excel原数据表list,form={}", paraForm);
		return excelOriginalDataServiceImpl.queryList(paraForm);
	}

	@Override
	public ResultVO<List<ExcelOriginalDataVO>> page(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("根据form查询导入发薪excel原数据表列表,form={}", paraForm);
		return excelOriginalDataServiceImpl.page(paraForm);
	}

	/**
	 * 状态编辑
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> editStatus(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO) {
		log.debug("修改数据状态paraForm={},loginVO={}", paraForm, loginVO);
		return excelOriginalDataServiceImpl.editStatus(paraForm);
	}

	/**
	 * 批量状态编辑
	 * 
	 * @param ids
	 * @param status
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> editStatusBatch(List<Integer> ids, ExcelDataStatusType status, MConsumerLoginVO loginVO) {
		log.debug("批量修改状态ids={},status={}", ids, status);
		return excelOriginalDataServiceImpl.editStatusBatch(ids, status);
	}

}