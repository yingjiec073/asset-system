import http from './http'

export const getAssets = (params) => http.get('/assets', { params })
export const createAsset = (data) => http.post('/assets', data)
export const updateAsset = (id, data) => http.put(`/assets/${id}`, data)
export const deleteAsset = (id) => http.delete(`/assets/${id}`)
export const getDashboard = () => http.get('/assets/dashboard')
export const getStats = () => http.get('/assets/statistics')
