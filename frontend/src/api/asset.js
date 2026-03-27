import request from './request';

export const fetchAssets = (params) => request.get('/api/assets', { params });
export const fetchAssetDetail = (id) => request.get(`/api/assets/${id}`);
export const createAsset = (payload) => request.post('/api/assets', payload);
export const updateAsset = (id, payload) => request.put(`/api/assets/${id}`, payload);
export const removeAsset = (id) => request.delete(`/api/assets/${id}`);
export const fetchDashboard = () => request.get('/api/assets/dashboard');
export const fetchCategories = () => request.get('/api/assets/categories');
