package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    @Query(value="SELECT admin FROM Admin admin")
    public List<Admin> findAllAdmins();
    @Query(value="SELECT admin FROM Admin admin WHERE admin.id=:id")
    public Admin findAdminById(@Param("id") Integer id);
    @Query(value="SELECT admin FROM Admin admin WHERE admin.username=:username")
    public Admin findByAdminname(@Param("username") String username);
}