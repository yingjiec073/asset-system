CREATE DATABASE IF NOT EXISTS asset_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE asset_system;

CREATE TABLE IF NOT EXISTS vehicle (
    id BIGINT PRIMARY KEY,
    plate_number VARCHAR(20) NOT NULL COMMENT '车牌号',
    vin VARCHAR(50) NOT NULL COMMENT '车架号',
    brand VARCHAR(50) NOT NULL COMMENT '品牌',
    model VARCHAR(50) NOT NULL COMMENT '型号',
    purchase_date DATE NOT NULL COMMENT '购买日期',
    purchase_price DECIMAL(12,2) NOT NULL COMMENT '购买价格',
    status VARCHAR(20) NOT NULL COMMENT '状态（在用/维修/闲置/报废）',
    department_id BIGINT NOT NULL COMMENT '部门ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_plate_number (plate_number),
    UNIQUE KEY uk_vin (vin),
    KEY idx_department_id (department_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆资产表';

CREATE TABLE IF NOT EXISTS asset (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '资产名称',
    category VARCHAR(30) NOT NULL COMMENT '分类 VEHICLE/COMPUTER/DEVICE/FURNITURE',
    brand VARCHAR(50) NULL COMMENT '品牌',
    model VARCHAR(80) NULL COMMENT '型号',
    serial_number VARCHAR(80) NOT NULL COMMENT '资产编号',
    purchase_date DATE NOT NULL COMMENT '采购日期',
    purchase_price DECIMAL(12,2) NOT NULL COMMENT '采购价格',
    status VARCHAR(30) NOT NULL COMMENT 'IN_STOCK/IN_USE/IN_MAINTENANCE/SCRAPPED',
    department_id BIGINT NOT NULL COMMENT '所属部门',
    location VARCHAR(255) NULL COMMENT '存放位置',
    extra_fields JSON NULL COMMENT '扩展字段',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_serial_number (serial_number),
    KEY idx_asset_category (category),
    KEY idx_asset_status (status),
    KEY idx_asset_department (department_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统一资产主表';

CREATE TABLE IF NOT EXISTS asset_category (
    id BIGINT PRIMARY KEY,
    code VARCHAR(30) NOT NULL,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_asset_category_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产分类配置';

CREATE TABLE IF NOT EXISTS asset_operation (
    id BIGINT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    type VARCHAR(30) NOT NULL COMMENT 'CHECKOUT/RETURN/TRANSFER/MAINTENANCE/SCRAP/INVENTORY',
    operator VARCHAR(50) NOT NULL,
    time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500) NULL,
    KEY idx_asset_operation_asset_id (asset_id),
    KEY idx_asset_operation_type (type),
    CONSTRAINT fk_asset_operation_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产操作日志';

CREATE TABLE IF NOT EXISTS maintenance_order (
    id BIGINT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL COMMENT 'PENDING/IN_PROGRESS/COMPLETED',
    issue VARCHAR(500) NULL,
    record VARCHAR(500) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_maintenance_order_asset_id (asset_id),
    CONSTRAINT fk_maintenance_order_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修工单';

CREATE TABLE IF NOT EXISTS inventory_task (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    remark VARCHAR(500) NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='盘点任务';

CREATE TABLE IF NOT EXISTS inventory_record (
    id BIGINT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    asset_id BIGINT NOT NULL,
    result VARCHAR(20) NOT NULL COMMENT 'NORMAL/LOST/ABNORMAL',
    remark VARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_inventory_record_task_id (task_id),
    KEY idx_inventory_record_asset_id (asset_id),
    CONSTRAINT fk_inventory_record_task FOREIGN KEY (task_id) REFERENCES inventory_task(id),
    CONSTRAINT fk_inventory_record_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='盘点记录';

CREATE TABLE IF NOT EXISTS user_account (
    id BIGINT PRIMARY KEY,
    username VARCHAR(60) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(100),
    enabled TINYINT NOT NULL DEFAULT 1,
    UNIQUE KEY uk_user_account_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账号';

CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY,
    code VARCHAR(80) NOT NULL,
    name VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_role_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

CREATE TABLE IF NOT EXISTS permission (
    id BIGINT PRIMARY KEY,
    code VARCHAR(80) NOT NULL,
    name VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_permission_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';

CREATE TABLE IF NOT EXISTS role_permission (
    id BIGINT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    KEY idx_rp_role_id (role_id),
    KEY idx_rp_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系';

CREATE TABLE IF NOT EXISTS user_role (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    KEY idx_ur_user_id (user_id),
    KEY idx_ur_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系';

CREATE TABLE IF NOT EXISTS workflow (
    id BIGINT PRIMARY KEY,
    code VARCHAR(80) NOT NULL,
    name VARCHAR(120) NOT NULL,
    UNIQUE KEY uk_workflow_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批流程';

CREATE TABLE IF NOT EXISTS workflow_step (
    id BIGINT PRIMARY KEY,
    workflow_id BIGINT NOT NULL,
    step_order INT NOT NULL,
    approver_role_code VARCHAR(80) NOT NULL,
    KEY idx_workflow_step_workflow (workflow_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程步骤';

CREATE TABLE IF NOT EXISTS approval (
    id BIGINT PRIMARY KEY,
    workflow_id BIGINT NOT NULL,
    business_id BIGINT NOT NULL,
    business_type VARCHAR(80) NOT NULL,
    current_step_order INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    requester_id BIGINT NOT NULL,
    reason VARCHAR(500),
    acted_by BIGINT,
    action_comment VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_approval_workflow_id (workflow_id),
    KEY idx_approval_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批实例';

CREATE TABLE IF NOT EXISTS audit_log (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(120) NOT NULL,
    details TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_audit_user_id (user_id),
    KEY idx_audit_action (action)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审计日志';
