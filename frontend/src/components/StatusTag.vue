<script setup>
const props = defineProps({ status: String })
const colorMap = { 在库: '#67c23a', 在用: '#409eff', 维修: '#e6a23c', 报废: '#f56c6c', 异常: '#f56c6c', 丢失: '#909399' }
</script>

<template>
  <span class="status-tag" :style="{ background: colorMap[props.status] || '#909399' }">{{ props.status }}</span>
</template>
