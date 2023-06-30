package cn.hnist.sharo.mcinema.db.service.user;

import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.db.dao.FilmBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.FilmCollectMapper;
import cn.hnist.sharo.mcinema.db.dao.custom.FilmCollectCustomMapper;
import cn.hnist.sharo.mcinema.db.dao.custom.FilmCustomMapper;
import cn.hnist.sharo.mcinema.db.pojo.*;
import cn.hnist.sharo.mcinema.db.pojo.custom.FilmCollectCount;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class U_FilmBaseService {
    @Resource
    FilmBaseMapper filmBaseMapper;
    @Resource
    FilmCollectMapper filmCollectMapper;
    @Resource
    FilmCollectCustomMapper filmCollectCustomMapper;
    @Resource
    FilmCustomMapper filmCustomMapper;

    public PageInfo<FilmBase> listByUserCollect(Long userId, Short type, String filmType, PagingRec paging){
        FilmBase.Column[] columns = {FilmBase.Column.filmId, FilmBase.Column.score, FilmBase.Column.actor, FilmBase.Column.name};
        FilmCollectExample.Criteria collectCri = FilmCollectExample.newAndCreateCriteria();
        collectCri.andUserIdEqualTo(userId);
        collectCri.andTypeEqualTo(type);
        List<FilmCollect> collects = filmCollectMapper.selectByExample(collectCri.example());
        List<Long> filmIds = new ArrayList<>();
        for (FilmCollect collect : collects) {
            filmIds.add(collect.getFilmId());
        }
        if(filmIds.size() > 0){
            PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
            FilmBaseExample.Criteria filmCri = FilmBaseExample.newAndCreateCriteria();
            filmCri.andFilmIdIn(filmIds);
            filmCri.andFilmTypeEqualTo(filmType);
            List<FilmBase> list = filmBaseMapper.selectByExampleSelective(filmCri.example(),columns);
            return new PageInfo<>(list);
        }else{
            return new PageInfo<>();
        }
    }

    public List<FilmCollectCount> countCollect(Long userId){
        FilmCollectExample.Criteria collectCri = FilmCollectExample.newAndCreateCriteria();
        collectCri.andUserIdEqualTo(userId);
        return filmCollectCustomMapper.countAllCollectGroup(userId);
    }

    public FilmCollect oneByUserFilm(Long userId,Long filmId){
        FilmCollectExample.Criteria collectCri = FilmCollectExample.newAndCreateCriteria();
        collectCri.andUserIdEqualTo(userId);
        collectCri.andFilmIdEqualTo(filmId);
        return filmCollectMapper.selectOneByExample(collectCri.example());
    }

    public int insertCollect(FilmCollect collect){
        return filmCollectMapper.insert(collect);
    }

    public List<FilmBase> listByType(String type) {
        if("hot".equals(type))
            return filmCustomMapper.listHotFilm();
        else if("soon".equals(type))
            return filmCustomMapper.listSoonFilm();
        else if("tobe".equals(type))
            return filmCustomMapper.listToBeFilm();
        else
        {
            FilmBase.Column[] columns = {
                    FilmBase.Column.name,
                    FilmBase.Column.filmId,
                    FilmBase.Column.images,
                    FilmBase.Column.actor,
                    FilmBase.Column.score
            };
            FilmBaseExample.Criteria criteria = FilmBaseExample.newAndCreateCriteria();
            return filmBaseMapper.selectByExampleSelective(criteria.example(), columns);
        }
    }


    public List<FilmBase> listByNameType(String name, String filmType) {
        FilmBaseExample.Criteria criteria = FilmBaseExample.newAndCreateCriteria();
        criteria.andAliasLike("%" + name + "%").andFilmTypeEqualTo(filmType)
                .example().or()
                .andNameLike("%" + name + "%").andFilmTypeEqualTo(filmType);
        return filmBaseMapper.selectByExample(criteria.example());
    }

    public List<FilmBase> listByActor(String name) {
        FilmBaseExample.Criteria criteria = FilmBaseExample.newAndCreateCriteria();
        criteria.andActorLike("%" + name + "%");
        return filmBaseMapper.selectByExample(criteria.example());
    }

    public FilmBase oneById(Long filmId) {
        return filmBaseMapper.selectByPrimaryKey(filmId);
    }
}
