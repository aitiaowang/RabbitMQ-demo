package com.example.common.enums;

import lombok.Getter;

/**
 * @Description: 交换机类型枚举
 * @Author: sxk
 * @CreateDate: 2020/1/14 10:52
 * @Version: 1.0
 */
@Getter
public enum ExchangTypeEnum {
    FANOUT("fanout", "广播模式");

    private String type;
    private String name;

    ExchangTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
