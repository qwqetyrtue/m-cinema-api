package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.db.dao.AdminRoleMapper;
import cn.hnist.sharo.mcinema.db.pojo.AdminRole;
import cn.hnist.sharo.mcinema.db.pojo.AdminRoleExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Resource
    AdminRoleMapper roleMapper;

    public Set<String> selectRoleSet(Integer[] roleList){
        if(roleList == null || roleList.length == 0)
            return new HashSet<>();
        AdminRoleExample e = new AdminRoleExample();
        AdminRoleExample.Criteria c = e.createCriteria();
        c.andDeletedEqualTo(false).andRoleIdIn(Arrays.asList(roleList));
        List<AdminRole> roles = roleMapper.selectByExample(e);
        return roles.stream().map(AdminRole::getName).collect(Collectors.toSet());
    }

    public List<AdminRole> selectAvailable(boolean available){
        AdminRoleExample e = new AdminRoleExample();
        AdminRoleExample.Criteria c = e.createCriteria();
        c.andLogicalDeleted(!available);
        return roleMapper.selectByExample(e);
    }

    public List<AdminRole> selectAll(){
        return roleMapper.selectByExample(new AdminRoleExample());
    }

    public List<AdminRole> selectAllOption(){
        AdminRole.Column[] columns = {AdminRole.Column.roleId, AdminRole.Column.name};
        return roleMapper.selectByExampleSelective(new AdminRoleExample(),columns);
    }

}
