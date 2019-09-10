package com.complex.practice.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * excel 上传解析为Map 数据
 * 
 * @author lenovo
 *
 */
@SuppressWarnings("rawtypes")
public class ResultExcelVO<T> extends BaseVO<ResultExcelVO> implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 7438080142261826261L;
	private List<Map<String, Object>> listMap;// 查询的 集合数据

	public ResultExcelVO() {
		super();
	}

	public List<Map<String, Object>> getListMap() {
		return listMap;
	}

	public void setListMap(List<Map<String, Object>> listMap) {
		this.listMap = listMap;
	}

	public ResultExcelVO(boolean success, String msgcode, String msg) {
		super(success, msgcode, msg);
	}

}
