import http from './http'

export const getVehicles = () => http.get('/vehicles')
export const createVehicle = (data) => http.post('/vehicles', data)
