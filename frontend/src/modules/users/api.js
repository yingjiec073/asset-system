import request from '../../api/request';

export const loginApi = (payload) => request.post('/api/v1/auth/login', payload);
export const refreshApi = (payload) => request.post('/api/v1/auth/refresh', payload);
export const userPermissionCodes = (userId) => request.get(`/api/v1/rbac/users/${userId}/permission-codes`);
