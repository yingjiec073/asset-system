import http from './http'

export const getOperations = (params) => http.get('/asset-operations', { params })
export const checkout = (data) => http.post('/asset-operations/checkout', data)
export const giveBack = (assetId, data) => http.post(`/asset-operations/return/${assetId}`, data)
export const transfer = (data) => http.post('/asset-operations/transfer', data)
export const createMaintenance = (data) => http.post('/maintenance', data)
export const scrap = (data) => http.post('/scrap', data)
