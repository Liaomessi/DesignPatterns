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
@Service("PAUSED")
public class PausedState implements MovieState {
    @Override
    public void stop(Movie movie) {
        if (movie.getOpLevel() != OpLevel.REGULATOR) {
            throw new IllegalArgumentException("只有监管人员才可以永久下架电影。");
        }
        movie.setStatus(MovieStatus.STOPPED);
    }

    @Override
    public void publish(Movie movie) {
        if (movie.getOpLevel() != OpLevel.REGULATOR) {
            throw new IllegalArgumentException("只有监管人员才可以重新上架电影。");
        }
        movie.setStatus(MovieStatus.ACTIVE);
    }
}
