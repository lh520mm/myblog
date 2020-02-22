<template>
  <div class="user_con">
    <div class="user_list">
      <div class="user_search">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input size="small" v-model="account" placeholder="用户名"></el-input>
          </el-col>
          <el-col :span="6">
            <el-input size="small" v-model="nickname" placeholder="昵称"></el-input>
          </el-col>
          <el-col :span="6">
            <el-input size="small" v-model="tel" placeholder="联系电话"></el-input>
          </el-col>
          <el-col :span="6" style="text-align: left;">
            <el-button type="primary" size="small" icon="el-icon-search" @click="getUsers">搜索</el-button>
            <el-button
              type="primary"
              size="small"
              icon="el-icon-plus"
              @click="dialogFormVisible = true"
            >新增</el-button>
          </el-col>
        </el-row>
      </div>

      <el-table :data="userData" stripe border :header-cell-style="tableHeader" class="userTable">
        <el-table-column prop="account" label="用户名" width="180"></el-table-column>
        <el-table-column prop="nickname" label="昵称" width="180"></el-table-column>
        <el-table-column prop="sex" label="性别" :formatter="getSex"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="birthday" label="出生日期"></el-table-column>
        <el-table-column prop="tel" label="联系电话"></el-table-column>
        <el-table-column prop="lev" label="等级" :formatter="getLev"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-reading"
              @click="seeUser(scope.$index, scope.row)"
            ></el-button>
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-delete"
                slot="reference" 
                @click="deleteUSer(scope.$index, scope.row)"
              ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="pageNumber"
          :page-sizes="[10, 20, 50, 200]"
          :page-size="pageSize"
          :prev-text="prevPage"
          :next-text="nextPage"
          layout="prev, pager, next, sizes, total"
          :total="total"
          style="text-align: left"
        ></el-pagination>
      </div>
      <el-dialog title="新增" :visible.sync="dialogFormVisible" class="addUserDialog">
        <el-form :model="userform">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="用户名" :label-width="formLabelWidth">
                <el-input v-model="userform.account" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="昵称" :label-width="formLabelWidth">
                <el-input v-model="userform.nickname" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="性别" :label-width="formLabelWidth">
                <el-select v-model="userform.sex" placeholder="请选择" style="width:100%">
                  <el-option
                    v-for="item in userform.sexs"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年龄" :label-width="formLabelWidth">
                <el-input v-model="userform.age" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="出生日期" :label-width="formLabelWidth">
                <el-date-picker
                  v-model="userform.birthday"
                  type="date"
                  style="width:100%"
                  placeholder="选择日期"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系电话" :label-width="formLabelWidth">
                <el-input v-model="userform.tel" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="登录密码" :label-width="formLabelWidth">
                <el-input v-model="userform.pwd" show-password autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="确认密码" :label-width="formLabelWidth">
                <el-input v-model="userform.pwd1" show-password autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="座右铭" :label-width="formLabelWidth">
                <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="userform.motto"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addUser">确 定</el-button>
        </div>
      </el-dialog>

       <el-dialog title="查看" :visible.sync="dialogVisible" width="500px" class="addUserDialog">
          <User v-bind:rowId="seeUserRowId"></User>
      </el-dialog>
    </div>
  </div>
</template>
<style scoped>
.user_con {
  /* width: 100%;
  overflow: hidden; */
  padding: 10px;
}
.user_list {
  width: 100%;
}
.user_search {
  margin-bottom: 10px;
}
.userTable {
  width: 100%;
  /* padding: 10px; */
}
.tableHeader {
  background: #ccc;
}
.block {
  width: 100%;
  background: #ecf5ff;
}
.addUserDialog {
  text-align: left;
}
</style>
 
<script>
import qs from "qs";
import User from '@/components/User.vue'
export default {
  data() {
    return {
      account: "",
      nickname: "",
      tel: "",
      pageNumber: 1,
      pageSize: 10,
      total: 0,
      prevPage: "上一页",
      nextPage: "下一页",
      tableHeader: {
        background: "#ecf5ff",
        color: "#606266"
      },
      userData: [],
      dialogFormVisible: false,
      dialogVisible: false,
      userform: {
        account: "",
        nickname: "",
        tel: "",
        age: "",
        sex: "1",
        pwd: "",
        pwd1: "",
        sexs: [
          {
            value: "1",
            label: "男"
          },
          {
            value: "0",
            label: "女"
          }
        ],
        birthday: "",
        motto: ""
      },
      formLabelWidth: "80px",
      seeUserRowId:''
    };
  },
  methods: {
    seeUser(index, row) {
      console.log(index, row);
      this.seeUserRowId=row.rowId;
      this.dialogVisible=true;
    },
    deleteUSer(index, row) {
      console.log(index, row);
      this.$confirm('您将删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {    
        });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log(`每页 ${val} 条`);
      this.getUsers();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.getUsers();
    },
    getSex(row) {
      console.info(row);
      if (row.sex) {
        return "男";
      } else {
        return "女";
      }
    },
    getLev(row) {
      if (row.lev === 1) {
        return "码渣";
      } else if (row.lev === 2) {
        return "码农";
      } else if (row.lev === 3) {
        return "菜鸟";
      } else if (row.lev === 4) {
        return "程序猿";
      } else if (row.lev === 5) {
        return "大佬";
      } else if (row.lev === 6) {
        return "大神";
      } else {
        return "外星人";
      }
    },
    getUsers() {
      var _this = this;
      let account = _this.account;
      let nickname = _this.nickname;
      let tel = _this.tel;
      this.axios({
        method: "post",
        url: "/user/list",
        transformRequest: [
          function(data) {
            return qs.stringify(data);
          }
        ],
        // withCredentials:true,
        data: {
          account: account,
          nickname: nickname,
          tel: tel,
          pageSize: _this.pageSize,
          pageNumber: _this.pageNumber
        }
      }).then(response => {
        console.info(response);
        if (response.data.success) {
          //登录成功，将token记录到localstorage中
          _this.userData = response.data.data;
          _this.total = response.data.totalCount;
        }
      });
    },
    addUser() {
      var _this = this;
      console.info(this.userform);
      if (_this.userform.pwd != _this.userform.pwd1) {
        _this.$alert("两次输入的密码不一致，请确认", "提示", {
          type: "error",
          confirmButtonText: "确定"
        });
        return false;
      }
      this.axios({
        method: "post",
        url: "/user/save",
        transformRequest: [
          function(data) {
            return qs.stringify(data);
          }
        ],
        // withCredentials:true,
        data: JSON.parse(JSON.stringify(this.userform))
      }).then(response => {
        console.info(response);
        if (response.data.success) {
          //登录成功，将token记录到localstorage中
          this.dialogFormVisible = false;
          _this.getUsers();
        }
      });
    }
  },
  mounted() {
    var _this = this;
    let account = _this.account;
    let nickname = _this.nickname;
    let tel = _this.tel;
    this.axios({
      method: "post",
      url: "/user/list",
      transformRequest: [
        function(data) {
          return qs.stringify(data);
        }
      ],
      // withCredentials:true,
      data: {
        account: account,
        nickname: nickname,
        tel: tel,
        pageSize: _this.pageSize,
        pageNumber: _this.pageNumber
      }
    }).then(response => {
      console.info(response);
      if (response.data.success) {
        //登录成功，将token记录到localstorage中
        _this.userData = response.data.data;
        _this.total = response.data.totalCount;
      }
    });
  },
 components: {
    User
  }
};
</script>

