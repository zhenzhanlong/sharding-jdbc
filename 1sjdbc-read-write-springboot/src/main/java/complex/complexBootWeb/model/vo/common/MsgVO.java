package complex.complexBootWeb.model.vo.common;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;


/**
 * excel导入信息，程序出错提示实体类
 * 
 * @author lenovo
 *
 */
public class MsgVO implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 4917000114204616168L;
	/** 错误提示 */
	private StringBuilder error = new StringBuilder();
	private String beginMsg;

	private String endMsg;

	public MsgVO() {
		super();
	}

	public String getError() {
		if (StringUtils.isNotBlank(beginMsg)) {
			error.insert(0, beginMsg);
		}
		if (StringUtils.isNotBlank(endMsg)) {
			error.append(endMsg);
		}
		return error.toString();
	}

	public MsgVO append(String msg) {
		if (error.length() > 0) {
			error.append(",").append(msg);
		} else {
			error.append(msg);
		}
		return this;
	}

	public MsgVO appendDirectly(String msg) {
		error.append(msg);
		return this;
	}

	public String getBeginMsg() {
		return beginMsg;
	}

	public void setBeginMsg(String beginMsg) {
		this.beginMsg = beginMsg;
	}

	public String getEndMsg() {
		return endMsg;
	}

	public void setEndMsg(String endMsg) {
		this.endMsg = endMsg;
	}

	@Override
	public String toString() {
		return this.getError();
	}

}
