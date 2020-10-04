package se.dandel.gameon.infrastructure.json;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Team;

public class JsonMatchParser {

    public Collection<Match> parseMatches(JSONObject jsonObject) {
        JSONArray fixture = jsonObject.getJSONArray("Season");
        Map<String, Team> teams = new HashMap<>();
        Collection<Match> matches = new ArrayList<>();
        for (Object o : fixture) {
            JSONObject match = (JSONObject) o;
            matches.add(parse(teams, match));
        }
        return matches;
    }

    private Match parse(Map<String, Team> teams, JSONObject match) {
        String dateTime = match.getString("DateTime");
        String group = match.getString("Group");
        String venue = match.getString("Venue");
        String homeTeam = match.getString("HomeTeam");
        String awayteam = match.getString("AwayTeam");
        int homeGoal = match.getInt("HomeGoal");
        int awayGoal = match.getInt("AwayGoal");
        Match m = new Match();
        m.setMatchStart(ZonedDateTime.of(LocalDate.parse(dateTime), LocalTime.MIDNIGHT, ZoneId.systemDefault()));
        m.setVenue(venue);
        m.setTeams(getorCreateTeam(teams, homeTeam), getorCreateTeam(teams, awayteam));
        m.getFinalScore().setHome(homeGoal);
        m.getFinalScore().setAway(awayGoal);
        return m;
    }

    private Team getorCreateTeam(Map<String, Team> teams, String name) {
        Team team = teams.get(name);
        if (team == null) {
            team = Team.of(name, name.replace("_", " "));
            teams.put(name, team);
        }
        return team;
    }

}
