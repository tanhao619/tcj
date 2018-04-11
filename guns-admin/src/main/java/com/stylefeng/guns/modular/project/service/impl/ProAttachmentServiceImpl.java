package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProAttachment;
import com.stylefeng.guns.common.persistence.model.ProCompany;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProAttachmentDao;
import com.stylefeng.guns.modular.project.service.IProAttachmentService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 附件信息Service
 *
 * @author monkey
 * @Date 2017-11-07 15:56:52
 */
@Service
public class ProAttachmentServiceImpl extends ServiceImpl<BaseMapper<ProAttachment>,ProAttachment> implements IProAttachmentService {

    @Autowired
    private ProAttachmentDao proAttachmentDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object attachCount = map.get("attachCount");
        if (attachCount == null || attachCount == "") {
            return false;
        }
        ProAttachment attachment = null;
        List<ProAttachment> attachments = new ArrayList<ProAttachment>();
        List<Integer> attachIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(attachCount.toString()); i ++ ) {
            attachment = new ProAttachment();
            attachment.setProId(proId);
            attachment.setAttachName(ParamsUtils.getMap("attachName" + i, map));
            attachment.setUrl(ParamsUtils.getMap("attachUrl" + i, map));
            attachment.setUserId(Integer.parseInt(ParamsUtils.getMap("userId", map)));
            attachment.setUserName(ParamsUtils.getMap("userName", map));
            attachment.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            attachment.setFolType(1);
            if (StringUtils.isNotBlank(ParamsUtils.getMap("attachId" + i, map))) {
                int attachId = Integer.parseInt(map.get("attachId" + i).toString());
                attachment.setId(attachId);
                //proAttachmentDao.updateProAttachment(attachment);
                attachIds.add(attachId);
                continue;
            }
            attachments.add(attachment);
        }
        if (attachIds.size() > 0 || attachments.size() > 0 || Integer.parseInt(attachCount.toString()) == 0) {
            proAttachmentDao.updateCurrentTimeByProId(attachIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proAttachmentDao.deleteProAttachmentByProId(attachIds, proId);
        }
        if (attachments.size() > 0) {
            long size  = proAttachmentDao.batchInsert(attachments);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
