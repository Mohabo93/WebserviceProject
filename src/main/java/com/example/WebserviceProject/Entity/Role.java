package com.example.WebserviceProject.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer roleId;

    // behörighet för rollen
    private String authority;

    public Role(){
        super();
    }

    public Role(String authority){
        this.authority = authority;
    }

    public Role(Integer roleId, String authority){
        this.roleId = roleId;
        this.authority = authority;
    }

    // Metod från GrantedAuthority för att hämta rollens behörighet
    @Override
    public String getAuthority() {
        return this.authority;
    }

    //gett- och setter
    public void setAuthority(String authority){
        this.authority = authority;
    }

    public Integer getRoleId(){
        return this.roleId;
    }

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }
}