package com.liaomessi.design_patterns.state_pattern.controller;

import com.liaomessi.design_patterns.state_pattern.factory.MovieStateFactory;
import com.liaomessi.design_patterns.state_pattern.manager.state.MovieState;
import com.liaomessi.design_patterns.state_pattern.model.Movie;
import com.liaomessi.design_patterns.state_pattern.model.MovieStatus;
import com.liaomessi.design_patterns.state_pattern.model.OpLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Blue
 * @date 2021/3/6
 * @description 下面所有的接口为了省掉与数据库交互的部分，所以接口参数全部使用直接传入我们需要的Movie对象的，方便在写集成测试的时候直接构造Mock对象来测试。
 * 包括省略了movie service层具体的逻辑代码。
 */
@RestController("movie")
@RequestMapping("/movie")
public class MovieResource {

    @Autowired
    private MovieStateFactory stateFactory;

    @PostMapping("/create")
    public Movie create(@RequestBody Movie movie) {
        movie.setStatus(MovieStatus.INITIAL);
        return movie;
    }

    @PostMapping("/prepare")
    public Movie preparing(@RequestBody Movie movie) {
        MovieState movieState = stateFactory.getMovieState(movie.getStatus());
        movieState.preparing(movie);
        return movie;
    }

    @PostMapping("/pause")
    public Movie pause(@RequestBody Movie movie) {
        stateFactory.getMovieState(movie.getStatus()).pause(movie);
        return movie;
    }

    @PostMapping("/active")
    public Movie publish(@RequestBody Movie movie) {
        stateFactory.getMovieState(movie.getStatus()).publish(movie);
        return movie;
    }

    @PostMapping("/stop")
    public Movie stop(@RequestBody Movie movie) {
        stateFactory.getMovieState(movie.getStatus()).stop(movie);
        return movie;
    }

    @PostMapping("/complete")
    public Movie complete(@RequestBody Movie movie) {
        stateFactory.getMovieState(movie.getStatus()).complete(movie);
        return movie;
    }

    @PostMapping("/archive")
    public Movie archive(@RequestBody Movie movie) {
        stateFactory.getMovieState(movie.getStatus()).archive(movie);
        return movie;
    }
}
