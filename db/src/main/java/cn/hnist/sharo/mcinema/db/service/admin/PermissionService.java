package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.db.dao.AdminPermissionMapper;
import cn.hnist.sharo.mcinema.db.pojo.AdminPermission;
import cn.hnist.sharo.mcinema.db.pojo.AdminPermissionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    @Resource
    AdminPermissionMapper permissionMapper;

    public List<AdminPermission> selectAvailable(boolean available){
        AdminPermissionExample e = new AdminPermissionExample();
        AdminPermissionExample.Criteria c = e.createCriteria();
        c.andLogicalDeleted(!available);
        return permissionMapper.selectByExample(e);
    }

    public List<AdminPermission> selectAll(){
        return permissionMapper.selectByExample(new AdminPermissionExample());
    }

    public Set<String> selectPermissionSet(Integer[] roleList) {
        if(roleList == null || roleList.length == 0)
            return new HashSet<>();
        AdminPermissionExample e = new AdminPermissionExample();
        AdminPermissionExample.Criteria c = e.createCriteria();
        c.andDeletedEqualTo(false).andRoleIdIn(Arrays.asList(roleList));
        List<AdminPermission> permissions = permissionMapper.selectByExample(e);
        return permissions.stream().map(AdminPermission::getPermission).collect(Collectors.toSet());
    }
}
