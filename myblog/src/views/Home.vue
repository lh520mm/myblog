<template>
  <div class="home">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="12">
            <el-button
              @click="changeState"
              type="primary"
              class="sbt"
              :icon="isCollapse ? 'el-icon-s-unfold': 'el-icon-s-fold'"
            ></el-button>
          </el-col>
          <el-col :span="12">
            <!-- <div class="grid-content bg-purple-light ss"> -->
            <el-row>
              <el-col :span="24" class="ss">
                <div class="ss">
                  <el-dropdown>
                    <span class="el-dropdown-link">
                      <el-avatar
                        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                      ></el-avatar>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item icon="el-icon-user-solid">个人中心</el-dropdown-item>
                      <el-dropdown-item icon="el-icon-refresh-right">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </div>
                <div class="ss">
                  <el-badge is-dot class="item">
                    <el-button class="message-button" circle icon="el-icon-message-solid"></el-button>
                  </el-badge>
                </div>
              </el-col>
            </el-row>
            <!-- </div> -->
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside>
          <el-menu
            default-active="/"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            :collapse="isCollapse"
            :router="true"
          >
            <div v-for="row in menu" :key="row.uri">
              <el-menu-item v-if="row.items.length<=0" :index="row.uri">
                <i :class="row.lable"></i>
                <span slot="title">{{row.title}}</span>
              </el-menu-item>
              <el-submenu v-else :index="row.uri">
                <template slot="title">
                  <i :class="row.lable"></i>
                  <span slot="title" :class="isCollapse ? 'title-hide':'title-show'">{{row.title}}</span>
               </template>
                <el-menu-item-group>
                  <el-menu-item v-for="ch in row.items " :key="ch.uri" :index="ch.uri">{{ch.title}}</el-menu-item>
                </el-menu-item-group>
              </el-submenu>
            </div>
            <!-- <el-menu-item index="/">
              <i class="el-icon-s-home"></i>
              <span slot="title">系统首页</span>
            </el-menu-item>
            <el-menu-item index="2">
              <i class="el-icon-s-management"></i>
              <span slot="title">我的博客</span>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-camera-solid"></i>
              <span slot="title">我的相册</span>
            </el-menu-item>
            <el-submenu index="4">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">权限管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="1-1">用户管理</el-menu-item>
                <el-menu-item index="1-2">角色管理</el-menu-item>
                <el-menu-item index="1-3">菜单管理</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="5">
              <template slot="title">
                <i class="el-icon-s-tools"></i>
                <span slot="title">运维管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="1-1">系统监控</el-menu-item>
                <el-menu-item index="1-2">日志管理</el-menu-item>
              </el-menu-item-group>
            </el-submenu> -->
          </el-menu>
        </el-aside>
        <el-main>
          <!-- <el-button size="small" @click="addTab(editableTabsValue)">add tab</el-button> -->
          <div class="cc">
            <router-view></router-view>
          </div>
          <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
.home {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.sbt {
  background: #20222a !important;
  border-color: #20222a !important;
  color: #fff;
  font-size: 24px;
}
.cc {
  width: 100%;
  height: 100%;
  overflow-y: auto;
  background: #fff;
  /* padding-left: 10px; */
}
.el-container {
  width: 100%;
  height: 100%;
}
.el-header,
.el-footer {
  background-color: #20222a;
  color: #333;
  text-align: left;
  line-height: 60px;
}
.el-header {
  padding: 0 !important;
}
.el-aside {
  width: auto !important;
  background-color: #d3dce6;
  color: #333;
  text-align: left;
  line-height: 200px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  /* line-height: 160px; */
  overflow: hidden;
  height: 100%;
  padding: 10px 15px 15px 10px !important;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.el-menu {
  /* width: 100%; */
  height: 100%;
  /* overflow-y: auto; */
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-menu--collapse >div>.el-submenu>.el-submenu__title>.el-submenu__icon-arrow {
  display: none !important;
}
.ss {
  float: right;
  margin: 4px 20px;
  line-height: normal;
}
.el-dropdown {
  margin-right: 30px !important;
}
.title-show {
  display: inline-block;
}
.title-hide {
  display: none !important;
}
.el-submenu>.el-submenu__title .el-submenu__icon-arrow{
	-webkit-transform: rotateZ(-90deg); 
	-ms-transform: rotate(-90deg);
	transform: rotateZ(-90deg); 
}
/*菜单展开*/
.el-submenu.is-opened>.el-submenu__title .el-submenu__icon-arrow{
	-webkit-transform: rotateZ(0deg); 
	-ms-transform: rotate(0deg);
	transform: rotateZ(0deg); 
}
</style>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
export default {
  name: "home",
  data() {
    return {
      isCollapse: true,
      menu: [
        {
          title: "系统首页",
          lable: "el-icon-s-home",
          uri: "/",
          items: []
        },
        {
          title: "我的博客",
          lable: "el-icon-s-management",
          uri: "2",
          items: []
        },
        {
          title: "我的相册",
          lable: "el-icon-camera-solid",
          uri: "3",
          items: []
        },
        {
          title: "权限管理",
          lable: "el-icon-s-tools",
          uri: "4",
          items: [
            {
              title: "用户管理",
              uri: "4-1"
            },
            {
              title: "角色管理",
              uri: "4-2"
            },
            {
              title: "菜单管理",
              uri: "4-3"
            }
          ]
        }
      ]
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    changeState() {
      console.info(this.isCollapse);
      var flag = this.isCollapse;
      if (flag) {
        this.isCollapse = false;
      } else {
        this.isCollapse = true;
      }
    }
  }
};
</script>
