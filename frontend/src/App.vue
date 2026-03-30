<template>
  <div class="layout">
    <aside>
      <h3>Enterprise EAM</h3>
      <ul>
        <li><RouterLink to="/dashboard">Dashboard</RouterLink></li>
        <li><RouterLink to="/modules/assets">modules/assets</RouterLink></li>
        <li><RouterLink to="/modules/users">modules/users</RouterLink></li>
        <li><RouterLink to="/modules/approvals">approvals</RouterLink></li>
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
