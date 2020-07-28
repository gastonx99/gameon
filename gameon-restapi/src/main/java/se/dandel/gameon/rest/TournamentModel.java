package se.dandel.gameon.rest;

import java.util.Collection;
import java.util.Collections;

public class TournamentModel {
    private String name;
    private Collection<SeasonModel> seasons;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSeasons(Collection<SeasonModel> seasons) {
        this.seasons = seasons;
    }

    public Collection<SeasonModel> getSeasons() {
        return Collections.unmodifiableCollection(seasons);
    }
}
