<template>
  <el-card>
    <template #header>
      <div class="toolbar">
        <div class="toolbar-left">
          <el-select v-model="query.category" clearable placeholder="按类型筛选" style="width: 140px" @change="handleSearch">
            <el-option label="车辆" value="VEHICLE" />
            <el-option label="电脑" value="COMPUTER" />
            <el-option label="设备" value="DEVICE" />
            <el-option label="家具" value="FURNITURE" />
          </el-select>
          <el-input v-model="query.keyword" placeholder="搜索名称/编号/品牌" clearable style="width:260px" @keyup.enter="handleSearch" @clear="handleSearch" />
        </div>
        <el-button type="primary" @click="openCreate">新增资产</el-button>
      </div>
    </template>

    <asset-table :data="list" :loading="loading" @edit="openEdit" @delete="handleDelete" />

    <div class="pager">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next"
        :total="total"
        v-model:current-page="query.page"
        v-model:page-size="query.size"
        :page-sizes="[10, 20, 50]"
        @current-change="fetchList"
        @size-change="fetchList"
      />
    </div>
  </el-card>

  <asset-form-dialog v-model:visible="dialogVisible" :is-edit="isEdit" :initial-data="currentData" @submit="submitForm" />
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { createAsset, deleteAsset, getAssets, updateAsset } from '../../api/asset'
import AssetFormDialog from '../../components/AssetFormDialog.vue'
import AssetTable from '../../components/AssetTable.vue'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentData = ref({})
const route = useRoute()

const query = reactive({
  category: route.query.category || '',
  keyword: '',
  page: 1,
  size: 10
})

const normalize = (data) => {
  if (Array.isArray(data)) return { records: data, total: data.length }
  if (Array.isArray(data?.records)) return { records: data.records, total: data.total ?? data.records.length }
  if (Array.isArray(data?.content)) return { records: data.content, total: data.totalElements ?? data.content.length }
  return { records: [], total: 0 }
}

const fetchList = async () => {
  loading.value = true
  try {
    const { data } = await getAssets(query)
    const res = normalize(data)
    list.value = res.records
    total.value = res.total
  } catch {
    ElMessage.error('加载资产列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  query.page = 1
  fetchList()
}

const openCreate = () => {
  isEdit.value = false
  currentData.value = {}
  dialogVisible.value = true
}

const openEdit = (row) => {
  isEdit.value = true
  currentData.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async (payload) => {
  try {
    if (isEdit.value) {
      await updateAsset(currentData.value.id, payload)
      ElMessage.success('资产更新成功')
    } else {
      await createAsset(payload)
      ElMessage.success('资产新增成功')
    }
    dialogVisible.value = false
    fetchList()
  } catch {
    ElMessage.error('保存失败')
  }
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确认删除资产【${row.name}】吗？`, '删除确认', { type: 'warning' })
    await deleteAsset(row.id)
    ElMessage.success('删除成功')
    fetchList()
  } catch (error) {
    if (error !== 'cancel' && error !== 'close') ElMessage.error('删除失败')
  }
}

fetchList()

watch(
  () => route.query.category,
  (category) => {
    query.category = category || ''
    handleSearch()
  }
)
</script>

<style scoped>
.toolbar { display:flex; justify-content:space-between; align-items:center; gap:12px; }
.toolbar-left { display:flex; align-items:center; gap:10px; }
.pager { margin-top:16px; display:flex; justify-content:flex-end; }
</style>
