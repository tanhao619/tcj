package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.constant.dictmap.DeptDict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.DeptMapper;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.DeptDao;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.warpper.DeptWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Resource
    DeptDao deptDao;

    @Resource
    DeptMapper deptMapper;

    @Resource
    IDeptService deptService;

    @Autowired
    private IRoleService roleService;
    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptMapper.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        if (dept.getPid() == 0){
            return PREFIX + "dept_unit_edit.html";
        }
        return  PREFIX + "dept_office_edit.html";

    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptDao.tree();
        //tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * add by lgg
     * 获取部门添加、修改的部门tree
     */
    @RequestMapping(value = "/getDeptTree")
    @ResponseBody
    public List<ZTreeNode> getDeptTree() {
        List<ZTreeNode> tree = new ArrayList<ZTreeNode>();
        //tree.add(ZTreeNode.createDeptParent());
        List<ZTreeNode> treeT = this.deptDao.getDeptTree();
        tree.addAll(treeT);
        return tree;
    }

    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断名字是否唯一 单位名称唯一  一个单位下该科室名称唯一
        // 单位名唯一
        if (dept.getPid() == null){
            int existNum = deptService.selectCount(new EntityWrapper<Dept>().eq("pid", 0)
                    .and().eq("simplename", dept.getSimplename()));
            if (existNum > 0){
                return new FailTip(HttpReStatus.BAD_REQUEST,"添加失败！该单位已存在！");
            }
        }
        // 科室名称唯一
        if (dept.getPid() != null){
            int existNum = deptService.selectCount(new EntityWrapper<Dept>().eq("pid",dept.getPid())
                    .and().eq("simplename", dept.getSimplename()));
            if (existNum > 0){
                return new FailTip(HttpReStatus.BAD_REQUEST,"添加失败！该科室已存在！");
            }
        }

        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);
        dept.setNum(1);// 保持顺序排序，新增的排在最后
        dept.setIsSystem("0");// 新增的均不是系统默认部门
        dept.setFullname(dept.getSimplename());
        if (dept.getPid() == 0){
/*            String tip = deptDao.selectMaxTips();
            Integer ntip =  Integer.parseInt(tip) + 1;
            dept.setTips(ntip + "");*/
            dept.setTips(null);
        }else {
            dept.setTips(deptService.selectById(dept.getPid()).getTips());
        }

        return this.deptMapper.insert(dept);
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        if (condition != null){
            condition = condition.trim();
        }
        List<Map<String, Object>> list = this.deptDao.list(condition);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptMapper.selectById(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断名字是否唯一 单位名称唯一  一个单位下该科室名称唯一
        // 单位名唯一
        Dept selfDept = deptService.selectById(dept.getId());// 判断与原名是否相同
        if (!dept.getSimplename().equals(selfDept.getSimplename()) && dept.getPid() == null){
            int existNum = deptService.selectCount(new EntityWrapper<Dept>().eq("pid", 0)
                    .and().eq("simplename", dept.getSimplename()));
            if (existNum > 0){
                return new FailTip(HttpReStatus.BAD_REQUEST,"修改失败！该单位已存在！");
            }
        }
        // 科室名称唯一
        if (!dept.getSimplename().equals(selfDept.getSimplename()) && dept.getPid() != null){
            int existNum = deptService.selectCount(new EntityWrapper<Dept>().eq("pid",dept.getPid())
                    .and().eq("simplename", dept.getSimplename()));
            if (existNum > 0){
                return new FailTip(HttpReStatus.BAD_REQUEST,"修改失败！该科室已存在！");
            }
        }

        deptSetPids(dept);
        deptMapper.updateById(dept);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        // 删除单位，需删除子科室所有对应的角色
        List<Dept> delDepts = deptService.selectList(new EntityWrapper<Dept>().eq("pid", deptId));
        if (!CollectionUtils.isEmpty(delDepts)){
            for (Dept delDept : delDepts) {
                if (ToolUtil.isNotEmpty(delDept) && ToolUtil.isNotEmpty(delDept.getId())){
                    roleService.delete(new EntityWrapper<Role>().eq("deptid",delDept.getId()));
                }
            }
        }
        // 删除单位，需删除子科室，这里已经处理
        deptService.deleteDept(deptId);
        // add by lgg start
        // 	删除科室，需联动删除科室对应的所有角色
        roleService.delete(new EntityWrapper<Role>().eq("deptid",deptId));
        // add by lgg end

        return SUCCESS_TIP;
    }

    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptMapper.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }
}
