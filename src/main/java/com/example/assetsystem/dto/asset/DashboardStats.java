package com.example.assetsystem.dto.asset;

import java.util.Map;

public class DashboardStats {
    private long totalAssets;
    private Map<String, Long> categoryDistribution;
    private Map<String, Long> statusDistribution;
    private Map<Long, Long> departmentDistribution;

    public long getTotalAssets() { return totalAssets; }
    public void setTotalAssets(long totalAssets) { this.totalAssets = totalAssets; }
    public Map<String, Long> getCategoryDistribution() { return categoryDistribution; }
    public void setCategoryDistribution(Map<String, Long> categoryDistribution) { this.categoryDistribution = categoryDistribution; }
    public Map<String, Long> getStatusDistribution() { return statusDistribution; }
    public void setStatusDistribution(Map<String, Long> statusDistribution) { this.statusDistribution = statusDistribution; }
    public Map<Long, Long> getDepartmentDistribution() { return departmentDistribution; }
    public void setDepartmentDistribution(Map<Long, Long> departmentDistribution) { this.departmentDistribution = departmentDistribution; }
}
