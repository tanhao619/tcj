package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 导出配置表
 * </p>
 *
 * @author monkey
 * @since 2017-11-08
 */
public class ExportConfig extends Model<ExportConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 配置列名
     */
	private String name;
    /**
     * 是否有下级：0否1是
     */
	@TableField("hasNext")
	private Integer hasNext;
    /**
     * 父级
     */
	private Integer pid;


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

	public Integer getHasNext() {
		return hasNext;
	}

	public void setHasNext(Integer hasNext) {
		this.hasNext = hasNext;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ExportConfig{" +
			"id=" + id +
			", name=" + name +
			", hasNext=" + hasNext +
			", pid=" + pid +
			"}";
	}
}
