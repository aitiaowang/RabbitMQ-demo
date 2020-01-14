package com.example.common.enums;

import lombok.Getter;

/**
 * @Description: 广播模式交换机名称枚举
 * @Author: sxk
 * @CreateDate: 2020/1/14 11:36
 * @Version: 1.0
 */
@Getter
public enum FanoutExchangEnum {
    EXCHANG_1("test_fanout_topic");

    private String name;

    FanoutExchangEnum(String name) {
        this.name = name;
    }}
