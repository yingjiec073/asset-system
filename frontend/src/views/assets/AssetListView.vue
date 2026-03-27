<script setup>
import { onMounted, ref } from 'vue'
import { createAsset, deleteAsset, getAssets, updateAsset } from '../../api/asset'
import UnifiedTable from '../../components/UnifiedTable.vue'
import StatusTag from '../../components/StatusTag.vue'
import AssetFormModal from '../../components/AssetFormModal.vue'

const records = ref([])
const total = ref(0)
const loading = ref(false)
const query = ref({ page: 1, size: 10, category: '', keyword: '' })
const showModal = ref(false)
const editing = ref(null)
const columns = [
  { key: 'name', title: '名称' }, { key: 'category', title: '类型' }, { key: 'brand', title: '品牌' },
  { key: 'model', title: '型号' }, { key: 'serialNumber', title: '编号' }, { key: 'status', title: '状态' },
  { key: 'departmentId', title: '部门' }, { key: 'location', title: '位置' }, { key: 'createdAt', title: '创建时间' },
  { key: 'action', title: '操作' }
]

async function load() {
  loading.value = true
  try {
    const res = await getAssets(query.value)
    records.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

async function submit(form) {
  if (editing.value?.id) await updateAsset(editing.value.id, form)
  else await createAsset(form)
  showModal.value = false
  editing.value = null
  alert('保存成功')
  load()
}

async function remove(id) {
  if (!confirm('确认删除该资产吗？')) return
  await deleteAsset(id)
  alert('删除成功')
  load()
}

onMounted(load)
</script>

<template>
  <div class="card">
    <h2>统一资产列表</h2>
    <div class="toolbar">
      <select v-model="query.category" @change="load">
        <option value="">全部类型</option>
        <option value="VEHICLE">车辆</option>
        <option value="COMPUTER">电脑</option>
        <option value="DEVICE">设备</option>
        <option value="FURNITURE">家具</option>
      </select>
      <input v-model="query.keyword" placeholder="搜索名称/编号/品牌" @keyup.enter="load" />
      <button @click="load">查询</button>
      <button @click="showModal = true; editing = null">新增</button>
      <span>总数：{{ total }}</span>
    </div>
    <p v-if="loading">loading...</p>
    <UnifiedTable v-else :columns="columns" :data="records">
      <template #status="{ row }"><StatusTag :status="row.status" /></template>
      <template #action="{ row }">
        <button @click="editing = row; showModal = true">编辑</button>
        <button @click="remove(row.id)">删除</button>
      </template>
    </UnifiedTable>
  </div>
  <AssetFormModal v-model="showModal" :form-data="editing" @submit="submit" />
</template>
