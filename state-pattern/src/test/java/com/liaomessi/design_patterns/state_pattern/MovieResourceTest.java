package com.liaomessi.design_patterns.state_pattern;

import com.liaomessi.design_patterns.state_pattern.model.Movie;
import com.liaomessi.design_patterns.state_pattern.model.MovieStatus;
import com.liaomessi.design_patterns.state_pattern.model.OpLevel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

/**
 * @author Blue
 * @date 2021/3/6
 * @description 测试movie状态改变相关接口
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class MovieResourceTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void create() {
        Movie movie = initMockMovie(null, OpLevel.OWNER);
        ResponseEntity<Movie> responseEntity = template.postForEntity("/movie/create", movie, Movie.class);
        Movie createdMovie = responseEntity.getBody();
        assert createdMovie != null;
        Assert.assertEquals(MovieStatus.INITIAL, createdMovie.getStatus());
    }

    @Test
    public void preparing() {
        Movie movie = initMockMovie(MovieStatus.INITIAL, OpLevel.OWNER);
        HttpEntity<Movie> httpEntity = new HttpEntity<>(movie);
        ResponseEntity<Movie> responseEntity = template.postForEntity("/movie/prepare", httpEntity, Movie.class);
        Movie returnedMovie = responseEntity.getBody();
        assert returnedMovie != null;
        log.info(returnedMovie.toString());
        Assert.assertEquals(MovieStatus.PREPARING, returnedMovie.getStatus());
    }

    @Test
    public void pause() {
        Movie movie = initMockMovie(MovieStatus.ACTIVE, OpLevel.REGULATOR);
        HttpEntity<Movie> httpEntity = new HttpEntity<>(movie);
        ResponseEntity<Movie> responseEntity = template.postForEntity("/movie/pause", httpEntity, Movie.class);
        Movie returnedMovie = responseEntity.getBody();
        assert returnedMovie != null;
        log.info(returnedMovie.toString());
        Assert.assertEquals(MovieStatus.PAUSED, returnedMovie.getStatus());
    }

    @Test(expected = RestClientException.class)
    public void pauseWithUnMatchStatus() {
        Movie movie = initMockMovie(MovieStatus.INITIAL, OpLevel.OWNER);
        HttpEntity<Movie> httpEntity = new HttpEntity<>(movie);
        template.postForEntity("/movie/pause", httpEntity, Movie.class);
    }

    @Test(expected = RestClientException.class)
    public void pauseWithUnMatchOpLevel() {
        Movie movie = initMockMovie(MovieStatus.ACTIVE, OpLevel.MOVIE_POSTER);
        HttpEntity<Movie> httpEntity = new HttpEntity<>(movie);
        template.postForEntity("/movie/pause", httpEntity, Movie.class);
    }

    @Test
    public void publish() {
        Movie movie = initMockMovie(MovieStatus.PREPARING, OpLevel.OWNER);
        HttpEntity<Movie> httpEntity = new HttpEntity<>(movie);
        ResponseEntity<Movie> responseEntity = template.postForEntity("/movie/active", httpEntity, Movie.class);
        Movie returnedMovie = responseEntity.getBody();
        assert returnedMovie != null;
        log.info(returnedMovie.toString());
        Assert.assertEquals(MovieStatus.ACTIVE, returnedMovie.getStatus());
    }

    @Test(expected = RestClientException.class)
    public void publishWithUnMatchStatus(){
        Movie movie = initMockMovie(MovieStatus.INITIAL, OpLevel.OWNER);
        HttpEntity<Movie> httpEntity = new HttpEntity<>(movie);
        template.postForEntity("/movie/active", httpEntity, Movie.class);
    }

    @Test
    public void stop() {
        Movie movie = initMockMovie(MovieStatus.ACTIVE, OpLevel.REGULATOR);
        ResponseEntity<Movie> responseEntity = template.postForEntity("/movie/stop", movie, Movie.class);
        Movie returnedMovie = responseEntity.getBody();
        assert returnedMovie != null;
        log.info(returnedMovie.toString());
        Assert.assertEquals(MovieStatus.STOPPED, returnedMovie.getStatus());
    }

    @Test
    public void complete() {
        Movie movie = initMockMovie(MovieStatus.STOPPED, OpLevel.OWNER);
        ResponseEntity<Movie> responseEntity = template.postForEntity("/movie/complete", movie, Movie.class);
        Movie returnedMovie = responseEntity.getBody();
        assert returnedMovie != null;
        log.info(returnedMovie.toString());
        Assert.assertEquals(MovieStatus.COMPLETE, returnedMovie.getStatus());
    }

    @Test
    public void archive() {
        Movie movie = initMockMovie(MovieStatus.COMPLETE, OpLevel.OWNER);
        ResponseEntity<Movie> responseEntity = template.postForEntity("/movie/archive", movie, Movie.class);
        Movie returnedMovie = responseEntity.getBody();
        assert returnedMovie != null;
        log.info(returnedMovie.toString());
        Assert.assertEquals(MovieStatus.ARCHIVED, returnedMovie.getStatus());
    }

    private Movie initMockMovie(MovieStatus status, OpLevel opLevel) {
        Movie movie = new Movie();
        movie.setStatus(status);
        movie.setId(1L);
        movie.setOpLevel(opLevel);
        movie.setUrl("www.tultuq.com");
        return movie;
    }
}
