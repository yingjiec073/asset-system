import { createRouter, createWebHistory } from 'vue-router'
import DashboardView from '../views/dashboard/DashboardView.vue'
import AssetListView from '../views/assets/AssetListView.vue'
import VehicleView from '../views/vehicles/VehicleView.vue'
import OperationsView from '../views/operations/OperationsView.vue'
import InventoryView from '../views/inventory/InventoryView.vue'
import StatisticsView from '../views/statistics/StatisticsView.vue'

const Placeholder = { template: '<div class="card">模块预留中</div>' }

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/dashboard' },
    { path: '/dashboard', component: DashboardView },
    { path: '/assets', component: AssetListView },
    { path: '/vehicles', component: VehicleView },
    { path: '/computers', component: Placeholder },
    { path: '/devices', component: Placeholder },
    { path: '/furniture', component: Placeholder },
    { path: '/operations', component: OperationsView },
    { path: '/inventory', component: InventoryView },
    { path: '/statistics', component: StatisticsView }
  ]
})
