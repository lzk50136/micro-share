package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.response.PostResponse;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostCustomMapper {

    PostResponse selectPostById(@Param("id") Integer id);

    List<PostResponse> selectPostListByIds(List<Integer> list);

    IPage<PostResponse> selectPostListByUserId(Page page, @Param("userId") Integer userId);

    IPage<PostResponse> selectPostListByTopicId(Page page, @Param("userId") Integer topicId);

    IPage<PostResponse> selectPostListByFollowId(Page page, List<Integer> list, LocalDateTime startTime, LocalDateTime endTime);

}
