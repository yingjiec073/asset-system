USE asset_system;

INSERT INTO user_account (id, username, password_hash, display_name, enabled)
VALUES (1001, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Super Admin', 1)
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

INSERT INTO role (id, code, name)
VALUES (2001, 'ADMIN', 'Administrator'),
       (2002, 'MANAGER', 'Manager'),
       (2003, 'USER', 'User')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO permission (id, code, name)
VALUES (3001, 'rbac:manage', 'Manage RBAC'),
       (3002, 'asset:create', 'Create Asset'),
       (3003, 'asset:delete', 'Delete Asset'),
       (3004, 'asset:import', 'Import Asset Excel'),
       (3005, 'asset:export', 'Export Asset Excel'),
       (3006, 'approval:submit', 'Submit Approval'),
       (3007, 'approval:act', 'Approve/Reject'),
       (3008, 'approval:view', 'View Approvals'),
       (3009, 'audit:view', 'View Audit Logs')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO workflow (id, code, name)
VALUES (4001, 'ASSET_BORROW', 'Asset Borrow Workflow'),
       (4002, 'ASSET_TRANSFER', 'Asset Transfer Workflow')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO workflow_step (id, workflow_id, step_order, approver_role_code)
VALUES (4101, 4001, 1, 'MANAGER'), (4102, 4001, 2, 'ADMIN'),
       (4201, 4002, 1, 'MANAGER'), (4202, 4002, 2, 'ADMIN')
ON DUPLICATE KEY UPDATE approver_role_code = VALUES(approver_role_code);
