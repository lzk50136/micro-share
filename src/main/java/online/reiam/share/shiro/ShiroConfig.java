package online.reiam.share.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 设置过滤器，将自定义的Filter加入
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加ShiroFilter过滤器且命名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new ShiroFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterRuleMap = new HashMap<>();
        // 所有的请求通过ShiroFilter执行处理
        filterRuleMap.put("/**", "jwt");
        // 排除不需要权限的路径，ShiroFilter将不做过滤的操作
        filterRuleMap.put("/user/sign_up_validate", "anon");
        filterRuleMap.put("/user/sign_up", "anon");
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/user/reset_password_validate", "anon");
        filterRuleMap.put("/user/reset_password", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 设置自定义的Realm
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 强制使用cglib，防止重复代理和可能引起代理出错的问题
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
