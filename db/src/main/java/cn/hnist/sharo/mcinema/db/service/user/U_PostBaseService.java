package cn.hnist.sharo.mcinema.db.service.user;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.StatusEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.PostBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.PostBase;
import cn.hnist.sharo.mcinema.db.pojo.PostBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class U_PostBaseService implements CRUDService<PostBase, Long> {
    @Resource
    PostBaseMapper postMapper;

    public List<PostBase> listByFilter(PostBase filter) {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getPostClassify()))
            criteria.andPostClassifyIn(Collections.singletonList(filter.getPostClassify()));
        if (DataUtil.checkEmpty(filter.getUserId()))
            criteria.andUserIdEqualTo(filter.getUserId());
        return postMapper.selectByExample(criteria.example().orderBy("'release_time','upvote','read'"));
    }

    public List<PostBase> listByOwner(PostBase filter) {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(filter.getUserId());
        if (DataUtil.checkEmpty(filter.getPostStatus()))
            criteria.andPostStatusEqualTo(filter.getPostStatus());
        else criteria.andPostStatusNotEqualTo(StatusEnum.PostStatus.TRASHED.v());
        if (DataUtil.checkEmpty(filter.getPostClassify()))
            criteria.andPostClassifyIn(Collections.singletonList(filter.getPostClassify()));
        return postMapper.selectByExample(criteria.example());
    }

    @Override
    public PostBase oneByPK(Long pk) {
        return postMapper.selectByPrimaryKey(pk);
    }

    @Override
    public int delete(Long pk) throws RuntimeException {
        return 0;
    }

    @Override
    public int update(PostBase update) throws RuntimeException {
       return postMapper.updateByPrimaryKeySelective(update);
    }
    public int updateDraft(PostBase update) throws RuntimeException {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(update.getUserId());
        criteria.andPostIdEqualTo(update.getPostId());
        criteria.andPostStatusEqualTo(StatusEnum.PostStatus.DRAFT.v());
        PostBase res = postMapper.selectOneByExample(criteria.example());
        if(res == null)
            throw new DatabaseException("未查询到该文章",ErrorEnum.DATA_NOT_FOUND);
        if(!res.getPostStatus().equals(StatusEnum.PostStatus.DRAFT.v()))
            throw new DatabaseException("文章已经被发布了",ErrorEnum.ILLEGAL_OPERATE);
        return postMapper.updateByPrimaryKeySelective(update);
    }



    public int updateStatus(Long userId, Long postId, StatusEnum.PostStatus status) {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andPostIdEqualTo(postId);
        PostBase res = postMapper.selectOneByExample(example);
        if (res == null)
            throw new DatabaseException("此文章不存在", ErrorEnum.DATA_NOT_FOUND);
        PostBase update = new PostBase();
        update.setPostId(postId);
        update.setPostStatus(status.v());
        // 提交发布
        if (status.equals(StatusEnum.PostStatus.RELEASE))
            throw new DatabaseException("没有此修改权限", ErrorEnum.ILLEGAL_OPERATE);
            // 提交审核
        else if (status.equals(StatusEnum.PostStatus.REVIEW)) {
            if (StatusEnum.PostStatus.REVIEW.v().equals(res.getPostStatus()))
                throw new DatabaseException("此文章正在审核中", ErrorEnum.ILLEGAL_OPERATE);
            else if (StatusEnum.PostStatus.RELEASE.v().equals(res.getPostStatus()))
                throw new DatabaseException("此文章已经发布了", ErrorEnum.ILLEGAL_OPERATE);
            else if (!DataUtil.checkEmpty(res.getTitle()) || !DataUtil.checkEmpty(res.getPostClassify()))
                throw new DatabaseException("请补充文章的必要信息再提交审核", ErrorEnum.CONSTRAINT_EXCEPTION);
            // 更新审核时间
            update.setReviewTime(LocalDateTime.now());
        }
        // 提交下架为草稿
        else if (status.equals(StatusEnum.PostStatus.DRAFT)) {
            if (StatusEnum.PostStatus.DRAFT.v().equals(res.getPostStatus()))
                throw new DatabaseException("此文章没有处于审核或处于发布中", ErrorEnum.ILLEGAL_OPERATE);
            if (StatusEnum.PostStatus.TRASHED.v().equals(res.getPostStatus()))
                throw new DatabaseException("此文章已经被删除了", ErrorEnum.DATA_IS_DELETED);
        }
        return postMapper.updateByPrimaryKeySelective(update);
    }


    @Override
    public int insert(PostBase insert) throws RuntimeException {
        return postMapper.insertSelective(insert);
    }
}
