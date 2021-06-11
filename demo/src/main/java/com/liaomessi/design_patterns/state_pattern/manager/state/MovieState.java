package com.liaomessi.design_patterns.state_pattern.manager.state;

import com.liaomessi.design_patterns.state_pattern.model.Movie;

/**
 * @author Blue
 * @date 2021/3/6
 * @description 电影状态抽象类
 */

public interface MovieState {
    default void create(Movie movie) { throw new IllegalArgumentException("该状态不支持筹备电影." + movie.getStatus()); }

    default void preparing(Movie movie) {
        throw new IllegalArgumentException("该状态不支持拍电影" + movie.getStatus());
    }

    default void publish(Movie movie) {
        throw new IllegalArgumentException("该状态不支持上映电影" + movie.getStatus());
    }

    default void pause(Movie movie) { throw new IllegalArgumentException("该状态不支持被迫下架电影" + movie.getStatus()); }

    default void stop(Movie movie) {
        throw new IllegalArgumentException("该状态不支持停止上映电影" + movie.getStatus());
    }

    default void complete(Movie movie) {
        throw new IllegalArgumentException("该状态不支持计算电影收益" + movie.getStatus());
    }

    default void archive(Movie movie) {
        throw new IllegalArgumentException("该状态不支持归档电影" + movie.getStatus());
    }
}
