package com.jdotdev.teambuilder.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
    private int userId;

    @Column(name="first_name")
    private String firstName; 

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="photo_key")
    private String photoKey;

    // Foreign key referencing Teams table
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="team_id", referencedColumnName = "team_id")
    private Team team;

     // Foreign key referencing UserRoles table
     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name="user_role_id", referencedColumnName = "user_role_id")
     private UserRole userRole;

    public User()
    {

    }

    public User( String firstName, String lastName, String email, 
    String password, String phoneNumber, String photoKey) 
    {
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.photoKey = photoKey;
    }


    public User(int userId, String firstName, String lastName, String email, 
                String password, String phoneNumber, String photoKey) 
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.photoKey = photoKey;
    }

    public int getUserId() 
    {
        return userId;
    }

    public void setUserId(int userId) 
    {
        this.userId = userId;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoKey() 
    {
        return photoKey;
    }

    public void setPhotoKey(String photoKey) 
    {
        this.photoKey = photoKey;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", password=" + password + ", phoneNumber=" + phoneNumber + ", photoKey=" + photoKey + ", teamId="
                + (team != null ? team.getTeamId() : "null") + 
                ", userRoleId=" + (userRole != null ? userRole.getUserRoleId() : "null") + "]";
    }
}
