import request from './request';

export const submitOperation = (payload) => request.post('/api/asset-operations', payload);
export const fetchOperations = (params) => request.get('/api/asset-operations', { params });
export const createMaintenance = (payload) => request.post('/api/maintenance', payload);
export const updateMaintenance = (id, payload) => request.put(`/api/maintenance/${id}`, payload);
export const createInventoryTask = (payload) => request.post('/api/inventory/tasks', payload);
export const createInventoryRecord = (payload) => request.post('/api/inventory/records', payload);
export const scrapAsset = (payload) => request.post('/api/scrap', payload);
