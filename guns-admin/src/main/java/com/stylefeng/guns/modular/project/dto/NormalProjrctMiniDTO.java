package com.stylefeng.guns.modular.project.dto;

import com.stylefeng.guns.common.persistence.model.ProCompany;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
public class NormalProjrctMiniDTO {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 创建人编号
     */
    private Integer createUserId;
    /**
     * 创建人名
     */
    private String createUserName;
    /**
     * 创建人名
     */
    private String createAccount;
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
    private String fromArea;
    /**
     * 投资类型：1外资2内资
     */
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
     * 跟踪人员id
     */
    private Integer followUserId;
    /**
     * 跟踪人员姓名
     */
    private String followName;
    /**
     * 跟踪人员联系电话
     */
    private String followTel;
    /**
     * 企业提供者姓名
     */
    private String enterOfferName;
    /**
     * 企业提供者电话
     */
    private String enterOfferTel;
    /**
     * 是否主导产业
     */
    private String leadCategory;
    /**
     * 项目状态：1新建 2跟踪在谈 3拟签约 4已签约 0已取消
     */
    private String status;
    /**
     * 项目跟踪状态:1.待审核;2.待分配;3.正在洽谈;
     */
    private String folType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String infoDesc;
    /**
     * 投资方公司
     */
    private List<ProCompany> companies;
    /**
     * 跟踪科室id
     */
    private List<Object> followOffice;
    /**
     *跟踪平台id
     * @return
     */
    private List<Object> followFlat;
    private List<Map> chanyeList;//归属产业集合

    public List<Map> getChanyeList() {
        return chanyeList;
    }

    public void setChanyeList(List<Map> chanyeList) {
        this.chanyeList = chanyeList;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public List<ProCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<ProCompany> companies) {
        this.companies = companies;
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = infoDesc;
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

    public List<Object> getFollowOffice() {
        return followOffice;
    }

    public void setFollowOffice(List<Object> followOffice) {
        this.followOffice = followOffice;
    }

    public List<Object> getFollowFlat() {
        return followFlat;
    }

    public void setFollowFlat(List<Object> followFlat) {
        this.followFlat = followFlat;
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

    public String getFolType() {
        return folType;
    }

    public void setFolType(String folType) {
        this.folType = folType;
    }

    public Integer getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Integer followUserId) {
        this.followUserId = followUserId;
    }
}
