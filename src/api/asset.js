import http from './http'

export function getAssets(params = {}) {
  return http.get('/api/assets', { params })
}

export function getAssetById(id) {
  return http.get(`/api/assets/${id}`)
}

export function createAsset(data) {
  return http.post('/api/assets', data)
}

export function updateAsset(id, data) {
  return http.put(`/api/assets/${id}`, data)
}

export function deleteAsset(id) {
  return http.delete(`/api/assets/${id}`)
}

export function getAssetStats() {
  return http.get('/api/assets/stats')
}

export function getCategories(params = {}) {
  return http.get('/api/assets/categories', { params })
}

export function createCategory(data) {
  return http.post('/api/assets/categories', data)
}

export function updateCategory(id, data) {
  return http.put(`/api/assets/categories/${id}`, data)
}
