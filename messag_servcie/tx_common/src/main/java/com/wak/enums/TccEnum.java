package com.wak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuankang
 * @date 2023/11/29 10:13
 * @Description TODO
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum TccEnum {
    /**
     * Not pay order status enum.
     */
    TRY((byte)1, "try"),

    CONFIRM((byte)2, "confirm"),

    CANCEL((byte)3, "cancel");

    /**
     * 代码
     */
    private byte code;
    /**
     *  描述
     */
    private String desc;
}
