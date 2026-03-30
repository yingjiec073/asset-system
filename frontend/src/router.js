import { createRouter, createWebHistory } from 'vue-router';
import { authStore } from './store/auth';

const routes = [
  { path: '/', redirect: '/modules/assets' },
  { path: '/modules/assets', component: () => import('./modules/assets/Index.vue'), meta: { permission: 'asset:export' } },
  { path: '/modules/users', component: () => import('./views/vehicles/Index.vue') },
  { path: '/modules/approvals', component: () => import('./modules/approvals/Index.vue'), meta: { permission: 'approval:view' } },
  { path: '/dashboard', component: () => import('./views/dashboard/Index.vue') },
];

const router = createRouter({ history: createWebHistory(), routes });
router.beforeEach((to) => {
  if (to.meta?.permission && !authStore.permissions.includes(to.meta.permission)) return '/dashboard';
  return true;
});

export default router;
