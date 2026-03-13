<template>
  <div class="register-box">
    <h3 class="title">注册</h3>

    <div class="input-area">
      <p id="email">电子邮件</p>
      <el-input v-model="input.username" class="input-email" placeholder="输入邮箱账号" />
      <p id="password">密码</p>
      <el-input v-model="input.password" class="input-password" placeholder="输入密码" />
    </div>

    <div>
      <div class="primary-btn">
        <el-button type="primary" class="register-btn">注册</el-button>
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

</script>

<style>
    @import '../assets/styles/register.css'
</style>