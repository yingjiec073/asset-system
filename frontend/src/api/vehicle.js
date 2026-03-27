import request from './request';

export const fetchVehicles = () => request.get('/api/vehicles');
export const createVehicle = (payload) => request.post('/api/vehicles', payload);
export const updateVehicle = (id, payload) => request.put(`/api/vehicles/${id}`, payload);
export const removeVehicle = (id) => request.delete(`/api/vehicles/${id}`);
