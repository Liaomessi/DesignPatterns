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
@Service("STOPPED")
public class StoppedState implements MovieState {
    @Override
    public void complete(Movie movie) {
        if (OpLevel.OWNER != movie.getOpLevel()) {
            throw new IllegalArgumentException("只有制作者才能计算最终收益。");
        }
        movie.setStatus(MovieStatus.COMPLETE);
    }
}
