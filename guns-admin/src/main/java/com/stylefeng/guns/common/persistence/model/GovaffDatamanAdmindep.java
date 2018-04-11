package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lgg
 * @since 2017-10-26
 */
@TableName("govaff_dataman_admindep")
public class  GovaffDatamanAdmindep extends Model<GovaffDatamanAdmindep> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 机构标识
     */
	@TableField("admindep_id")
	private String admindepId;
    /**
     * 机构名称
     */
	@NotEmpty(message="姓名不能为空")
	private String name;
    /**
     * 机构描述
     */
	private String descript;
	@TableField("is_deleted")
	private Integer isDeleted;
	@TableField("created_time")
	private Date createdTime;
	@TableField("update_time")
	private Date updateTime;
    /**
     * 数据主题图片路径
     */
	private String imgurl;
    /**
     * 数据集主题联系方式
     */
	private String contact;
    /**
     * 机构类型
     */
	private String type;
    /**
     * 数据机构下的数据制作部门标识(如GA10001000-01)
     */
	@TableField("department_id_sign")
	private String departmentIdSign;
    /**
     * 数据机构下数据制作部门名称
     */
	@TableField("department_name")
	private String departmentName;
    /**
     * 数据机构下数据制作部门描述信息
     */
	@TableField("department_desc")
	private String departmentDesc;
    /**
     * 数据机构下数据制作部门联系方式
     */
	@TableField("department_contact")
	private String departmentContact;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdmindepId() {
		return admindepId;
	}

	public void setAdmindepId(String admindepId) {
		this.admindepId = admindepId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepartmentIdSign() {
		return departmentIdSign;
	}

	public void setDepartmentIdSign(String departmentIdSign) {
		this.departmentIdSign = departmentIdSign;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public String getDepartmentContact() {
		return departmentContact;
	}

	public void setDepartmentContact(String departmentContact) {
		this.departmentContact = departmentContact;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "GovaffDatamanAdmindep{" +
			"id=" + id +
			", admindepId=" + admindepId +
			", name=" + name +
			", descript=" + descript +
			", isDeleted=" + isDeleted +
			", createdTime=" + createdTime +
			", updateTime=" + updateTime +
			", imgurl=" + imgurl +
			", contact=" + contact +
			", type=" + type +
			", departmentIdSign=" + departmentIdSign +
			", departmentName=" + departmentName +
			", departmentDesc=" + departmentDesc +
			", departmentContact=" + departmentContact +
			"}";
	}
}
