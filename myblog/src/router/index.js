import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import store from '@/store'
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
    }]
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
store.commit('login/setToken', '123456')
console.info( store.getters["login/getToken"])
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
        next({
          path: to.path || '/'
        })
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