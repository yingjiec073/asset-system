<template>
  <el-row :gutter="16">
    <el-col :span="12">
      <el-card>
        <template #header>资产领用 / 归还 / 调拨 / 报废</template>
        <el-form :model="operationForm" label-width="90px">
          <el-form-item label="资产ID"><el-input-number v-model="operationForm.assetId" :min="1" style="width:100%" /></el-form-item>
          <el-form-item label="操作人"><el-input v-model="operationForm.user" /></el-form-item>
          <el-form-item label="用途"><el-input v-model="operationForm.purpose" /></el-form-item>
          <el-form-item label="目标部门"><el-input-number v-model="operationForm.targetDepartmentId" :min="1" style="width:100%" /></el-form-item>
          <el-form-item>
            <el-space>
              <el-button type="primary" @click="submitReceive">领用</el-button>
              <el-button @click="submitReturn">归还</el-button>
              <el-button type="warning" @click="submitTransfer">调拨</el-button>
              <el-button type="danger" @click="submitScrap">报废</el-button>
            </el-space>
          </el-form-item>
        </el-form>
      </el-card>

      <el-card style="margin-top:16px">
        <template #header>维修管理</template>
        <el-form :model="maintenanceForm" label-width="90px">
          <el-form-item label="资产ID"><el-input-number v-model="maintenanceForm.assetId" :min="1" style="width:100%" /></el-form-item>
          <el-form-item label="操作人"><el-input v-model="maintenanceForm.operator" /></el-form-item>
          <el-form-item label="备注"><el-input type="textarea" v-model="maintenanceForm.remark" /></el-form-item>
          <el-form-item>
            <el-button type="warning" @click="submitMaintenance">提交维修</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>

    <el-col :span="12">
      <el-card>
        <template #header>操作日志</template>
        <el-table :data="logs" border stripe v-loading="loading">
          <el-table-column prop="assetId" label="资产ID" width="90" />
          <el-table-column prop="type" label="操作类型" width="100" />
          <el-table-column prop="operator" label="操作人" width="100" />
          <el-table-column prop="time" label="时间" min-width="160" />
          <el-table-column prop="remark" label="备注" min-width="160" />
        </el-table>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {
  createMaintenanceOrder,
  getOperationLogs,
  receiveAsset,
  returnAsset,
  scrapAsset,
  transferAsset
} from '../../api/operation'

const loading = ref(false)
const logs = ref([])

const operationForm = reactive({
  assetId: 1,
  user: '',
  purpose: '',
  targetDepartmentId: 1,
  time: ''
})

const maintenanceForm = reactive({ assetId: 1, operator: '', remark: '' })

const fetchLogs = async () => {
  loading.value = true
  try {
    const { data } = await getOperationLogs()
    logs.value = Array.isArray(data) ? data : data?.records || []
  } finally {
    loading.value = false
  }
}

const actionWrapper = async (request, message) => {
  try {
    await request()
    ElMessage.success(message)
    fetchLogs()
  } catch {
    ElMessage.error(`${message}失败`)
  }
}

const submitReceive = () => actionWrapper(() => receiveAsset(operationForm), '领用成功')
const submitReturn = () => actionWrapper(() => returnAsset(operationForm), '归还成功')
const submitTransfer = () => actionWrapper(() => transferAsset(operationForm), '调拨成功')
const submitScrap = () => actionWrapper(() => scrapAsset(operationForm), '报废成功')
const submitMaintenance = () => actionWrapper(() => createMaintenanceOrder(maintenanceForm), '维修工单创建成功')

fetchLogs()
</script>
