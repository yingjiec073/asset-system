<template>
  <table class="asset-table">
    <thead>
      <tr>
        <th>名称</th><th>分类</th><th>品牌</th><th>型号</th><th>编号</th><th>状态</th><th>部门</th><th>位置</th><th>创建时间</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in data" :key="item.id">
        <td>{{ item.name }}</td>
        <td>{{ item.category }}</td>
        <td>{{ item.brand }}</td>
        <td>{{ item.model }}</td>
        <td>{{ item.serialNumber }}</td>
        <td><slot name="status" :item="item" /></td>
        <td>{{ item.departmentId }}</td>
        <td>{{ item.location }}</td>
        <td>{{ item.createdAt }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
defineProps({
  data: { type: Array, default: () => [] },
});
</script>

<style scoped>
.asset-table { width: 100%; border-collapse: collapse; }
.asset-table th, .asset-table td { border: 1px solid #f0f0f0; padding: 8px; }
</style>
