<template>
  <section>
    <h2>统一资产列表</h2>
    <div class="filters">
      <select v-model="query.category">
        <option value="">全部</option>
        <option value="VEHICLE">车辆</option>
        <option value="COMPUTER">电脑</option>
        <option value="DEVICE">设备</option>
        <option value="FURNITURE">家具</option>
      </select>
      <input v-model="query.keyword" placeholder="名称 / 编号 / 品牌" />
      <button @click="loadData">搜索</button>
    </div>
    <AssetTable :data="list">
      <template #status="{ item }">
        <StatusTag :value="item.status" />
      </template>
    </AssetTable>
  </section>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import { fetchAssets } from '@/api/asset';
import AssetTable from '@/components/AssetTable.vue';
import StatusTag from '@/components/StatusTag.vue';

const query = reactive({ category: '', keyword: '', pageNum: 1, pageSize: 10 });
const list = ref([]);

const loadData = async () => {
  const res = await fetchAssets(query);
  list.value = res.data?.records || [];
};

onMounted(loadData);
</script>
