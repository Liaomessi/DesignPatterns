package com.liaomessi.design_patterns.state_pattern.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author Blue
 * @date 2021/3/6
 * @description 电影
 */
@Data
@ToString
public class Movie {
    private Long id;
    private MovieStatus status;
    private OpLevel opLevel;
    private String url;
}
