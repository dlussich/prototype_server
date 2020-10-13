package com.nbastandings.prototype.service;

import com.nbastandings.prototype.domain.PredictionsCalculator;
import com.nbastandings.prototype.model.Conference;
import com.nbastandings.prototype.model.Standings;
import com.nbastandings.prototype.model.Statistics;
import com.nbastandings.prototype.model.Team;
import com.nbastandings.prototype.repositories.StandingsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandingsServiceTest {

    @Mock
    private StandingsRepository standingsRepository;

    @Mock
    private PredictionsCalculator calculator = new PredictionsCalculator();

    @InjectMocks
    private StandingsService standingsService;

    @AfterEach
    public void setup() {
        reset(standingsRepository);
        reset(calculator);
    }

    @Test
    void getStandingsBySeasonOK() {
        String paramSeason = "2018-19";
        Standings standings = new Standings();
        standings.setSeason("2018-19");
        standings.setEasternConference(null);
        standings.setWesternConference(null);
        when(standingsRepository.getStandingsForSeason(paramSeason)).thenReturn(standings);
        Standings response = standingsService.getStandingsBySeason(paramSeason);
        assertNotNull(standings);
        assertEquals(standings.getSeason(), response.getSeason());
    }

    @Test
    void getStandingsBySeasonNotFound() {
        String paramSeason = "2030-31";
        when(standingsRepository.getStandingsForSeason(paramSeason)).thenReturn(null);
        Standings response = standingsService.getStandingsBySeason(paramSeason);
        assertEquals(null, response);
    }

    @Test
    void getPredictionsOK() {
        String paramSeason = "2018-19";

        List<Standings> list = new ArrayList<>();
        Standings standings = new Standings();
        standings.setSeason("2018-19");
        Conference east = new Conference();
        east.setName("East");
        List<Team> eastTeams = new ArrayList<>();
        Team t1 = new Team();
        t1.setName("Team1");
        t1.setCity("City1");
        t1.setDivision("Division1");
        Statistics stats1 = new Statistics();
        stats1.setWins(50);
        t1.setStats(stats1);
        eastTeams.add(t1);
        east.setTeams(eastTeams);
        Conference west = new Conference();
        west.setName("West");
        List<Team> westTeams = new ArrayList<>();
        Team t2 = new Team();
        t2.setName("Team2");
        t2.setCity("City2");
        t2.setDivision("Division2");
        Statistics stats2 = new Statistics();
        stats2.setWins(48);
        t2.setStats(stats2);
        westTeams.add(t2);
        west.setTeams(westTeams);
        standings.setEasternConference(east);
        standings.setWesternConference(west);
        list.add(standings);
        when(standingsRepository.getAllPreviousSeasons(paramSeason)).thenReturn(list);
        Standings standingsPred = new Standings();
        standingsPred.setSeason("2018-19");
        standingsPred.setWesternConference(west);
        standingsPred.setEasternConference(east);
        when(calculator.calculatePredictionsForNextSeason(list,paramSeason)).thenReturn(standingsPred);
        Standings response = standingsService.getPredictions(paramSeason);
        assertNotNull(response);
        assertEquals(paramSeason, response.getSeason());
    }

}