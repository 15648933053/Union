import request from '@/utils/request'

export default {
    //1.讲师列表(条件)
    //current当前页   limit每页记录数    teacherQuerry条件对象
    getTecherListPage(current , limit , teacherQuerry){
        return request({
            // url: '/eduservice/teacher/pageTeacher/'+current+"/"+limit,
            url: `/eduservice/user/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            //teacherQuerry条件对象,后端使用RequestBody获取数据
            //data表示把对象转换为json数据进行传递到接口里面
            data:teacherQuerry
          })
    },
    //删除讲师
    deleteTeacherId(id){
        return request({
            // url: '/eduservice/user/pageTeacher/'+current+"/"+limit,
            url: `/eduservice/user/${id}`,
            method: 'delete',
          })
    },

    //根据id查询讲师
    getTeacherInfo(id){
        return request({
            url: `/eduservice/user/getTeacher/${id}`,
            method: 'get'
          })
    },
    //修改讲师
    updateTeacherInfo(teacher){
        return request({
            url: `/eduservice/user/updateTeacher`,
            method: 'post',
            data: teacher
          })
    }


}