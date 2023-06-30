package cn.hnist.sharo.mcinema.db.service.admin;

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
public class PostBaseService implements CRUDService<PostBase, Long> {
    @Resource
    PostBaseMapper postMapper;

    public List<PostBase> listByProfile(PostBase filter) {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();

        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        if (DataUtil.checkEmpty(filter.getTitle()))
            criteria.andTitleLike("%" + filter.getTitle() + "%");
        return postMapper.selectByExample(criteria.example());
    }

    public List<PostBase> listByClassify(PostBase filter) {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();

        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        if (DataUtil.checkEmpty(filter.getPostClassify()))
            criteria.andPostClassifyLike("%" + filter.getPostClassify() + "%");
        if (DataUtil.checkEmpty(filter.getPostStatus()))
            criteria.andPostStatusEqualTo(filter.getPostStatus());
        return postMapper.selectByExample(criteria.example());
    }

    public List<PostBase> listByTime(
            PostBase filter,
            LocalDateTime releaseTimeUp, LocalDateTime releaseTimeDown,
            LocalDateTime reviewTimeUp, LocalDateTime reviewTimeDown) {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();

        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        if (DataUtil.checkEmpty(releaseTimeUp))
            criteria.andReleaseTimeLessThanOrEqualTo(releaseTimeUp);
        if (DataUtil.checkEmpty(releaseTimeDown))
            criteria.andReleaseTimeGreaterThanOrEqualTo(releaseTimeDown);
        if (DataUtil.checkEmpty(reviewTimeUp))
            criteria.andReviewTimeLessThanOrEqualTo(reviewTimeUp);
        if (DataUtil.checkEmpty(reviewTimeDown))
            criteria.andReviewTimeGreaterThanOrEqualTo(reviewTimeDown);
        return postMapper.selectByExample(criteria.example());
    }

    public List<PostBase> listByFK(PostBase filter) {
        PostBaseExample example = new PostBaseExample();
        PostBaseExample.Criteria criteria = example.createCriteria();

        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        if (DataUtil.checkEmpty(filter.getUserId()))
            criteria.andUserIdEqualTo(filter.getUserId());
        if (DataUtil.checkEmpty(filter.getPostStatus()))
            criteria.andPostStatusEqualTo(filter.getPostStatus());
        return postMapper.selectByExample(criteria.example());
    }


    @Override
    public PostBase oneByPK(Long pk) {
        return null;
    }

    @Override
    public int delete(Long pk) throws RuntimeException {
        return 0;
    }

    @Override
    public int update(PostBase update) throws RuntimeException {
        PostBase res = postMapper.selectByPrimaryKey(update.getPostId());
        if (res == null)
            throw new DatabaseException("文章不存在", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("文章已被移入回收站", ErrorEnum.DATA_IS_DELETED);
        return postMapper.updateByPrimaryKey(update);
    }

    public int releasePost(Long postId, Boolean pass) {
        PostBase update = new PostBase();
        PostBase res = postMapper.selectByPrimaryKey(update.getPostId());
        if (res == null)
            throw new DatabaseException("文章不存在", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("文章已被移入回收站", ErrorEnum.DATA_IS_DELETED);
        if (res.getPostStatus().equals(StatusEnum.PostStatus.REVIEW.v())) {
            LocalDateTime now = LocalDateTime.now();
            update.setReviewTime(now);
            if (pass) {
                update.setReleaseTime(now);
                update.setPostStatus(StatusEnum.PostStatus.RELEASE.v());
            } else
                update.setPostStatus(StatusEnum.PostStatus.DRAFT.v());
            return postMapper.updateByPrimaryKey(update);
        } else throw new DatabaseException("文章状态错误", ErrorEnum.ILLEGAL_OPERATE);
    }

    public int backPost(Long postId) {
        PostBase update = new PostBase();
        update.setPostStatus(StatusEnum.PostStatus.DRAFT.v());
        PostBase res = postMapper.selectByPrimaryKey(update.getPostId());
        if (res == null)
            throw new DatabaseException("文章不存在", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("文章已被移入回收站", ErrorEnum.DATA_IS_DELETED);
        if (res.getPostStatus().equals(StatusEnum.PostStatus.RELEASE.v()))
            return postMapper.updateByPrimaryKey(update);
        else throw new DatabaseException("文章状态错误", ErrorEnum.ILLEGAL_OPERATE);
    }

    @Override
    public int insert(PostBase insert) throws RuntimeException {
        return 0;
    }
}
