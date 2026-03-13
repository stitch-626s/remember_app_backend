<template>
  <div class="home-layout">
    <el-container>
      <el-aside class="sidebar-container">
            <el-menu
                default-active="2"
                class="el-menu-vertical"
            >
                <!-- 带有下拉列表的组件 -->
                <el-sub-menu index="1">
                <template #title>
                    <el-icon><home-filled /></el-icon>
                    <span>首页</span>
                </template>
                </el-sub-menu>

                <!-- 无下拉列表 -->
                <el-menu-item index="2">
                <el-icon><icon-menu /></el-icon>
                <template #title>背题</template>
                </el-menu-item>

                <el-menu-item index="3">
                <el-icon><document /></el-icon>
                <template #title>题库</template>
                </el-menu-item>

                <el-menu-item index="4">
                <el-icon><setting /></el-icon>
                <template #title>个人主页</template>
                </el-menu-item>
            </el-menu>
      </el-aside>
      <el-main>Main</el-main>

      <el-aside width="200px">Aside</el-aside>
    </el-container>
  </div>
</template>

<script setup>
import {
  Document,
  HomeFilled,
  Menu as IconMenu,
  Setting,
} from '@element-plus/icons-vue'
</script>

<style lang="css" scoped>
    @import '../assets/styles/home.css'
</style>
