package complex.complexBootWeb.service.salary.excel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complex.practice.type.excel.ExcelDataStatusType;
import com.complex.practice.util.BeanUtils;
import com.sx.cache.Cache;

import complex.complexBootWeb.dao.salary.excel.IExcelOriginalDataDao;
import complex.complexBootWeb.model.form.salary.excel.ExcelOriginalDataForm;
import complex.complexBootWeb.model.po.salary.excel.ExcelOriginalDataPO;
import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.salary.excel.ExcelOriginalDataVO;
import complex.complexBootWeb.service.salary.excel.IExcelOriginalDataService;

/**
 * 导入发薪excel原数据表
 *
 * @author zzl
 *
 */
@Service("excelOriginalDataServiceImpl")
public class ExcelOriginalDataServiceImpl implements IExcelOriginalDataService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	// redis 缓存
	@Resource(name = "redisCache")
	private Cache cache;

	// 导入发薪excel原数据表 dao
	@Autowired
	private IExcelOriginalDataDao excelOriginalDataDao;

	/**
	 * 新增
	 */
	@Override
	public ResultVO<ExcelOriginalDataForm> add(ExcelOriginalDataForm paraForm) {
		log.debug("新增导入发薪excel原数据表 paraForm={}", paraForm);

		ResultVO<ExcelOriginalDataForm> result = new ResultVO<>();
		if (null == paraForm) {
			result.setFailMsg("e.parameter.is.null");
			return result;
		}
		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			excelOriginalDataDao.add(po);
			paraForm.setId(po.getId());
		} catch (Exception e) {
			log.error("新增{table_comment}异常error={}", e);
			result.setFailMsg("e.param.config.add.is.error");
		}
		return result;

	}

	/**
	 * 批量新增
	 */
	@Override
	public ResultVO<?> addBatch(List<ExcelOriginalDataForm> formList) {
		ResultVO<?> result = new ResultVO<>();
		if (null == formList || formList.isEmpty()) {
			result.setMsg("参数为空");
			return result;
		} // end if
		List<ExcelOriginalDataPO> poList = createPOList(formList);
		try {
			for (ExcelOriginalDataPO po : poList) {
				po.setId(System.currentTimeMillis());
				Thread.sleep(1);
				excelOriginalDataDao.add(po);
			}
			// excelOriginalDataDao.addBatch(poList);
		} catch (Exception ex) {
			log.error("批量新增数据error={}", ex);
			result.setMsg("批量新增数据失败");
			result.setSuccess(false);
			return result;
		}
		return result;
	}

	/**
	 * 编辑
	 */
	@Override
	public ResultVO<?> update(ExcelOriginalDataForm paraForm) {
		log.debug("编辑导入发薪excel原数据表paraForm={}", paraForm);
		ResultVO<ExcelOriginalDataForm> result = new ResultVO<>();
		if (null == paraForm) {
			result.setFailMsg("e.parameter.is.null");
			return result;
		}
		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			excelOriginalDataDao.update(po);
		} catch (Exception e) {
			log.error("编辑导入发薪excel原数据表异常error={}", e);
			result.setFailMsg("e.param.config.edit.is.error");
		}
		return result;
	}

	/**
	 * 根据对象删除
	 */
	@Override
	public ResultVO<?> deleteById(ExcelOriginalDataForm paraForm) {
		log.debug("删除导入发薪excel原数据表form={}", paraForm);
		ResultVO<?> result = new ResultVO<>();
		if (null == paraForm || null == paraForm.getId()) {
			result.setFailMsg("e.parameter.is.null");
			return result;
		}
		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			excelOriginalDataDao.deleteById(po);
		} catch (Exception e) {
			log.error("删除参导入发薪excel原数据表异常error={}", e);
			result.setFailMsg("e.param.config.delete.is.error");
		}
		return result;
	}

	/**
	 * 根据id获取
	 */
	@Override
	public ResultVO<ExcelOriginalDataVO> queryById(ExcelOriginalDataForm paraForm) {
		log.debug("根据id查询导入发薪excel原数据表form={}", paraForm);
		ResultVO<ExcelOriginalDataVO> result = new ResultVO<>();
		if (null == paraForm || null == paraForm.getId()) {
			result.setFailMsg("e.parameter.is.null");
			return result;
		}
		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			ExcelOriginalDataPO queryPO = excelOriginalDataDao.queryById(po);
			result.setData(this.createVO(queryPO));
		} catch (Exception e) {
			log.error("根据id查询导入发薪excel原数据表异常error={}", e);
			result.setFailMsg("e.param.config.query.by.id.is.error");
		}
		return result;
	}

	/**
	 * 集合查询
	 */
	@Override
	public ResultVO<List<ExcelOriginalDataVO>> queryList(ExcelOriginalDataForm paraForm) {
		log.debug("查询导入发薪excel原数据表集合paraForm={}", paraForm);
		ResultVO<List<ExcelOriginalDataVO>> result = new ResultVO<>();
		if (null == paraForm) {
			result.setFailMsg("e.parameter.is.null");
			return result;
		}
		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			List<ExcelOriginalDataPO> poList = excelOriginalDataDao.queryList(po);
			result.setData(this.createVOList(poList));
		} catch (Exception e) {
			log.error("查询导入发薪excel原数据表集合异常error={}", e);
			result.setFailMsg("e.param.config.query.list.is.error");
		}
		return result;

	}

	/**
	 * 数量查询
	 */
	@Override
	public ResultVO<Long> count(ExcelOriginalDataForm paraForm) {
		log.debug("查询导入发薪excel原数据表数量paraForm={}", paraForm);
		if (null == paraForm) {
			return new ResultVO<>(Long.valueOf("0"));
		}
		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			Long num = excelOriginalDataDao.count(po);
			return new ResultVO<>(num);
		} catch (Exception e) {
			log.error("查询导入发薪excel原数据表异常error={}", e);
		}
		return new ResultVO<>(Long.valueOf("0"));
	}

	/**
	 * 状态编辑
	 * 
	 * @param paraForm
	 * @return
	 */
	@Override
	public ResultVO<?> editStatus(ExcelOriginalDataForm paraForm) {
		log.debug("状态编辑paraForm={}", paraForm);
		ResultVO<?> result = new ResultVO<>();
		if (null == paraForm) {
			result.setFailMsg("e.parameter.is.null");
			return result;
		}
		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			excelOriginalDataDao.editStatus(po);
		} catch (Exception e) {
			log.error("编辑导入发薪excel原数据表状态error={}", e);
			result.setFailMsg("状态编辑失败");
		}
		return result;
	}

	/**
	 * 批量状态编辑
	 * 
	 * @param ids
	 * @param status
	 * @return
	 */
	@Override
	public ResultVO<?> editStatusBatch(List<Integer> ids, ExcelDataStatusType status) {
		log.debug("状态编辑ids={},status={}", ids, status);
		ResultVO<?> result = new ResultVO<>();
		if (null == ids || null == status) {
			result.setFailMsg("e.parameter.is.null");
			return result;
		}
		Map<String, Object> paraMap = new HashMap<>();
		try {
			paraMap.put("ids_", ids);
			paraMap.put("status", status);
			excelOriginalDataDao.editStatusBatch(paraMap);
		} catch (Exception e) {
			log.error("编辑导入发薪excel原数据表状态error={}", e);
			result.setFailMsg("状态编辑失败");
		}
		return result;
	}

	/**
	 * 分页查询
	 */
	@Override
	public ResultVO<List<ExcelOriginalDataVO>> page(ExcelOriginalDataForm paraForm) {
		log.info("查询导入发薪excel原数据表列表paraForm ={}", paraForm);
		ResultVO<List<ExcelOriginalDataVO>> result = new ResultVO<>();
		if (null == paraForm.getStart() || null == paraForm.getLength()) {
			log.info("参数为空paraForm={}", paraForm);
			result.setFailMsg("e.parameter.page.param.is.null");
			return result;
		}

		ExcelOriginalDataPO po = paraForm.convertToPO();
		try {
			// 查询数量
			Map<String, Object> countMap = excelOriginalDataDao.countPage(po);

			// Page<?> page = new Page<>(paraForm.getPageNumber(), paraForm.getPageSize(),
			// count.intValue());
			// po.setPageNumber(page.getStartRow());
			// po.setPageSize(page.getPageSize());
			// 查询数据
			List<ExcelOriginalDataPO> list = excelOriginalDataDao.page(po);
			result.setData(createVOList(list));
			result.setRecordsFiltered((Long) countMap.get("num"));
			result.setRecordsTotal(result.getRecordsFiltered());
			result.setParaMap(countMap);
		} catch (Exception e) {
			log.info("查询导入发薪excel原数据表列表失败error={}", e);
			result.setFailMsg("查询excel原数据分页信息异常");
			;
		}
		return result;
	}

	/** poList 转 voList **/
	private List<ExcelOriginalDataVO> createVOList(List<ExcelOriginalDataPO> poList) {
		if (null == poList || poList.isEmpty()) {
			return new ArrayList<>();
		}
		List<ExcelOriginalDataVO> voList = new ArrayList<>();
		for (ExcelOriginalDataPO po : poList) {
			voList.add(this.createVO(po));
		}
		return voList;
	}

	/** po 转 vo **/
	private ExcelOriginalDataVO createVO(ExcelOriginalDataPO po) {
		if (null == po) {
			return null;
		}
		ExcelOriginalDataVO vo = new ExcelOriginalDataVO();
		try {
			// 属性复制
			BeanUtils.copyProperties(po, vo, false);
		} catch (Exception ex) {
			log.error("属性复制出错error={}", ex);
		}
		return vo;
	}

	/** voList 2 poList **/
	private List<ExcelOriginalDataPO> createPOList(List<ExcelOriginalDataForm> formList) {
		if (null == formList || formList.isEmpty()) {
			return new ArrayList<>();
		}
		// 返回的集合
		List<ExcelOriginalDataPO> poList = new ArrayList<>(formList.size());
		// 批量数据复制
		for (ExcelOriginalDataForm paraForm : formList) {
			ExcelOriginalDataPO po = paraForm.convertToPO();
			poList.add(po);
		}
		return poList;
	}

	/** 添加时唯一性的验证 **/
	private ResultVO<ExcelOriginalDataForm> queryListValidate(ExcelOriginalDataForm paraForm) {
		log.debug("导入发薪excel原数据表添加时唯一性验证paraForm={}", paraForm);
		ResultVO<ExcelOriginalDataForm> uniqueValidate = new ResultVO<>();
		ExcelOriginalDataPO po = new ExcelOriginalDataPO();
		log.debug("检查重复数据po={}", po);
		List<ExcelOriginalDataPO> poList = this.excelOriginalDataDao.queryListValidate(po);
		if (!poList.isEmpty()) {
			log.debug("数据重复size={},paraForm={}", poList.size(), paraForm);
			paraForm.setId(poList.get(0).getId());
			uniqueValidate.setData(paraForm);
			uniqueValidate.setMsg("重复");
			return uniqueValidate;
		} // end if

		uniqueValidate.setSuccess(false);
		return uniqueValidate;
	}

	/** 编辑时唯一性的验证 **/
	private ResultVO<ExcelOriginalDataForm> updateValidate(ExcelOriginalDataForm paraForm) {
		log.debug("导入发薪excel原数据表编辑时唯一性验证paraForm={}", paraForm);
		ResultVO<ExcelOriginalDataForm> uniqueValidate = new ResultVO<>();
		ExcelOriginalDataPO po = new ExcelOriginalDataPO();
		log.debug("检查重复数据queryPO={}", po);
		List<ExcelOriginalDataPO> poList = this.excelOriginalDataDao.queryListValidate(po);
		if (!poList.isEmpty()) {
			for (ExcelOriginalDataPO poTemp : poList) {
				if (!poTemp.getId().equals(paraForm.getId())) {
					log.info("重复paraForm={}", paraForm);
					uniqueValidate.setMsg("重复");
					return uniqueValidate;
				} // end if
			} // end for
		} // end if
		uniqueValidate.setSuccess(false);
		return uniqueValidate;
	}

}