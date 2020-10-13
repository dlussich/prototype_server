package com.nbastandings.prototype.model;



import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DynamoDBDocument
public class Conference implements Serializable {

    static final long serialVersionUID = 41L;

    private String name;

    private List<Team> teams = new ArrayList<Team>();


    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    @DynamoDBAttribute
    public List<Team> getTeams() {
        return teams;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Conference(){}

    public Conference(String name, List<Team> teams) {
        this.name = name;
        this.teams = teams;
    }
}
