package com.nbastandings.prototype.domain;

import com.nbastandings.prototype.model.Conference;
import com.nbastandings.prototype.model.Standings;
import com.nbastandings.prototype.model.Statistics;
import com.nbastandings.prototype.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PredictionsCalculator {

    private static final Logger logger = LoggerFactory.getLogger(PredictionsCalculator.class);

    private static final Integer PLAYED_GAMES = 82;

    // setting percentage value to each season
    HashMap<String, Double> weightsForPredictions = new HashMap<>();

    HashMap<String, Double> predictionsForEast = new HashMap<>();

    HashMap<String, Double> predictionsForWest = new HashMap<>();

    private void loadHashMapsForCalculation(List<Standings> list){
        //loop through every seasons standings
        for(Standings standings: list){
            loadHashFromEasternConf(standings.getEasternConference().getTeams(),standings.getSeason());
            loadHashFromWesternConf(standings.getWesternConference().getTeams(),standings.getSeason());
        }
    }

    public Standings calculatePredictionsForNextSeason(List<Standings> list, String season){
        Standings response = null;
        try{
            //Load structures to ease calculation process
            loadPredictionsWeightValues();
            loadHashMapsForCalculation(list);
            // getting the predictions into list of pair values (teamName-City,predictedWins)
            List<Pair<String,Integer>> easternTeams = transformHashIntoSortedList("East");
            List<Pair<String,Integer>> westernTeams = transformHashIntoSortedList("West");

            //sort teams by predicted winning record
            Comparator<Pair<String, Integer>> comparator = Comparator.comparingInt(Pair::getSecond);
            List<Pair<String,Integer>> eastList = easternTeams.stream()
                    .sorted(comparator.reversed())
                    .collect(Collectors.toList());
            List<Pair<String,Integer>> westList = westernTeams.stream()
                    .sorted(comparator.reversed())
                    .collect(Collectors.toList());
            // create list of teams based on the results of the prediction
            List<Team> easternConf = getSortedConference(eastList);
            List<Team> westernConf = getSortedConference(westList);
            response = createStandingsForResponse(season, easternConf, westernConf);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }

        return response;
    }

    private List<Team> getSortedConference(List<Pair<String, Integer>> conferenceTeams){
        List<Team> teams = new ArrayList<>();
        Integer count = 1;
        for(Pair pair: conferenceTeams){
                Team team = new Team();
                String[] values = pair.getFirst().toString().split("-");
                team.setName(values[0]);
                team.setCity(values[1]);
                Statistics stats = new Statistics();
                Integer wins = (Integer)pair.getSecond();
                stats.setWins(wins);
                stats.setLosses(PLAYED_GAMES-wins);
                stats.setLeagueRank(count);
                stats.setRecord(wins+"-"+(PLAYED_GAMES-wins));
                team.setStats(stats);
                count++;
                teams.add(team);
        }
        return teams;
    }

    private Standings createStandingsForResponse(String season,List<Team> eastConf, List<Team> westConf){
        Standings response = new Standings();
        response.setSeason(season);
        Conference east = new Conference();
        east.setName("East");
        east.setTeams(eastConf);
        response.setEasternConference(east);
        Conference west = new Conference();
        west.setName("West");
        west.setTeams(westConf);
        response.setWesternConference(west);

        return  response;
    }

    private List<Pair<String,Integer>> transformHashIntoSortedList(String conference){
        List<Pair<String,Integer>> result = new ArrayList<>();
        if(conference.equals("East")) {
            predictionsForEast.forEach((key, value) -> {
                int data = (int) (Math.round(value));
                result.add(Pair.of(key, Integer.valueOf(data)));
            });
        } else {
            predictionsForWest.forEach((key, value)->{
                int data = (int)(Math.round(value));
                result.add(Pair.of(key,Integer.valueOf(data)));
            });
        }
        return result;
    }

    private void loadHashFromEasternConf( List<Team> teams, String season){
        Double winsPrediction = 0.0;
        String hashKey = "";
        // calculate prediction values based on probabilities given in the requirements
        // for east conference teams
        for(Team team : teams){
            StringBuilder sb = new StringBuilder();
            sb.append(team.getName()).append("-").append(team.getCity());
            hashKey = sb.toString();
            if(predictionsForEast.containsKey(hashKey)){
                winsPrediction = predictionsForEast.get(hashKey);
                winsPrediction = winsPrediction + (team.getStats().getWins() * weightsForPredictions.get(season));
                predictionsForEast.put(hashKey, winsPrediction);
            } else {
                winsPrediction = (team.getStats().getWins() * weightsForPredictions.get(season));
                predictionsForEast.put(hashKey,winsPrediction);
            }
        }
    }

    private void loadHashFromWesternConf( List<Team> teams, String season){
        Double winsPrediction = 0.0;
        String hashKey = "";
        // calculate prediction values based on probabilities given in the requirements
        // for west conference teams
        for(Team team : teams){
            StringBuilder sb = new StringBuilder();
            sb.append(team.getName()).append("-").append(team.getCity());
            hashKey = sb.toString();
            if(predictionsForWest.containsKey(hashKey)){
                winsPrediction = predictionsForWest.get(hashKey);
                winsPrediction = winsPrediction + (team.getStats().getWins() * weightsForPredictions.get(season));
                predictionsForWest.put(hashKey, winsPrediction);
            } else {
                winsPrediction = (team.getStats().getWins() * weightsForPredictions.get(season));
                predictionsForWest.put(hashKey,winsPrediction);
            }
        }
    }

    private void loadPredictionsWeightValues(){
        // I'm assigning the weight value given for the predictions to each season
        weightsForPredictions.put("2018-19",0.50);
        weightsForPredictions.put("2017-18",0.30);
        weightsForPredictions.put("2016-17",0.20);
    }
}
