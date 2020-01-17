package com.hslzk.share.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hslzk.share.entity.Permission;
import com.hslzk.share.mapper.PermissionMapper;
import com.hslzk.share.service.PermissionService;
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
