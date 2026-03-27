import http from './http'

const BASE = '/api/vehicles'

export function getVehicles(params = {}) {
  return http.get(BASE, { params })
}

export function createVehicle(data) {
  return http.post(BASE, data)
}

export function updateVehicle(id, data) {
  return http.put(`${BASE}/${id}`, data)
}

export function deleteVehicle(id) {
  return http.delete(`${BASE}/${id}`)
}
