package com.starcpdk.edu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author 内农大计算机院
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Touch对象", description="")
public class Touch implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "openid ")
    private String openid;

    @ApiModelProperty(value = "活动id")
    private String actid;


}
