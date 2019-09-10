package complex.complexBootWeb.service.salary.excel;

import java.util.List;

import com.complex.practice.type.excel.ExcelDataStatusType;

import complex.complexBootWeb.model.form.salary.excel.ExcelOriginalDataForm;
import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.salary.excel.ExcelOriginalDataVO;

/**
 * 导入发薪excel原数据表 service api
 * 
 * @author zzl
 *
 */
public interface IExcelOriginalDataService {

	/**
	 * 新增
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<ExcelOriginalDataForm> add(ExcelOriginalDataForm paraForm);

	/**
	 * 批量新增
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> addBatch(List<ExcelOriginalDataForm> paraForm);

	/**
	 * 编辑
	 * 
	 * @param paraForm
	 * @return
	 */

	public ResultVO<?> update(ExcelOriginalDataForm paraForm);

	/**
	 * 根据id删除
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> deleteById(ExcelOriginalDataForm paraForm);

	/**
	 * 根据id获取对象
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<ExcelOriginalDataVO> queryById(ExcelOriginalDataForm paraForm);

	/**
	 * 数据集合查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> queryList(ExcelOriginalDataForm paraForm);

	/**
	 * 数量查询
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<Long> count(ExcelOriginalDataForm paraForm);

	/**
	 * 翻页信息
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> page(ExcelOriginalDataForm paraForm);

	/**
	 * 状态编辑
	 * 
	 * @param paraForm
	 * @return
	 */
	public ResultVO<?> editStatus(ExcelOriginalDataForm paraForm);

	/**
	 * 批量状态编辑
	 * 
	 * @param ids
	 * @param status
	 * @return
	 */
	public ResultVO<?> editStatusBatch(List<Integer> ids, ExcelDataStatusType status);
}