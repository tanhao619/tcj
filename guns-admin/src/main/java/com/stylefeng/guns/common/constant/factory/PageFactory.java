package com.stylefeng.guns.common.constant.factory;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.state.Order;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.ToolUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author fengshuonan
 * @date 2017-04-05 22:25
 */
public class PageFactory<T> {
    int limit = 20;//使用swagger默认条数
    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        if(request.getParameter("limit") != null){
             limit = Integer.valueOf(request.getParameter("limit"));
        }
        int offset = 0;//使用swagger默认游标
        if(request.getParameter("offset") != null){
            offset = Integer.valueOf(request.getParameter("offset"));
        }
          //每页多少条数据
//        int    //每页的偏移量(本页当前有多少条)
        String sort = request.getParameter("sort");         //排序字段名称
        String order = request.getParameter("order");       //asc或desc(升序或降序)
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }

    public Page<T> bigPage() {
        limit = 1000;

        return  defaultPage();
    }
}
