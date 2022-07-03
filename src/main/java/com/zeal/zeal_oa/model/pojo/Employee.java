package com.zeal.zeal_oa.model.pojo;

public class Employee {
    private Long employeeId;

    private String name;

    private Long departmentId;

    private String title;

    private Integer level;

    public Employee() {
    }

    public Employee(Long employeeId, String name, Long departmentId, String title, Integer level) {
        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
        this.title = title;
        this.level = level;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", departmentId=" + departmentId +
                ", title='" + title + '\'' +
                ", level=" + level +
                '}';
    }
}