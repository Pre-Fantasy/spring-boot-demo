package com.example.demo.core.shiro;

import com.example.demo.model.UserInfo;
import com.example.demo.service.RolePermService;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.UserRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  Pre_fantasy
 * @create  2018-06-30 17:45
 * @desc    自定义Realm
 *          在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.
 *          Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法
 *          该方法主要执行以下操作:
 *              检查提交的进行认证的令牌信息
 *              根据令牌信息从数据源(通常为数据库)中获取用户信息
 *              对用户信息进行匹配验证。
 *              验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
 *              验证失败则抛出AuthenticationException异常信息。
 *          而在我们的应用程序中要做的就是自定义一个Realm类，继承AuthorizingRealm抽象类，重载doGetAuthenticationInfo()，重写获取用户信息的方法。
 *          重载doGetAuthorizationInfo()，来定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
 **/
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermService rolePermService;

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/30 18:26
     *  @desc   告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
     *          这里使用的是局部代码块，他会在构造函数之前执行，为的是给当前这个类在创建之前赋予所有要的值
     */
    {
        /*设置用于匹配密码的CredentialsMatcher*/
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
       /* hashMatcher.setHashAlgorithmName("md5");
        hashMatcher.setStoredCredentialsHexEncoded(true);
        *//*加密次数*//*
        hashMatcher.setHashIterations(1024);*/
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/30 18:28
     *  @param  {PrincipalCollection principalCollection}
     *  @return AuthorizationInfo
     *  @desc   定义如何获取用户和权限的逻辑，给shiro做权限判断
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        UserInfo user = (UserInfo) getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(user.getRoles());
        info.setStringPermissions(user.getPerms());
        return info;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/30 18:46
     *  @param  {AuthenticationToken authenticationToken}
     *  @return AuthenticationInfo 当用户通过验证是，返回了一个包含了用户信息的实例对象
     *  @desc   定义如何获取用户信息的业务逻辑，给shiro做登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (null == username) {
            throw new AccountException("Null username are not allowed by this realm");
        }

        UserInfo userDB = userService.selectBy("userName", username);
        if (null == userDB) {
            throw new UnknownAccountException("No account found for admin[" + username + "]");
        }

        /*查询用户的角色和权限存储到SimleAuthenticationInfo中，这样就可以在其他地方通过SecurityUtils.getSuject().getPrincipal()获取到所有的用户信息*/
        List<String> roleList = userRoleService.getRolesByUserId(userDB.getId());
        List<String> permList = rolePermService.getPermsByUserId(userDB.getId());
        Set<String> roles = new HashSet<>(roleList);
        Set<String> perms = new HashSet<>(permList);
        userDB.setRoles(roles);
        userDB.setPerms(perms);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPassword(), getName());
        info.setCredentials(ByteSource.Util.bytes(userDB.getSalt()));
        return info;
    }


}
