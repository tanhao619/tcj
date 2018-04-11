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
 * OA审批流程附加属性表
 * </p>
 *
 * @author lgg
 * @since 2017-12-04
 */
public class WorkflowProperty extends Model<WorkflowProperty> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 审批流程id
     */
	@TableField("workflowId")
	private Integer workflowId;
    /**
     * 审批流程附加属性名
     */
	private String name;
    /**
     * 审批流程附加属性值
     */
	private String value;
	private String type;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WorkflowProperty{" +
			"id=" + id +
			", workflowId=" + workflowId +
			", name=" + name +
			", value=" + value +
			", type=" + type +
			", createTime=" + createTime +
			"}";
	}
}
