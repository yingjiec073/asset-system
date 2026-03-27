<template>
  <el-dialog :model-value="visible" :title="isEdit ? '编辑资产' : '新增资产'" width="760px" @close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="110px">
      <el-row :gutter="16">
        <el-col :span="12"><el-form-item label="资产名称" prop="name"><el-input v-model="form.name" /></el-form-item></el-col>
        <el-col :span="12">
          <el-form-item label="资产类型" prop="category">
            <el-select v-model="form.category" style="width:100%">
              <el-option label="车辆" value="VEHICLE" />
              <el-option label="电脑" value="COMPUTER" />
              <el-option label="设备" value="DEVICE" />
              <el-option label="家具" value="FURNITURE" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12"><el-form-item label="品牌" prop="brand"><el-input v-model="form.brand" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="型号" prop="model"><el-input v-model="form.model" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="编号" prop="serialNumber"><el-input v-model="form.serialNumber" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="部门ID" prop="departmentId"><el-input-number v-model="form.departmentId" :min="1" style="width:100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="存放位置" prop="location"><el-input v-model="form.location" /></el-form-item></el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-select v-model="form.status" style="width:100%">
              <el-option label="在库" value="IN_STOCK" />
              <el-option label="在用" value="IN_USE" />
              <el-option label="维修中" value="MAINTENANCE" />
              <el-option label="已报废" value="SCRAPPED" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12"><el-form-item label="采购日期" prop="purchaseDate"><el-date-picker v-model="form.purchaseDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="采购价格" prop="purchasePrice"><el-input-number v-model="form.purchasePrice" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>

        <template v-if="form.category === 'VEHICLE'">
          <el-col :span="12"><el-form-item label="车牌号" prop="plateNumber"><el-input v-model="form.ext.plateNumber" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="VIN" prop="vin"><el-input v-model="form.ext.vin" /></el-form-item></el-col>
        </template>

        <template v-if="form.category === 'COMPUTER'">
          <el-col :span="12"><el-form-item label="CPU"><el-input v-model="form.ext.cpu" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="内存"><el-input v-model="form.ext.memory" /></el-form-item></el-col>
        </template>
      </el-row>
    </el-form>

    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, ref, watch } from 'vue'

const props = defineProps({
  visible: Boolean,
  isEdit: Boolean,
  initialData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:visible', 'submit'])
const formRef = ref(null)

const createDefault = () => ({
  name: '',
  category: 'VEHICLE',
  brand: '',
  model: '',
  serialNumber: '',
  purchaseDate: '',
  purchasePrice: 0,
  status: 'IN_STOCK',
  departmentId: 1,
  location: '',
  ext: {}
})

const form = reactive(createDefault())

const rules = {
  name: [{ required: true, message: '请输入资产名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择资产类型', trigger: 'change' }],
  serialNumber: [{ required: true, message: '请输入资产编号', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  departmentId: [{ required: true, message: '请输入部门ID', trigger: 'change' }]
}

watch(
  () => props.initialData,
  (val) => {
    Object.assign(form, createDefault(), val || {})
    form.ext = { ...(val?.ext || {}) }
  },
  { immediate: true, deep: true }
)

const handleClose = () => emit('update:visible', false)

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate((valid) => {
    if (!valid) return
    emit('submit', JSON.parse(JSON.stringify(form)))
  })
}
</script>
