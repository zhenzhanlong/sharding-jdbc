package complex.complexBootWeb.dao.salary.excel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import complex.complexBootWeb.model.po.salary.excel.ExcelOriginalDataPO;

/**
 *
 * 导入发薪excel原数据表 dao接口
 * 
 * @author Automatic Generation
 * @version 1.0.0
 */
@Mapper
public interface IExcelOriginalDataDao {
	/**
	 * 增加数据
	 *
	 * @param po
	 */
	public int add(ExcelOriginalDataPO po);

	/**
	 * 批量增加数据
	 *
	 * @param po
	 */
	public int addBatch(List<ExcelOriginalDataPO> poList);

	/**
	 * 根据id删除
	 *
	 * @param po
	 */
	public int deleteById(ExcelOriginalDataPO po);

	/**
	 * 根据id编辑
	 *
	 * @param po
	 */
	public int update(ExcelOriginalDataPO po);

	/**
	 * 根据id查询
	 *
	 * @param po
	 */
	public ExcelOriginalDataPO queryById(ExcelOriginalDataPO po);

	/**
	 * 分页查询
	 *
	 * @param po
	 */
	public List<ExcelOriginalDataPO> page(ExcelOriginalDataPO po);

	/**
	 * 集合查询
	 *
	 * @param po
	 */
	public List<ExcelOriginalDataPO> queryList(ExcelOriginalDataPO po);

	/**
	 * 数据验证集合查询
	 *
	 * @param po
	 */
	public List<ExcelOriginalDataPO> queryListValidate(ExcelOriginalDataPO po);

	/**
	 * 数量查询
	 *
	 * @param po
	 */
	public Long count(ExcelOriginalDataPO po);

	/**
	 * 数量查询
	 *
	 * @param po
	 */
	public Map<String, Object> countPage(ExcelOriginalDataPO po);

	/**
	 * 数量查询
	 *
	 * @param po
	 */
	public Long editStatus(ExcelOriginalDataPO po);

	/**
	 * 批量修改状态
	 * 
	 * @param paraMap
	 * @return
	 */
	public Long editStatusBatch(Map<String, Object> paraMap);
}