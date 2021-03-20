package se.dandel.gameon.adapter.cli;

import se.dandel.gameon.domain.model.Match;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;
import java.util.Collection;

public class JsonMatchParser {

    public Collection<Match> parseMatches(String json) {
        Jsonb jsonb = JsonbBuilder.create();
        Collection<Match> matches = jsonb.fromJson("", new ArrayList<Match>() {
        }.getClass().getGenericSuperclass());
        return matches;
    }

}
