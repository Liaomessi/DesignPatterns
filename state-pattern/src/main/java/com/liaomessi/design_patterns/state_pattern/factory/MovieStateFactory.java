package com.liaomessi.design_patterns.state_pattern.factory;

import com.liaomessi.design_patterns.state_pattern.manager.state.MovieState;
import com.liaomessi.design_patterns.state_pattern.model.MovieStatus;
import org.springframework.stereotype.Component;

/**
 * @author Blue
 * @date 2021/3/6
 * @description 获取各个状态下的movie对应的状态处理器
 */
@Component
public interface MovieStateFactory {
    MovieState getMovieState(MovieStatus status);

    MovieState getMovieState(String status);
}
