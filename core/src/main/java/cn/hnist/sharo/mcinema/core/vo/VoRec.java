package cn.hnist.sharo.mcinema.core.vo;


import java.util.function.Function;

public interface VoRec<T>{
    T adder(Function<T, T> set);
    T updater(Function<T, T> set);
}
