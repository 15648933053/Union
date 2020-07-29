package com.starcpdk.edu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ActiveInfo {
    @ExcelProperty(value = "id" , index = 0)
    private String id;

    @ExcelProperty(value = "活动名称" , index = 1)
    private String name;

    @ExcelProperty(value = "活动内容" , index = 2)
    private String content;

    @ExcelProperty(value = "活动开始时间" , index = 3)
    private Date date;

    @ExcelProperty(value = "活动状态" , index = 4)
    private String status;

    @ExcelProperty(value = "活动截止时间" , index = 5)
    private Date datejiezhi;

    @ExcelProperty(value = "活动分数" , index = 6)
    private Integer score;

    @ExcelProperty(value = "创建时间" , index = 7)
    private Date uniCreate;

    @ExcelProperty(value = "修改时间" , index = 8)
    private Date uniModified;
}
