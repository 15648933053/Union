import request from '@/utils/request'

export default {
    //1.讲师列表(条件)
    //current当前页   limit每页记录数    teacherQuerry条件对象
    getActiveListPage(current , limit , activeQuerry){
        return request({
            url: `/eduservice/active/pageActiveCondition/${current}/${limit}`,
            method: 'post',
            //teacherQuerry条件对象,后端使用RequestBody获取数据
            //data表示把对象转换为json数据进行传递到接口里面
            data:activeQuerry
          })
    },
    //删除讲师
    deleteActiveId(id){
        return request({
            url: `/eduservice/active/${id}`,
            method: 'delete',
          })
    },

    //根据id查询讲师
    getActiveInfo(id){
        return request({
            url: `/eduservice/active/getActive/${id}`,
            method: 'get'
          })
    },
    //修改讲师
    updateActiveInfo(active){
        return request({
            url: `/eduservice/active/updateActive`,
            method: 'post',
            data: active
          })
    },

    addActive(active){
        return request({
            url: `/eduservice/active/addActive`,
            method: 'post',
            data: active
          })
    },

    dow(){
        return request({
            url: `/eduservice/active/dow`,
            method: 'get'
          })
    }
}