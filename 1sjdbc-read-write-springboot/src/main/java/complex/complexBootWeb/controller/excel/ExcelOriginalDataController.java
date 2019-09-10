package complex.complexBootWeb.controller.excel;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import complex.complexBootWeb.controller.MBaseController;
import complex.complexBootWeb.model.form.salary.excel.ExcelOriginalDataForm;
import complex.complexBootWeb.model.vo.ResultVO;
import complex.complexBootWeb.model.vo.common.MImpExcelErrorVO;
import complex.complexBootWeb.model.vo.salary.excel.ExcelOriginalDataVO;
import complex.complexBootWeb.web.salary.excel.IExcelOriginalDataWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 导入发薪excel原数据表
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/excel/original/data")
@Api(value = "导入发薪excel原数据表", tags = { "导入发薪excel原数据表" })
public class ExcelOriginalDataController extends MBaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 导入发薪excel原数据表
	@Resource(name = "excelOriginalDataWebServiceImpl")
	private IExcelOriginalDataWebService excelOriginalDataWebServiceImpl;

	@RequestMapping(value = "/edit")
	@ApiOperation("编辑")
	public ResultVO<?> update(ExcelOriginalDataForm paraForm) {
		log.debug("修改导入发薪excel原数据表form={}", paraForm);
		return excelOriginalDataWebServiceImpl.update(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/delete")
	@ApiOperation("删除")
	public ResultVO<?> deleteById(ExcelOriginalDataForm paraForm) {
		log.debug("删除导入发薪excel原数据表form={}", paraForm);
		return excelOriginalDataWebServiceImpl.deleteById(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/info")
	@ApiOperation("详情")
	public ResultVO<?> queryById(ExcelOriginalDataForm paraForm) {
		log.debug("根据id查询导入发薪excel原数据表form={}", paraForm);
		return excelOriginalDataWebServiceImpl.queryById(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/list")
	@ApiOperation("集合查询")
	public ResultVO<List<ExcelOriginalDataVO>> queryList(ExcelOriginalDataForm paraForm) {
		log.debug("根据form查询导入发薪excel原数据表list,form={}", paraForm);
		return excelOriginalDataWebServiceImpl.queryList(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/page")
	@ApiOperation("分页信息查询")
	public ResultVO<List<ExcelOriginalDataVO>> page(ExcelOriginalDataForm paraForm) {
		log.debug("根据form查询导入发薪excel原数据表列表,form={}", paraForm);
		return excelOriginalDataWebServiceImpl.page(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/enable")
	@ApiOperation("启用")
	public ResultVO<?> enable(ExcelOriginalDataForm paraForm) {
		log.debug("启用f导入发薪excel原数据表,form={}", paraForm);
		return excelOriginalDataWebServiceImpl.enable(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/disable")
	@ApiOperation("停用")
	public ResultVO<?> disable(ExcelOriginalDataForm paraForm) {
		log.debug("停用导入发薪excel原数据表,form={}", paraForm);
		return excelOriginalDataWebServiceImpl.enable(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/inTransfer")
	@ApiOperation("单条进入转账表")
	public ResultVO<?> inTransfer(ExcelOriginalDataForm paraForm) {
		log.debug("单条进入转账表,form={}", paraForm);
		return excelOriginalDataWebServiceImpl.inTransfer(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/inTransfer/batch")
	@ApiOperation("多条进入转账表")
	public ResultVO<?> inTransferBatch(ExcelOriginalDataForm paraForm) {
		log.debug("多条进入转账表,form={}", paraForm);
		return excelOriginalDataWebServiceImpl.inTransferBatch(paraForm, this.loginConsumer());
	}

	@RequestMapping(value = "/inTransfer/batchNo")
	@ApiOperation("统一批次进入转账表")
	public ResultVO<?> inTransferBatchNo(ExcelOriginalDataForm paraForm) {
		log.debug("统一批次进入转账表,form={}", paraForm);
		return excelOriginalDataWebServiceImpl.batchNoInTransferBatch(paraForm, this.loginConsumer());
	}

	/** excel 上传 **/
	@RequestMapping("/upload")
	@ResponseBody
	public ResultVO<List<MImpExcelErrorVO>> excelUpload(@RequestParam(value = "files", required = false) MultipartFile file) {
		return excelOriginalDataWebServiceImpl.excelImport(file, this.loginConsumer());
	}

}
