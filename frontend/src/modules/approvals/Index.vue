<template>
  <section>
    <h2>Approvals Module</h2>
    <button @click="quickSubmit">Submit Borrow Request</button>
    <table border="1" cellpadding="8" style="margin-top:12px;">
      <thead><tr><th>ID</th><th>Type</th><th>Status</th><th>Step</th><th>Action</th></tr></thead>
      <tbody>
        <tr v-for="it in approvals" :key="it.id">
          <td>{{ it.id }}</td><td>{{ it.businessType }}</td><td>{{ it.status }}</td><td>{{ it.currentStepOrder }}</td>
          <td>
            <PermissionGate permission="approval:act"><button @click="act(it.id,true)">Approve</button></PermissionGate>
            <PermissionGate permission="approval:act"><button @click="act(it.id,false)">Reject</button></PermissionGate>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import PermissionGate from '../../components/common/PermissionGate.vue';
import { actApproval, listApprovals, submitApproval } from './api';

const approvals = ref([]);
const load = async () => { approvals.value = (await listApprovals()).data || []; };
const quickSubmit = async () => {
  await submitApproval({ workflowCode: 'ASSET_BORROW', businessId: Date.now(), businessType: 'ASSET_BORROW', reason: 'Need laptop' });
  await load();
};
const act = async (id, approve) => { await actApproval(id, { approve, comment: approve ? 'ok' : 'reject' }); await load(); };
onMounted(load);
</script>
