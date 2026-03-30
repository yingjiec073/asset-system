import { reactive } from 'vue';

export const authStore = reactive({
  userId: Number(localStorage.getItem('userId') || 0),
  accessToken: localStorage.getItem('accessToken') || '',
  refreshToken: localStorage.getItem('refreshToken') || '',
  permissions: JSON.parse(localStorage.getItem('permissions') || '[]'),
  setTokens({ userId, accessToken, refreshToken }) {
    this.userId = userId;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    localStorage.setItem('userId', String(userId));
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
  },
  setPermissions(permissions) {
    this.permissions = permissions;
    localStorage.setItem('permissions', JSON.stringify(permissions));
  },
});
