package complex.complexBootWeb.model.po;

import java.io.Serializable;

import com.complex.practice.util.JackJsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基础PO
 * 
 * @author zwm
 *
 */
public class BasePO implements Serializable {
	/**
	 * 序列化
	 */
	protected static final long serialVersionUID = 201342L;
	@JsonIgnore
	protected Long[] ids;
	@JsonIgnore
	protected Long[] ids_;
	@JsonIgnore
	protected Long start; // 数据查询开始
	@JsonIgnore
	protected Long length; // 数据查询长度
	@JsonIgnore
	protected Long total; // 查询数据总量

	public BasePO() {
		super();
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long[] getIds_() {
		return ids_;
	}

	public void setIds_(Long[] ids_) {
		this.ids_ = ids_;
	}

	@Override
	public String toString() {
		return "TomorrowPersonalTransferInfoPO:" + JackJsonUtil.objToJson(this);
	}

}
