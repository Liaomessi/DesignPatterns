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
@Service("COMPLETE")
public class CompleteState implements MovieState {
    @Override
    public void archive(Movie movie) {
        if (movie.getOpLevel()!= OpLevel.OWNER){
            throw new IllegalArgumentException("只有制作人才可以归档电影。");
        }
        movie.setStatus(MovieStatus.ARCHIVED);
    }
}
