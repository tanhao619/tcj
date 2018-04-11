package com.stylefeng.guns.modular.oa.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.WorkflowAttachment;
/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 20:07:47
 */
public interface IWorkflowAttachmentService extends IService<WorkflowAttachment>{

    Boolean addAttachments(String attachments,Integer workFlowId);
}
