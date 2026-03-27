import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/dashboard' },
  { path: '/dashboard', name: 'Dashboard', component: () => import('../views/dashboard/Dashboard.vue') },
  { path: '/assets/list', name: 'AssetList', component: () => import('../views/assets/AssetList.vue') },
  { path: '/assets/categories', name: 'CategoryManage', component: () => import('../views/assets/CategoryManage.vue') },
  { path: '/vehicles', name: 'VehicleList', component: () => import('../views/vehicles/VehicleList.vue') },
  { path: '/computers', name: 'ComputerManage', component: () => import('../views/assets/ComputerManage.vue') },
  { path: '/devices', name: 'DeviceManage', component: () => import('../views/assets/DeviceManage.vue') },
  { path: '/furnitures', name: 'FurnitureManage', component: () => import('../views/assets/FurnitureManage.vue') },
  { path: '/operations', name: 'OperationManage', component: () => import('../views/operations/OperationManage.vue') },
  { path: '/inventory', name: 'InventoryManage', component: () => import('../views/inventory/InventoryManage.vue') },
  { path: '/analytics', name: 'Statistics', component: () => import('../views/analytics/Statistics.vue') }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
