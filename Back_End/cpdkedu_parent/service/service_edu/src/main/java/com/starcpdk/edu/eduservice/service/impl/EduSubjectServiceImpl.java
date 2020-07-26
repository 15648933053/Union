package com.starcpdk.edu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starcpdk.edu.eduservice.entity.EduSubject;
import com.starcpdk.edu.eduservice.entity.excel.SubjectData;
import com.starcpdk.edu.eduservice.entity.subject.OneSubject;
import com.starcpdk.edu.eduservice.entity.subject.TwoSubject;
import com.starcpdk.edu.eduservice.listener.SubjectExcelListener;
import com.starcpdk.edu.eduservice.mapper.EduSubjectMapper;
import com.starcpdk.edu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file , EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in , SubjectData.class , new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //查询所有一级分类  parent_id=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id" , "0");
        wrapperOne.orderByAsc("sort", "id");
        List<EduSubject> oneSubjectsList = baseMapper.selectList(wrapperOne);

        //查询所有二级分类  parent_id!=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id" , "0");
        wrapperTwo.orderByAsc("sort", "id");
        List<EduSubject> twoSubjectsList = baseMapper.selectList(wrapperTwo);

        //创建list集合 , 用于存储最终的分装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        //查询出来的所有的一级分类list集合遍历 , 得到每一个一级分类对象 , 获取每一个一级分类对象的值,
        //封装到要求的list集合里面list<OneSubject>finalSubjectList
        for (EduSubject eduOneSubject:oneSubjectsList){
            //把eduOneSubject中的值获取到 , 放到oneSubject对象中
            //多个OneSubject放到finalSubjectList中
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduOneSubject.getId());
//            oneSubject.setTitle(eduOneSubject.getTitle());

            //与上边的代码等价 , 即将eduOneSubject对象中的值get出来然后set到oneSubject中
            BeanUtils.copyProperties(eduOneSubject , oneSubject);//springboot封装的方法 , 将一个对象中的值取出来 , 赋值给领英对象

            //分装二级分类
            //在一级分类中遍历查询的所有的二级分类
            //创建list集合 , 封装每个一级分类中的二级分类
            List<TwoSubject> finalTwoSubjectList = new ArrayList<>();

            for (EduSubject eduTwoSubject:twoSubjectsList) {
                //判断当前外循环下的一级分类的id是否与当前内循环中对象的二级分类的parent_id是否相等 , 若相等则从eduTwoSubject中取出值放到twoSubject中 , 然后封装到finalTwoSubjectList中
                if (eduOneSubject.getId().equals(eduTwoSubject.getParentId())){
                    //把eduTwoSubject对象中的值获取到 , 放到TwoSubject对象中
                    //多个twoSubject放到finalTwoSubjectList中
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduTwoSubject , twoSubject);
                    finalTwoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(finalTwoSubjectList);

            finalSubjectList.add(oneSubject);
        }
        return finalSubjectList;
    }
}
