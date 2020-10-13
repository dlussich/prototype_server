package com.nbastandings.prototype.controller;

import com.nbastandings.prototype.model.Standings;
import com.nbastandings.prototype.service.StandingsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandingsControllerTest {

    @InjectMocks
    private StandingsController controller;

    @Mock
    private StandingsService standingsService;

    @AfterEach
    public void setup() {
        reset(standingsService);
    }

    @Test
    void getStandingsBySeasonOK() {
        String paramSeason = "2018-19";
        Standings standings = new Standings();
        standings.setSeason("2018-19");
        standings.setEasternConference(null);
        standings.setWesternConference(null);
        when(standingsService.getStandingsBySeason(paramSeason)).thenReturn(standings);
        ResponseEntity<Standings> response = controller.getStandingsBySeason(paramSeason);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(standings.getSeason(), response.getBody().getSeason());
    }

    @Test
    void getStandingsBySeasonNotFound() {
        String paramSeason = "2011-12";
        when(standingsService.getStandingsBySeason(paramSeason)).thenReturn(null);
        ResponseEntity<Standings> response = controller.getStandingsBySeason(paramSeason);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(null, response.getBody());
    }

    @Test
    void getPredictionsOK() {
        String paramSeason = "2019-20";
        Standings standings = new Standings();
        standings.setSeason("2019-20");
        standings.setEasternConference(null);
        standings.setWesternConference(null);
        when(standingsService.getPredictions(paramSeason)).thenReturn(standings);
        ResponseEntity<Standings> response = controller.getPredictions(paramSeason);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(standings.getSeason(), response.getBody().getSeason());
    }

    @Test
    void getPredictionsNotFound() {
        String paramSeason = "2011-12";
        when(standingsService.getPredictions(paramSeason)).thenReturn(null);
        ResponseEntity<Standings> response = controller.getPredictions(paramSeason);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(null, response.getBody());
    }

}