import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

axios.defaults.baseURL = process.env.VUE_APP_API_URL
axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=UTF-8'
axios.defaults.timeout=5000
axios.defaults.withCredentials=true
//拦截器
axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    console.info("----------------------")
    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  })

  axios.interceptors.response.use(function (response) {
    // Do something with response data
    return response;
  }, function (error) {
    // Do something with response error
    return Promise.reject(error);
  })

Vue.use(VueAxios, axios)