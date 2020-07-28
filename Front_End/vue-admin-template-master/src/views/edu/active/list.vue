<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="名称" style="margin-right: 0px">
        <el-input v-model="activeQuery.name" placeholder="活动名称" />
      </el-form-item>

      <el-form-item label="积分" style="margin-right: 0px">
        <el-input-number
          :min="0"
          v-model="activeQuery.score"
          controls-position="right"
          placeholder
        />
      </el-form-item>

      <el-form-item label="开始时间" style="margin-right: 0px">
        <el-date-picker
          v-model="activeQuery.date"
          type="datetime"
          placeholder="选择活动起始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item label="截止时间" style="margin-right: 0px">
        <el-date-picker
          v-model="activeQuery.datejiezhi"
          type="datetime"
          placeholder="选择活动截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
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
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{ (page - 1) * limit + scope.$index + 1 }}</template>
      </el-table-column>

      <el-table-column prop="name" label="活动名称" width="220" />

      <el-table-column prop="score" label="活动积分" width="220" />

      <el-table-column prop="date" label="活动开始时间" width="220" />

      <el-table-column prop="datejiezhi" label="活动截止时间" width="220" />

      <el-table-column prop="content" label="活动内容" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/active/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
          >删除</el-button>
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
//引入调用active.js文件
import active from "@/api/edu/active.js";

export default {
  //写核心代码的位置
  // data:{

  // },
  data() {
    // 定义变量和初始值
    return {
      listLoading: true, //是否显示加载中
      list: null, //查询之后接口返回的数据集合
      page: 1, //当前页
      limit: 10, //每页显示的记录数
      total: 0, //总记录数
      activeQuery: {}, //条件封装对象
    };
  },

  created() {
    // 页面渲染之前执行 , 一般调用methods定义的方法
    //调用
    this.getList();
  },
  methods: {
    // 创建具体的方法 , 调用active..js定义的方法
    getList(page = 1) {
      this.listLoading = true;
      this.page = page;
      active
        .getActiveListPage(this.page, this.limit, this.activeQuery)
        .then((response) => {
          // 请求成功
          //response 接口返回的数据
          // console.log(response)
          if (response.success == true) {
            this.list = response.data.rows;
            this.total = response.data.total;
            console.log(this.list, this.total);
          } else {
            console.log(response.data.rows, response.data.total);
          }
          this.listLoading = false;
        });
    },

    //清空条件
    clear() {
      //将输入项数据清空
      this.activeQuery = {};
      //查询所有讲师数据
      this.getList();
    },

    //删除讲师的方法
    removeDataById(id) {
      this.$confirm("此操作将永久删除讲师记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 点击确定 , 执行then方法
          //调用 删除方法
          active.deleteActiveId(id).then((response) => {
            // 提示信息
            if (response.success == true) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            } else {
              this.$message({
                type: "success",
                message: "删除失败!",
              });
            }
            //回到列表页面
            this.getList();
          });
        })
        .catch(() => {
          // 点击取消 , 执行catch方法
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });

      // alert(id)
    },
  },
};
</script>