package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.response.PostAtResponse;
import org.apache.ibatis.annotations.Param;

public interface PostAtCustomMapper {

    IPage<PostAtResponse> selectPostListByUserId(Page page, @Param("userId") Integer userId);

}
