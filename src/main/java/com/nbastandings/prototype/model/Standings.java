package com.nbastandings.prototype.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;

@DynamoDBTable(tableName = "standings")
public class Standings implements Serializable {

    static final long serialVersionUID = 52L;

    private String season;

    private Conference easternConference;

    private Conference westernConference;

    @DynamoDBHashKey(attributeName = "season")
    public String getSeason() {
        return season;
    }

    @DynamoDBAttribute(attributeName = "easternConference")
    public Conference getEasternConference() {
        return easternConference;
    }

    @DynamoDBAttribute(attributeName = "westernConference")
    public Conference getWesternConference() {
        return westernConference;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setEasternConference(Conference easternConference) {
        this.easternConference = easternConference;
    }

    public void setWesternConference(Conference westernConference) {
        this.westernConference = westernConference;
    }

    public Standings() { }

    public Standings(String season, Conference eastern, Conference western){
        this.season = season;
        this.easternConference = eastern;
        this.westernConference =  western;
    }
}
