<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称" />
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item>
        <el-select v-model="courseQuery.teacherId" placeholder="课程讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <!-- 课程状态 -->
      <el-form-item class="teacher">
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option :value="1" label="已发布" />
          <el-option :value="0" label="未发布" />
        </el-select>
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item>
        <el-select
          v-model="courseQuery.subjectParentId"
          placeholder="一级分类"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseQuery.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="clear()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{ (page - 1) * limit + scope.$index + 1 }}</template>
      </el-table-column>
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px" />
            </div>
            <div class="title">
              <a href>{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">{{ scope.row.gmtCreate.substr(0, 10) }}</template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">{{ scope.row.gmtModified.substr(0, 10) }}</template>
      </el-table-column>
      <el-table-column label="价格" width="100" align="center">
        <template slot-scope="scope">
          {{ Number(scope.row.price) === 0 ? '免费' :
          '¥' + scope.row.price.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="buyCount" label="付费学员" width="100" align="center">
        <template slot-scope="scope">{{ scope.row.buyCount }}人</template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程信息</el-button>
          </router-link>
          <router-link :to="'/edu/course/chapter/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
          </router-link>
          <el-button
            type="text"
            size="mini"
            icon="el-icon-delete"
            @click="deleteCourseById(scope.row.id)"
          >删除课程信息</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
//引入调用teacher.js文件
import teacher from "@/api/edu/course.js";
import course from "@/api/edu/course";
import subject from "@/api/edu/subject";

export default {
  data() {
    // 定义变量和初始值
    return {
      listLoading: true, //是否显示加载中
      list: null, //查询之后接口返回的数据集合
      page: 1, //当前页
      limit: 10, //每页显示的记录数
      total: 0, //总记录数
      courseQuery: {
        subjectId: "", //二级分类ID
        teacherId: "",
        subjectParentId: "", //一级分类ID
        title: "",
        status: ""
      }, //条件封装对象
      subjectOneList: [],
      subjectTwoList: [],
      teacherList: []
    };
  },

  created() {
    this.getListTeacher();
    this.getOneSubject();
    this.getList();
  },
  methods: {
    //删除课程
    deleteCourseById(id) {
        console.log("删除的id值为:" , id)
      this.$confirm("此操作将永久删除讲师记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        // 点击确定 , 执行then方法
        //调用 删除方法
        course.deleteCourse(id).then(response => {
          if (response.success == true) {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
          } else {
            this.$message({
              type: "success",
              message: "删除失败!"
            });
          }
          //刷新页面
          this.getList()
        });
      });
    },
    //清空查询条件
    clear() {
      //将输入项数据清空
      this.courseQuery = {};
      //清空二级分类的list
      this.subjectTwoList = [];
      //查询所有讲师数据
      this.getList();
    },

    //查询所有的讲师
    getListTeacher() {
      course.getListTeacher().then(response => {
        if (response.success == true) {
          this.teacherList = response.data.items;
        }
      });
    },

    //分页查询列表
    getList(page = 1) {
      this.listLoading = true;
      this.page = page;
      course
        .getPageQueryCourse(this.page, this.limit, this.courseQuery)
        .then(response => {
          if (response.success == true) {
            this.list = response.data.rows;
            this.total = response.data.total;
          } else {
            this.$message({
              type: "success",
              message: "查询数据失败!"
            });
          }
          this.listLoading = false;
        });
    },

    //查询所有一级课程分类
    getOneSubject() {
      this.courseQuery.subjectParentId = "";
      subject.getSubjectList().then(response => {
        if (response.success == true) {
          this.subjectOneList = response.data.list;
        }
      });
    },

    //点击一级分类 , 触发change , 显示对应二级分类
    subjectLevelOneChanged(value) {
      //把二级分类的id值清空
      this.courseQuery.subjectId = "";

      //value就是一级分类的id值
      //遍历所有的分类 , 包含一级二级
      for (var i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        var oneSubject = this.subjectOneList[i];
        //判断所有一级分类的id和点击一级分类的id是否一样

        if (value === oneSubject.id) {
          //从一级分类中获取所有的二级分类
          this.subjectTwoList = oneSubject.children;
        }
      }
    }
  }
};
</script>

<style scoped>
.teacher {
  width: 135px;
}
.myClassList .info {
  width: 450px;
  overflow: hidden;
}
.myClassList .info .pic {
  width: 150px;
  height: 90px;
  overflow: hidden;
  float: left;
}
.myClassList .info .pic a {
  display: block;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
.myClassList .info .pic img {
  display: block;
  width: 100%;
}
.myClassList td .info .title {
  width: 280px;
  float: right;
  height: 90px;
}
.myClassList td .info .title a {
  display: block;
  height: 48px;
  line-height: 24px;
  overflow: hidden;
  color: #00baf2;
  margin-bottom: 12px;
}
.myClassList td .info .title p {
  line-height: 20px;
  margin-top: 5px;
  color: #818181;
}
</style>