package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProAttachment;

import java.util.Map;

/**
 * 附件信息Service
 *
 * @author monkey
 * @Date 2017-11-07 15:56:52
 */
public interface IProAttachmentService extends IService<ProAttachment>{

    boolean batchMerge(int proId, Map<Object, Object> map);

}
