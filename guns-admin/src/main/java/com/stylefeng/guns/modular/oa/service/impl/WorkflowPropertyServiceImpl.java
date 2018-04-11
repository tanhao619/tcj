package com.stylefeng.guns.modular.oa.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.WorkflowProperty;
import com.stylefeng.guns.modular.oa.dao.WorkflowPropertyDao;
import com.stylefeng.guns.modular.oa.dto.QJTimePropDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.oa.service.IWorkflowPropertyService;

import java.util.HashMap;
import java.util.List;

/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 20:07:17
 */
@Service
public class WorkflowPropertyServiceImpl extends ServiceImpl<BaseMapper<WorkflowProperty>,WorkflowProperty> implements IWorkflowPropertyService {
    @Autowired
    private WorkflowPropertyDao workflowPropertyDao;
    @Override
    public List<WorkflowProperty> selectProByNameAndWfwId(String name, Integer workflowId) {
        HashMap<Object, Object> parmsMap = new HashMap<>();
        parmsMap.put("name",name);
        parmsMap.put("workflowId",workflowId);
        return workflowPropertyDao.selectProByNameAndWfwId(parmsMap);
    }

    @Override
    public QJTimePropDTO escapQJTime(Integer workflowId) {
        QJTimePropDTO qjTimePropDTO = new QJTimePropDTO();
        List<WorkflowProperty> pros = super.selectList(new EntityWrapper<WorkflowProperty>().eq("workflowId", workflowId));
        for(WorkflowProperty pro : pros){
            if ("qjTimeB".equals(pro.getName())){
                qjTimePropDTO.setQjTimeB(pro.getValue());
            }
            if ("qjAMPMB".equals(pro.getName())){
                qjTimePropDTO.setQjAMPMB(pro.getValue());
            }
            if ("qjTimeE".equals(pro.getName())){
                qjTimePropDTO.setQjTimeE(pro.getValue());
            }
            if ("qjAMPME".equals(pro.getName())){
                qjTimePropDTO.setQjAMPME(pro.getValue());
            }
            if ("qjTotalDays".equals(pro.getName())){
                qjTimePropDTO.setQjTotalDays(pro.getValue());
            }
        }
        // 判断是否是请半天
        if("0.5".equals(qjTimePropDTO.getQjTotalDays())){
            qjTimePropDTO.setIsSame("true");
        }else{
            qjTimePropDTO.setIsSame("false");
        }
        return qjTimePropDTO;
    }
}
