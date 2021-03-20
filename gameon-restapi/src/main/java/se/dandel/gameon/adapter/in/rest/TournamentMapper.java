package se.dandel.gameon.adapter.in.rest;

import se.dandel.gameon.domain.model.Match;
import se.dandel.gameon.domain.model.Season;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.adapter.in.rest.TournamentModel;

import static java.util.stream.Collectors.toList;

public class TournamentMapper {
    public TournamentModel map(Tournament tournament) {
        TournamentModel model = new TournamentModel();
        model.setName(tournament.getName());
        model.setSeasons(tournament.getSeasons().stream().map(s -> map(s)).collect(toList()));
        return model;
    }

    private TournamentModel.SeasonModel map(Season season) {
        TournamentModel.SeasonModel model = new TournamentModel.SeasonModel();
        model.setName(season.getName());
        model.setMatches(season.getMatches().stream().map(m -> map(m)).collect(toList()));
        return model;
    }

    private TournamentModel.MatchModel map(Match match) {
        TournamentModel.MatchModel model = new TournamentModel.MatchModel();
//        model.setHomeTeam(match.getHomeTeam().getName());
//        model.setAwayTeam(match.getAwayTeam().getName());
        model.setDateTime(match.getMatchStart().toString());
        return model;
    }
}
