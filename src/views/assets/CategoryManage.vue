<template>
  <el-card>
    <template #header>
      <div class="toolbar">
        <span>资产分类管理</span>
        <el-button type="primary" @click="openCreate">新增分类</el-button>
      </div>
    </template>

    <el-table :data="categories" border stripe v-loading="loading">
      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="code" label="分类编码" />
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button type="primary" link @click="openEdit(row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog v-model="visible" :title="form.id ? '编辑分类' : '新增分类'" width="420px">
    <el-form :model="form" label-width="90px">
      <el-form-item label="分类名称"><el-input v-model="form.name" /></el-form-item>
      <el-form-item label="分类编码"><el-input v-model="form.code" /></el-form-item>
      <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createCategory, getCategories, updateCategory } from '../../api/asset'

const loading = ref(false)
const visible = ref(false)
const categories = ref([])
const form = reactive({ id: null, name: '', code: '', description: '' })

const resetForm = () => Object.assign(form, { id: null, name: '', code: '', description: '' })

const fetchData = async () => {
  loading.value = true
  try {
    const { data } = await getCategories()
    categories.value = Array.isArray(data) ? data : data?.records || []
  } finally {
    loading.value = false
  }
}

const openCreate = () => {
  resetForm()
  visible.value = true
}

const openEdit = (row) => {
  Object.assign(form, row)
  visible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.id) await updateCategory(form.id, form)
    else await createCategory(form)
    ElMessage.success('保存成功')
    visible.value = false
    fetchData()
  } catch {
    ElMessage.error('保存失败')
  }
}

fetchData()
</script>

<style scoped>
.toolbar { display:flex; justify-content:space-between; align-items:center; }
</style>
