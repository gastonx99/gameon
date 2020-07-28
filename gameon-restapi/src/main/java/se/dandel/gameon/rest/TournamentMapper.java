package se.dandel.gameon.rest;

import static java.util.stream.Collectors.toList;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Tournament;

public class TournamentMapper {
    public TournamentModel map(Tournament tournament) {
        TournamentModel model = new TournamentModel();
        model.setName(tournament.getName());
        model.setSeasons(tournament.getSeasons().stream().map(s -> map(s)).collect(toList()));
        return model;
    }

    private SeasonModel map(Season season) {
        SeasonModel model = new SeasonModel();
        model.setName(season.getName());
        model.setMatches(season.getMatches().stream().map(m -> map(m)).collect(toList()));
        return model;
    }

    private MatchModel map(Match match) {
        MatchModel model = new MatchModel();
        model.setHomeTeam(match.getHomeTeam().getName());
        model.setAwayTeam(match.getAwayTeam().getName());
        model.setDateTime(match.getZonedDateTime().toString());
        model.setVenue(match.getVenue());
        return model;
    }
}
