package com.nbastandings.prototype.controller;

import com.nbastandings.prototype.model.Standings;
import com.nbastandings.prototype.service.StandingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")

public class StandingsController {

    private static final Logger logger = LoggerFactory.getLogger(StandingsController.class);

    @Autowired
    private StandingsService standingsService;

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public ResponseEntity insertIntoDynamoDB(@RequestBody Standings dto) {
        ResponseEntity responseEntity = null;
        try {
            Standings body = standingsService.insertIntoDynamoDB(dto);
            if(body == null){
                responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                responseEntity = new ResponseEntity(body, HttpStatus.OK);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @CrossOrigin
    @RequestMapping(path = "/standings",method = RequestMethod.GET)
    public ResponseEntity<Standings> getStandingsBySeason(@RequestParam(name = "season") String season) {
        ResponseEntity<Standings> responseEntity = null;
        try {
            Standings body = standingsService.getStandingsBySeason(season);
            if(body == null){
                responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            }else {
                responseEntity = new ResponseEntity(body, HttpStatus.OK);
            }
        } catch (Exception ex){
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  responseEntity;
    }

    @CrossOrigin
    @RequestMapping(path = "/predictions",method = RequestMethod.GET)
    public ResponseEntity<Standings> getPredictions(@RequestParam(name = "season") String season) {
        ResponseEntity<Standings> responseEntity = null;
        try {
            Standings body = standingsService.getPredictions(season);
            if(body == null){
                responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            }else {
                responseEntity = new ResponseEntity(body, HttpStatus.OK);
            }
        } catch (Exception ex){

            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  responseEntity;
    }
}
