<template>
  <div class="about">
    <h1>This is an about page</h1>
    <mavon-editor v-model="value"/>
  </div>
</template>
<script>
export default {
  data(){
    return {
      value:'hello world'
    }
  }
}
</script>
