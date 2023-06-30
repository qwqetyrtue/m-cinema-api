package cn.hnist.sharo.mcinema.db.service;

import cn.hnist.sharo.mcinema.core.exception.WithTypeException;

import java.util.List;

public interface CRUDService<T,V>{
    T oneByPK(V pk);
    int delete(V pk) throws WithTypeException;
    int update(T update) throws WithTypeException;
    int insert(T insert) throws WithTypeException;
}
