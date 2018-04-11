package com.stylefeng.guns.modular.oa.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.WorkflowAttachment;
import com.stylefeng.guns.common.persistence.model.ZateLandimg;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.oa.dto.WorkFlowAttachmentDTO;
import com.stylefeng.guns.modular.zate.dto.ZateImgDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.oa.service.IWorkflowAttachmentService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 20:07:47
 */
@Service
public class WorkflowAttachmentServiceImpl extends ServiceImpl<BaseMapper<WorkflowAttachment>,WorkflowAttachment> implements IWorkflowAttachmentService {
    @Autowired
    private IWorkflowAttachmentService attachmentService;
    @Override
    public Boolean addAttachments(String attachments, Integer workFlowId) {
        boolean reBool = false;
        List<WorkflowAttachment> attmList = new ArrayList<>();
        if (StringUtils.hasText(attachments)){
            try {
                JSONArray attmsArr = (JSONArray)JSONArray.parse(attachments);
                for (int i = 0; i < attmsArr.size(); i++) {
                    WorkflowAttachment attachment = new WorkflowAttachment();
                    JSONObject jsonObj = (JSONObject) JSON.toJSON(attmsArr.get(i));
                    WorkFlowAttachmentDTO attachmentDTO = JSON.toJavaObject(jsonObj, WorkFlowAttachmentDTO.class);
                    BeanUtils.copyProperties(attachmentDTO,attachment);
                    // 添加土地类载体资源成功，载体图片插入载体资源外键
                    attachment.setWorkflowId(workFlowId);
                    attachment.setCreateTime(new Date());
                    ShiroUser user = ShiroKit.getUser();
                    if (user != null){
                        attachment.setUserId(user.getId());
                        attachment.setUserName(user.getName());
                    }
                    attmList.add(attachment);
                }
            } catch (Exception e) {
                RecordLogTools.error("addAttachments",e);
            }
            reBool = super.insertBatch(attmList);
        }
        return reBool;
    }
}
