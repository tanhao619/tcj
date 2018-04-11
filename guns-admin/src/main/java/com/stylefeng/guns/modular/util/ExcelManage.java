package com.stylefeng.guns.modular.util;

import com.alibaba.druid.util.StringUtils;
import com.stylefeng.guns.modular.project.dao.NormalProjectDao;
import com.stylefeng.guns.modular.project.dto.BigExcelExportDTO;
import com.stylefeng.guns.modular.project.dto.ExportExcelDTO;
import com.stylefeng.guns.modular.project.service.INormalProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tanhao on 2017/11/21.
 */
@Component
public class ExcelManage {
    @Autowired
    private INormalProjectService normalProjectService;
    @Autowired
    NormalProjectDao normalProjectDao;
    /***
     * 构造方法
     */
    private ExcelManage() {

    }
    public Map<String, String> setHeader(String[] name){
        List<String> names = new ArrayList<>(Arrays.asList(name));
        Map<String, String> titleMap = new LinkedHashMap<>();
        for (int i = 0 ; i < names.size() ; i++){
                /**数据开始*/

            /**
             * 项目基本信息
             */
                 if (names.get(i).contains("normal_project.id")){
                    titleMap.put("id", "序号");
                }
                 if (names.get(i).contains("pro_company.name")){
                    titleMap.put("comName", "投资方名称");
                }
                if (names.get(i).contains("pro_company.addr")){
                    titleMap.put("comAddr", "投资公司地址");
                }
                if (names.get(i).contains("pro_company.content")){
                    titleMap.put("comContent", "投资方简介");
                }
                if (names.get(i).contains("pro_company.comType")){
                    titleMap.put("comComType", "投资方是否六类500强、独角兽企业情况");
                }
                if (names.get(i).contains("pro_company.author")){
                    titleMap.put("companyInfo", "投资方联系人及电话");
                }
                if (names.get(i).contains("normal_project.name")){
                    titleMap.put("norName", "投资项目名称");
                }
                if (names.get(i).contains("normal_project.content")){
                    titleMap.put("norContent", "项目建设内容");
                }
                if (names.get(i).contains("normal_project.fromArea")){
                    titleMap.put("norFromArea", "投资来源地归属");
                }
                if (names.get(i).contains("normal_project.investType")){
                    titleMap.put("norInvestType", "投资类型");
                }
                if (names.get(i).contains("normal_project.category")){
                    titleMap.put("norCategory", "项目归属产业");
                }
                if (names.get(i).contains("normal_project.leadCategory")){
                    titleMap.put("norLeadCategory", "是否主导产业");
                }
                if (names.get(i).contains("normal_project.enterOfferName")){
                    titleMap.put("norEnterOfferInfo", "企业联系人及联系电话");
                }
                if (names.get(i).contains("normal_project.author")){
                    titleMap.put("norProInfo", "信息提供人及联系电话");
                }
                if (names.get(i).contains("follow_project.deptId")){
                    titleMap.put("folDeptId", "投促局跟进科室");
                }
                if (names.get(i).contains("normal_project.followName")){
                    titleMap.put("norFollowInfo", "投促局跟进人及联系电话");
                }
                if (names.get(i).contains("pro_area.addr")){
                    titleMap.put("proAddr", "项目地址");
                }
                if (names.get(i).contains("pro_area.useArea")){
                    titleMap.put("proUseArea", "用地/房面积（亩、平方米）");
                }
                if (names.get(i).contains("normal_project.countRmb")){
                    titleMap.put("norInvestRmb", "投资额(亿元人民币）");
                }
                if (names.get(i).contains("normal_project.investDollar")){
                    titleMap.put("norInvestDollar", "投资额(万美元）");
                }
                if (names.get(i).contains("normal_project.isBigPro")){
                    titleMap.put("norIsBigPro", "是否重大项目");
                }
                if (names.get(i).contains("normal_project.leader")){
                    titleMap.put("norLeader", "联系区领导");
                }
                if (names.get(i).contains("unit_liable.liable")){
                    titleMap.put("uniLiable", "项目责任单位");
                }
                if (names.get(i).contains("unit_liable.name")){
                    titleMap.put("uniInfo", "责任人及联系电话");
                }
                if (names.get(i).contains("normal_project.bigProCom")){
                    titleMap.put("norBigProCom", "重大项目备案单位");
                }
                if (names.get(i).contains("normal_project.bigProTime")){
                    titleMap.put("norBigProTime", "重大项目备案时间");
                }
                if (names.get(i).contains("normal_project.status")){
                    titleMap.put("norStatus", "项目状态");
                }
                if (names.get(i).contains("pro_adviseOpeType.name")){
                    titleMap.put("advName", "建议实施方式");
                }

            /**
             * 项目洽谈与签约信息
             */
            if (names.get(i).contains("normal_project.firstTalkTime")){
                titleMap.put("norFirstTalkTime", "项目初次洽谈时间");
            }
            if (names.get(i).contains("pro_talk.progress")){
                titleMap.put("talkProgress", "进展情况");
            }
            if (names.get(i).contains("pro_talk.question")){
                titleMap.put("talkQuestion", "存在问题");
            }
            if (names.get(i).contains("pro_talk.nextStep")){
                titleMap.put("talkNextStep", "下一步举措");
            }
            if (names.get(i).contains("pro_talk.isVisit")){
                titleMap.put("talkIsvisit", "是否需要拜访");
            }
            if (names.get(i).contains("pro_talk.visitLv")){
                titleMap.put("talkVisitLv", "拜访所需领导层级");
            }
            if (names.get(i).contains("normal_project.proMeetTime")){
                titleMap.put("norProMeetTime", "项目初审过会时间");
            }
            if (names.get(i).contains("normal_project.proViewTime")){
                titleMap.put("norProViewTime", "项目审定过会时间");
            }
            if (names.get(i).contains("normal_project.contractTime")){
                titleMap.put("norContractTime", "项目合同签约时间");
            }
            if (names.get(i).contains("pro_contractType.name")){
                titleMap.put("conContractType", "合同类型");
            }
            if (names.get(i).contains("normal_project.controlCom")){
                titleMap.put("norControlCom", "项目履约监管单位");
            }
            if (names.get(i).contains("normal_project.proContractTime")){
                titleMap.put("norProContractTime", "项目合同备案时间");
            }
            if (names.get(i).contains("normal_project.proRegTime")){
                titleMap.put("norProRegTime", "项目移交注册时间");
            }
            if (names.get(i).contains("normal_project.proType")){
                titleMap.put("norProType", "项目是否已交办");
            }
            if (names.get(i).contains("normal_project.regBigProTime")){
                titleMap.put("norRegBigProTime", "项目申报市重大项目时间");
            }
            if (names.get(i).contains("normal_project.regedBigProTime")){
                titleMap.put("norRegedBigProTime", "项目认定市重大项目时间");
            }

            /**
             * 项目履约信息
             */
            if (names.get(i).contains("normal_project.proConcatCom")){
                titleMap.put("norProConcatCom", "项目牵头单位");
            }
            if (names.get(i).contains("normal_project.regComName")){
                titleMap.put("norRegComName", "注册公司名称");
            }
            if (names.get(i).contains("normal_project.regComTime")){
                titleMap.put("norRegComTime", "注册公司时间");
            }
            if (names.get(i).contains("normal_project.regInvest")){
                titleMap.put("norRegInvest", "注册资金（万元）");
            }
            if (names.get(i).contains("normal_project.regNo")){
                titleMap.put("norRegNo", "注册号");
            }
            if (names.get(i).contains("normal_project.proConventionType")){
                titleMap.put("norProConventionType", "是否正常履约");
            }
            if (names.get(i).contains("pro_convention.proConventionInfo")){
                titleMap.put("conProConventionInfo", "未正常履约原因");
            }
            if (names.get(i).contains("pro_convention.nextAdvise")){
                titleMap.put("conNextAdvise", "下一步工作建议");
            }


            if (names.get(i).contains("normal_project.infoDesc")){
                titleMap.put("norInfoDesc", "备注");
            }
            /**数据结束*/
                }
        return titleMap;
    }

    public List<ExportExcelDTO> setInfo(List<ExportExcelDTO> list,String[] name){
        List<String> fn = new ArrayList<>(Arrays.asList(name));
        List<ExportExcelDTO> res = new ArrayList<>();
        for (int i = 0 ; i < list.size() ; i++){
            String companyInfo = "";
            String norEnterOfferInfo = "";
            String norProInfo = "";
            String norFollowInfo = "";
            String uniInfo = "";
            for (int j = 0 ; j < fn.size() ; j++){
                if (fn.get(j).contains("pro_company.author")){
                    List<String> author = new ArrayList<>(Arrays.asList(list.get(i).getComAuthor().split(",")));
                    List<String> tel = new ArrayList<>(Arrays.asList(list.get(i).getComTel().split(",")));
                    for (int z = 0 ; z < author.size() ; z ++){
                        if (!StringUtils.isEmpty(author.get(z)) || !StringUtils.isEmpty(tel.get(z))){
                            String str = author.get(z) + "—" +tel.get(z) + ",";
                            companyInfo = companyInfo + str;
                        }
                    }
                }
                if (fn.get(j).contains("normal_project.enterOfferName")){
                    List<String> author = new ArrayList<>(Arrays.asList(list.get(i).getNorEnterOfferName().split(",")));
                    List<String> tel = new ArrayList<>(Arrays.asList(list.get(i).getNorEnterOfferTel().split(",")));
                    for (int z = 0 ; z < author.size() ; z ++){
                        if (!StringUtils.isEmpty(author.get(z)) || !StringUtils.isEmpty(tel.get(z))){
                        String str = author.get(z) + "—" +tel.get(z) + ",";
                        norEnterOfferInfo = norEnterOfferInfo + str;
                        }
                    }
                }
                if (fn.get(j).contains("normal_project.author")){
                    List<String> author = new ArrayList<>(Arrays.asList(list.get(i).getNorAuthor().split(",")));
                    List<String> tel = new ArrayList<>(Arrays.asList(list.get(i).getNorTel().split(",")));
                    for (int z = 0 ; z < author.size() ; z ++){
                        if (!StringUtils.isEmpty(author.get(z)) || !StringUtils.isEmpty(tel.get(z))) {
                            String str = author.get(z) + "—" + tel.get(z) + ",";
                            norProInfo = norProInfo + str;
                        }
                    }
                }
                if (fn.get(j).contains("normal_project.followName")){
                    List<String> author = new ArrayList<>(Arrays.asList(list.get(i).getNorFollowName().split(",")));
                    List<String> tel = new ArrayList<>(Arrays.asList(list.get(i).getNorFollowTel().split(",")));
                    for (int z = 0 ; z < author.size() ; z ++){
                        if (!StringUtils.isEmpty(author.get(z)) || !StringUtils.isEmpty(tel.get(z))) {
                            String str = author.get(z) + "—" + tel.get(z) + ",";
                            norFollowInfo = norFollowInfo + str;
                        }
                    }
                }
                if (fn.get(j).contains("unit_liable.name")){
                    List<String> author = new ArrayList<>(Arrays.asList(list.get(i).getUniName().split(",")));
                    List<String> tel = new ArrayList<>(Arrays.asList(list.get(i).getUniTel().split(",")));
                    for (int z = 0 ; z < author.size() ; z ++){
                        if (!StringUtils.isEmpty(author.get(z)) || !StringUtils.isEmpty(tel.get(z))) {
                            String str = author.get(z) + "—" + tel.get(z) + ",";
                            uniInfo = uniInfo + str;
                        }
                    }
                }
            }

            ExportExcelDTO dto = new ExportExcelDTO(list.get(i).getId(),list.get(i).getComName(),list.get(i).getComComType(),list.get(i).getComAddr(),list.get(i).getComContent(),companyInfo,
                    list.get(i).getNorName(),list.get(i).getNorContent(),list.get(i).getNorFromArea(),list.get(i).getNorInvestType(),list.get(i).getNorCategory(),
                    list.get(i).getNorLeadCategory(),norEnterOfferInfo,norProInfo,list.get(i).getFolDeptId(),norFollowInfo,
                    list.get(i).getProAddr(),list.get(i).getProUseArea(),list.get(i).getNorInvestRmb(),list.get(i).getNorInvestDollar(),list.get(i).getNorInvestRatio(),list.get(i).getNorIsBigPro(),
                    list.get(i).getNorLeader(),list.get(i).getUniLiable(),uniInfo,list.get(i).getNorBigProCom(),list.get(i).getNorBigProTime(),list.get(i).getNorStatus(),list.get(i).getAdvName(),list.get(i).getNorFirstTalkTime(),
                    list.get(i).getTalkProgress(),list.get(i).getTalkQuestion(),list.get(i).getTalkNextStep(),list.get(i).getTalkIsvisit(),list.get(i).getTalkVisitLv(),list.get(i).getNorProMeetTime(),list.get(i).getNorProViewTime(),
                    list.get(i).getNorContractTime(),list.get(i).getConContractType(),list.get(i).getNorControlCom(),list.get(i).getNorProContractTime(),list.get(i).getNorProRegTime(),list.get(i).getNorProType(),
                    list.get(i).getNorRegBigProTime(),list.get(i).getNorRegedBigProTime(),list.get(i).getNorProConcatCom(),list.get(i).getNorRegComName(),list.get(i).getNorRegComTime(),list.get(i).getNorRegInvest(),
                    list.get(i).getNorRegNo(),list.get(i).getNorProConventionType(),list.get(i).getConProConventionInfo(),list.get(i).getConNextAdvise(),list.get(i).getNorInfoDesc());
            res.add(dto);
        }
        return res;
    }

    public List<ExportExcelDTO> resultManageList(List<ExportExcelDTO> list){
        for (int k = 0 ; k < list.size() ; k ++){
            String comComType = "";
            String norLeadCategory = "";
            String norFromArea = "";
            String norInvestType = "";
            String norCategory = "";
            String norProType = "";
            String norProConventionType = "";
            String norIsBigPro = "";
            String conContractType = "";
            String norIsvisit = "";
            String norStatus = "";
            String proAddr = "";
            String proUseArea = "";
            String comName = "";
            String comAddr = "";
            String folDeptId = "";
            String companyInfo = "";
            String norEnterOfferInfo = "";
            String norProInfo = "";
            String norFollowInfo = "";
            String uniInfo = "";
            String comContent = "";
            String norBigProTime = "";
            String norFirstTalkTime = "";
            String norContractTime = "";
            String norProMeetTime = "";
            String norProViewTime = "";
            String norRegBigProTime = "";
            String norRegedBigProTime = "";
            String norProContractTime = "";
            String norProRegTime = "";
            String norRegComTime = "";
            String talkProgress = "";
            String talkQuestion = "";
            String talkNextStep = "";
            String talkIsvisit = "";
            String talkVisitLv = "";
            String advName = "";
            String uniLiable = "";
            String conProConventionInfo = "";
            String conNextAdvise = "";
            //企业情况:1.世界500强企业;2.中国500强企业;3.中国服务业企业500强;4.中国民营500强企业;5.美国企业500强;6.独角兽估值300强;7.不属于任何类;
            List<String> ct = new ArrayList<>(Arrays.asList(list.get(k).getComComType().split(",")));
            for (int i = 0 ; i < ct.size() ; i ++){
                String str = "";
                if (!StringUtils.isEmpty(ct.get(i)) && ct.get(i).trim().equals("1")){
                    str = i + 1 + "." + "世界500强企业;";
                }
                if (!StringUtils.isEmpty(ct.get(i)) && ct.get(i).trim().equals("2")){
                    str = i + 1 + "." + "中国500强企业;";
                }
                if (!StringUtils.isEmpty(ct.get(i)) && ct.get(i).trim().equals("3")){
                    str = i + 1 + "." + "中国服务业企业500强;";
                }
                if (!StringUtils.isEmpty(ct.get(i)) && ct.get(i).trim().equals("4")){
                    str = i + 1 + "." + "中国民营500强企业;";
                }
                if (!StringUtils.isEmpty(ct.get(i)) && ct.get(i).trim().equals("5")){
                    str = i + 1 + "." + "美国企业500强;";
                }
                if (!StringUtils.isEmpty(ct.get(i)) && ct.get(i).trim().equals("6")){
                    str = i + 1 + "." + "独角兽估值300强;";
                }
                if (!StringUtils.isEmpty(ct.get(i)) && ct.get(i).trim().equals("7")){
                    str = i + 1 + "." + "否;";
                }
                comComType = comComType + str +"\r\n";
            }

            //是否主导产业:1.是;0.否;
            String nlc = list.get(k).getNorLeadCategory();
            if (!StringUtils.isEmpty(nlc) && nlc.trim().equals("0")){
                norLeadCategory = "否";
            }
            if (!StringUtils.isEmpty(nlc) && nlc.trim().equals("1")){
                norLeadCategory = "是";
            }

            //投资来源地归属:1.市内; 2.市外;
            String nfe = list.get(k).getNorFromArea();
            if (!StringUtils.isEmpty(nfe) && nfe.trim().equals("1")){
                norFromArea = "市内";
            }
            if (!StringUtils.isEmpty(nfe) && nfe.trim().equals("2")){
                norFromArea = "市外";
            }

            //投资类型:1.外资;2.内资;3.合资
            String nit = list.get(k).getNorInvestType();
            if (!StringUtils.isEmpty(nit) && nit.trim().equals("1")){
                norInvestType = "外资";
            }
            if (!StringUtils.isEmpty(nit) && nit.trim().equals("2")){
                norInvestType = "内资";
            }
            if (!StringUtils.isEmpty(nit) && nit.trim().equals("3")){
                norInvestType = "合资";
            }

            //归属产业:1.第一;2.第二;3.第三;
            String nc = list.get(k).getNorCategory();
            if (!StringUtils.isEmpty(nc) && nc.trim().equals("1")){
                norCategory = "第一";
            }
            if (!StringUtils.isEmpty(nc) && nc.trim().equals("2")){
                norCategory = "第二";
            }
            if (!StringUtils.isEmpty(nc) && nc.trim().equals("3")){
                norCategory = "第三";
            }

            //项目交办情况:1.已交办;2.未交办;
            String npt = list.get(k).getNorProType();
            if (!StringUtils.isEmpty(npt) && npt.trim().equals("1")){
                norProType = "已交办";
            }
            if (!StringUtils.isEmpty(npt) && npt.trim().equals("2")){
                norProType = "未交办";
            }

            //项目履约情况:1.不正常履约;2.正常履约;
            String npct = list.get(k).getNorProConventionType();
            if (!StringUtils.isEmpty(npct) && npct.trim().equals("1")){
                norProConventionType = "不正常履约";
            }
            if (!StringUtils.isEmpty(npct) && npct.trim().equals("2")){
                norProConventionType = "正常履约";
            }

            //是否重大项目:1.是;0.否;
            String nibp = list.get(k).getNorIsBigPro();
            if (!StringUtils.isEmpty(nibp) && nibp.trim().equals("0")){
                norIsBigPro = "否";
            }
            if (!StringUtils.isEmpty(nibp) && nibp.trim().equals("1")){
                norIsBigPro = "是";
            }

            //合同类型名:1.框架协议;2.投资协议;3.PPP协议;4.双创协议;5.政校银企合作协议;6.解除协议;7.其他协议;
            List<String> cct = new ArrayList<>(Arrays.asList(list.get(k).getConContractType().split(",")));
            for (int j = 0 ; j < cct.size() ; j ++) {
                String str = "";
                if (!StringUtils.isEmpty(cct.get(j)) && cct.get(j).trim().equals("1")) {
                    str = j + 1 + "." +"框架协议;";
                }
                if (!StringUtils.isEmpty(cct.get(j)) && cct.get(j).trim().equals("2")) {
                    str = j + 1 + "." +"投资协议;";
                }
                if (!StringUtils.isEmpty(cct.get(j)) && cct.get(j).trim().equals("3")) {
                    str = j + 1 + "." +"PPP协议;";
                }
                if (!StringUtils.isEmpty(cct.get(j)) && cct.get(j).trim().equals("4")) {
                    str = j + 1 + "." +"双创协议;";
                }
                if (!StringUtils.isEmpty(cct.get(j)) && cct.get(j).trim().equals("5")) {
                    str = j + 1 + "." +"政校银企合作协议;";
                }
                if (!StringUtils.isEmpty(cct.get(j)) && cct.get(j).trim().equals("6")) {
                    str = j + 1 + "." +"解除协议;";
                }
                if (!StringUtils.isEmpty(cct.get(j)) && cct.get(j).trim().equals("7")) {
                    str = j + 1 + "." +"其他协议;";
                }
                conContractType = conContractType + str +"\r\n";
            }

            //是否需要拜访:1.是;0.否;
            String niv = list.get(k).getTalkIsvisit();
            if (!StringUtils.isEmpty(niv) && niv.trim().equals("0")){
                norIsvisit = "否";
            }
            if (!StringUtils.isEmpty(niv) && niv.trim().equals("1")){
                norIsvisit = "是";
            }

            //常规项目项目状态:1.申报储备;2.跟踪在谈;3.拟签约;4.已签约;5.履约;0.已取消;
            String ns = list.get(k).getNorStatus();
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("1")){
                norStatus = "申报储备";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("2")){
                norStatus = "跟踪在谈";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("3")){
                norStatus = "拟签约";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("4")){
                norStatus = "已签约";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("5")){
                norStatus = "履约";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("0")){
                norStatus = "已取消";
            }

            //项目地址
            List<String> pa = new ArrayList<>(Arrays.asList(list.get(k).getProAddr().split(",")));
            for (int t = 0 ; t < pa.size() ; t ++) {
                if (!StringUtils.isEmpty(pa.get(t))) {
                    String str = t + 1 + "." + pa.get(t) + ";";
                    proAddr = proAddr + str +"\r\n";
                }
            }

            //用地/房面积（亩、平方米）
            List<String> pua = new ArrayList<>(Arrays.asList(list.get(k).getProUseArea().split(",")));
            for (int x = 0 ; x < pua.size() ; x ++) {
                if (!StringUtils.isEmpty(pua.get(x))) {
                    String str = x + 1 + "." + pua.get(x) + ";";
                    proUseArea = proUseArea + str +"\r\n";
                }
            }

            //投资方名称
            List<String> cn = new ArrayList<>(Arrays.asList(list.get(k).getComName().split(",")));
            for (int s = 0 ; s < cn.size() ; s ++) {
                if (!StringUtils.isEmpty(cn.get(s))) {
                    String str = s + 1 + "." + cn.get(s) + ";";
                    comName = comName + str +"\r\n";
                }
            }

            //投资方地址
            List<String> ca = new ArrayList<>(Arrays.asList(list.get(k).getComAddr().split(",")));
            for (int q = 0 ; q < ca.size() ; q ++) {
                if (!StringUtils.isEmpty(ca.get(q))) {
                    String str = q + 1 + "." + ca.get(q) + ";";
                    comAddr = comAddr + str +"\r\n";
                }
            }

            //投促局跟进科室
            List<String> fdi = new ArrayList<>(Arrays.asList(list.get(k).getFolDeptId().split(",")));
            for (int w = 0 ; w < fdi.size() ; w ++) {
                String deptName = "";
                if (!StringUtils.isEmpty(fdi.get(w))){
                    deptName = normalProjectService.getDeptNameById(Integer.valueOf(fdi.get(w)));
                }
                if (!StringUtils.isEmpty(deptName)){
                    String str = w + 1 + "." + deptName + ";";
                    folDeptId = folDeptId + str +"\r\n";
                }
            }

            //投资方联系人及电话
            List<String> ci = new ArrayList<>(Arrays.asList(list.get(k).getCompanyInfo().split(",")));
            for (int e = 0 ; e < ci.size() ; e ++) {
                if (!StringUtils.isEmpty(ci.get(e))) {
                    String str = e + 1 + "." + ci.get(e) + ";";
                    companyInfo = companyInfo + str +"\r\n";
                }
            }

            //企业联系人及联系电话
            List<String> neoi = new ArrayList<>(Arrays.asList(list.get(k).getNorEnterOfferInfo().split(",")));
            for (int r = 0 ; r < neoi.size() ; r ++) {
                if (!StringUtils.isEmpty(neoi.get(r))) {
                    String str = r + 1 + "." + neoi.get(r) + ";";
                    norEnterOfferInfo = norEnterOfferInfo + str +"\r\n";
                }
            }

            //信息提供人及联系电话
            List<String> npi = new ArrayList<>(Arrays.asList(list.get(k).getNorProInfo().split(",")));
            for (int tt = 0 ; tt < npi.size() ; tt ++) {
                if (!StringUtils.isEmpty(npi.get(tt))) {
                    String str = tt + 1 + "." + npi.get(tt) + ";";
                    norProInfo = norProInfo + str +"\r\n";
                }
            }

            //投促局跟进人及联系电话
            List<String> nfi = new ArrayList<>(Arrays.asList(list.get(k).getNorFollowInfo().split(",")));
            for (int y = 0 ; y < nfi.size() ; y ++) {
                if (!StringUtils.isEmpty(nfi.get(y))) {
                    String str = y + 1 + "." + nfi.get(y) + ";";
                    norFollowInfo = norFollowInfo + str +"\r\n";
                }
            }

            //责任单位
            List<String> ul = new ArrayList<>(Arrays.asList(list.get(k).getUniLiable().split(",")));
            for (int p = 0 ; p < ul.size() ; p ++) {
                if (!StringUtils.isEmpty(ul.get(p))) {
                    String str = p + 1 + "." + ul.get(p) + ";";
                    uniLiable = uniLiable + str +"\r\n";
                }
            }

            //责任人及联系电话
            List<String> ui = new ArrayList<>(Arrays.asList(list.get(k).getUniInfo().split(",")));
            for (int p = 0 ; p < ui.size() ; p ++) {
                if (!StringUtils.isEmpty(ui.get(p))) {
                    String str = p + 1 + "." + ui.get(p) + ";";
                    uniInfo = uniInfo + str +"\r\n";
                }
            }

            //公司简介
            List<String> cc = new ArrayList<>(Arrays.asList(list.get(k).getComContent().split("%&")));
                for (int p = 0 ; p < cc.size() ; p ++) {
                    if (!StringUtils.isEmpty(cc.get(p))){
                        String str = p + 1 + "." + cc.get(p) + ";";
                        comContent = comContent + str +"\r\n";
                    }
                }


            /**
             * 时间格式处理
             */
            //重大项目备案时间
            String nbpt = list.get(k).getNorBigProTime();
                if (!StringUtils.isEmpty(nbpt)){
                    norBigProTime = nbpt.substring(0,10).replace("-","/");
                }

                //项目初次洽谈时间
            String nftt = list.get(k).getNorFirstTalkTime();
            if (!StringUtils.isEmpty(nftt)){
                norFirstTalkTime = nftt.substring(0,10).replace("-","/");
            }

            //项目合同签约时间
            String nct = list.get(k).getNorContractTime();
            if (!StringUtils.isEmpty(nct)){
                norContractTime = nct.substring(0,10).replace("-","/");
            }

            //项目初审过会时间
            String npmt = list.get(k).getNorProMeetTime();
            if (!StringUtils.isEmpty(npmt)){
                norProMeetTime = npmt.substring(0,10).replace("-","/");
            }

            //项目审定过会时间
            String npvt = list.get(k).getNorProViewTime();
            if (!StringUtils.isEmpty(npvt)){
                norProViewTime = npvt.substring(0,10).replace("-","/");
            }

            //项目申报市重大项目时间
            String nrbpt = list.get(k).getNorRegBigProTime();
            if (!StringUtils.isEmpty(nrbpt)){
                norRegBigProTime = nrbpt.substring(0,10).replace("-","/");
            }

            //项目认定市重大项目时间
            String nrdbpt = list.get(k).getNorRegedBigProTime();
            if (!StringUtils.isEmpty(nrdbpt)){
                norRegedBigProTime = nrdbpt.substring(0,10).replace("-","/");
            }

            //项目合同备案时间
            String npctt = list.get(k).getNorProContractTime();
            if (!StringUtils.isEmpty(npctt)){
                norProContractTime = npctt.substring(0,10).replace("-","/");
            }

            //项目移交注册时间
            String nprt = list.get(k).getNorProRegTime();
            if (!StringUtils.isEmpty(nprt)){
                norProRegTime = nprt.substring(0,10).replace("-","/");
            }

            //注册公司时间
            String nrct = list.get(k).getNorRegComTime();
            if (!StringUtils.isEmpty(nrct)){
                norRegComTime = nrct.substring(0,10).replace("-","/");
            }

            //进展情况
            List<String> tp = new ArrayList<>(Arrays.asList(list.get(k).getTalkProgress().split("##")));
            for (int x = 0 ; x < tp.size() ; x ++) {
                if (!StringUtils.isEmpty(tp.get(x))) {
                    String str = x + 1 + "." + tp.get(x) + ";";
                    talkProgress = talkProgress + str +"\r\n";
                }
            }

            //存在问题
            List<String> tq = new ArrayList<>(Arrays.asList(list.get(k).getTalkQuestion().split("##")));
            for (int x = 0 ; x < tq.size() ; x ++) {
                if (!StringUtils.isEmpty(tq.get(x))) {
                    String str = x + 1 + "." + tq.get(x) + ";";
                    talkQuestion = talkQuestion + str +"\r\n";
                }
            }

            //下一步举措
            List<String> tns = new ArrayList<>(Arrays.asList(list.get(k).getTalkNextStep().split("##")));
            for (int x = 0 ; x < tns.size() ; x ++) {
                if (!StringUtils.isEmpty(tns.get(x))) {
                    String str = x + 1 + "." + tns.get(x) + ";";
                    talkNextStep = talkNextStep + str +"\r\n";
                }
            }

            //是否需要拜访 0.否;1.是
            List<String> tis = new ArrayList<>(Arrays.asList(list.get(k).getTalkIsvisit().split(",")));
            for (int x = 0 ; x < tis.size() ; x ++) {
                if (!StringUtils.isEmpty(tis.get(x))) {
                    String str = "";
                    if (tis.get(x).trim().equals("0")){
                        str = x + 1 + ".否;";
                    }
                    if (tis.get(x).trim().equals("1")){
                        str = x + 1 + ".是;";
                    }
                    talkIsvisit = talkIsvisit + str +"\r\n";
                }
            }


            //拜访所需领导层级
            List<String> tvl = new ArrayList<>(Arrays.asList(list.get(k).getTalkVisitLv().split(",")));
            for (int x = 0 ; x < tvl.size() ; x ++) {
                if (!StringUtils.isEmpty(tvl.get(x))) {
                    String str = x + 1 + "." + tvl.get(x) + ";";
                    talkVisitLv = talkVisitLv + str +"\r\n";
                }
            }

            //建议实施方式 1.招商引资;2.基本建设;3.融资;4.PPP;
            List<String> an = new ArrayList<>(Arrays.asList(list.get(k).getAdvName().split(",")));
            for (int x = 0 ; x < an.size() ; x ++) {
                if (!StringUtils.isEmpty(an.get(x))) {
                    String str = "";
                    if (an.get(x).trim().equals("1")){
                        str = x + 1 + ".招商引资;";
                    }
                    if (an.get(x).trim().equals("2")){
                        str = x + 1 + ".基本建设;";
                    }
                    if (an.get(x).trim().equals("3")){
                        str = x + 1 + ".融资;";
                    }
                    if (an.get(x).trim().equals("4")){
                        str = x + 1 + ".PPP;";
                    }
                    advName = advName + str +"\r\n";
                }
            }

            //下一步工作建议
            List<String> cna = new ArrayList<>(Arrays.asList(list.get(k).getConNextAdvise().split(",")));
            for (int x = 0 ; x < cna.size() ; x ++) {
                if (!StringUtils.isEmpty(cna.get(x))) {
                    String str = x + 1 + "." + cna.get(x) + ";";
                    conNextAdvise = conNextAdvise + str +"\r\n";
                }
            }

            //未正常履约原因
            List<String> cpci = new ArrayList<>(Arrays.asList(list.get(k).getConProConventionInfo().split(",")));
            for (int x = 0 ; x < cpci.size() ; x ++) {
                if (!StringUtils.isEmpty(cpci.get(x))) {
                    String str = x + 1 + "." + cpci.get(x) + ";";
                    conProConventionInfo = conProConventionInfo + str +"\r\n";
                }
            }


            list.get(k).setComComType(comComType);
            list.get(k).setNorLeadCategory(norLeadCategory);
            list.get(k).setNorFromArea(norFromArea);
            list.get(k).setNorInvestType(norInvestType);
            list.get(k).setNorCategory(norCategory);
            list.get(k).setNorProType(norProType);
            list.get(k).setNorProConventionType(norProConventionType);
            list.get(k).setNorIsBigPro(norIsBigPro);
            list.get(k).setConContractType(conContractType);
            list.get(k).setTalkIsvisit(norIsvisit);
            list.get(k).setNorStatus(norStatus);
            list.get(k).setProAddr(proAddr);
            list.get(k).setProUseArea(proUseArea);
            list.get(k).setComName(comName);
            list.get(k).setComAddr(comAddr);
            list.get(k).setFolDeptId(folDeptId);
            list.get(k).setCompanyInfo(companyInfo);
            list.get(k).setNorEnterOfferInfo(norEnterOfferInfo);
            list.get(k).setNorProInfo(norProInfo);
            list.get(k).setNorFollowInfo(norFollowInfo);
            list.get(k).setUniInfo(uniInfo);
            list.get(k).setComContent(comContent);
            list.get(k).setNorBigProTime(norBigProTime);
            list.get(k).setNorFirstTalkTime(norFirstTalkTime);
            list.get(k).setNorContractTime(norContractTime);
            list.get(k).setNorProMeetTime(norProMeetTime);
            list.get(k).setNorProViewTime(norProViewTime);
            list.get(k).setNorRegBigProTime(norRegBigProTime);
            list.get(k).setNorRegedBigProTime(norRegedBigProTime);
            list.get(k).setNorProContractTime(norProContractTime);
            list.get(k).setNorProRegTime(norProRegTime);
            list.get(k).setNorRegComTime(norRegComTime);
            list.get(k).setTalkProgress(talkProgress);
            list.get(k).setTalkQuestion(talkQuestion);
            list.get(k).setTalkNextStep(talkNextStep);
            list.get(k).setTalkIsvisit(talkIsvisit);
            list.get(k).setTalkVisitLv(talkVisitLv);
            list.get(k).setAdvName(advName);
            list.get(k).setUniLiable(uniLiable);
            list.get(k).setConNextAdvise(conNextAdvise);
            list.get(k).setConProConventionInfo(conProConventionInfo);
        }
        return list;
    }

    public List<BigExcelExportDTO> setBigInfo(List<BigExcelExportDTO> list, String[] name) {
        List<String> nameList = new ArrayList<>(Arrays.asList(name));
        for (int i = 0 ; i < list.size() ; i ++){
            String uniInfo = "";
            for (int k = 0 ; k < nameList.size() ; k ++){
                    if (nameList.get(k).contains("unit_liable.name")){
                        List<String> author = new ArrayList<>(Arrays.asList(list.get(i).getUniName().split(",")));
                        List<String> tel = new ArrayList<>(Arrays.asList(list.get(i).getUniTel().split(",")));
                        for (int z = 0 ; z < author.size() ; z ++){
                            if (!StringUtils.isEmpty(author.get(z)) || !StringUtils.isEmpty(tel.get(z))) {
                                String str = author.get(z) + "—" + tel.get(z) + ",";
                                uniInfo = uniInfo + str;
                            }
                        }
                    }
            }
            list.get(i).setUniInfo(uniInfo);
        }
        return list;
    }


    public Map<String,String> setBigHeader(String[] name) {
        List<String> names = new ArrayList<>(Arrays.asList(name));
        Map<String, String> titleMap = new LinkedHashMap<>();
        for (int i = 0 ; i < names.size() ; i++){
            if (names.get(i).contains("big_project.id")){
                titleMap.put("id","序号");
            }
            if (names.get(i).contains("big_project.name")){
                titleMap.put("bigProName","项目名称");
            }
            if (names.get(i).contains("big_project.status")){
                titleMap.put("bigProStatus","项目阶段");
            }
            if (names.get(i).contains("big_project.investMvRmb")){
                titleMap.put("bigProInvestMvRmb","拆迁费(亿元)");
            }
            if (names.get(i).contains("pro_adviseOpeType.name")){
                titleMap.put("advName","建议实施方式");
            }
            if (names.get(i).contains("big_project.leader")){
                titleMap.put("bigProLeader","分管区领导");
            }
            if (names.get(i).contains("big_project.planStartTime")){
                titleMap.put("bigProPlanStartTime","计划开工时间");
            }
            if (names.get(i).contains("big_project.planEndTime")){
                titleMap.put("bigProPlanEndTime","计划竣工时间");
            }
            if (names.get(i).contains("big_project.planProcess")){
                titleMap.put("bigProPlanProcess","建议年底拟达到的工作进度");
            }
            if (names.get(i).contains("unit_liable.liable")){
                titleMap.put("uniLiable","责任单位");
            }
            if (names.get(i).contains("unit_liable.name")){
                titleMap.put("uniInfo","责任人姓名及联系电话");
            }
            if (names.get(i).contains("big_project.category")){
                titleMap.put("bigProCategory","归属产业");
            }
            if (names.get(i).contains("pro_buildPlace.name")){
                titleMap.put("buildName","拟建设地点");
            }
            if (names.get(i).contains("pro_packPosition.name")){
                titleMap.put("positionName","策划包装定位");
            }
            if (names.get(i).contains("pro_categary.name")){
                titleMap.put("categoryName","具体行业分类");
            }
            if (names.get(i).contains("big_project.content")){
                titleMap.put("bigProAreaInfo","拟建设内容和用地规模(注明国有建设用地、集体建设用地、农用地)");
            }
            if (names.get(i).contains("pro_workProcess.workProcess")){
                titleMap.put("workProcess","目前工作进度");
            }
            if (names.get(i).contains("big_project.investRmb")){
                titleMap.put("bigProInvestRmb","匡算总投资额(亿元)");
            }
            if (names.get(i).contains("big_project.infoDesc")){
                titleMap.put("bigProInfoDesc","备注");
            }
        }
        return titleMap;
    }

    public List<BigExcelExportDTO> resultBigManageList(List<BigExcelExportDTO> list) {
        for (int k = 0 ; k < list.size() ; k ++){
            String buildName = "";
            String bigProPlanStartTime = "";
            String bigProPlanEndTime = "";
            String bigProCategory = "";
            String advName = "";
            String bigProStatus = "";
            String uniLiable = "";
            String positionName = "";
            String workProcess = "";
            String uniInfo = "";
            //拟建设地点
            List<String> bn = new ArrayList<>(Arrays.asList(list.get(k).getBuildName().split("##")));
            for (int i = 0 ; i < bn.size() ; i ++){
                if (!StringUtils.isEmpty(bn.get(i))) {
                    String str = i + 1 + "." + bn.get(i) + ";";
                    buildName = buildName + str + "\r\n";
                }
            }

            //计划开工时间
            String bppst = list.get(k).getBigProPlanStartTime();
            if (!StringUtils.isEmpty(bppst)){
                bigProPlanStartTime = bppst.substring(0,10).replace("-","/");
            }

            //计划竣工时间
            String bppet = list.get(k).getBigProPlanEndTime();
            if (!StringUtils.isEmpty(bppet)){
                bigProPlanEndTime = bppet.substring(0,10).replace("-","/");
            }

            //归属产业:1.第一;2.第二;3.第三;
            String bpc = list.get(k).getBigProCategory();
            if (!StringUtils.isEmpty(bpc) && bpc.trim().equals("1")){
                bigProCategory = "第一产业";
            }
            if (!StringUtils.isEmpty(bpc) && bpc.trim().equals("2")){
                bigProCategory = "第二产业";
            }
            if (!StringUtils.isEmpty(bpc) && bpc.trim().equals("3")){
                bigProCategory = "第三产业";
            }

            //建议实施方式 1.招商引资;2.基本建设;3.融资;4.PPP;
            List<String> an = new ArrayList<>(Arrays.asList(list.get(k).getAdvName().split(",")));
            for (int x = 0 ; x < an.size() ; x ++) {
                if (!StringUtils.isEmpty(an.get(x))) {
                    String str = "";
                    if (an.get(x).trim().equals("1")){
                        str = x + 1 + ".招商引资;";
                    }
                    if (an.get(x).trim().equals("2")){
                        str = x + 1 + ".基本建设;";
                    }
                    if (an.get(x).trim().equals("3")){
                        str = x + 1 + ".融资;";
                    }
                    if (an.get(x).trim().equals("4")){
                        str = x + 1 + ".PPP;";
                    }
                    advName = advName + str +"\r\n";
                }
            }

            //重大项目项目阶段:1.申报储备;2.跟踪在谈;3.拟签约;4.已签约;5.履约;0.已取消
            String ns = list.get(k).getBigProStatus();
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("1")){
                bigProStatus = "申报储备";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("2")){
                bigProStatus = "跟踪在谈";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("3")){
                bigProStatus = "拟签约";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("4")){
                bigProStatus = "已签约";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("5")){
                bigProStatus = "履约";
            }
            if (!StringUtils.isEmpty(ns) && ns.trim().equals("0")){
                bigProStatus = "已取消";
            }

            //责任单位
            List<String> ul = new ArrayList<>(Arrays.asList(list.get(k).getUniLiable().split(",")));
            for (int p = 0 ; p < ul.size() ; p ++) {
                if (!StringUtils.isEmpty(ul.get(p))) {
                    String str = p + 1 + "." + ul.get(p) + ";";
                    uniLiable = uniLiable + str +"\r\n";
                }
            }

            //策划包装定位:1.五中心一枢纽功能;2.十字方针战略;3.产业生态圈培育;4.其他;
            List<String> pn = new ArrayList<>(Arrays.asList(list.get(k).getPositionName().split(",")));
            for (int x = 0 ; x < pn.size() ; x ++) {
                if (!StringUtils.isEmpty(pn.get(x))) {
                    String str = "";
                    if (pn.get(x).trim().equals("1")){
                        str = x + 1 + ".五中心一枢纽功能;";
                    }
                    if (pn.get(x).trim().equals("2")){
                        str = x + 1 + ".十字方针战略;";
                    }
                    if (pn.get(x).trim().equals("3")){
                        str = x + 1 + ".产业生态圈培育;";
                    }
                    if (pn.get(x).trim().equals("4")){
                        str = x + 1 + ".其他;";
                    }
                    positionName = positionName + str +"\r\n";
                }
            }

            //工作进度
            List<String> wp = new ArrayList<>(Arrays.asList(list.get(k).getWorkProcess().split("##")));
            for (int p = 0 ; p < wp.size() ; p ++) {
                if (!StringUtils.isEmpty(wp.get(p))) {
                    String str = p + 1 + "." + wp.get(p) + ";";
                    workProcess = workProcess + str +"\r\n";
                }
            }

            //责任人及联系电话
            List<String> ui = new ArrayList<>(Arrays.asList(list.get(k).getUniInfo().split(",")));
            for (int p = 0 ; p < ui.size() ; p ++) {
                if (!StringUtils.isEmpty(ui.get(p))) {
                    String str = p + 1 + "." + ui.get(p) + ";";
                    uniInfo = uniInfo + str +"\r\n";
                }
            }

            //拟建设内容和用地规模(注明国有建设用地、集体建设用地、农用地)
            String content = list.get(k).getBigProContent();
            String govArea = list.get(k).getBigProGovArea();
            String collectArea = list.get(k).getBigProCollectArea();
            String farmArea = list.get(k).getBigProFarmArea();
            String bigProAreaInfo = content + "\r\n";
            if (!StringUtils.isEmpty(govArea)){
                bigProAreaInfo = bigProAreaInfo + "国有建设用地" + govArea + "亩；";
            }
            if (!StringUtils.isEmpty(collectArea)){
                bigProAreaInfo = bigProAreaInfo + "集体建设用地" + collectArea + "亩；";
            }
            if (!StringUtils.isEmpty(farmArea)){
                bigProAreaInfo = bigProAreaInfo + "农用地" + farmArea + "亩；";
            }



            list.get(k).setBuildName(buildName);
            list.get(k).setBigProPlanStartTime(bigProPlanStartTime);
            list.get(k).setBigProPlanEndTime(bigProPlanEndTime);
            list.get(k).setBigProCategory(bigProCategory);
            list.get(k).setAdvName(advName);
            list.get(k).setBigProStatus(bigProStatus);
            list.get(k).setUniLiable(uniLiable);
            list.get(k).setPositionName(positionName);
            list.get(k).setWorkProcess(workProcess);
            list.get(k).setUniInfo(uniInfo);
            list.get(k).setBigProAreaInfo(bigProAreaInfo);
        }
        return list;
    }
}
