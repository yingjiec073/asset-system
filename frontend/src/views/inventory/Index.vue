<template>
  <section>
    <h2>盘点管理</h2>
    <button @click="createTask">创建盘点任务</button>
    <ul>
      <li v-for="task in tasks" :key="task.id">{{ task.name }} - {{ task.status }}</li>
    </ul>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { createInventoryTask } from '@/api/operation';
import request from '@/api/request';

const tasks = ref([]);

const load = async () => {
  const res = await request.get('/api/inventory/tasks');
  tasks.value = res.data || [];
};

const createTask = async () => {
  await createInventoryTask({ name: `盘点任务-${Date.now()}`, remark: '自动创建', status: 'OPEN' });
  await load();
};

onMounted(load);
</script>
