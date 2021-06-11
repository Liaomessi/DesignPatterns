package com.liaomessi.design_patterns.state_pattern.model;

/**
 * @author Blue
 * @date 2021/3/6
 * @description
 */
public enum MovieStatus {
    /**
     * 初始化状态(电影刚拍完)
     */
    INITIAL("INITIAL"),
    /**
     * 准备状态（电影上线宣传阶段）
     */
    PREPARING("PREPARING"),
    /**
     * 活跃状态（电影已经上线阶段）
     */
    ACTIVE("ACTIVE"),
    /**
     * 暂停状态（因为某些原因上线又下架整改阶段）
     */
    PAUSED("PAUSED"),
    /**
     * 停止状态（电影在线下已经停止售映阶段）
     */
    STOPPED("STOPPED"),
    /**
     * 完成状态（电影计算计算最终获取收益阶段）
     */
    COMPLETE("COMPLETE"),
    /**
     * 归档状态（电影呗记录进入各个app中，可提供给所有用户免费观看）
     */
    ARCHIVED("ARCHIVED");

    private String name;

    private MovieStatus(String name) {
        this.name =name;
    }

    public String getName(){
        return this.name;
    }
}
