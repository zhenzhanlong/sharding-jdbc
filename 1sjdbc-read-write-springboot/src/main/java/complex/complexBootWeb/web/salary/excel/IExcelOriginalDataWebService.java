package complex.complexBootWeb.web.salary.excel;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import complex.complexBootWeb.model.form.salary.excel.ExcelOriginalDataForm;
import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.common.MImpExcelErrorVO;
import complex.complexBootWeb.model.vo.login.MConsumerLoginVO;
import complex.complexBootWeb.model.vo.salary.excel.ExcelOriginalDataVO;

public interface IExcelOriginalDataWebService {

	/**
	 * 新增
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<ExcelOriginalDataForm> add(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 编辑
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> update(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 根据id删除
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> deleteById(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 根据id查询
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<ExcelOriginalDataVO> queryById(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 集合查询
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> queryList(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 分页查询
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<List<ExcelOriginalDataVO>> page(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 停用、下架
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> disable(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 启用、上架
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> enable(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 数据进入转账信息表
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> inTransfer(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 数据进入转账信息表(批量)
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> inTransferBatch(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * 数据进入转账信息表（一个批次）
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<?> batchNoInTransferBatch(ExcelOriginalDataForm paraForm, MConsumerLoginVO loginVO);

	/**
	 * excel 数据导入
	 * 
	 * @param paraForm
	 * @param loginVO
	 * @return
	 */
	public ResultVO<List<MImpExcelErrorVO>> excelImport(MultipartFile file, MConsumerLoginVO loginVO);
}