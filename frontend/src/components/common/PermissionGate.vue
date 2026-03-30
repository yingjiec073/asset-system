<template><slot v-if="has"></slot></template>
<script setup>
import { computed } from 'vue';
import { authStore } from '../../store/auth';
const props = defineProps({ permission: { type: String, required: true } });
const has = computed(() => authStore.permissions.includes(props.permission));
</script>
