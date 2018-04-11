package com.stylefeng.guns.modular.util;


import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.config.ZateBuildingEnum;
import com.stylefeng.guns.config.ZateLandEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lgg
 */
public class ExcelUtils {


    private static UserMapper userMapper = SpringContextUtil.getBean(UserMapper.class);

    //导出土地类载体
    public static Workbook exportZLandExcel(List<ExpZateland> landList) throws Exception {

        try {
            //XSSFWorkbook wb = new XSSFWorkbook(); // 07版
            HSSFWorkbook wb = new HSSFWorkbook();// 03版
            List<ZateLandEnum> headerEnumList = encapLandEnumHeaders();
            if (CollectionUtils.isNotEmpty(headerEnumList)) {
                //XSSFSheet sheet = wb.createSheet(Constant.ZATE_LAND_EXPORT_NAME);// 07版
                //XSSFRow row0 =  sheet.createRow(0);// 07版
                HSSFSheet sheet = wb.createSheet(Constant.ZATE_LAND_EXPORT_NAME);
                HSSFCellStyle cellStylet = wb.createCellStyle();
                //cellStylet.setWrapText(true);//设置自动换行
                //cellStylet.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
                cellStylet.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左对齐

                HSSFRow row0 = sheet.createRow(0);
                // 封装表头
                for (int i = 0; i < headerEnumList.size(); i++) {
                    HSSFCell cell = row0.createCell(i);
                    cell.setCellValue(headerEnumList.get(i).getFieldName());
                    // 设置列宽
                    if (Arrays.asList(Constant.ZATE_EXCEL_ALEN).contains(headerEnumList.get(i).getFieldName())) {
                        sheet.setColumnWidth(i, headerEnumList.get(i).getFieldName().getBytes().length * 2 * 200);
                    } else {
                        sheet.setColumnWidth(i, headerEnumList.get(i).getFieldName().getBytes().length * 256);
                    }

                }

                // 封装正文
                for (int i = 1; i < landList.size() + 1; i++) {
                    HSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < headerEnumList.size(); j++) {

                        HSSFCell cell = row.createCell(j);
                        // 编号左对齐
                        if (j == 0) {
                            cell.setCellStyle(cellStylet);
                        }

                        ExpZateland et = landList.get(i - 1);
                        String reflecMonthValue = getReflecZateLandValue(et, ExpZateland.class, headerEnumList.get(j).getField());

                        if (isNumeric(reflecMonthValue)) {
                            cell.setCellValue(Double.parseDouble(reflecMonthValue));
                        } else {
                            cell.setCellValue(reflecMonthValue);
                        }

                    }
                }

            }
            return wb;
        } catch (Exception e) {
        }
        return null;
    }

    //导出楼宇类载体
    public static Workbook exportZBuildingExcel(List<ExpZatebuilding> buildingList) {

        try {
            //XSSFWorkbook wb = new XSSFWorkbook(); // 07版
            HSSFWorkbook wb = new HSSFWorkbook();// 03版
            List<ZateBuildingEnum> headerEnumList = encapBuildingEnumHeaders();
            if (CollectionUtils.isNotEmpty(headerEnumList)) {
                //XSSFSheet sheet = wb.createSheet(Constant.ZATE_LAND_EXPORT_NAME);// 07版
                //XSSFRow row0 =  sheet.createRow(0);// 07版
                HSSFSheet sheet = wb.createSheet(Constant.ZATE_BUILDING_EXPORT_NAME);// 03版
                HSSFCellStyle cellStylet = wb.createCellStyle();
                cellStylet.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左对齐
                HSSFRow row0 = sheet.createRow(0);
                // 封装表头
                for (int i = 0; i < headerEnumList.size(); i++) {
                    // 设置列宽
                    if (Arrays.asList(Constant.ZATE_EXCEL_ALEN).contains(headerEnumList.get(i).getFieldName())) {
                        sheet.setColumnWidth(i, headerEnumList.get(i).getFieldName().getBytes().length * 2 * 200);
                    } else {
                        sheet.setColumnWidth(i, headerEnumList.get(i).getFieldName().getBytes().length * 256);
                    }
                    HSSFCell cell = row0.createCell(i);
                    cell.setCellValue(headerEnumList.get(i).getFieldName());

                }

                // 封装正文
                for (int i = 1; i < buildingList.size() + 1; i++) {
                    HSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < headerEnumList.size(); j++) {
                        HSSFCell cell = row.createCell(j);
                        // 编号左对齐
                        if (j == 0) {
                            cell.setCellStyle(cellStylet);
                        }
                        ExpZatebuilding et = buildingList.get(i - 1);
                        String reflecMonthValue = getReflecZateBuildingValue(et, ExpZatebuilding.class, headerEnumList.get(j).getField());
                        if (isNumeric(reflecMonthValue)) {
                            cell.setCellValue(Double.parseDouble(reflecMonthValue));
                        } else {
                            cell.setCellValue(reflecMonthValue);
                        }

                    }
                }

            }
            return wb;
        } catch (Exception e) {
        }
        return null;
    }


    private static Date strToDate(String dateStr) throws Exception {
        Date date = null;
        if (dateStr != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = dateFormat.parse(dateStr);
        }
        return date;
    }

    private static Integer getYear(Date date) throws Exception {
        String year = null;
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            year = dateFormat.format(date);
        }
        return Integer.parseInt(year);
    }

    private static Integer getMonth(Date date) throws Exception {
        String month = null;
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
            month = dateFormat.format(date);
        }
        return Integer.parseInt(month);
    }


    // 土地类载体资源资源状态分类
    //资源状态:1.未使用;2.已使用;3.已停用;
    private static String switchStatus(String landStatus) {
        String statusStr = "";
        if (StringUtils.hasText(landStatus)) {

            switch (landStatus) {
                case "1":
                    statusStr = "未使用";
                    break;
                case "2":
                    statusStr = "已使用";
                    break;
                case "3":
                    statusStr = "已停用";
                    break;
                default:
                    return "";
            }
        }
        return statusStr;
    }

    // 土地类载体资用地性质
    //用地性质:1.国有土地;2.集体土地;3.农用地;4.建设用地;5.未用地;
    private static String switchLandProperty(String landProperty) {
        String propertyStr = "";
        if (StringUtils.hasText(landProperty)) {
            switch (landProperty) {
                case "1":
                    propertyStr = "国有土地";
                    break;
                case "2":
                    propertyStr = "集体土地";
                    break;
                case "3":
                    propertyStr = "农用地";
                    break;
                case "4":
                    propertyStr = "建设用地";
                    break;
                case "5":
                    propertyStr = "未用地";
                    break;
                default:
                    return "";
            }
        }
        return propertyStr;
    }


    // Building载体资用地性质
    //产权性质:1.出租;2.出售;3.租售一体;
    private static String switchBuildingProperty(String buildProperty) {
        String propertyStr = "";
        if (StringUtils.hasText(buildProperty)) {
            switch (buildProperty) {
                case "1":
                    propertyStr = "出租";
                    break;
                case "2":
                    propertyStr = "出售";
                    break;
                case "3":
                    propertyStr = "租售一体";
                    break;
                default:
                    return "";
            }
        }
        return propertyStr;
    }


    // Building载体资用地性质
    //入驻产业类别:1.第一产业;2.第二产业;3.第三产业;
    private static String switchBuildingInduType(String buildInduType) {
        String induTypeStr = "";
        if (StringUtils.hasText(buildInduType)) {
            switch (buildInduType) {
                case "1":
                    induTypeStr = "第一产业";
                    break;
                case "2":
                    induTypeStr = "第二产业";
                    break;
                case "3":
                    induTypeStr = "第三产业";
                    break;
                default:
                    return "";
            }
        }
        return induTypeStr;
    }


    // cell是否为null
    private static Boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }


    /**
     * 处理时间
     *
     * @param date
     * @return
     */
    private static String switchTime(Date date) throws Exception {
        if (date != null) {
            String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
            return strDate;
        } else {
            return "";
        }
    }

    // 获取ZateLand对象属性值
    private static String getReflecZateLandValue(ExpZateland et, Class clz, String attribute) throws Exception {
        Object fieldValue = null;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(attribute)) {
                // 处理编号
                if ("id".equals(field.getName())) {
                    fieldValue = Constant.ZATE_LAND_TYPE + field.get(et);
                    break;
                }
                // 处理时间
                if (field.getType() == new Date().getClass()) {
                    fieldValue = switchTime((Date) field.get(et));
                    break;
                }
                // 处理有分类的
                if ("status".equals(field.getName())) {
                    fieldValue = switchStatus(field.get(et) + "");
                    break;
                }
                if ("property".equals(field.getName())) {
                    fieldValue = switchLandProperty(field.get(et) + "");
                    break;
                }
                fieldValue = field.get(et);
                break;
            }
        }
        if (fieldValue == null) {
            return "";
        }
        return fieldValue + "";

    }

    // 获取ZateLand对象属性值
    private static String getReflecZateBuildingValue(ExpZatebuilding et, Class clz, String attribute) throws Exception {
        Object fieldValue = null;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(attribute)) {
                // 处理编号
                if ("id".equals(field.getName())) {
                    fieldValue = Constant.ZATE_BUILDING_TYPE + field.get(et);
                    break;
                }
                // 处理时间
                if (field.getType() == new Date().getClass()) {
                    fieldValue = switchTime((Date) field.get(et));
                    break;
                }
                // 处理有分类的
                if ("status".equals(field.getName())) {
                    fieldValue = switchStatus(field.get(et) + "");
                    break;
                }
                if ("property".equals(field.getName())) {
                    fieldValue = switchBuildingProperty(field.get(et) + "");
                    break;
                }
                if ("induType".equals(field.getName())) {
                    fieldValue = switchBuildingInduType(field.get(et) + "");
                    break;
                }
                fieldValue = field.get(et);
                break;
            }
        }
        if (fieldValue == null) {
            return "";
        }
        return fieldValue + "";

    }

    // 获取对象所有属性
    public static List<String> getReflecAttribute(Class etClass) {
        List<String> fileds = new ArrayList<>();
        Field[] fields = etClass.getDeclaredFields();
        for (Field field : fields) {
            fileds.add(field.getName());
        }
        return fileds;
    }


    // 封装头部
    public static List<ZateLandEnum> encapLandEnumHeaders() {

        List<ZateLandEnum> headerEnumList = new ArrayList<>();
        // YcEntMonthDataEntity的所有属性
        List<String> landAttributes = ExcelUtils.getReflecAttribute(ExpZateland.class);

        for (ZateLandEnum filed : ZateLandEnum.values()) {
            if (landAttributes.contains(filed.getField())) {// 怕枚举手写字段出错，排除手写错误
                headerEnumList.add(filed);
            }
        }
        return headerEnumList;
    }

    // 封装头部
    public static List<ZateBuildingEnum> encapBuildingEnumHeaders() {

        List<ZateBuildingEnum> headerEnumList = new ArrayList<>();
        List<String> landAttributes = ExcelUtils.getReflecAttribute(ExpZatebuilding.class);

        for (ZateBuildingEnum filed : ZateBuildingEnum.values()) {
            if (landAttributes.contains(filed.getField())) {// 怕枚举手写字段出错，排除手写错误
                headerEnumList.add(filed);
            }
        }
        return headerEnumList;
    }

    private static Boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 处理时间
     *
     * @param date
     * @param type 1：到日时间， 2：到分时间
     * @return
     */
    private static String switchTime(Date date, Integer type) {
        if (date != null && type == 1) {
            String strDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
            return strDate;
        } else if (date != null && type == 2) {
            String strDate = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date);
            return strDate;
        } else {
            return "";
        }
    }
}
