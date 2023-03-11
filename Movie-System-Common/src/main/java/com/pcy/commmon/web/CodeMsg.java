package com.pcy.commmon.web;

import lombok.*;

import java.io.Serializable;

/**
 * Created by wolfcode-lanxw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeMsg implements Serializable {
    private Integer code;
    private String msg;
}
