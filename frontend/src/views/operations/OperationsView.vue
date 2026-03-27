<script setup>
import { ref } from 'vue'
import { checkout, giveBack, transfer } from '../../api/operation'

const checkoutForm = ref({ assetId: '', user: '', purpose: '' })
const returnForm = ref({ assetId: '', operator: '', remark: '' })
const transferForm = ref({ assetId: '', targetDepartmentId: '', operator: '', remark: '' })

async function submitCheckout() { await checkout(checkoutForm.value); alert('领用成功') }
async function submitReturn() { await giveBack(returnForm.value.assetId, { operator: returnForm.value.operator, remark: returnForm.value.remark }); alert('归还成功') }
async function submitTransfer() { await transfer(transferForm.value); alert('调拨成功') }
</script>

<template>
  <div class="card">
    <h2>资产操作</h2>
    <h4>领用</h4>
    <div class="toolbar"><input v-model="checkoutForm.assetId" placeholder="assetId" /><input v-model="checkoutForm.user" placeholder="user" /><input v-model="checkoutForm.purpose" placeholder="purpose" /><button @click="submitCheckout">提交</button></div>
    <h4>归还</h4>
    <div class="toolbar"><input v-model="returnForm.assetId" placeholder="assetId" /><input v-model="returnForm.operator" placeholder="operator" /><input v-model="returnForm.remark" placeholder="remark" /><button @click="submitReturn">提交</button></div>
    <h4>调拨</h4>
    <div class="toolbar"><input v-model="transferForm.assetId" placeholder="assetId" /><input v-model="transferForm.targetDepartmentId" placeholder="targetDept" /><input v-model="transferForm.operator" placeholder="operator" /><input v-model="transferForm.remark" placeholder="remark" /><button @click="submitTransfer">提交</button></div>
  </div>
</template>
