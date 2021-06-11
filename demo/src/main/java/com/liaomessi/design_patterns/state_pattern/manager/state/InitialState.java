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
@Service("INITIAL")
public class InitialState implements MovieState {
    @Override
    public void preparing(Movie movie) {
        if (movie.getOpLevel() != OpLevel.OWNER) {
            throw new IllegalArgumentException("只有制片人能拍电影");
        }
        //TODO: 做些操作电影对象的行为（拍电影，剪辑，后期啊啥的）
        movie.setStatus(MovieStatus.PREPARING);
        //TODO: 保存操作过的电影
    }
}
