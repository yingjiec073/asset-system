<script setup>
defineProps({ columns: Array, data: Array })
</script>

<template>
  <table class="table">
    <thead>
      <tr>
        <th v-for="col in columns" :key="col.key">{{ col.title }}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="row in data" :key="row.id">
        <td v-for="col in columns" :key="col.key">
          <slot :name="col.key" :row="row">{{ row[col.key] }}</slot>
        </td>
      </tr>
    </tbody>
  </table>
</template>
