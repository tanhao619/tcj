package com.stylefeng.guns.modular.oa.dao;
import com.stylefeng.guns.modular.oa.dto.WorkFlowAttachmentDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * OA管理Dao
 *
 * @author fengshuonan
 * @Date 2017-12-04 20:07:47
 */
@Component
public interface WorkflowAttachmentDao {
    List<WorkFlowAttachmentDTO> selectByWfwId(Integer workflowId);

}
