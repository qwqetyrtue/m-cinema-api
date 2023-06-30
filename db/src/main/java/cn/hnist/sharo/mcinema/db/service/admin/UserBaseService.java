package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.UserBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.custom.UserCustomMapper;
import cn.hnist.sharo.mcinema.db.pojo.UserBase;
import cn.hnist.sharo.mcinema.db.pojo.UserBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserBaseService implements CRUDService<UserBase, Long> {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Resource
    UserBaseMapper userMapper;
    @Resource
    UserCustomMapper userCustomMapper;

    public List<UserBase> listByProfile(UserBase filter, Integer ageUp, Integer ageDown) {
        UserBaseExample example = new UserBaseExample();
        UserBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getName()))
            criteria.andNameLike("%" + filter.getName() + "%");

        if (DataUtil.checkEmpty(filter.getGender()))
            criteria.andGenderEqualTo(filter.getGender());

        if (DataUtil.checkEmpty(ageUp))
            criteria.andAgeLessThanOrEqualTo(ageUp);
        if (DataUtil.checkEmpty(ageDown))
            criteria.andAgeGreaterThanOrEqualTo(ageDown);

        if (DataUtil.checkEmpty(filter.getDeleted())) {
            criteria.andDeletedEqualTo(filter.getDeleted());
        }
        return userMapper.selectByExample(criteria.example());
    }

    public List<UserBase> listByAccount(UserBase filter) {
        UserBaseExample example = new UserBaseExample();
        UserBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getPhone()))
            criteria.andPhoneLike("%" + filter.getPhone() + "%");
        if (DataUtil.checkEmpty(filter.getEmail()))
            criteria.andEmailLike("%" + filter.getEmail() + "%");
        if (DataUtil.checkEmpty(filter.getLastLoginIp()))
            criteria.andLastLoginIpLike("%" + filter.getLastLoginIp() + "%");
        if (DataUtil.checkEmpty(filter.getDeleted())) {
            criteria.andDeletedEqualTo(filter.getDeleted());
        }
        return userMapper.selectByExample(criteria.example());
    }

    public List<UserBase> listByTime(UserBase filter,
                                     LocalDateTime createTimeUp, LocalDateTime createTimeDown,
                                     LocalDateTime lastLoginTimeUp, LocalDateTime lastLoginTimeDown) {
        UserBaseExample example = new UserBaseExample();
        UserBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(createTimeUp))
            criteria.andCreateTimeLessThanOrEqualTo(createTimeUp);
        if (DataUtil.checkEmpty(createTimeDown))
            criteria.andCreateTimeGreaterThanOrEqualTo(createTimeDown);
        if (DataUtil.checkEmpty(lastLoginTimeUp))
            criteria.andLastLoginTimeLessThanOrEqualTo(lastLoginTimeUp);
        if (DataUtil.checkEmpty(lastLoginTimeDown))
            criteria.andLastLoginTimeGreaterThanOrEqualTo(lastLoginTimeDown);
        if (DataUtil.checkEmpty(filter.getDeleted())) {
            criteria.andDeletedEqualTo(filter.getDeleted());
        }
        return userMapper.selectByExample(criteria.example());
    }

    public Map<String,Object> statistics(){
        return userCustomMapper.statistics();
    }

    public List<UserBase> listFormOptionsByFilter(UserBase filter){
        UserBase.Column[] columns = {UserBase.Column.name, UserBase.Column.userId};
        UserBaseExample example = new UserBaseExample();
        UserBaseExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%" + filter.getName() + "%");
        return userMapper.selectByExampleSelective(criteria.example(),columns);
    }

    /**
     * <h2>通过ID查询用户</h2>
     *
     * @param userID 用户ID
     * @return 用户信息
     */
    @Override
    public UserBase oneByPK(Long userID) {
        return userMapper.selectByPrimaryKey(userID);
    }

    /**
     * <h2>更新用户信息</h2>
     *
     * @param user 用户更新信息
     * @return update语句检索条数
     */
    @Override
    public int update(UserBase user) throws RuntimeException {
        UserBase res = oneByPK(user.getUserId());
        if (res == null)
            throw new DatabaseException("未查询到此用户", ErrorEnum.DATA_NOT_FOUND);
        return userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * <h2>删除用户信息(逻辑删除)</h2>
     *
     * @param userId 用户ID
     * @return delete语句检索检索条数
     */
    @Override
    public int delete(Long userId) throws RuntimeException {
        UserBase res = oneByPK(userId);
        if (res == null)
            throw new DatabaseException("未查询到此用户", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("用户已经被删除了", ErrorEnum.DATA_IS_DELETED);
        return userMapper.logicalDeleteByPrimaryKey(userId);
    }

    /**
     * <h2>添加用户信息</h2>
     *
     * @param user 用户添加信息
     * @return 插入条数
     */
    @Override
    public int insert(UserBase user) throws RuntimeException {
        List<UserBase> res = selectByPhone(user.getPhone());
        // res 不会为空
        if (res != null && res.size() > 0) {
            throw new DatabaseException("手机号重复", ErrorEnum.CONSTRAINT_EXCEPTION);
        }
        return userMapper.insertSelective(user);
    }

    /**
     * <h2>根据手机号查询用户</h2>
     *
     * @param phone 手机号
     * @return list
     */
    public List<UserBase> selectByPhone(String phone) {
        UserBaseExample example = new UserBaseExample();
        UserBaseExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        return userMapper.selectByExample(criteria.example());
    }

}
