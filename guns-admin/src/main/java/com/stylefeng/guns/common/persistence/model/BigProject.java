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
 * 重大包装项目表
 * </p>
 *
 * @author monkey
 * @since 2017-11-30
 */
public class BigProject extends Model<BigProject> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 投资项目名称
     */
	private String name;
    /**
     * 跟踪人id
     */
	@TableField("followUserId")
	private String followUserId;
    /**
     * 跟踪人员名称
     */
	@TableField("followName")
	private String followName;
    /**
     * 跟踪人员电话
     */
	@TableField("followTel")
	private String followTel;
    /**
     * 归属产业：1第一，2第二，3第三
     */
	private String category;
    /**
     * 项目阶段：1新建 2跟踪在谈 3拟签约 4已签约 0已取消
     */
	private String status;
    /**
     * 匡算总投资额人民币(亿元)
     */
	@TableField("investRmb")
	private String investRmb;
    /**
     * 匡算总投资额拆迁费人民币(亿元)
     */
	@TableField("investMvRmb")
	private String investMvRmb;
    /**
     * 投资额(美元)
     */
	@TableField("investDollar")
	private String investDollar;
    /**
     * 投资额(汇率)
     */
	@TableField("investRatio")
	private String investRatio;
    /**
     * 建设内容
     */
	private String content;
    /**
     * 国有建设用地(亩)
     */
	@TableField("govArea")
	private String govArea;
    /**
     * 集体建设用地(亩)
     */
	@TableField("collectArea")
	private String collectArea;
    /**
     * 农用地(亩)
     */
	@TableField("farmArea")
	private String farmArea;
    /**
     * 计划开工日期
     */
	@TableField("planStartTime")
	private Date planStartTime;
    /**
     * 计划竣工日期
     */
	@TableField("planEndTime")
	private Date planEndTime;
    /**
     * 建议年底拟达到的工作进度
     */
	@TableField("planProcess")
	private String planProcess;
    /**
     * 分管区领导
     */
	private String leader;
    /**
     * 修改工作进度时间
     */
	@TableField("updateWorkTime")
	private Date updateWorkTime;
    /**
     * 目前工作进度
     */
	@TableField("workProcess")
	private String workProcess;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("updateTime")
	private Date updateTime;
    /**
     * 创建人
     */
	@TableField("createUserId")
	private Integer createUserId;
    /**
     * 修改人
     */
	@TableField("updateUserId")
	private Integer updateUserId;
    /**
     * 备注
     */
	@TableField("infoDesc")
	private String infoDesc;
    /**
     * 项目跟踪状态:1.待审核;2.待分配;3.正在洽谈;
     */
	@TableField("folType")
	private String folType;
    /**
     * 时间戳
     */
	@TableField("currentTime")
	private String currentTime;
	/**
	 * 是否新增:1.是;0.否;
	 */
	@TableField("isMerge")
	private String isMerge;

	/**
	 * 是否修改（新增）
	 */
	@TableField("isBack")
	private String isBack;

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getIsMerge() {
		return isMerge;
	}

	public void setIsMerge(String isMerge) {
		this.isMerge = isMerge;
	}

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

	public String getFollowUserId() {
		return followUserId;
	}

	public void setFollowUserId(String followUserId) {
		this.followUserId = followUserId;
	}

	public String getFollowName() {
		return followName;
	}

	public void setFollowName(String followName) {
		this.followName = followName;
	}

	public String getFollowTel() {
		return followTel;
	}

	public void setFollowTel(String followTel) {
		this.followTel = followTel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvestRmb() {
		return investRmb;
	}

	public void setInvestRmb(String investRmb) {
		this.investRmb = investRmb;
	}

	public String getInvestMvRmb() {
		return investMvRmb;
	}

	public void setInvestMvRmb(String investMvRmb) {
		this.investMvRmb = investMvRmb;
	}

	public String getInvestDollar() {
		return investDollar;
	}

	public void setInvestDollar(String investDollar) {
		this.investDollar = investDollar;
	}

	public String getInvestRatio() {
		return investRatio;
	}

	public void setInvestRatio(String investRatio) {
		this.investRatio = investRatio;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGovArea() {
		return govArea;
	}

	public void setGovArea(String govArea) {
		this.govArea = govArea;
	}

	public String getCollectArea() {
		return collectArea;
	}

	public void setCollectArea(String collectArea) {
		this.collectArea = collectArea;
	}

	public String getFarmArea() {
		return farmArea;
	}

	public void setFarmArea(String farmArea) {
		this.farmArea = farmArea;
	}

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getPlanProcess() {
		return planProcess;
	}

	public void setPlanProcess(String planProcess) {
		this.planProcess = planProcess;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public Date getUpdateWorkTime() {
		return updateWorkTime;
	}

	public void setUpdateWorkTime(Date updateWorkTime) {
		this.updateWorkTime = updateWorkTime;
	}

	public String getWorkProcess() {
		return workProcess;
	}

	public void setWorkProcess(String workProcess) {
		this.workProcess = workProcess;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getInfoDesc() {
		return infoDesc;
	}

	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}

	public String getFolType() {
		return folType;
	}

	public void setFolType(String folType) {
		this.folType = folType;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "BigProject{" +
			"id=" + id +
			", name=" + name +
			", followUserId=" + followUserId +
			", followName=" + followName +
			", followTel=" + followTel +
			", category=" + category +
			", status=" + status +
			", investRmb=" + investRmb +
			", investMvRmb=" + investMvRmb +
			", investDollar=" + investDollar +
			", investRatio=" + investRatio +
			", content=" + content +
			", govArea=" + govArea +
			", collectArea=" + collectArea +
			", farmArea=" + farmArea +
			", planStartTime=" + planStartTime +
			", planEndTime=" + planEndTime +
			", planProcess=" + planProcess +
			", leader=" + leader +
			", updateWorkTime=" + updateWorkTime +
			", workProcess=" + workProcess +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", createUserId=" + createUserId +
			", updateUserId=" + updateUserId +
			", infoDesc=" + infoDesc +
			", folType=" + folType +
			", currentTime=" + currentTime +
				", isBack='" + isBack +
			"}";
	}
}
