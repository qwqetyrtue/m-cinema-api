package cn.hnist.sharo.mcinema.db.service.user;

import cn.hnist.sharo.mcinema.core.model.VoteEnum;
import cn.hnist.sharo.mcinema.db.dao.CommentBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.CommentVoteMapper;
import cn.hnist.sharo.mcinema.db.dao.VCommentMapper;
import cn.hnist.sharo.mcinema.db.pojo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class U_CommentBaseService {
    @Resource
    CommentBaseMapper commentMapper;
    @Resource
    VCommentMapper vCommentMapper;
    @Resource
    CommentVoteMapper commentVoteMapper;

    public CommentBase oneByPK(Long pk) {
        return null;
    }


    public int delete(Long userId, Long commentId) throws RuntimeException {
        CommentBaseExample example = new CommentBaseExample();
        CommentBaseExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andCommentIdEqualTo(commentId);
        return commentMapper.deleteByExample(criteria.example());
    }

    public int vote(Long commentId, Long userId, VoteEnum vote){
        CommentVote commentVote = new CommentVote();
        commentVote.setVote(vote.getValue());
        commentVote.setCreateTime(LocalDateTime.now());
        commentVote.setUserId(userId);
        commentVote.setCommentId(commentId);
        return commentVoteMapper.insert(commentVote);
    }

    public int insert(CommentBase insert) throws RuntimeException {
        return commentMapper.insertSelective(insert);
    }

    public List<VComment> listLayerByCondition(Short condition, Long conditionId) {
        VCommentExample.Criteria criteria = VCommentExample.newAndCreateCriteria();
        criteria.andConditionEqualTo(condition).andConditionIdEqualTo(conditionId).andParentIdIsNull();
        return vCommentMapper.selectByExample(criteria.example());
    }

    public List<VComment> listLayerByParent(Short condition, Long conditionId, Long parentId) {
        VCommentExample.Criteria criteria = VCommentExample.newAndCreateCriteria();
        criteria.andConditionEqualTo(condition).andConditionIdEqualTo(conditionId).andParentIdEqualTo(parentId);
        return vCommentMapper.selectByExample(criteria.example());
    }
}
