package com.starcpdk.edu.eduservice.controller;


import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.EduVideo;
import com.starcpdk.edu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-03
 */
@Api(description = "小节操作")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //添加小节
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = videoService.save(eduVideo);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //删除小节
    // TODO 后面的方法需要完善 , 删除的时候要同时将视频删除
    @ApiOperation(value = "删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        boolean remove = videoService.removeById(id);
        if (remove){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查询小节信息
    @ApiOperation("根据id查询小节")
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        if (eduVideo != null){
            return R.ok().data("video" , eduVideo);
        }else {
            return R.error();
        }
    }

    //修改小节
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean update = videoService.updateById(eduVideo);
        if (update){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

