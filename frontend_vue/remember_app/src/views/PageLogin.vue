<template>
  <div class="login-box">
    <h3 class="title">登录</h3>

    <div class="input-area">
      <p id="email">电子邮件</p>
      <el-input v-model="input.username" class="input-email" placeholder="输入邮箱账号" />
      <p id="password">密码</p>
      <el-input v-model="input.password" class="input-password" placeholder="输入密码" />
      <router-link class="re-password" to="/re-password">忘记密码？</router-link>
    </div>

    <div>
      <div class="primary-btn">
        <el-checkbox class="check-btn" v-model="checked1" label="记住账号" size="large" />
        <el-button type="primary" class="login-btn">登录</el-button>
        <span id="register-tip">还没有账号？ <router-link class="to-register" to="/register">立即注册</router-link></span>
      </div>
    </div>
      
  </div>
</template>

<script setup >
  import { ref } from 'vue'

  const input = ref({
    username: "",
    password: ""
  })

  const checked1 = ref(false)

</script>

<style lang="css" scoped>
  @import '../assets/styles/login.css'
</style>
