<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="教师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>

      <el-form-item label="教师生日">
        <el-date-picker
          v-model="teacher.birth"
          type="datetime"
          placeholder="选择生日"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item label="教师积分">
        <el-input-number
          :min="0"
          v-model="teacher.score"
          controls-position="right"
          placeholder=""
        />
      </el-form-item>
      
      <el-form-item label="教师电话">
        <el-input v-model="teacher.phone" />
      </el-form-item>

      <el-form-item label="教师职称">
        <el-input v-model="teacher.position" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import teacherApi from "@/api/edu/teacher.js";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";

export default {
  components: { ImageCropper, PanThumb },
  data:function() {
    return {
      teacher: {
        name: "",
        birth: "",
        score: "",
      },
      saveBtnDisabled: false, // 保存按钮是否禁用
      //上传弹框的组件是否显示
      imagecropperShow: false,
      imagecropperKey: 0,
      BASE_API: process.env.BASE_API, //获取dev.env.js中的地址
    };
  },

  watch: {
    $route(to, from) {
      // 路由变换方式  ,当路由发生变化时 , 执行此方法
      console.log("watch $route");
      this.init();
    },
  },

  created:function() {
    console.log("created");
    this.init();
  },

  methods: {
    close:function() {
      // 关闭上传弹框的方法
      this.imagecropperShow = false;
      this.imagecropperKey = this.imagecropperKey + 1;
    },

    init:function() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getInfo(id);
      } else {
        this.teacher = {  };
      }
    },
    
    saveOrUpdate:function() {
      //判断是修改还是添加
      //根据teacher是否有id做判断
      if (this.teacher.id) {
        //修改
        console.log("执行了修改讲师的方法");
        this.updateTeacher();
      }
    },
   
    //根据讲师id查询方法
    getInfo:function(id) {
      teacherApi.getTeacherInfo(id).then((response) => {
        if (response.success == true) {
          this.teacher = response.data.teacher;
          console.log(this.teacher);
        } else {
          alert("查询失败");
        }
      });
    },
    //修改讲师方法
    updateTeacher:function() {
      teacherApi.updateTeacherInfo(this.teacher).then((response) => {
        if (response.success == true) {
          //提示信息
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          //回到列表页面 , 路由跳转
          this.$router.push({
            path: "/teacher/table",
          });
        } else {
          this.$message({
            type: "success",
            message: "修改失败!",
          });
        }
      });
    },
  },
};
</script>