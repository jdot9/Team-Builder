package com.jdotdev.teambuilder.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="teams")
public class Team {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="team_id")
    private int teamId;

    @Column(name="name")
    private String name; 

    @Column(name="photo_key")
    private String photoKey;

    @OneToMany(mappedBy = "team")
    private List<User> users;
    
    public Team()
    {

    }

    public Team(int teamId, String name)
    {
        this.teamId = teamId;
        this.name = name;
    }

    public Team(String name)
    {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoKey() {
        return photoKey;
    }

    public void setPhotoKey(String photoKey) {
        this.photoKey = photoKey;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Team [teamId=" + teamId + ", name=" + name + ", photoKey=" + photoKey + "]";
    }
}
