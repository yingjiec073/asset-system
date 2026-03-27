import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  { path: '/', redirect: '/dashboard' },
  { path: '/dashboard', component: () => import('./views/dashboard/Index.vue') },
  { path: '/assets', component: () => import('./views/assets/Index.vue') },
  { path: '/vehicles', component: () => import('./views/vehicles/Index.vue') },
  { path: '/operations', component: () => import('./views/operations/Index.vue') },
  { path: '/inventory', component: () => import('./views/inventory/Index.vue') },
  { path: '/computers', component: () => import('./views/assets/Index.vue') },
  { path: '/devices', component: () => import('./views/assets/Index.vue') },
  { path: '/furniture', component: () => import('./views/assets/Index.vue') },
];

export default createRouter({
  history: createWebHistory(),
  routes,
});
