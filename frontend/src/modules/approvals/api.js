import request from '../../api/request';

export const listApprovals = () => request.get('/api/v1/approvals');
export const submitApproval = (payload) => request.post('/api/v1/approvals', payload);
export const actApproval = (id, payload) => request.post(`/api/v1/approvals/${id}/actions`, payload);
