package com.starcpdk.edu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "openID")
    @TableId(value = "openid", type = IdType.ID_WORKER_STR)
    private String openid;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "生日")
    private Date birth;

    @ApiModelProperty(value = "活动积分")
    private Integer score;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date uniCreate;

    @ApiModelProperty(value = "修改时间")
    private Date uniModified;


}
