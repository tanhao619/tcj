package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * <p>
 * 常规项目表
 * </p>
 *
 * @author monkey
 * @since 2017-11-08
 */
public class NormalProject extends Model<NormalProject> {

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
     * 投资建设内容
     */
	private String content;
    /**
     * 投资来源地归属：1.市内 2市外
     */
	@TableField("fromArea")
	private String fromArea;
    /**
     * 投资类型：1外资2内资
     */
	@TableField("investType")
	private String investType;
    /**
     * 归属产业：1第一，2第二，3第三
     */
	private String category;
    /**
     * 信息提供者
     */
	private String author;
    /**
     * 信息提供者联系电话
     */
	private String tel;
    /**
     * 企业提供者
     */
	@TableField("enterOfferName")
	private String enterOfferName;
    /**
     * 企业提供者联系电话
     */
	@TableField("enterOfferTel")
	private String enterOfferTel;
    /**
     * 是否主导产业:1.是;0.否
     */
	@TableField("leadCategory")
	private String leadCategory;
    /**
     * 项目状态：1新建 2跟踪在谈 3拟签约 4已签约 0已取消
     */
	private String status;
    /**
     * 创建时间
     */
	@TableField("createTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("updateTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
    /**
     * 创建人
     */
	@TableField("createUserId")
	private Integer createUserId;
    /**
     * 修改人(每次修改必填)
     */
	@TableField("updateUserId")
	private Integer updateUserId;
    /**
     * 修改人(每次修改必填)
     */
	@TableField("followUserId")
	private Integer followUserId;
    /**
     * 投资额（rmb）
     */
	@TableField("investRmb")
	private String investRmb;
    /**
     * 投资额（美元）
     */
	@TableField("investDollar")
	private String investDollar;
    /**
     * 投资额（汇率）
     */
	@TableField("investRatio")
	private String investRatio;
    /**
     * 是否重大项目:1是 0否
     */
	@TableField("isBigPro")
	private String isBigPro;
    /**
     * 重大项目备案单位
     */
	@TableField("bigProCom")
	private String bigProCom;
    /**
     * 重大项目备案单位
     */
	@TableField("BigProTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date bigProTime;
    /**
     * 上传项目备案表(暂不用)
     */
	@TableField("uploadProTab")
	private String uploadProTab;
    /**
     * 联系区领导
     */
	private String leader;
    /**
     * 项目初次洽谈时间
     */
	@TableField("firstTalkTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date firstTalkTime;
    /**
     * 进展情况
     */
	private String progress;
    /**
     * 存在问题
     */
	private String question;
    /**
     * 下一步举措
     */
	@TableField("nextStep")
	private String nextStep;
    /**
     * 是否需要拜访
     */
	@TableField("isVisit")
	private String isVisit;
    /**
     * 是否需要拜访领导层级
     */
	@TableField("visitLv")
	private String visitLv;
    /**
     * 项目初审过会时间
     */
	@TableField("proMeetTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date proMeetTime;
    /**
     * 项目审定过会时间
     */
	@TableField("proViewTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date proViewTime;
    /**
     * 项目合同签约时间
     */
	@TableField("contractTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractTime;
    /**
     * 合同类型:1.框架协议 2.投资协议 3.PPP协议 4.其他协议
     */
	@TableField("contractType")
	private String contractType;
    /**
     * 项目牵头单位
     */
	@TableField("proConcatCom")
	private String proConcatCom;
    /**
     * 注册公司名称
     */
	@TableField("regComName")
	private String regComName;
    /**
     * 注册公司时间
     */
	@TableField("regComTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regComTime;
    /**
     * 注册资金（万元）
     */
	@TableField("regInvest")
	private String regInvest;
    /**
     * 注册号
     */
	@TableField("regNo")
	private String regNo;
    /**
     * 项目合同备案时间
     */
	@TableField("proContractTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date proContractTime;
    /**
     * 项目移交注册时间
     */
	@TableField("proRegTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date proRegTime;
    /**
     * 项目交办情况:1已交办 2未交办
     */
	@TableField("proType")
	private String proType;
    /**
     * 项目申报市重大项目时间
     */
	@TableField("regBigProTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regBigProTime;
    /**
     * 项目认定市重大项目时间
     */
	@TableField("regedBigProTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regedBigProTime;
    /**
     * 项目履约监管单位
     */
	@TableField("controlCom")
	private String controlCom;
    /**
     * 项目履约情况:1正常履约 2不正常履约
     */
	@TableField("proConventionType")
	private String proConventionType;
    /**
     * 情况说明
     */
	@TableField("proConventionInfo")
	private String proConventionInfo;
    /**
     * 洽谈创建时间
     */
	@TableField("talkCreateTime")
	private String talkCreateTime;
	/**
	 * 洽谈修改时间
	 */
	@TableField("talkUpdateTime")
	private Date talkUpdateTime;
	/**
	 * 履约创建时间
	 */
	@TableField("conCreateTime")
	private String conCreateTime;
	/**
	 * 履约修改时间
	 */
	@TableField("conUpdateTime")
	private Date conUpdateTime;
	/**
	 *
	 */
	@TableField("infoDesc")
	private String infoDesc;

	/**
	 * 下一步工作建议
	 */
	@TableField("nextAdvise")
	private String nextAdvise;

	@TableField("followName")
	private String followName;

	@TableField("followTel")
	private String followTel;

	@TableField("folType")
	private String folType;

	@TableField("isMerge")
	private String isMerge;

	@TableField("isMergeT")
	private String isMergeT;
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

	public String getIsMergeT() {
		return isMergeT;
	}

	public void setIsMergeT(String isMergeT) {
		this.isMergeT = isMergeT;
	}

	public String getIsMerge() {
		return isMerge;
	}

	public void setIsMerge(String isMerge) {
		this.isMerge = isMerge;
	}

	public String getFolType() {
		return folType;
	}

	public void setFolType(String folType) {
		this.folType = folType;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromArea() {
		return fromArea;
	}

	public void setFromArea(String fromArea) {
		this.fromArea = fromArea;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getInvestRmb() {
		return investRmb;
	}

	public void setInvestRmb(String investRmb) {
		this.investRmb = investRmb;
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

	public String getIsBigPro() {
		return isBigPro;
	}

	public void setIsBigPro(String isBigPro) {
		this.isBigPro = isBigPro;
	}

	public String getBigProCom() {
		return bigProCom;
	}

	public void setBigProCom(String bigProCom) {
		this.bigProCom = bigProCom;
	}

	public Date getBigProTime() {
		return bigProTime;
	}

	public void setBigProTime(Date bigProTime) {
		this.bigProTime = bigProTime;
	}

	public Date getFirstTalkTime() {
		return firstTalkTime;
	}

	public void setFirstTalkTime(Date firstTalkTime) {
		this.firstTalkTime = firstTalkTime;
	}

	public String getUploadProTab() {
		return uploadProTab;
	}

	public void setUploadProTab(String uploadProTab) {
		this.uploadProTab = uploadProTab;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(String isVisit) {
		this.isVisit = isVisit;
	}

	public String getVisitLv() {
		return visitLv;
	}

	public void setVisitLv(String visitLv) {
		this.visitLv = visitLv;
	}

	public Date getProMeetTime() {
		return proMeetTime;
	}

	public void setProMeetTime(Date proMeetTime) {
		this.proMeetTime = proMeetTime;
	}

	public Date getProViewTime() {
		return proViewTime;
	}

	public void setProViewTime(Date proViewTime) {
		this.proViewTime = proViewTime;
	}

	public Date getContractTime() {
		return contractTime;
	}

	public void setContractTime(Date contractTime) {
		this.contractTime = contractTime;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getProConcatCom() {
		return proConcatCom;
	}

	public void setProConcatCom(String proConcatCom) {
		this.proConcatCom = proConcatCom;
	}

	public String getRegComName() {
		return regComName;
	}

	public void setRegComName(String regComName) {
		this.regComName = regComName;
	}

	public Date getRegComTime() {
		return regComTime;
	}

	public void setRegComTime(Date regComTime) {
		this.regComTime = regComTime;
	}

	public String getRegInvest() {
		return regInvest;
	}

	public void setRegInvest(String regInvest) {
		this.regInvest = regInvest;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Date getProContractTime() {
		return proContractTime;
	}

	public void setProContractTime(Date proContractTime) {
		this.proContractTime = proContractTime;
	}

	public Date getProRegTime() {
		return proRegTime;
	}

	public void setProRegTime(Date proRegTime) {
		this.proRegTime = proRegTime;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public Date getRegBigProTime() {
		return regBigProTime;
	}

	public void setRegBigProTime(Date regBigProTime) {
		this.regBigProTime = regBigProTime;
	}

	public Date getRegedBigProTime() {
		return regedBigProTime;
	}

	public void setRegedBigProTime(Date regedBigProTime) {
		this.regedBigProTime = regedBigProTime;
	}

	public String getControlCom() {
		return controlCom;
	}

	public void setControlCom(String controlCom) {
		this.controlCom = controlCom;
	}

	public String getProConventionType() {
		return proConventionType;
	}

	public void setProConventionType(String proConventionType) {
		this.proConventionType = proConventionType;
	}

	public String getProConventionInfo() {
		return proConventionInfo;
	}

	public void setProConventionInfo(String proConventionInfo) {
		this.proConventionInfo = proConventionInfo;
	}

	public String getNextAdvise() {
		return nextAdvise;
	}

	public void setNextAdvise(String nextAdvise) {
		this.nextAdvise = nextAdvise;
	}

	public String getTalkCreateTime() {
		return talkCreateTime;
	}

	public void setTalkCreateTime(String talkCreateTime) {
		this.talkCreateTime = talkCreateTime;
	}

	public Date getTalkUpdateTime() {
		return talkUpdateTime;
	}

	public void setTalkUpdateTime(Date talkUpdateTime) {
		this.talkUpdateTime = talkUpdateTime;
	}

	public String getConCreateTime() {
		return conCreateTime;
	}

	public void setConCreateTime(String conCreateTime) {
		this.conCreateTime = conCreateTime;
	}

	public Date getConUpdateTime() {
		return conUpdateTime;
	}

	public void setConUpdateTime(Date conUpdateTime) {
		this.conUpdateTime = conUpdateTime;
	}

	public String getInfoDesc() {
		return infoDesc;
	}

	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}

	public String getEnterOfferName() {
		return enterOfferName;
	}

	public void setEnterOfferName(String enterOfferName) {
		this.enterOfferName = enterOfferName;
	}

	public String getEnterOfferTel() {
		return enterOfferTel;
	}

	public void setEnterOfferTel(String enterOfferTel) {
		this.enterOfferTel = enterOfferTel;
	}

	public String getLeadCategory() {
		return leadCategory;
	}

	public void setLeadCategory(String leadCategory) {
		this.leadCategory = leadCategory;
	}

	public Integer getFollowUserId() {
		return followUserId;
	}

	public void setFollowUserId(Integer followUserId) {
		this.followUserId = followUserId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "NormalProject{" +
				"id=" + id +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", fromArea='" + fromArea + '\'' +
				", investType='" + investType + '\'' +
				", category='" + category + '\'' +
				", author='" + author + '\'' +
				", tel='" + tel + '\'' +
				", status='" + status + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", createUserId=" + createUserId +
				", updateUserId=" + updateUserId +
				", investRmb='" + investRmb + '\'' +
				", investDollar='" + investDollar + '\'' +
				", investRatio='" + investRatio + '\'' +
				", isBigPro='" + isBigPro + '\'' +
				", bigProCom='" + bigProCom + '\'' +
				", bigProTime='" + bigProTime + '\'' +
				", uploadProTab='" + uploadProTab + '\'' +
				", leader='" + leader + '\'' +
				", firstTalkTime='" + firstTalkTime + '\'' +
				", progress='" + progress + '\'' +
				", question='" + question + '\'' +
				", nextStep='" + nextStep + '\'' +
				", isVisit='" + isVisit + '\'' +
				", visitLv='" + visitLv + '\'' +
				", proMeetTime=" + proMeetTime +
				", proViewTime=" + proViewTime +
				", contractTime=" + contractTime +
				", contractType='" + contractType + '\'' +
				", proConcatCom='" + proConcatCom + '\'' +
				", regComName='" + regComName + '\'' +
				", regComTime=" + regComTime +
				", regInvest='" + regInvest + '\'' +
				", regNo='" + regNo + '\'' +
				", proContractTime=" + proContractTime +
				", proRegTime=" + proRegTime +
				", proType='" + proType + '\'' +
				", regBigProTime=" + regBigProTime +
				", regedBigProTime=" + regedBigProTime +
				", controlCom='" + controlCom + '\'' +
				", proConventionType='" + proConventionType + '\'' +
				", proConventionInfo='" + proConventionInfo + '\'' +
				", talkCreateTime='" + talkCreateTime + '\'' +
				", talkUpdateTime='" + talkUpdateTime + '\'' +
				", conCreateTime='" + conCreateTime + '\'' +
				", conUpdateTime='" + conUpdateTime + '\'' +
				", nextAdvise='" + nextAdvise + '\'' +
				", isBack='" + isBack + '\'' +
				'}';
	}
}
