<template>
  <div class="login">
    <el-row>
      <el-col :span="24">
        <div class="login-form">
          <div class="top">
            <div class="block">
              <el-avatar :size="50" style="background-color:#409EFF">L&H</el-avatar>
            </div>
            <div style="line-height:45px;font-size:18px;">
              <span>世界因你而美好</span>
            </div>
          </div>

          <div class="center">
            <div class="row">
              <el-input placeholder="请输入登录账号" prefix-icon="el-icon-user" v-model="account"></el-input>
            </div>
            <div class="row">
              <el-input
                placeholder="请输入登录密码"
                show-password
                prefix-icon="el-icon-lock"
                v-model="pwd"
              ></el-input>
            </div>
            <div class="row">
              <el-row :gutter="18">
                <el-col :span="18">
                  <div class="grid-content bg-purple">
                    <el-input v-model="code" placeholder="请输入图片验证码"></el-input>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="grid-content bg-purple-light">
                    <el-image style="width:100%;height:40px" :src="url" @click="changCode"></el-image>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div class="row">
              <el-button type="primary" style="width:100%" @click="login">登录</el-button>
            </div>

            <div class="row">
              <el-row>
                <el-col :span="12">
                  <div class="grid-content bg-purple">
                    <el-checkbox v-model="checked">记住密码</el-checkbox>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="grid-content bg-purple-light">
                    <el-link href="https://element.eleme.io" target="_blank">忘记密码</el-link>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div class="row">
              <el-link href="https://element.eleme.io" target="_blank">注册账号</el-link>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<style scoped>
.login {
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/20191231.jpg);
  background-repeat: no-repeat;
  /* background-position-x: center; */
  background-position-y: center;
  background-size: cover;
  position: fixed;
  top: 0;
  left: 0;
  /* z-index: 10000; */
}
.login-form {
  width: 400px;
  height: 450px;
  background: #fff;
  position: fixed;
  top: 50%;
  left: 50%;
  margin-left: -200px;
  margin-top: -225px;
  /* z-index: 10001; */
  border-radius: 4px;
  border: 1px solid #ccc;
  opacity: 0.95;
}
.top {
  height: 80px;
  width: 100%;
  margin-top: 20px;
}
.center {
  overflow: hidden;
  padding: 10px;
}
.row {
  margin-top: 20px;
}
</style>
<script>
import qs from 'qs'
import uuidv1 from 'uuid/v1'
export default {
  data() {
    return {
      account: "",
      pwd: "",
      code: "",
      checked: false,
      vtoken:uuidv1(),
      fits: ["fill", "contain", "cover", "none", "scale-down"],
      url: process.env.VUE_APP_API_URL + "/kaptcha/getcode?vtoken="+this.vtoken
    };
  },
  methods: {
    changCode: function() {
      var token=this.vtoken=uuidv1();
      this.url =process.env.VUE_APP_API_URL + "/kaptcha/getcode?vtoken="+token+"&code=" + Math.random();
    },
    login() {
      var account = this.account;
      var pwd = this.pwd;
      var code = this.code;
      var vtoken=this.vtoken;
      if (account === null || account === undefined || account === "") {
        console.info("------------");
        this.$notify.error({
          title: "错误",
          message: "请输入登录账号",
          duration: 2000
        });
        return;
      }
      if (pwd === null || pwd === undefined || pwd === "") {
        this.$notify.error({
          title: "错误",
          message: "请输入登录密码",
          duration: 2000
        });
        return;
      }
      if (code === null || code === undefined || code === "") {
        this.$notify.error({
          title: "错误",
          message: "请输入图片验证码",
          duration: 2000
        });
        return;
      }

      //调用登录接口
      this.axios({
        method: "post",
        url:"/login",
        transformRequest: [function (data) {   
        return qs.stringify(data);
      }],
  // withCredentials:true,
        data: {
          account: account,
          pwd: pwd,
          verifycode:code,
          vtoken:vtoken
        }
      }).then(function(response){
         console.info(response)
      });
    }
  }
};
</script>