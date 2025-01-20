package com.jdotdev.teambuilder.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "team")
    private User user;

    public Team()
    {

    }

    public Team(int teamId, String name, String photoKey, User user)
    {
        this.teamId = teamId;
        this.name = name;
        this.photoKey = photoKey;
        this.user = user;
    }

    public int getTeamId()
    {
        return teamId;
    }

    public void setTeamId(int teamId)
    {
        this.teamId = teamId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhotoKey()
    {
        return photoKey;
    }
    
    public void setPhotoKey(String photoKey)
    {
        this.photoKey = photoKey;
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
        return "Team [teamId=" + teamId + ", name=" + name + ", photoKey=" + photoKey + ", user=" + user + "]";
    }
}
