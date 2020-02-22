import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import User from '../views/UserList.vue'
import store from '@/store'
import axios from 'axios'
Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'home',
    component: Home,
    children: [{
        path: '/',
        component: () => import( /* webpackChunkName: "about" */ '../views/About.vue'),
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'user',
        component: User,
        meta: {
          requiresAuth: true
        }
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: {
      requiresAuth: false
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
var token = localStorage.getItem('token');
store.commit('login/setToken', token)
console.info(store.getters["login/getToken"])
//导航守卫判断是否登录
router.beforeEach((to, from, next) => {
  // 判断该路由是否需要登录权限
  if (to.matched.some(r => r.meta.requiresAuth)) {
    if (localStorage.getItem('token')) {
      if (to.path === '/login') {
        next({
          path: '/'
        })
      } else {
        //调用登录接口

        axios({
          method: "get",
          url: "/checktoken?token=" + localStorage.getItem('token')
          // withCredentials:true,
        }).then(function (response) {
          console.info(response);
          if (response.data.success) {
            console.info("################");
            next();
          } else {
            next(`/login?redirect=${to.path}`);
          }
        });
      }
    } else {
      if (to.path === '/login') {
        next()
      } else {
        next(`/login?redirect=${to.path}`)
      }
    }
  } else {
    next();
  }

})

export default router