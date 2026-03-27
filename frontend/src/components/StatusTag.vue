<template>
  <span :class="['status-tag', value.toLowerCase()]">{{ value }}</span>
</template>

<script setup>
defineProps({
  value: { type: String, default: 'IN_STOCK' },
});
</script>

<style scoped>
.status-tag { padding: 2px 8px; border-radius: 10px; font-size: 12px; }
.in_stock { background: #e6f4ea; color: #237804; }
.in_use { background: #e6f7ff; color: #0958d9; }
.in_maintenance { background: #fff7e6; color: #d46b08; }
.scrapped { background: #fff1f0; color: #cf1322; }
</style>
