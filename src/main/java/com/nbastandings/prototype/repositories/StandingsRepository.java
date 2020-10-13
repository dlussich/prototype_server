package com.nbastandings.prototype.repositories;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nbastandings.prototype.model.Standings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class StandingsRepository{

    private static final Logger logger = LoggerFactory.getLogger(StandingsRepository.class);

    @Autowired
    private DynamoDBMapper mapper;

    public Standings getStandingsForSeason(String season){
        return mapper.load(Standings.class, season);
    }

    //Created the CRUD but ended up not using delete
    public void deleteStandings(Standings standings){
        mapper.delete(standings);
    }

    // this was used to load data into database
    public Standings saveStandings(Standings standings){
        Standings response = null;
        mapper.save(standings);
        try{
            response = mapper.load(Standings.class,standings.getSeason());
        } catch(DynamoDBMappingException ex){
            logger.error(ex.getMessage());
        }

        return response;
    }

    public List<Standings> getAllPreviousSeasons(String season){
        Map<String, AttributeValue>  expected = new HashMap<String, AttributeValue>();
        expected.put(":val1", new AttributeValue().withS(season));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("season < :val1 ").withExpressionAttributeValues(expected);

        return mapper.scan(Standings.class, scanExpression);
    }




}


