package cn.hnist.sharo.mcinema.db.service.user;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.dao.UserBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.UserBase;
import cn.hnist.sharo.mcinema.db.pojo.UserBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class U_UserBaseService implements CRUDService<UserBase, Long> {
    @Resource
    UserBaseMapper userMapper;

    public UserBase oneByAuth(String username, String password) {
        UserBaseExample example = new UserBaseExample();
        UserBaseExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(username);
        criteria.andPwdEqualTo(password);
        UserBase user = userMapper.selectOneByExample(criteria.example());
        if (user == null) {
            throw new DatabaseException("账号或密码错误", ErrorEnum.DATA_NOT_FOUND);
        }
        if (user.getDeleted())
            throw new DatabaseException("账户已被注销", ErrorEnum.DATA_IS_DELETED);
        return user;
    }

    public UserBase oneByAuthOrInsert(String phone) {
        UserBaseExample example = new UserBaseExample();
        UserBaseExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        UserBase user = userMapper.selectOneByExample(criteria.example());
        if (user == null) {
            LocalDateTime now = LocalDateTime.now();
            UserBase add = new UserBase();
            add.setPhone(phone);
            add.setCreateTime(now);
            add.setUpdateTime(now);
            add.setName("用户" + DataUtil.hex10To62(System.nanoTime()));
            add.setPwd(DataUtil.hex10To62(System.nanoTime()));
            userMapper.insertSelective(add);
            return add;
        }
        if (user.getDeleted())
            throw new DatabaseException("账户已被注销", ErrorEnum.DATA_IS_DELETED);
        return user;
    }

    @Override
    public UserBase oneByPK(Long pk) {
        return userMapper.selectByPrimaryKey(pk);
    }

    @Override
    public int delete(Long pk) throws RuntimeException {
        return 0;
    }

    @Override
    public int update(UserBase update) throws RuntimeException {
        return userMapper.updateByPrimaryKeySelective(update);
    }

    public int updatePwd(UserBase update, String NPwd) throws RuntimeException {
        UserBase res = oneByPK(update.getUserId());
        if (res == null)
            throw new DatabaseException("此用户不存在", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("此用户账户已被注销", ErrorEnum.DATA_IS_DELETED);
        if (!res.getPwd().equals(update.getPwd()))
            throw new DatabaseException("密码错误", ErrorEnum.FAILED_AUTHENTICATED_OPERATE);
        update.setPwd(NPwd);
        return update(update);
    }


    @Override
    public int insert(UserBase insert) throws RuntimeException {
        return 0;
    }

}
