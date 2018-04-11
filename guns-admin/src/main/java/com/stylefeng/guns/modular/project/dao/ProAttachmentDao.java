package com.stylefeng.guns.modular.project.dao;

import com.stylefeng.guns.common.persistence.model.ProAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 附件信息Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 15:56:52
 */
public interface ProAttachmentDao {

    Long updateProAttachment(ProAttachment attachment);

    Long batchInsert(List<ProAttachment> attachments);

    Long deleteProAttachmentByProId(@Param("list") List<Integer> attathIds, @Param("proId") int proId);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> attathIds, @Param("proId") int proId, @Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);

}
