import axios from 'axios'

const vehicleApi = axios.create({
  baseURL: 'http://localhost:8080/api/vehicles',
  timeout: 10000
})

export function getVehicles(params = {}) {
  return vehicleApi.get('', { params })
}

export function createVehicle(data) {
  return vehicleApi.post('', data)
}

export function updateVehicle(id, data) {
  return vehicleApi.put(`/${id}`, data)
}

export function deleteVehicle(id) {
  return vehicleApi.delete(`/${id}`)
}
