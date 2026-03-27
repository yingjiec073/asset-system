package com.example.assetsystem.dto.response;

import java.util.Map;

public class DashboardStatsResponse {
    private long totalAssets;
    private Map<String, Long> categoryStats;
    private Map<String, Long> statusStats;
    private Map<Long, Long> departmentStats;

    public long getTotalAssets() { return totalAssets; }
    public void setTotalAssets(long totalAssets) { this.totalAssets = totalAssets; }
    public Map<String, Long> getCategoryStats() { return categoryStats; }
    public void setCategoryStats(Map<String, Long> categoryStats) { this.categoryStats = categoryStats; }
    public Map<String, Long> getStatusStats() { return statusStats; }
    public void setStatusStats(Map<String, Long> statusStats) { this.statusStats = statusStats; }
    public Map<Long, Long> getDepartmentStats() { return departmentStats; }
    public void setDepartmentStats(Map<Long, Long> departmentStats) { this.departmentStats = departmentStats; }
}
