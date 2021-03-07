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
@Service("PREPARING")
public class PreparingState implements MovieState {
    @Override
    public void pause(Movie movie) {
        if (movie.getOpLevel()!= OpLevel.REGULATOR){
            throw new IllegalArgumentException("只有监管人员才可以下架电影。");
        }
        movie.setStatus(MovieStatus.PAUSED);
    }

    @Override
    public void publish(Movie movie) {
        if (movie.getOpLevel()!= OpLevel.OWNER && OpLevel.MOVIE_POSTER != movie.getOpLevel()){
            throw new IllegalArgumentException("只有制作者和上映者才可以上映电影。");
        }
        movie.setStatus(MovieStatus.ACTIVE);
    }
}
