package se.dandel.gameon.rest;

import java.util.Collection;
import java.util.Collections;

public class SeasonModel {
    private String name;
    private Collection<MatchModel> matches;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMatches(Collection<MatchModel> matches) {
        this.matches = matches;
    }

    public Collection<MatchModel> getMatches() {
        return Collections.unmodifiableCollection(matches);
    }
}
