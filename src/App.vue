<template>
  <el-container class="app-shell">
    <el-aside width="240px" class="sidebar">
      <div class="logo">EAM 资产管理系统</div>
      <el-scrollbar>
        <el-menu router :default-active="$route.path" background-color="#1f2d3d" text-color="#d3dce6" active-text-color="#409eff">
          <el-menu-item index="/dashboard">仪表盘</el-menu-item>

          <el-sub-menu index="assets">
            <template #title>资产管理</template>
            <el-menu-item index="/assets/list">资产列表</el-menu-item>
            <el-menu-item index="/assets/categories">分类管理</el-menu-item>
            <el-menu-item index="/vehicles">车辆管理</el-menu-item>
            <el-menu-item index="/computers">电脑管理（预留）</el-menu-item>
            <el-menu-item index="/devices">设备管理（预留）</el-menu-item>
            <el-menu-item index="/furnitures">家具管理（预留）</el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/operations">资产操作</el-menu-item>
          <el-menu-item index="/inventory">盘点管理</el-menu-item>
          <el-menu-item index="/analytics">统计分析</el-menu-item>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header class="topbar">企业资产管理系统（EAM）</el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.app-shell { min-height: 100vh; background: #f5f7fa; }
.sidebar { background: #1f2d3d; color: #fff; }
.logo { height: 60px; line-height: 60px; text-align: center; font-size: 16px; font-weight: 600; border-bottom: 1px solid #324157; }
.topbar { height: 60px; line-height: 60px; font-size: 18px; font-weight: 600; color: #303133; background: #fff; border-bottom: 1px solid #e4e7ed; }
.main-content { padding: 20px; }
</style>
