<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({ modelValue: Boolean, formData: Object })
const emit = defineEmits(['update:modelValue', 'submit'])
const form = reactive({ name: '', category: 'VEHICLE', brand: '', model: '', serialNumber: '', status: '在库', departmentId: 1, location: '' })

watch(() => props.formData, (v) => Object.assign(form, v || {}), { immediate: true })

function close() { emit('update:modelValue', false) }
function onSubmit() {
  if (!form.name || !form.serialNumber) return alert('名称和编号必填')
  emit('submit', { ...form })
}
</script>

<template>
  <div v-if="modelValue" class="card">
    <h4>资产表单</h4>
    <div class="toolbar">
      <input v-model="form.name" placeholder="资产名称" />
      <select v-model="form.category">
        <option value="VEHICLE">车辆</option>
        <option value="COMPUTER">电脑</option>
        <option value="DEVICE">设备</option>
        <option value="FURNITURE">家具</option>
      </select>
      <input v-model="form.serialNumber" placeholder="资产编号" />
      <input v-model="form.brand" placeholder="品牌" />
      <input v-model="form.model" placeholder="型号" />
      <input v-model="form.location" placeholder="位置" />
    </div>
    <div class="toolbar">
      <button @click="onSubmit">保存</button>
      <button @click="close">取消</button>
    </div>
  </div>
</template>
