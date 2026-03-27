<template>
  <div v-if="visible" class="dialog-mask">
    <div class="dialog-panel">
      <h3>{{ title }}</h3>
      <slot />
      <div class="actions">
        <button @click="$emit('cancel')">取消</button>
        <button @click="$emit('confirm')">确认</button>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  visible: Boolean,
  title: { type: String, default: '资产表单' },
});
</script>

<style scoped>
.dialog-mask { position: fixed; inset: 0; background: rgba(0,0,0,.35); display: flex; justify-content: center; align-items: center; }
.dialog-panel { width: 560px; background: #fff; padding: 16px; border-radius: 12px; }
.actions { display: flex; justify-content: flex-end; gap: 8px; margin-top: 12px; }
</style>
