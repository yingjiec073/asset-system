<script setup>
import { onMounted, ref } from 'vue'
import { getVehicles } from '../../api/vehicle'

const vehicles = ref([])
onMounted(async () => { vehicles.value = (await getVehicles()).data })
</script>

<template>
  <div class="card">
    <h2>车辆管理（扩展表）</h2>
    <pre>{{ vehicles }}</pre>
  </div>
</template>
