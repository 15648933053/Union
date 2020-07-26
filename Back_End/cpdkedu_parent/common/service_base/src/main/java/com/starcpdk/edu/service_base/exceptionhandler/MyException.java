package com.starcpdk.edu.service_base.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//生成set、get方法
@AllArgsConstructor//生成有参构造
@NoArgsConstructor//生成无参构造
public class MyException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "异常信息")
    private String msg;
}
