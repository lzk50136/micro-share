package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.reiam.share.entity.Permission;
import online.reiam.share.mapper.PermissionMapper;
import online.reiam.share.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
