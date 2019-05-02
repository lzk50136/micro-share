package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.TopicPost;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贴子话题表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface TopicPostMapper extends BaseMapper<TopicPost> {

    IPage<Integer> selectPostIdListByTopicId(Page page, @Param("topicId") Integer topicId);

}
