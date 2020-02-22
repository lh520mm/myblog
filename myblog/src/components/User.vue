<template>
  <div class="user">
    <el-row>
      <el-col :span="24" style="text-align: center;">
        <div>
          <el-avatar icon="el-icon-user-solid" :size="64"></el-avatar>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">用户名：{{user.account}}</el-col>
      <el-col :span="12">昵称：{{user.nickname}}</el-col>
    </el-row>
    <el-row>
      <el-col :span="12">联系电话：{{user.tel}}</el-col>
      <el-col :span="12">年龄：{{user.age}}</el-col>
    </el-row>
    <el-row>
      <el-col :span="12">出生日期：{{user.account}}</el-col>
      <el-col :span="12">性别：{{user.nickname}}</el-col>
    </el-row>
    <el-row>
      <el-col :span="12">等级：{{user.tel}}</el-col>
      <el-col :span="12">文章数目：{{user.tel}}</el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "User",
  props: {
    rowId: String
  },
  data() {
    return {
      user: {
        account: "admin",
        nickname: "超级管理员",
        tel: "18366262538",
        age: "26",
        sex: "1",
        birthday: "1993-02-04",
        motto: "希望"
      }
    };
  }
};
</script>