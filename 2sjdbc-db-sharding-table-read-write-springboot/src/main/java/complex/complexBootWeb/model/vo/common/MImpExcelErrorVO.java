package complex.complexBootWeb.model.vo.common;

import java.io.Serializable;

import com.complex.practice.util.JackJsonUtil;

/**
 * excel导入信息，程序出错提示实体类
 * @author lenovo
 *
 */
public class MImpExcelErrorVO implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 4917000114204616168L;
	/**错误提示*/
	private StringBuilder error = new StringBuilder();
	private String user_name;
	
	public MImpExcelErrorVO() {
		super();
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setError(StringBuilder error) {
		this.error = error;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void appError(String err){
		if( err != null && error.indexOf( err )<0){
			if( error.length() >0){
				error.append(",").append(err);
			}else{
				error.append(err);
			}
		}
	}
	public StringBuilder getError(){
		return error;
	}
	public String getUser_name() {
		return user_name;
	}
	
	@Override
	public String toString() {
		return "MImpExcelErrorVO:" + JackJsonUtil.objToJson(this);
	}
	
}
