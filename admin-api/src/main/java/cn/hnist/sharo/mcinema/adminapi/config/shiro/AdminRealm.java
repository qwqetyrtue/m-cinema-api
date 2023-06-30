package cn.hnist.sharo.mcinema.adminapi.config.shiro;

import cn.hnist.sharo.mcinema.core.util.IPUtil;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;
import cn.hnist.sharo.mcinema.db.service.admin.AdminBaseService;
import cn.hnist.sharo.mcinema.db.service.admin.PermissionService;
import cn.hnist.sharo.mcinema.db.service.admin.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 设置shiro Realm类
 */
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminBaseService adminService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Resource
    NativeWebRequest request;

    /**
     * 授权
     *
     * @param principalCollection 对象集合
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        AdminBase admin = (AdminBase) subject.getPrincipal();
        // 获取角色
        Integer[] roleList = admin.getRoleIdList();
        Set<String> roleSet = roleService.selectRoleSet(roleList);
        Set<String> permissionSet = permissionService.selectPermissionSet(roleList);
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        List<AdminBase> admins = adminService.listByAccount(username);
        if (admins.size() == 0)
            throw new UnknownAccountException("账号不存在");
        AdminBase admin = admins.get(0);
        if (!admin.getPwd().equals(password))
            throw new UnknownAccountException("密码错误");
        admin.setLastLoginTime(LocalDateTime.now());
        admin.setLastLoginIp(IPUtil.getIPAddress(request));
        adminService.updateNoCheck(admin);
        return new SimpleAuthenticationInfo(admin, password, getName());
    }
}
