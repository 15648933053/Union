import request from '@/utils/request'

export default {
    //1.课程分类列表
    //current当前页   limit每页记录数    teacherQuerry条件对象
    addCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/addCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },
    //2.查询所有讲师
    getListTeacher() {
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get'
        })
    },

    //根据课程id查询课程基本信息
    getCourseInfoId(courseId) {
        return request({
            url: `/eduservice/course/getCourseInfo/${courseId}`,
            method: 'get'
        })
    },

    //根据课程id修改课程基本信息
    updateCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/updateCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },

    //课程确认信息的显示
    getPublishCourseInfo(courseId) {
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${courseId}`,
            method: 'get'
        })
    },

    //课程最终发布
    publishCourse(courseId) {
        return request({
            url: `/eduservice/course/publishCourse/${courseId}`,
            method: 'post'
        })
    },

    //课程列表
    getPageQueryCourse(current , limit , courseQuerry) {
        return request({
            url: `/eduservice/course/getCourseByPage/${current}/${limit}`,
            method: 'post',
            data:courseQuerry
        })
    },

    //删除课程
    deleteCourse(courseId) {
        return request({
            url: `/eduservice/course/${courseId}`,
            method: 'delete'
        })
    },
}