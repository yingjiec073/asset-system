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
