package com.starcpdk.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starcpdk.edu.eduservice.entity.EduChapter;
import com.starcpdk.edu.eduservice.entity.EduVideo;
import com.starcpdk.edu.eduservice.entity.chapter.ChapterVo;
import com.starcpdk.edu.eduservice.entity.chapter.VideoVo;
import com.starcpdk.edu.eduservice.mapper.EduChapterMapper;
import com.starcpdk.edu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starcpdk.edu.eduservice.service.EduVideoService;
import com.starcpdk.edu.service_base.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据course_id查询所有该课程下的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id" , courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);

        //根据course_id查询所有该课程下的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id" , courseId);
        List<EduVideo> eduVideos = videoService.list(wrapperVideo);

        //封装chapter数据
        List<ChapterVo> chapterVos = new ArrayList<>();

        for (EduChapter eduChapter:eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter , chapterVo);

            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo:eduVideos) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo , videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            chapterVos.add(chapterVo);
        }

        return chapterVos;
    }

    //删除章节
    @Override
    public Boolean deleteChapter(String chapterId) {
        //根据章节id查询小结表 , 如果查询到不能删 , 如果没查询到则可以删除
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq(chapterId , "chapter_id");
        int count = videoService.count(eduVideoQueryWrapper);
        if (count > 0){//查询出小结 , 无法删除
            throw new MyException(20001 , "不能删除");
        }else {//无法查询到小结 , 可以删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id" , courseId);
        baseMapper.delete(wrapper);
    }
}
