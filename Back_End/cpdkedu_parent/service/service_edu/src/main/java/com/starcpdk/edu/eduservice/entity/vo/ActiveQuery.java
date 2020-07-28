package com.starcpdk.edu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActiveQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "活动时间")
    private String date;

    @ApiModelProperty(value = "活动时间")
    private String datejiezhi;

    @ApiModelProperty(value = "活动积分")
    private String score;
}
