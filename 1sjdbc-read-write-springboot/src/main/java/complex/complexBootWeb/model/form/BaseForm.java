package complex.complexBootWeb.model.form;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class BaseForm implements java.io.Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6136191560456227722L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected Integer pageNumber;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	protected Integer pageSize;

	// 开始时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date startTime;
	// 结束时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date endTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected Long start;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	protected Long length;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	protected Long[] ids_;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	protected Long[] ids;

	public BaseForm() {
		super();
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long[] getIds_() {
		return ids_;
	}

	public void setIds_(Long[] ids_) {
		this.ids_ = ids_;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public abstract String toString();
}
