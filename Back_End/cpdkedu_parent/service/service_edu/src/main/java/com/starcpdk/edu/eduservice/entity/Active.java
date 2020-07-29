package com.starcpdk.edu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Active对象", description="")
public class Active implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "活动内容")
    private String content;

    @ApiModelProperty(value = "活动日期" , example = "2019-01-01 10:10:10")
    private Date date;

    @ApiModelProperty(value = "活动状态")
    private String status;

    @ApiModelProperty(value = "活动日期" , example = "2019-01-01 10:10:10")
    private Date datejiezhi;

    @ApiModelProperty(value = "活动分数")
    private Integer score;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date uniCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date uniModified;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;


}
