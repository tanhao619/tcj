package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 投资方公司
 * </p>
 *
 * @author monkey
 * @since 2017-11-08
 */
public class ProCompany extends Model<ProCompany> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 项目id
     */
	@TableField("proId")
	private Integer proId;
    /**
     * 公司名称
     */
	@TableField("name")
	private String comName;
    /**
     * 公司地址
     */
	@TableField("addr")
	private String addr;
    /**
     * 联系人
     */
	@TableField("author")
	private String author;
    /**
     * 联系电话
     */
	@TableField("tel")
	private String tel;
    /**
     * 企业情况：1.世界500强企业 2.中国500强企业 3.中国服务业企业500强 4.中国民营500强企业 5.美国企业500强 6.独角兽估值300强 7.不属于任何类
     */
	@TableField("comType")
	private String comType;
    /**
     * 操作人id
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 操作人员
     */
	@TableField("userName")
	private String userName;
    /**
     * 创建时间
     */
	@TableField("createTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
    /**
     * 简介
     */
	@TableField("content")
	private String content;
    /**
     * 类型：1常规 2重大
     */
	@TableField("folType")
	private Integer folType;

    @TableField("currentTime")
    private String currentTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFolType() {
		return folType;
	}

	public void setFolType(Integer folType) {
		this.folType = folType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	@Override
	public String toString() {
		return "ProCompany{" +
			"id=" + id +
			", proId=" + proId +
			", name=" + comName +
			", addr=" + addr +
			", author=" + author +
			", tel=" + tel +
			", comType=" + comType +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			", content=" + content +
			", folType=" + folType +
			"}";
	}
}
