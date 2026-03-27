<template>
  <el-card shadow="never">
    <template #header>
      <div class="toolbar">
        <el-input
          v-model="searchPlate"
          placeholder="按车牌号搜索"
          clearable
          class="search-input"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #append>
            <el-button @click="handleSearch">搜索</el-button>
          </template>
        </el-input>

        <el-button type="primary" @click="openCreateDialog">新增车辆</el-button>
      </div>
    </template>

    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="plateNumber" label="车牌号" min-width="130" />
      <el-table-column prop="brand" label="品牌" min-width="120" />
      <el-table-column prop="model" label="型号" min-width="120" />
      <el-table-column prop="status" label="状态" min-width="100" />
      <el-table-column prop="departmentId" label="部门ID" min-width="100" />
      <el-table-column prop="createdAt" label="创建时间" min-width="180" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="openEditDialog(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-wrap">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next"
        :total="total"
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50]"
        @size-change="fetchVehicleList"
        @current-change="fetchVehicleList"
      />
    </div>
  </el-card>

  <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑车辆' : '新增车辆'" width="640px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="110px">
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="车牌号" prop="plateNumber">
            <el-input v-model="form.plateNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="VIN" prop="vin">
            <el-input v-model="form.vin" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="品牌" prop="brand">
            <el-input v-model="form.brand" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="型号" prop="model">
            <el-input v-model="form.model" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="购买日期" prop="purchaseDate">
            <el-date-picker
              v-model="form.purchaseDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="选择日期"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="购买价格" prop="purchasePrice">
            <el-input-number v-model="form.purchasePrice" :min="0" :precision="2" style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择" style="width: 100%">
              <el-option label="在用" value="IN_USE" />
              <el-option label="闲置" value="IDLE" />
              <el-option label="维修" value="MAINTENANCE" />
              <el-option label="报废" value="SCRAPPED" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门ID" prop="departmentId">
            <el-input-number v-model="form.departmentId" :min="1" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { createVehicle, deleteVehicle, getVehicles, updateVehicle } from '../api/vehicle'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const searchPlate = ref('')

const dialogVisible = ref(false)
const isEdit = ref(false)
const currentId = ref(null)
const formRef = ref(null)

const createDefaultForm = () => ({
  plateNumber: '',
  vin: '',
  brand: '',
  model: '',
  purchaseDate: '',
  purchasePrice: 0,
  status: '',
  departmentId: 1
})

const form = reactive(createDefaultForm())

const rules = {
  plateNumber: [{ required: true, message: '请输入车牌号', trigger: 'blur' }],
  vin: [{ required: true, message: '请输入VIN', trigger: 'blur' }],
  brand: [{ required: true, message: '请输入品牌', trigger: 'blur' }],
  model: [{ required: true, message: '请输入型号', trigger: 'blur' }],
  purchaseDate: [{ required: true, message: '请选择购买日期', trigger: 'change' }],
  purchasePrice: [{ required: true, message: '请输入购买价格', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  departmentId: [{ required: true, message: '请输入部门ID', trigger: 'blur' }]
}

const assignForm = (data) => {
  Object.assign(form, createDefaultForm(), data)
}

const normalizeListResult = (payload) => {
  if (Array.isArray(payload)) {
    return { list: payload, total: payload.length }
  }

  if (payload && Array.isArray(payload.records)) {
    return { list: payload.records, total: payload.total ?? payload.records.length }
  }

  if (payload && Array.isArray(payload.content)) {
    return { list: payload.content, total: payload.totalElements ?? payload.content.length }
  }

  return { list: [], total: 0 }
}

const fetchVehicleList = async () => {
  loading.value = true
  try {
    const { data } = await getVehicles({
      page: pageNum.value,
      size: pageSize.value,
      plateNumber: searchPlate.value || undefined
    })

    const result = normalizeListResult(data)
    tableData.value = result.list
    total.value = result.total
  } catch (error) {
    ElMessage.error(error?.response?.data?.message || '获取车辆列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pageNum.value = 1
  fetchVehicleList()
}

const openCreateDialog = () => {
  isEdit.value = false
  currentId.value = null
  assignForm(createDefaultForm())
  dialogVisible.value = true
}

const openEditDialog = (row) => {
  isEdit.value = true
  currentId.value = row.id
  assignForm(row)
  dialogVisible.value = true
}

const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    try {
      if (isEdit.value) {
        await updateVehicle(currentId.value, form)
        ElMessage.success('更新成功')
      } else {
        await createVehicle(form)
        ElMessage.success('新增成功')
      }

      dialogVisible.value = false
      fetchVehicleList()
    } catch (error) {
      ElMessage.error(error?.response?.data?.message || '操作失败')
    }
  })
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确认删除车辆 ${row.plateNumber} 吗？`, '提示', {
      type: 'warning'
    })
    await deleteVehicle(row.id)
    ElMessage.success('删除成功')
    fetchVehicleList()
  } catch (error) {
    if (error !== 'cancel' && error !== 'close') {
      ElMessage.error(error?.response?.data?.message || '删除失败')
    }
  }
}

onMounted(() => {
  fetchVehicleList()
})
</script>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
}

.search-input {
  max-width: 360px;
}

.pagination-wrap {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}
</style>
