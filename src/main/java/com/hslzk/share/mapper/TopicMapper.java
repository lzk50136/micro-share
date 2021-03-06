package com.hslzk.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hslzk.share.entity.Topic;
import com.hslzk.share.response.TopicResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 话题表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface TopicMapper extends BaseMapper<Topic> {

    IPage<TopicResponse> selectTopicListByUserId(Page page, @Param("userId") Integer userId);

}
