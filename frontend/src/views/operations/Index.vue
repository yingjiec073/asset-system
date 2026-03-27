<template>
  <section>
    <h2>资产操作流</h2>
    <button @click="open('CHECKOUT')">领用</button>
    <button @click="open('RETURN')">归还</button>
    <button @click="open('TRANSFER')">调拨</button>
    <button @click="open('MAINTENANCE')">维修</button>
    <button @click="open('SCRAP')">报废</button>
    <AssetFormDialog :visible="dialogVisible" :title="`执行${form.type}`" @cancel="dialogVisible=false" @confirm="submit">
      <input v-model="form.assetId" placeholder="资产ID" />
      <input v-model="form.operator" placeholder="操作人" />
      <input v-model="form.remark" placeholder="备注" />
    </AssetFormDialog>
  </section>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { submitOperation } from '@/api/operation';
import AssetFormDialog from '@/components/AssetFormDialog.vue';

const dialogVisible = ref(false);
const form = reactive({ type: 'CHECKOUT', assetId: '', operator: '', remark: '' });

const open = (type) => {
  form.type = type;
  dialogVisible.value = true;
};

const submit = async () => {
  await submitOperation({ ...form, assetId: Number(form.assetId) });
  dialogVisible.value = false;
  alert('操作成功');
};
</script>
