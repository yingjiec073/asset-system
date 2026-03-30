import axios from 'axios';

const service = axios.create({ baseURL: '', timeout: 15000 });

service.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem('accessToken');
  const userId = localStorage.getItem('userId');
  if (accessToken) config.headers.Authorization = `Bearer ${accessToken}`;
  if (userId) config.headers['X-User-Id'] = userId;
  return config;
});

service.interceptors.response.use(
  (response) => {
    const payload = response.data;
    if (payload && typeof payload.code !== 'undefined') {
      if (payload.code !== 0) return Promise.reject(new Error(payload.message || 'Request failed'));
      if (response.config.responseType === 'blob') return response.data;
      return payload;
    }
    return response.data;
  },
  async (error) => {
    const original = error.config || {};
    if (error.response?.status === 401 && !original._retry && localStorage.getItem('refreshToken')) {
      original._retry = true;
      const refreshRes = await axios.post('/api/v1/auth/refresh', { refreshToken: localStorage.getItem('refreshToken') });
      if (refreshRes.data?.code === 0) {
        localStorage.setItem('accessToken', refreshRes.data.data.accessToken);
        localStorage.setItem('refreshToken', refreshRes.data.data.refreshToken);
        original.headers = original.headers || {};
        original.headers.Authorization = `Bearer ${refreshRes.data.data.accessToken}`;
        return service(original);
      }
    }
    return Promise.reject(error);
  },
);

export default service;
