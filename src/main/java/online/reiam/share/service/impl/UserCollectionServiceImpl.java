package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.UserCollection;
import online.reiam.share.mapper.UserCollectionMapper;
import online.reiam.share.service.UserCollectionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {

}
