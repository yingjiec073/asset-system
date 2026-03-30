<template>
  <section>
    <h2>Assets Module</h2>
    <div style="display:flex;gap:8px;margin-bottom:12px;">
      <PermissionGate permission="asset:import">
        <input type="file" @change="onFileChange" accept=".xlsx,.xls" />
      </PermissionGate>
      <PermissionGate permission="asset:export">
        <button @click="downloadExcel">Export Excel</button>
      </PermissionGate>
    </div>
    <table border="1" cellpadding="8">
      <thead><tr><th>Name</th><th>Category</th><th>Status</th></tr></thead>
      <tbody>
        <tr v-for="it in records" :key="it.id"><td>{{ it.name }}</td><td>{{ it.category }}</td><td>{{ it.status }}</td></tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import PermissionGate from '../../components/common/PermissionGate.vue';
import { exportAssetsExcel, importAssetsExcel, listAssets } from './api';

const records = ref([]);
const load = async () => {
  const res = await listAssets({ pageNum: 1, pageSize: 20 });
  records.value = res.data.records || [];
};

const onFileChange = async (event) => {
  const file = event.target.files?.[0];
  if (!file) return;
  await importAssetsExcel(file);
  await load();
};

const downloadExcel = async () => {
  const blob = await exportAssetsExcel();
  const url = window.URL.createObjectURL(new Blob([blob]));
  const a = document.createElement('a');
  a.href = url;
  a.download = 'assets.xlsx';
  a.click();
};

onMounted(load);
</script>
