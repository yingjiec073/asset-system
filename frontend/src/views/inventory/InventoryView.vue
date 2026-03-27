<script setup>
import { onMounted, ref } from 'vue'
import http from '../../api/http'

const tasks = ref([])
const form = ref({ name: '', departmentId: '' })

async function load() { tasks.value = (await http.get('/inventory/tasks')).data }
async function createTask() { await http.post('/inventory/tasks', form.value); alert('创建成功'); load() }
onMounted(load)
</script>

<template>
  <div class="card">
    <h2>盘点管理</h2>
    <div class="toolbar"><input v-model="form.name" placeholder="任务名称" /><input v-model="form.departmentId" placeholder="部门ID" /><button @click="createTask">创建任务</button></div>
    <pre>{{ tasks }}</pre>
  </div>
</template>
