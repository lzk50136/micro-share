package online.reiam.share.shiro;

import online.reiam.share.jwt.JwtToken;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.service.UserCustomService;
import online.reiam.share.util.SpringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static online.reiam.share.constants.Constants.REDIS_TOKEN;

@Component
public class ShiroRealm extends AuthorizingRealm {

    /**
     * 只支持JwtToken令牌类型
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        System.out.println("supports");
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo");
        String jwtToken = (String) token.getCredentials();
        Integer userId = JwtTokenUtil.getUserId(jwtToken);
        if (userId == null) {
            // Token解密失败，抛出异常
            throw new AuthenticationException();
        }
        StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        // 查看redis的token与提交token的是否一致，不一致说明提交的token已过期
        if (!jwtToken.equals(stringRedisTemplate.opsForValue().get(userId + REDIS_TOKEN))) {
            throw new AuthenticationException();
        }
        // Token解密成功，返回SimpleAuthenticationInfo对象
        return new SimpleAuthenticationInfo(jwtToken, jwtToken, "shiroRealm");
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("doGetAuthorizationInfo");
        // 从principals中拿到Token令牌
        Integer userId = JwtTokenUtil.getUserId(principals.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserCustomService userCustomService = SpringUtil.getBean(UserCustomService.class);
        // 获取当前用户的所有角色，并且通过addRole添加到simpleAuthorizationInfo当中
        List<String> roles = userCustomService.findRoleNameListByUserId(userId);
        // 这样当Shiro内部检查用户是否有某项权限时就会从SimpleAuthorizationInfo中拿取校验
        Set<String> permissions = new HashSet<>();
        for (String role : roles) {
            simpleAuthorizationInfo.addRole(role);
            permissions.addAll(userCustomService.findPermissionNameListByRoleName(role));
        }
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

}
