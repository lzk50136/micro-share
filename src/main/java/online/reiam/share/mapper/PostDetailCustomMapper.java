package online.reiam.share.mapper;

import online.reiam.share.response.PostDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostDetailCustomMapper {

    List<PostDetailResponse> selectPostDetailListByPostId(@Param("postId") Integer postId);

}
