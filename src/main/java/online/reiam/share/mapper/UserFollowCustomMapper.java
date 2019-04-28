package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.response.UserFollowerResponse;
import online.reiam.share.response.UserFollowingResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFollowCustomMapper {

    List<Integer> selectFollowIdListByUserId(@Param("userId") Integer userId);

    IPage<UserFollowerResponse> selectUserFollowerListByUserId(Page page, @Param("userId") Integer userId);

    IPage<UserFollowingResponse> selectUserFollowingListByUserId(Page page, @Param("userId") Integer userId);

}
