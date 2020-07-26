package com.starcpdk.edu.eduservice.service;

import com.alibaba.excel.EasyExcel;
import com.starcpdk.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starcpdk.edu.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file , EduSubjectService subjectService);

    //课程分类列表实现(树形)
    List<OneSubject> getAllOneTwoSubject();
}
