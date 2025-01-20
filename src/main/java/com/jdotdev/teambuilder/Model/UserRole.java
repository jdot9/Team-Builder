package com.jdotdev.teambuilder.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="userroles")
public class UserRole {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_role_id")
    private int userRoleId;
    
    @Column(name="role")
    private String role;

    @OneToOne(mappedBy = "userRole")
    private User user;

    public UserRole()
    {

    }

    public UserRole(int userRoleId, String role, User user)
    {
        this.userRoleId = userRoleId;
        this.role = role;
        this.user = user;
    }

    public int getUserRoleId()
    {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) 
    {
	this.userRoleId = userRoleId;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role) 
    {
	this.role = role;
    }

    public User getUser() 
    {
        return user;
    }

    public void setUser(User user) 
    {
        this.user = user;
    }

    @Override
    public String toString() 
    {
        return "UserRole [userRoleId=" + userRoleId + ", role=" + role + ", user=" + user + "]";
    }
}
