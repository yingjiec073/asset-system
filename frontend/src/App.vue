<template>
  <div class="layout">
    <aside>
      <h3>EAM 菜单</h3>
      <ul>
        <li><RouterLink to="/dashboard">仪表盘</RouterLink></li>
        <li><RouterLink to="/assets">资产列表</RouterLink></li>
        <li><RouterLink to="/vehicles">车辆管理</RouterLink></li>
        <li><RouterLink to="/computers">电脑管理（预留）</RouterLink></li>
        <li><RouterLink to="/devices">设备管理（预留）</RouterLink></li>
        <li><RouterLink to="/furniture">家具管理（预留）</RouterLink></li>
        <li><RouterLink to="/operations">资产操作</RouterLink></li>
        <li><RouterLink to="/inventory">盘点管理</RouterLink></li>
      </ul>
    </aside>
    <main><RouterView /></main>
  </div>
</template>

<style scoped>
.layout { display: grid; grid-template-columns: 220px 1fr; min-height: 100vh; }
aside { background: #0b1f3a; color: #fff; padding: 12px; }
ul { list-style: none; padding: 0; }
li { margin: 8px 0; }
a { color: #d6e4ff; text-decoration: none; }
main { padding: 16px; }
</style>
