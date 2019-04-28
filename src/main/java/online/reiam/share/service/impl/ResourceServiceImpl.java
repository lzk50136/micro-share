package online.reiam.share.service.impl;

import online.reiam.share.entity.Resource;
import online.reiam.share.mapper.ResourceMapper;
import online.reiam.share.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 贴子资源表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-28
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
