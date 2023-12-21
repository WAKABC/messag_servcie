package com.wak.enums;

import lombok.Getter;

/**
 * @author wuankang
 * @date 2023/12/12 17:07
 * @Description TODO
 * @Version 1.0
 */
@Getter
public enum MsgEnum {
    /**
     * Not pay order status enum.
     */
    ORDER(1, "order"),

    SCORE(2, "score");

    private int code;

    private String desc;

    MsgEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
