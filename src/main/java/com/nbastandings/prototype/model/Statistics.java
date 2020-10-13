package com.nbastandings.prototype.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.io.Serializable;

@DynamoDBDocument
public class Statistics implements Serializable {

    static final long serialVersionUID = 4L;

    private Integer wins;
    private Integer losses;
    private Double  winPCT;
    private Integer leagueRank;
    private String  record;
    private Double  avgPointsScored;
    private Double  opponentAvgPointsScored;

    @DynamoDBAttribute
    public Integer getWins() {
        return wins;
    }

    @DynamoDBAttribute
    public Integer getLosses() {
        return losses;
    }

    @DynamoDBAttribute
    public Double getWinPCT() {
        return winPCT;
    }

    @DynamoDBAttribute
    public Integer getLeagueRank() {
        return leagueRank;
    }

    @DynamoDBAttribute
    public String getRecord() {
        return record;
    }

    @DynamoDBAttribute
    public Double getAvgPointsScored() {
        return avgPointsScored;
    }

    @DynamoDBAttribute
    public Double getOpponentAvgPointsScored() {
        return opponentAvgPointsScored;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public void setWinPCT(Double winPCT) {
        this.winPCT = winPCT;
    }

    public void setLeagueRank(Integer leagueRank) {
        this.leagueRank = leagueRank;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setAvgPointsScored(Double avgPointsScored) {
        this.avgPointsScored = avgPointsScored;
    }

    public void setOpponentAvgPointsScored(Double opponentAvgPointsScored) {
        this.opponentAvgPointsScored = opponentAvgPointsScored;
    }

    public Statistics(){

    }
}
