package com.liaomessi.design_patterns.state_pattern.model;

/**
 * @author Blue
 * @date 2021/3/6
 * @description 用户等级
 */
public enum OpLevel {
    /**
     * 电影的所属人（拥有决定电影所有操作的权限）
     */
    OWNER,
    /**
     * 监管者（可以在电影准备阶段及之后的阶段拥有决定权）
     */
    REGULATOR,
    /**
     * 上映方（各大APP和线下电影院）
     */
    MOVIE_POSTER
}
