<template>
  <el-card>
    <template #header>盘点管理</template>
    <el-form :inline="true" :model="form">
      <el-form-item label="任务名称"><el-input v-model="form.name" placeholder="季度盘点" /></el-form-item>
      <el-form-item label="盘点部门"><el-input-number v-model="form.departmentId" :min="1" /></el-form-item>
      <el-form-item><el-button type="primary" @click="createTask">创建盘点任务</el-button></el-form-item>
    </el-form>

    <el-table :data="tasks" border stripe v-loading="loading" style="margin-top: 10px">
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="name" label="任务名称" min-width="140" />
      <el-table-column prop="departmentId" label="部门ID" width="100" />
      <el-table-column prop="diffStatus" label="差异状态" min-width="130">
        <template #default="{ row }">
          <el-tag :type="row.diffStatus === 'NORMAL' ? 'success' : 'danger'">{{ row.diffStatus || 'NORMAL' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button link type="warning" @click="markDiff(row, 'LOST')">标记丢失</el-button>
          <el-button link type="danger" @click="markDiff(row, 'ABNORMAL')">标记异常</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createInventoryTask, getInventoryTasks, updateInventoryResult } from '../../api/operation'

const loading = ref(false)
const tasks = ref([])
const form = reactive({ name: '', departmentId: 1 })

const fetchTasks = async () => {
  loading.value = true
  try {
    const { data } = await getInventoryTasks()
    tasks.value = Array.isArray(data) ? data : data?.records || []
  } finally {
    loading.value = false
  }
}

const createTask = async () => {
  try {
    await createInventoryTask(form)
    ElMessage.success('盘点任务创建成功')
    fetchTasks()
  } catch {
    ElMessage.error('盘点任务创建失败')
  }
}

const markDiff = async (row, diffStatus) => {
  try {
    await updateInventoryResult(row.id, { ...row, diffStatus })
    ElMessage.success('盘点结果更新成功')
    fetchTasks()
  } catch {
    ElMessage.error('盘点结果更新失败')
  }
}

fetchTasks()
</script>
