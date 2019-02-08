package com.example.cs4500_sp19_noideainc.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.Table;


@Entity
@Table(name="admins")
public class Admin extends User {
    private Integer employeeId;
    @ElementCollection(targetClass = Permission.class)
    private List<Permission> permissions;

    public Admin() {super();}
    public Admin(Integer i, String username, String firstName, String lastName, Integer employeeId, List<Permission> permissions) {
        super(i, username, firstName, lastName);
        this.employeeId = employeeId;
        this.permissions = permissions;
    }

    public Integer getEmployeeId() {
        return this.employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    public List<Permission> getPermissions() {
        return permissions;
    }
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}