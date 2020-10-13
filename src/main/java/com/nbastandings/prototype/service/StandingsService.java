package com.nbastandings.prototype.service;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.nbastandings.prototype.domain.PredictionsCalculator;
import com.nbastandings.prototype.model.Standings;
import com.nbastandings.prototype.repositories.StandingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandingsService {
    Logger logger = LoggerFactory.getLogger(StandingsService.class);

    @Autowired
    private StandingsRepository standingsRepository;

    public Standings insertIntoDynamoDB(Standings dto) {
        Standings standings = null;
        //todo: validations but this was for loading one time data only
        try{
            standings = standingsRepository.saveStandings(dto);
        }catch (Exception ex){
            logger.error("Error while calling standingsRepository...");
            logger.error(ex.getMessage());
        }
        return standings;
    }


    public Standings getStandingsBySeason(String season){
        Standings response = null;
        try{
            response = standingsRepository.getStandingsForSeason(season);
        } catch (AmazonDynamoDBException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    public Standings getPredictions(String season){
        List<Standings> list = standingsRepository.getAllPreviousSeasons(season);

        PredictionsCalculator calculator = new PredictionsCalculator();

        return calculator.calculatePredictionsForNextSeason(list,season);
    }
}
