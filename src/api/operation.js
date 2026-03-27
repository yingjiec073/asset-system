import http from './http'

export function receiveAsset(data) {
  return http.post('/api/asset-operations/receive', data)
}

export function returnAsset(data) {
  return http.post('/api/asset-operations/return', data)
}

export function transferAsset(data) {
  return http.post('/api/asset-operations/transfer', data)
}

export function getOperationLogs(params = {}) {
  return http.get('/api/asset-operations', { params })
}

export function createMaintenanceOrder(data) {
  return http.post('/api/maintenance', data)
}

export function getMaintenanceList(params = {}) {
  return http.get('/api/maintenance', { params })
}

export function completeMaintenance(id, data = {}) {
  return http.put(`/api/maintenance/${id}/complete`, data)
}

export function scrapAsset(data) {
  return http.post('/api/scrap', data)
}

export function createInventoryTask(data) {
  return http.post('/api/inventory', data)
}

export function getInventoryTasks(params = {}) {
  return http.get('/api/inventory', { params })
}

export function updateInventoryResult(id, data) {
  return http.put(`/api/inventory/${id}`, data)
}
