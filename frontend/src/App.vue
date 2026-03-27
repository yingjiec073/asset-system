<template>
  <div class="layout">
    <aside class="sidebar">
      <h3>EAM 资产系统</h3>
      <router-link to="/dashboard">仪表盘</router-link>
      <router-link to="/assets">资产列表</router-link>
      <router-link to="/vehicles">车辆管理</router-link>
      <router-link to="/computers">电脑管理（预留）</router-link>
      <router-link to="/devices">设备管理（预留）</router-link>
      <router-link to="/furniture">家具管理（预留）</router-link>
      <router-link to="/operations">资产操作</router-link>
      <router-link to="/inventory">盘点管理</router-link>
      <router-link to="/statistics">统计分析</router-link>
    </aside>
    <main class="main">
      <router-view />
    </main>
  </div>
</template>
