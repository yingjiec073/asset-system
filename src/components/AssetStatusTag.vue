<template>
  <el-tag :type="statusType" effect="plain">{{ statusMap[status] || status || '-' }}</el-tag>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  status: {
    type: String,
    default: ''
  }
})

const statusMap = {
  IN_STOCK: '在库',
  IN_USE: '在用',
  MAINTENANCE: '维修中',
  SCRAPPED: '已报废'
}

const statusType = computed(() => {
  const typeMap = {
    IN_STOCK: 'success',
    IN_USE: '',
    MAINTENANCE: 'warning',
    SCRAPPED: 'danger'
  }
  return typeMap[props.status] || 'info'
})
</script>
