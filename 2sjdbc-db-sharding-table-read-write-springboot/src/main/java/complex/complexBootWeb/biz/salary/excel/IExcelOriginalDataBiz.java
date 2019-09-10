package complex.complexBootWeb.biz.salary.excel;

import java.util.List;

import com.complex.practice.type.excel.ExcelDataStatusType;

import complex.complexBootWeb.model.form.salary.excel.ExcelOriginalDataForm;
import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.login.MConsumerLoginVO;
import complex.complexBootWeb.model.vo.salary.excel.ExcelOriginalDataVO;

/**
 * 导入发薪excel原数据表
 * 
 * @author zzl
 *
 */
public interface IExcelOriginalDataBiz {

	/**
	 * 新增
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<ExcelOriginalDataForm> add(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 批量新增
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> addBatch(List<ExcelOriginalDataForm> paraFormList, MConsumerLoginVO loginVO);

	/**
	 * 编辑
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> update(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 根据id删除
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> deleteById(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 根据id查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<ExcelOriginalDataVO> queryById(ExcelOriginalDataForm paraForm);

	/**
	 * 根据id查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<ExcelOriginalDataVO> queryById(Long id);

	/**
	 * 集合查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> queryList(ExcelOriginalDataForm paraForm);

	/**
	 * 根据批次号查询集合数据
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> queryListByBatchNo(ExcelOriginalDataForm paraForm);

	/**
	 * 数量查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> count(ExcelOriginalDataForm paraForm);

	/**
	 * 分页查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> page(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 状态编辑
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> editStatus(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 批量状态编辑
	 * 
	 * @param ids
	 * @param status
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> editStatusBatch(List<Integer> ids, ExcelDataStatusType status, MConsumerLoginVO loginVO);
}