package com.example.WebserviceProject.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer roleId;

    // Behörighet för användaren
    private String authority;

    // StandardConstructor
    public Role(){
        super();
    }

    // Constructor med role-ID och behörighet som parameter
    public Role(String authority){
        this.authority = authority;
    }
    public Role(Integer roleId, String authority){
        this.roleId = roleId;
        this.authority = authority;
    }

    // Metod från GrantedAuthority-gränssnittet som returnerar behörigheten för rollen
    @Override
    public String getAuthority() {
        return this.authority;
    }

    // Getter & Setter för behörighet samt role-ID
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