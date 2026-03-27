import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import VehicleList from '../views/VehicleList.vue'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { title: '仪表盘' }
  },
  {
    path: '/vehicles',
    name: 'VehicleList',
    component: VehicleList,
    meta: { title: '车辆管理' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
