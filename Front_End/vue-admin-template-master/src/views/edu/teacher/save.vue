<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="0" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">
          更换头像
        </el-button>
        <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调-->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import teacherApi from "@/api/edu/teacher.js";
import ImageCropper from '@/components/ImageCropper';
import PanThumb from '@/components/PanThumb';

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: {
        name: "",
        sort: 0,
        level: 1,
        career: "",
        intro: "",
        avatar: ""
      },
      saveBtnDisabled: false, // 保存按钮是否禁用
      //上传弹框的组件是否显示
      imagecropperShow:false,
      imagecropperKey:0,
      BASE_API:process.env.BASE_API,//获取dev.env.js中的地址
    };
  },

  watch: {
    $route(to, from) {
      // 路由变换方式  ,当路由发生变化时 , 执行此方法
      console.log("watch $route");
      this.init();
    }
  },

  created() {
    console.log("created");
    this.init();
  },
  
  methods: {
    close(){ // 关闭上传弹框的方法
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
    },
    //上传成功的方法
    cropSuccess(data){
      //关闭弹框
      this.close()
      //上传时候接口返回图片地址
      this.teacher.avatar = data.url
    },
    init() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getInfo(id);
      } else {
        this.teacher = {avatar: ""};
      }
    },
    saveOrUpdate() {
      //判断是修改还是添加
      //根据teacher是否有id做判断
      if (this.teacher.id) {
        //修改
        console.log("执行了修改讲师的方法");
        this.updateTeacher();
      } else {
        //添加
        console.log("执行了添加讲师的方法");
        this.saveTeacher();
      }
    },
    //添加讲师方法
    saveTeacher() {
      teacherApi.addTeacher(this.teacher).then(response => {
        //添加成功
        //提示成功
        if (response.success == true) {
          this.$message({
            type: "success",
            message: "增加成功!"
          });
          //回到列表页面 , 路由跳转
          this.$router.push({
            path: "/teacher/table"
          });
        } else {
          this.$message({
            type: "success",
            message: "增加失败!"
          });
        }
      });
    },
    //根据讲师id查询方法
    getInfo(id) {
      teacherApi.getTeacherInfo(id).then(response => {
        if (response.success == true) {
          this.teacher = response.data.teacher;
          console.log(this.teacher);
        } else {
          alert("查询失败");
        }
      });
    },
    //修改讲师方法
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher).then(response => {
        if (response.success == true) {
          //提示信息
          this.$message({
            type: "success",
            message: "修改成功!"
          });
          //回到列表页面 , 路由跳转
          this.$router.push({
            path: "/teacher/table"
          });
        } else {
          this.$message({
            type: "success",
            message: "修改失败!"
          });
        }
      });
    }
  }
};
</script>