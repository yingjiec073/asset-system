<template>
  <el-container class="app-shell">
    <el-aside width="220px" class="sidebar">
      <div class="logo">资产管理系统</div>
      <el-menu
        router
        :default-active="$route.path"
        background-color="#1f2d3d"
        text-color="#d3dce6"
        active-text-color="#409eff"
      >
        <el-menu-item index="/dashboard">仪表盘</el-menu-item>
        <el-menu-item index="/vehicles">车辆管理</el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="topbar">资产管理系统</el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.app-shell {
  min-height: 100vh;
  background: #f5f7fa;
}

.sidebar {
  background: #1f2d3d;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  border-bottom: 1px solid #324157;
}

.topbar {
  height: 60px;
  line-height: 60px;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
}

.main-content {
  padding: 20px;
}
</style>
