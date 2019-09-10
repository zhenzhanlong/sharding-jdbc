package complex.complexBootWeb.model.vo.salary.excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.complex.practice.type.consumer.PaperType;
import com.complex.practice.type.excel.AuditStatus;
import com.complex.practice.type.excel.ExcelDataStatusType;
import com.complex.practice.type.excel.VerifyStatusType;
import com.complex.practice.type.state.DataStatusType;
import com.complex.practice.util.BeanUtils;
import com.complex.practice.util.JackJsonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import complex.complexBootWeb.model.po.salary.excel.ExcelOriginalDataPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 导入发薪excel原数据表 form 类 **/
@ApiModel(description = "导入发薪excel原数据表")
public class ExcelOriginalDataVO implements Serializable {

	/** 序列化 */
	private static final long serialVersionUID = 1566622330782L;

	// 属性字段部分
	/** 数据标识 **/

	@ApiModelProperty(value = "数据标识", name = "id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	@ApiModelProperty(value = "用户中心，数据标识", name = "consumer_id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long consumer_id;
	/** 转账批次 **/

	@ApiModelProperty(value = "转账批次", name = "batch_no")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String batch_no;
	/** 公司名称 **/

	@ApiModelProperty(value = "公司名称", name = "company_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String company_name;
	/** 用户姓名 **/

	@ApiModelProperty(value = "用户姓名", name = "consumer_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String consumer_name;
	/** 证件类型 **/

	@ApiModelProperty(value = "证件类型", name = "law_type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private PaperType law_type;
	/** 证件类型(中文) **/

	@ApiModelProperty(value = "证件类型(中文)", name = "law_type_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String law_type_name;
	/** 证件号码 **/

	@ApiModelProperty(value = "证件号码", name = "law_no")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String law_no;
	/** 金额 **/

	@ApiModelProperty(value = "金额", name = "money")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal money;
	/** 数据状态 **/

	@ApiModelProperty(value = "数据状态", name = "data_status")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ExcelDataStatusType status;
	/** 转账状态 **/

	@ApiModelProperty(value = "转账状态", name = "transfer_status")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DataStatusType transfer_status;
	/** 转账时间 **/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "转账时间", name = "transfer_time")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date transfer_time;
	/** 提现状态 **/

	@ApiModelProperty(value = "提现状态", name = "withdraw_status")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DataStatusType withdraw_status;
	/** 验证状态 **/

	@ApiModelProperty(value = "验证状态", name = "verify_status")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private VerifyStatusType verify_status;
	/** 提现时间 **/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "提现时间", name = "withdraw_time")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date withdraw_time;
	/** 审核状态 **/

	@ApiModelProperty(value = "审核状态", name = "audit_status")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private AuditStatus audit_status;
	/** 提现方式 0 自动 1 手动 **/

	@ApiModelProperty(value = "提现方式 0 自动 1 手动", name = "withdraw_type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer withdraw_type;
	/** 摊位号 **/

	@ApiModelProperty(value = "摊位号", name = "account_no")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String account_no;
	/** 子账号 **/

	@ApiModelProperty(value = "子账号", name = "bank_account_no")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String bank_account_no;
	/** 会员代码 **/

	@ApiModelProperty(value = "会员代码", name = "tradeMemCode")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String tradeMemCode;
	/** 卡号(除默认卡外的其他卡必须传卡号) **/

	@ApiModelProperty(value = "卡号(除默认卡外的其他卡必须传卡号)", name = "card_num")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String card_num;
	/** 编辑时间 **/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "编辑时间", name = "edit_time")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date edit_time;
	/** 创建时间 **/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", name = "create_time")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date create_time;

	/** 发薪唯一表示 **/
	@ApiModelProperty(value = "发薪唯一表示", name = "salary_unique_id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String salary_unique_id;

	// 构造方法部分
	public ExcelOriginalDataVO() {
		super();
	}

	public ExcelOriginalDataVO(Long id) {
		super();
		this.id = id;
	}

	public ExcelOriginalDataVO(ExcelOriginalDataPO po) {
		if (null != po) {
			try {
				BeanUtils.copyProperties(po, this, false);
			} catch (Exception e) {
			}
		}
	}

	// get 、set代码部分

	public Long getId() {
		return this.id;
	}

	public String getSalary_unique_id() {
		return salary_unique_id;
	}

	public void setSalary_unique_id(String salary_unique_id) {
		this.salary_unique_id = salary_unique_id;
	}

	public Long getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(Long consumer_id) {
		this.consumer_id = consumer_id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBatch_no() {
		return this.batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getCompany_name() {
		return this.company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getConsumer_name() {
		return this.consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	public PaperType getLaw_type() {
		return this.law_type;
	}

	public void setLaw_type(PaperType law_type) {
		this.law_type = law_type;
	}

	public String getLaw_type_name() {
		return this.law_type_name;
	}

	public void setLaw_type_name(String law_type_name) {
		this.law_type_name = law_type_name;
	}

	public String getLaw_no() {
		return this.law_no;
	}

	public void setLaw_no(String law_no) {
		this.law_no = law_no;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public ExcelDataStatusType getStatus() {
		return status;
	}

	public void setStatus(ExcelDataStatusType status) {
		this.status = status;
	}

	public DataStatusType getTransfer_status() {
		return this.transfer_status;
	}

	public void setTransfer_status(DataStatusType transfer_status) {
		this.transfer_status = transfer_status;
	}

	public Date getTransfer_time() {
		return this.transfer_time;
	}

	public void setTransfer_time(Date transfer_time) {
		this.transfer_time = transfer_time;
	}

	public DataStatusType getWithdraw_status() {
		return this.withdraw_status;
	}

	public void setWithdraw_status(DataStatusType withdraw_status) {
		this.withdraw_status = withdraw_status;
	}

	public VerifyStatusType getVerify_status() {
		return this.verify_status;
	}

	public void setVerify_status(VerifyStatusType verify_status) {
		this.verify_status = verify_status;
	}

	public Date getWithdraw_time() {
		return this.withdraw_time;
	}

	public void setWithdraw_time(Date withdraw_time) {
		this.withdraw_time = withdraw_time;
	}

	public AuditStatus getAudit_status() {
		return this.audit_status;
	}

	public void setAudit_status(AuditStatus audit_status) {
		this.audit_status = audit_status;
	}

	public Integer getWithdraw_type() {
		return this.withdraw_type;
	}

	public void setWithdraw_type(Integer withdraw_type) {
		this.withdraw_type = withdraw_type;
	}

	public String getAccount_no() {
		return this.account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getBank_account_no() {
		return this.bank_account_no;
	}

	public void setBank_account_no(String bank_account_no) {
		this.bank_account_no = bank_account_no;
	}

	public String getTradeMemCode() {
		return this.tradeMemCode;
	}

	public void setTradeMemCode(String tradeMemCode) {
		this.tradeMemCode = tradeMemCode;
	}

	public String getCard_num() {
		return this.card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public Date getEdit_time() {
		return this.edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}

	public Date getCreate_time() {
		return this.create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "ExcelOriginalDataVO:" + JackJsonUtil.objToJson(this);
	}
}
