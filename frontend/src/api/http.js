import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
  timeout: 10000
})

http.interceptors.response.use(
  (res) => res.data,
  (err) => {
    alert(err.response?.data?.message || '请求失败')
    return Promise.reject(err)
  }
)

export default http
