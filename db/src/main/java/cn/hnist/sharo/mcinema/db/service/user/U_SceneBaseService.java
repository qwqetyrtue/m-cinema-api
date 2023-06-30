package cn.hnist.sharo.mcinema.db.service.user;

import cn.hnist.sharo.mcinema.db.dao.SceneBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.VSceneMapper;
import cn.hnist.sharo.mcinema.db.pojo.SceneBase;
import cn.hnist.sharo.mcinema.db.pojo.SceneBaseExample;
import cn.hnist.sharo.mcinema.db.pojo.VScene;
import cn.hnist.sharo.mcinema.db.pojo.VSceneExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class U_SceneBaseService {
    @Resource
    VSceneMapper vSceneMapper;
    @Resource
    SceneBaseMapper sceneBaseMapper;

    public List<VScene> listByFilmIdDay(Long filmId, LocalDate day) {
        VScene.Column[] columns = {
                VScene.Column.sceneId,
                VScene.Column.sceneType,
                VScene.Column.language,
                VScene.Column.beginTime,
                VScene.Column.endTime,
                VScene.Column.hallName,
                VScene.Column.hallId,
                VScene.Column.seatNum,
                VScene.Column.attendance,
                VScene.Column.price,
        };
        VSceneExample example = new VSceneExample();
        VSceneExample.Criteria criteria = example.createCriteria();
        criteria.andFilmIdEqualTo(filmId);
        criteria.andDayEqualTo(day);
        return vSceneMapper.selectByExampleSelective(criteria.example(), columns);
    }

    public VScene oneByPk(Long sceneId){
        VSceneExample example = new VSceneExample();
        VSceneExample.Criteria criteria = example.createCriteria();
        criteria.andSceneIdEqualTo(sceneId);
        return vSceneMapper.selectOneByExample(criteria.example());
    }

}
