package complex.complexBootWeb.model.vo;

import java.io.Serializable;
import java.util.Map;

import com.complex.practice.util.MsgCodeUtil;
import com.complex.practice.vo.BaseVO;

/**
 * 布尔类型返回类
 * 
 * @author lenovo
 *
 */
public class ResultVO<T> extends BaseVO<ResultVO<T>> implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5077047071678153968L;
	/**
	 * 序列化
	 */
	private T data;// 查询的 集合数据

	private Long recordsTotal;// 总条数
	private Long recordsFiltered;
	// 需要带入界面的独立参数
	private Map<String, Object> paraMap;

	public ResultVO() {
		super();
	}

	public ResultVO(String msgcode) {
		super(false, msgcode, null);
	}

	public ResultVO(boolean success, String msgcode, String msg) {
		super(success, msgcode, msg);
	}

	public ResultVO(boolean success, String msgcode, String msg, T objVO) {
		super(success, msgcode, msg);
		this.data = objVO;
	}

	public ResultVO(T objVO) {
		super(true, null, null);
		this.data = objVO;
	}

	public ResultVO(boolean success, String msgcode, String msg, T objVO, Long recordsTotal) {
		super(success, msgcode, msg);
		this.data = objVO;
		this.recordsTotal = recordsTotal;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Map<String, Object> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}

	public void falseMsg(String msgCode) {
		super.setMsg(MsgCodeUtil.getMsg(msgCode));
		super.setMsgcode(MsgCodeUtil.getMsgCode(msgCode));
		super.setSuccess(false);
	}

	public void setFailMsg(String msg) {
		super.setMsg(msg);
		super.setSuccess(false);
	}

	public void setSuccessMsg(String msg) {
		super.setMsg(msg);
		super.setSuccess(true);
	}
}
