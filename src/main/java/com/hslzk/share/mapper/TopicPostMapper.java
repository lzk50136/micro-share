package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hslzk.share.entity.TopicPost;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 话题贴子表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface TopicPostMapper extends BaseMapper<TopicPost> {

    IPage<Integer> selectPostIdListByTopicId(Page page, @Param("topicId") Integer topicId);

}
