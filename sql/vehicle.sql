CREATE DATABASE IF NOT EXISTS asset_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE asset_system;

CREATE TABLE IF NOT EXISTS asset (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(30) NOT NULL COMMENT 'VEHICLE/COMPUTER/DEVICE/FURNITURE',
    brand VARCHAR(50) NULL,
    model VARCHAR(50) NULL,
    serial_number VARCHAR(80) NOT NULL,
    purchase_date DATE NULL,
    purchase_price DECIMAL(12,2) NULL,
    status VARCHAR(20) NOT NULL DEFAULT '在库' COMMENT '在库/在用/维修/报废',
    department_id BIGINT NOT NULL,
    location VARCHAR(100) NULL,
    ext_fields JSON NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_asset_sn (serial_number),
    KEY idx_asset_category (category),
    KEY idx_asset_status (status),
    KEY idx_asset_department (department_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产主表';

CREATE TABLE IF NOT EXISTS vehicle (
    id BIGINT PRIMARY KEY,
    asset_id BIGINT NULL,
    plate_number VARCHAR(20) NOT NULL,
    vin VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    purchase_date DATE NOT NULL,
    purchase_price DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    department_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_plate_number (plate_number),
    UNIQUE KEY uk_vin (vin),
    KEY idx_vehicle_department_id (department_id),
    CONSTRAINT fk_vehicle_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆扩展表';

CREATE TABLE IF NOT EXISTS asset_category (
    id BIGINT PRIMARY KEY,
    code VARCHAR(30) NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
    UNIQUE KEY uk_asset_category_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产分类';

CREATE TABLE IF NOT EXISTS asset_operation (
    id BIGINT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL COMMENT '领用/归还/维修/调拨/报废/盘点',
    operator VARCHAR(50) NOT NULL,
    target_department VARCHAR(50) NULL,
    time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(255) NULL,
    KEY idx_operation_asset_id (asset_id),
    KEY idx_operation_type (type),
    CONSTRAINT fk_operation_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产操作日志';

CREATE TABLE IF NOT EXISTS maintenance_record (
    id BIGINT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL COMMENT '待维修/维修中/完成',
    description VARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finished_at DATETIME NULL,
    KEY idx_maintenance_asset_id (asset_id),
    CONSTRAINT fk_maintenance_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修记录';

CREATE TABLE IF NOT EXISTS scrap_record (
    id BIGINT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    operator VARCHAR(50) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_scrap_asset_id (asset_id),
    CONSTRAINT fk_scrap_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报废记录';

CREATE TABLE IF NOT EXISTS inventory_task (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL COMMENT '进行中/已完成',
    department_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='盘点任务';

CREATE TABLE IF NOT EXISTS inventory_record (
    id BIGINT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    asset_id BIGINT NOT NULL,
    result VARCHAR(20) NOT NULL COMMENT '正常/丢失/异常',
    remark VARCHAR(255) NULL,
    KEY idx_inventory_task_id (task_id),
    KEY idx_inventory_asset_id (asset_id),
    CONSTRAINT fk_inventory_task FOREIGN KEY (task_id) REFERENCES inventory_task(id),
    CONSTRAINT fk_inventory_asset FOREIGN KEY (asset_id) REFERENCES asset(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='盘点结果';

INSERT INTO asset_category (id, code, name, description) VALUES
(101, 'VEHICLE', '车辆', '车辆资产'),
(102, 'COMPUTER', '电脑', '办公电脑资产'),
(103, 'DEVICE', '设备', '生产设备资产'),
(104, 'FURNITURE', '家具', '办公家具资产')
ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description);
