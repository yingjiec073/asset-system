import axios from 'axios';

const request = axios.create({
  baseURL: '',
  timeout: 10000,
});

request.interceptors.response.use(
  (response) => response.data,
  (error) => {
    const message = error?.response?.data?.message || '请求失败';
    return Promise.reject(new Error(message));
  },
);

export default request;
