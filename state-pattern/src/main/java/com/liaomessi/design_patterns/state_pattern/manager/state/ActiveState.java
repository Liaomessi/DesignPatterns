package com.liaomessi.design_patterns.state_pattern.manager.state;

import com.liaomessi.design_patterns.state_pattern.model.Movie;
import com.liaomessi.design_patterns.state_pattern.model.MovieStatus;
import com.liaomessi.design_patterns.state_pattern.model.OpLevel;
import org.springframework.stereotype.Service;

/**
 * @author Blue
 * @date 2021/3/6
 * @description
 */
@Service("ACTIVE")
public class ActiveState implements MovieState {
    @Override
    public void pause(Movie movie) {
        //TODO:这里其实也是个分权，可以借鉴state的设计再调用一个opLevelOperation的接口方法。
        if (movie.getOpLevel() != OpLevel.REGULATOR) {
            throw new IllegalArgumentException("只有监管人员才可以下架电影。");
        }
        movie.setStatus(MovieStatus.PAUSED);
    }

    @Override
    public void stop(Movie movie) {
        if (movie.getOpLevel() != OpLevel.REGULATOR) {
            throw new IllegalArgumentException("只有监管人员才可以永久下架电影。");
        }
        movie.setStatus(MovieStatus.STOPPED);
    }
}
