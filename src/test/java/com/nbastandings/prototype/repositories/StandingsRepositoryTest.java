package com.nbastandings.prototype.repositories;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nbastandings.prototype.model.Standings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandingsRepositoryTest {

    @Mock
    private DynamoDBMapper mapper;

    @InjectMocks
    private StandingsRepository standingsRepository;

    @AfterEach
    public void setup() {
        reset(mapper);
    }


    @Test
    void getStandingsForSeasonOK() {
        String paramSeason = "2018-19";
        Standings standings = new Standings();
        standings.setSeason("2018-19");
        standings.setEasternConference(null);
        standings.setWesternConference(null);
        when(mapper.load(Standings.class,paramSeason)).thenReturn(standings);
        Standings response = standingsRepository.getStandingsForSeason(paramSeason);
        assertNotNull(response);
        assertEquals(response.getSeason(), paramSeason);
    }

    @Test
    void getStandingsForSeasonNotFound() {
        String paramSeason = "2018-19";
        when(mapper.load(Standings.class,paramSeason)).thenReturn(null);
        Standings response = standingsRepository.getStandingsForSeason(paramSeason);
        assertEquals(response, null);
    }

}