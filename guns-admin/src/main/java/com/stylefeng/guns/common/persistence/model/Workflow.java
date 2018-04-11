package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * OA审批流程主体表
 * </p>
 *
 * @author lgg
 * @since 2017-12-04
 */
public class Workflow extends Model<Workflow> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 审批名称
     */
	private String name;
    /**
     * 审批类型:1.请假审批;2.销假审批;3.出差审批;4.接待审批;5.公务用车审批;6.驻点人员请假审批;7.发文审批;8.收文审批
     */
	private String type;
    /**
     * 发起人id
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 发起人账号名
     */
	@TableField("userName")
	private String userName;
    /**
     * 发起科室id(预留字段)
     */
	@TableField("deptId")
	private Integer deptId;
    /**
     * 发起角色id
     */
	@TableField("roleId")
	private Integer roleId;
    /**
     * 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(必须通过);6.已撤销;
     */
	private String status;
    /**
     * 审批编号:生成策略 "1" += id,不直接使用id
     */
	private String ordnum;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;
    /**
     * 发起时间
     */
	@TableField("apprTime")
	private Date apprTime;
    /**
     * 审批结束时间: 审批结束时间: 审批通过 或者 审批未通过, 收文已办结
     */
	@TableField("endTime")
	private Date endTime;

/*	@TableField("mesEndTime")
	private Date mesEndTime;*/


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrdnum() {
		return ordnum;
	}

	public void setOrdnum(String ordnum) {
		this.ordnum = ordnum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

/*
	public Date getMesEndTime() {
		return mesEndTime;
	}

	public void setMesEndTime(Date mesEndTime) {
		this.mesEndTime = mesEndTime;
	}
*/

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Workflow{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", deptId=" + deptId +
				", roleId=" + roleId +
				", status='" + status + '\'' +
				", ordnum='" + ordnum + '\'' +
				", createTime=" + createTime +
				", apprTime=" + apprTime +
				", endTime=" + endTime +
				'}';
	}
}
