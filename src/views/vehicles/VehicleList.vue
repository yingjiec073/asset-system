<template>
  <el-alert title="车辆管理（继承统一资产体系）" type="info" :closable="false" style="margin-bottom: 12px" />
  <asset-list-wrapper />
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import AssetListWrapper from '../assets/AssetList.vue'

const router = useRouter()

onMounted(() => {
  router.replace({ path: '/vehicles', query: { category: 'VEHICLE' } })
})
</script>
