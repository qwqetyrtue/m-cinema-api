package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.CommentBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.CommentBase;
import cn.hnist.sharo.mcinema.db.pojo.CommentBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentBaseService implements CRUDService<CommentBase, Long> {
    @Resource
    CommentBaseMapper commentMapper;

    @Override
    public CommentBase oneByPK(Long pk) {
        return commentMapper.selectByPrimaryKey(pk);
    }

    public List<CommentBase> listByCondition(CommentBase filter, LocalDateTime createTimeUp, LocalDateTime createTimeDown) {
        CommentBaseExample example = new CommentBaseExample();
        CommentBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getCreateTime()))
            criteria.andCreateTimeGreaterThanOrEqualTo(filter.getCreateTime()).andCreateTimeLessThan(filter.getCreateTime().plusDays(1));
        else {
            if (DataUtil.checkEmpty(createTimeUp))
                criteria.andCreateTimeLessThanOrEqualTo(createTimeUp);
            if (DataUtil.checkEmpty(createTimeDown))
                criteria.andCreateTimeGreaterThanOrEqualTo(createTimeDown);
        }
        if (DataUtil.checkEmpty(filter.getCondition()))
            criteria.andConditionEqualTo(filter.getCondition());
        if (DataUtil.checkEmpty(filter.getConditionId()))
            criteria.andConditionIdEqualTo(filter.getConditionId());
        criteria.andParentIdIsNull();
        return commentMapper.selectByExample(criteria.example());
    }

    public List<CommentBase> listByUser(CommentBase filter, LocalDateTime createTimeUp, LocalDateTime createTimeDown) {
        CommentBaseExample.Criteria criteria = CommentBaseExample.newAndCreateCriteria();
        if (DataUtil.checkEmpty(filter.getUserId()))
            criteria.andUserIdEqualTo(filter.getUserId());
        if (DataUtil.checkEmpty(filter.getCreateTime()))
            criteria.andCreateTimeGreaterThanOrEqualTo(filter.getCreateTime()).andCreateTimeLessThan(filter.getCreateTime().plusDays(1));
        else {
            if (DataUtil.checkEmpty(createTimeUp))
                criteria.andCreateTimeLessThanOrEqualTo(createTimeUp);
            if (DataUtil.checkEmpty(createTimeDown))
                criteria.andCreateTimeGreaterThanOrEqualTo(createTimeDown);
        }
        criteria.andParentIdIsNull();
        return commentMapper.selectByExample(criteria.example());
    }

    public List<CommentBase> listByParent(Long parentId){
        CommentBaseExample.Criteria criteria = CommentBaseExample.newAndCreateCriteria();
        criteria.andParentIdEqualTo(parentId);
        return commentMapper.selectByExample(criteria.example());
    }


    @Override
    public int delete(Long pk) throws RuntimeException {
        CommentBase res = oneByPK(pk);
        if (res == null)
            throw new DatabaseException("不存在此评论", ErrorEnum.DATA_NOT_FOUND);
        return commentMapper.deleteByPrimaryKey(pk);
    }

    @Override
    public int update(CommentBase update) throws RuntimeException {
        CommentBase res = oneByPK(update.getCommentId());
        if (res == null)
            throw new DatabaseException("不存在此评论", ErrorEnum.DATA_NOT_FOUND);
        return commentMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public int insert(CommentBase insert) throws RuntimeException {
        return commentMapper.insertSelective(insert);
    }
}
