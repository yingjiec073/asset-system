import request from '../../api/request';

export const listAssets = (params) => request.get('/api/v1/assets', { params });
export const importAssetsExcel = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return request.post('/api/v1/assets/excel/import', formData);
};
export const exportAssetsExcel = () => request.get('/api/v1/assets/excel/export', { responseType: 'blob' });
