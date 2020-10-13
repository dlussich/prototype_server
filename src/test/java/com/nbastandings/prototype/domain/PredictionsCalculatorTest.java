package com.nbastandings.prototype.domain;

import com.nbastandings.prototype.model.Conference;
import com.nbastandings.prototype.model.Standings;
import com.nbastandings.prototype.model.Statistics;
import com.nbastandings.prototype.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PredictionsCalculatorTest {

    private PredictionsCalculator predictionsCalculator;

    @BeforeEach
    public void setup(){
        predictionsCalculator = new PredictionsCalculator();
    }

    @Test
    void calculatePredictionsForNextSeason() {

        String paramSeason = "2019-20";
        List<Standings> arrayList = new ArrayList<>();
        Standings standings = new Standings();
        standings.setSeason("2018-19");
        Conference east = new Conference();
        east.setName("East");
        List<Team> eastTeams = new ArrayList<>();
        //teaem 1 east
        Team t1 = new Team();
        t1.setName("Team1");
        t1.setCity("City1");
        t1.setDivision("Division1");
        Statistics stats1 = new Statistics();
        stats1.setWins(50);
        t1.setStats(stats1);
        eastTeams.add(t1);
        //team 3 east
        Team t3 = new Team();
        t3.setName("Team3");
        t3.setCity("City3");
        t3.setDivision("Division3");
        Statistics stats3 = new Statistics();
        stats3.setWins(45);
        t3.setStats(stats3);
        eastTeams.add(t3);
        east.setTeams(eastTeams);
        Conference west = new Conference();
        west.setName("West");
        List<Team> westTeams = new ArrayList<>();
        //team 2 west
        Team t2 = new Team();
        t2.setName("Team2");
        t2.setCity("City2");
        t2.setDivision("Division2");
        Statistics stats2 = new Statistics();
        stats2.setWins(48);
        t2.setStats(stats2);
        westTeams.add(t2);
        //team 4 west
        Team t4 = new Team();
        t4.setName("Team4");
        t4.setCity("City4");
        t4.setDivision("Division4");
        Statistics stats4 = new Statistics();
        stats4.setWins(50);
        t4.setStats(stats4);
        westTeams.add(t4);
        west.setTeams(westTeams);
        standings.setEasternConference(east);
        standings.setWesternConference(west);
        arrayList.add(standings);
        Standings response = predictionsCalculator.calculatePredictionsForNextSeason(arrayList,paramSeason);
        assertNotEquals(response, null);
        assertEquals(response.getEasternConference().getTeams().get(0).getName(),"Team1");
        assertEquals(response.getWesternConference().getTeams().get(0).getName(),"Team4");
        assertEquals(response.getWesternConference().getTeams().get(0)
                .getStats().getWins(),25);
        assertEquals(response.getEasternConference().getTeams().get(0)
                .getStats().getWins(),25);
    }
}