package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lgg
 * @since 2017-12-17
 */
public class WorkflowConfig extends Model<WorkflowConfig> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 审批类型:1.请假审批;2.销假审批;3.出差审批;4.接待审批;5.公务用车审批;6.驻点人员请假审批;7.发文审批;8.收文审批
     */
	private String type;
    /**
     * 发起人职位:1科员;2科长;3分管副局长;
     */
	private String duty;
    /**
     * 根据type跟职位duty确定的流程数量
     */
	private Integer num;
    /**
     * 审批职位:对应每一个下一步流程的职位以-分割如2-3-4
     */
	@TableField("spDuty")
	private String spDuty;
	private String name;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getSpDuty() {
		return spDuty;
	}

	public void setSpDuty(String spDuty) {
		this.spDuty = spDuty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WorkflowConfig{" +
			"id=" + id +
			", type=" + type +
			", duty=" + duty +
			", num=" + num +
			", spDuty=" + spDuty +
			", name=" + name +
			"}";
	}
}
