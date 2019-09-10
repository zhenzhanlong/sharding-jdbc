package complex.complexBootWeb.model.vo.common;

import com.complex.practice.util.JackJsonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * excel 标题行类
 * 
 * @author Administrator
 *
 */
@ApiModel(description = "excel 标题行类")
public class ExcelTitleVO implements java.io.Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6870298243114753403L;

	@ApiModelProperty(value = "数据主键", name = "id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer id;

	@ApiModelProperty(value = "英文标题", name = "englishTitle")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String englishTitle;

	@ApiModelProperty(value = "中文标题", name = "chineseTitle")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String chineseTitle;

	@ApiModelProperty(value = "数据类型", name = "dataType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String dataType;

	@ApiModelProperty(value = "排序号", name = "sno")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer sno;

	public ExcelTitleVO() {
		super();
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public String getChineseTitle() {
		return chineseTitle;
	}

	public void setChineseTitle(String chineseTitle) {
		this.chineseTitle = chineseTitle;
	}

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	@Override
	public String toString() {
		return "ExcelTitleVO:" + JackJsonUtil.objToJson(this);
	}

}
