package complex.complexBootWeb.model.vo.login;

import java.io.Serializable;

import com.complex.practice.util.JackJsonUtil;

/**
 * 登录用户类
 * 
 * @author lenovo
 *
 */
public class ConsumerLoginVO implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -2971819669571470153L;

	/**
	 * 用户ID
	 */
	private Long consumer_id;
	/**
	 * 登陆账户号
	 */
	private String login_num;
	/**
	 * 登陆方式(微信，qq，支付宝,手机号)
	 */
	// private MLoginType login_type;

	/**
	 * 密码
	 */
	private String password;
	/**
	 * 姓名
	 */
	private String consumer_name;
	/**
	 * 手机
	 */
	private String mobile_tel;
	/**
	 * 公司id
	 */
	private Long company_id;
	/**
	 * 公司名称
	 */
	private String company_name;
	/**
	 * 用户性质（个人，公司，系统）
	 */
	// private MConsumersNatures consumer_nature;
	/***
	 * 切换公司id
	 */
	private Long qh_company_id;
	/*** 切换公司 ***/
	// private MCompanyVO qh_company;// 切换公司所有信息
	/**
	 * 后台管理人员登录的代管公司id
	 */
	private Long load_company_id;
	/****
	 * 登录的ip地址
	 */
	private String ip_address;
	/*** 设备名称 **/
	private String device_name;
	/** 渠道类型 **/
	private String channnel_type;

	public ConsumerLoginVO() {
		super();
	}

	public ConsumerLoginVO(Long consumer_id) {
		super();
		this.consumer_id = consumer_id;
	}

	public ConsumerLoginVO(Long consumer_id, String consumer_name) {
		super();
		this.consumer_id = consumer_id;
		this.consumer_name = consumer_name;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Long getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(Long consumer_id) {
		this.consumer_id = consumer_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getQh_company_id() {
		return qh_company_id;
	}

	public void setQh_company_id(Long qh_company_id) {
		this.qh_company_id = qh_company_id;
	}

	public Long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	public Long getLoad_company_id() {
		return load_company_id;
	}

	public void setLoad_company_id(Long load_company_id) {
		this.load_company_id = load_company_id;
	}

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getLogin_num() {
		return login_num;
	}

	public void setLogin_num(String login_num) {
		this.login_num = login_num;
	}

	public String getChannnel_type() {
		return channnel_type;
	}

	public void setChannnel_type(String channnel_type) {
		this.channnel_type = channnel_type;
	}

	@Override
	public String toString() {
		return "MConsumerLoginVO:" + JackJsonUtil.objToJson(this);
	}

}
