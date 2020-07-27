<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="活动名称">
        <el-input v-model="active.name" />
      </el-form-item>

      <el-form-item label="活动日期">
        <el-date-picker
          v-model="active.date"
          type="datetime"
          placeholder="选择活动日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item label="活动积分">
        <el-input-number :min="0" v-model="active.score" controls-position="right" placeholder />
      </el-form-item>

      <el-form-item label="活动内容">
        <tinymce :height="300" v-model="active.content" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import active from "@/api/edu/active.js";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";
import Tinymce from "@/components/Tinymce"; //引入组件

export default {
  components: { ImageCropper, PanThumb , Tinymce },
  data() {
    return {
      active: {
        name: "",
        date: "",
        score: "",
        content: "",
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

  created() {
    console.log("created");
    this.init();
  },

  methods: {
    close() {
      // 关闭上传弹框的方法
      this.imagecropperShow = false;
      this.imagecropperKey = this.imagecropperKey + 1;
    },

    init() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getInfo(id);
      } else {
        this.activ = {};
      }
    },

    saveOrUpdate() {
      //判断是修改还是添加
      //根据active是否有id做判断
      if (this.active.id) {
        //修改
        console.log("执行了修改讲师的方法");
        this.updateActive();
      }else {
        //添加
        console.log("执行了添加讲师的方法");
        this.saveActive();
      }
    },

    //添加活动方法
    saveActive() {
      active.addActive(this.active).then(response => {
        //添加成功
        //提示成功
        if (response.success == true) {
          this.$message({
            type: "success",
            message: "增加成功!"
          });
          //回到列表页面 , 路由跳转
          this.$router.push({
            path: "/active/table"
          });
        } else {
          this.$message({
            type: "success",
            message: "增加失败!"
          });
        }
      });
    },

    //根据活动id查询方法
    getInfo(id) {
      active.getActiveInfo(id).then((response) => {
        if (response.success == true) {
          this.active = response.data.active;
          console.log(this.active);
        } else {
          alert("查询失败");
        }
      });
    },
    //修改活动方法
    updateActive() {
      active.updateActiveInfo(this.active).then((response) => {
        if (response.success == true) {
          //提示信息
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          //回到列表页面 , 路由跳转
          this.$router.push({
            path: "/active/table",
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

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>