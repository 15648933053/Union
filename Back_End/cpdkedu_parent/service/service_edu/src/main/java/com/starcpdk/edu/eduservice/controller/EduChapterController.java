package com.starcpdk.edu.eduservice.controller;


import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.EduChapter;
import com.starcpdk.edu.eduservice.entity.chapter.ChapterVo;
import com.starcpdk.edu.eduservice.service.EduChapterService;
import com.starcpdk.edu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-03
 */
@Api(description = "课程大纲管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表
    @ApiOperation(value = "课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    //添加章节
    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean save = chapterService.save(eduChapter);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据章节id查询
    @ApiOperation(value = "查询章节")
    @GetMapping("getChapter/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        if (eduChapter != null) {
            return R.ok().data("chapter", eduChapter);
        } else {
            return R.error();
        }
    }

    //修改章节
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean update = chapterService.updateById(eduChapter);
        if (update == true) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除章节
    @ApiOperation(value = "删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        Boolean deleteChapter = chapterService.deleteChapter(chapterId);
        if (deleteChapter) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

