<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-button type="text" @click="oppenChapterDialog">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="openVideo(chapter.id)">添加小节</el-button>
            <el-button style type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>
        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- TODO -->
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/uploadAlyVideo'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，
                <br />支持3GP、ASF、AVI、DAT、DV、FLV、F4V、
                <br />GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、
                <br />MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、
                <br />SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";
export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      chapterVideoList: [],
      courseId: "", //课程id
      dialogChapterFormVisible: false, //表示章节弹框
      dialogVideoFormVisible: false, // 小节的弹框
      fileList: [], //上传文件列表
      BASE_API: process.env.BASE_API, // 接口API地址
      video: {
        title: "",
        sort: 0,
        free: 0,
        videoSourceId: "",
        videoOriginalName: "" //视频上传名称
      },
      chapter: {
        //用于封装章节数据
        title: "",
        sort: 0
      }
    };
  },
  created() {
    //获取路由的courseId值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      console.log("我的id值", this.courseId);
      //根据课程id查询章节和小节的列表
      this.getChapterVideo();
    }
  },
  methods: {
    //删除时调用方法
    handleVodRemove() {
      //调用接口中删除视频的方法
      video.deleteAlyVod(this.video.videoSourceId).then(response => {
        if (response.success == true) {
          this.$message({
            type: "success",
            message: "删除视频成功!"
          });
          //把文件列表清空
          this.fileList = [];
          //把video里面的视频id和视频名称清空
          //上传视频id赋值
          this.video.videoSourceId = "";
          //上传视频名称赋值
          this.video.videoOriginalName = "";
        }
      });
    },

    //删除之前调用方法
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    //上传视频成功调用的方法
    handleVodUploadSuccess(response, file, fileList) {
      //上传视频id赋值
      this.video.videoSourceId = response.data.videoId;
      //上传视频名称赋值
      this.video.videoOriginalName = file.name;
    },

    //上传之前
    handleUploadExceed() {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },

    //=====================小节的操作=========================================

    // 修改弹框,数据回显
    openEditVideo(videoId) {
      this.dialogVideoFormVisible = true;
      video.getVideo(videoId).then(response => {
        if (response.success == true) {
          this.video = response.data.video;
        }
      });
    },

    //修改小节
    updateVideo() {
      video.updateVideo(this.video).then(response => {
        if (response.success == true) {
          //1. 关闭弹框
          this.dialogVideoFormVisible = false;
          //2. 提示信息
          this.$message({
            type: "success",
            message: "修改小节成功!"
          });
          //3. 刷新页面
          this.getChapterVideo();
        }
      });
    },

    //删除小节
    removeVideo(videoId) {
      this.$confirm("此操作将永久删除小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        // 点击确定 , 执行then方法
        //调用 删除方法
        video.deleteVideo(videoId).then(response => {
          if (response.success == true) {
            this.$message({
              type: "success",
              message: "删除小节成功!"
            });
            //刷新页面
            this.getChapterVideo();
          }
        });
      });
    },

    //添加小节弹框的方法
    openVideo(chapterId) {
      //清空数据
      this.video = {
        title: "",
        sort: 0,
        free: 0,
        videoSourceId: ""
      };

      this.dialogVideoFormVisible = true;

      //设置章节id
      this.video.chapterId = chapterId;
    },

    addVideo() {
      //设置课程id
      this.video.courseId = this.courseId;
      video.addVideo(this.video).then(response => {
        if (response.success == true) {
          //1. 关闭弹框
          this.dialogVideoFormVisible = false;
          //2. 提示信息
          this.$message({
            type: "success",
            message: "增加小节成功!"
          });
          //3. 刷新页面
          this.getChapterVideo();
        }
      });
    },

    saveOrUpdateVideo() {
      if (this.video.id) {
        this.updateVideo();
      } else {
        this.addVideo();
      }
    },

    //===================章节的操作===================================

    //删除章节
    removeChapter(chapterId) {
      this.$confirm("此操作将永久删除章节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        // 点击确定 , 执行then方法
        //调用 删除方法
        chapter.deleteChapter(chapterId).then(response => {
          if (response.success == true) {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            //刷新页面
            this.getChapterVideo();
          }
        });
      });
    },

    //修改章节弹框做数据回显
    openEditChapter(chapterId) {
      chapter.getChapter(chapterId).then(response => {
        if (response.success == true) {
          this.chapter = response.data.chapter;
          //显示弹框
          this.dialogChapterFormVisible = true;
        }
      });
    },

    //弹出添加章节的页面
    oppenChapterDialog() {
      //弹框
      this.dialogChapterFormVisible = true;
      //清空表单数据
      this.chapter = {
        title: "",
        sort: 0
      };
    },

    //添加章节
    addChapter() {
      //设置课程id到chapter对象中
      this.chapter.courseId = this.courseId;
      chapter.addChapter(this.chapter).then(response => {
        if (response.success == true) {
          //1. 关闭弹框
          this.dialogChapterFormVisible = false;
          //2. 提示信息
          this.$message({
            type: "success",
            message: "增加章节成功!"
          });
          //3. 刷新页面
          this.getChapterVideo();
        }
      });
    },

    //修改章节
    updateChapter() {
      chapter.updateChapter(this.chapter).then(response => {
        if (response.success == true) {
          //1. 关闭弹框
          this.dialogChapterFormVisible = false;
          //2. 提示信息
          this.$message({
            type: "success",
            message: "修改章节成功!"
          });
          //3. 刷新页面
          this.getChapterVideo();
        }
      });
    },

    saveOrUpdate() {
      if (this.chapter.id) {
        this.updateChapter();
      } else {
        this.addChapter();
      }
    },
    //根据课程id查询章节和小节的列表
    getChapterVideo() {
      chapter.getAllChapterVideo(this.courseId).then(response => {
        if (response.success == true) {
          this.chapterVideoList = response.data.allChapterVideo;
        }
      });
    },

    previous() {
      //跳转到上一步
      this.$router.push({ path: "/course/info/" + this.courseId });
    },
    next() {
      //跳转到第三步
      this.$router.push({ path: "/course/publish/" + this.courseId });
    }
  }
};
</script>

<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}

.chanpterList li {
  position: relative;
}

.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}

.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}

.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>
