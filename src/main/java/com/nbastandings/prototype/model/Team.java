package com.nbastandings.prototype.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.io.Serializable;

@DynamoDBDocument
public class Team implements Serializable {

    static final long serialVersionUID = 42L;

    private String name;

    private String city;

    private String division;

    private Statistics stats;

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    @DynamoDBAttribute
    public String getCity() {
        return city;
    }

    @DynamoDBAttribute
    public String getDivision() {
        return division;
    }

    @DynamoDBAttribute
    public Statistics getStats() {
        return stats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public Team(){ this.stats =  new Statistics(); }

    public Team(String name, String city,String division, Statistics stats){
        this.name = name;
        this.city = city;
        this.division = division;
        this.stats = stats;
    }
}
