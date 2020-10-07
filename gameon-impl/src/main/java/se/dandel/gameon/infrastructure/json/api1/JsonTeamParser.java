package se.dandel.gameon.infrastructure.json.api1;

import se.dandel.gameon.infrastructure.json.api1.data.LeagueDTO;
import se.dandel.gameon.infrastructure.json.api1.data.MatchDTO;
import se.dandel.gameon.infrastructure.json.api1.data.SeasonDTO;
import se.dandel.gameon.infrastructure.json.api1.data.TeamDTO;
import se.dandel.gameon.infrastructure.json.api1.query.LeaguesQueryDTO;
import se.dandel.gameon.infrastructure.json.api1.query.MatchesQueryDTO;
import se.dandel.gameon.infrastructure.json.api1.query.SeasonsQueryDTO;
import se.dandel.gameon.infrastructure.json.api1.query.TeamsQueryDTO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import java.util.*;

public class JsonTeamParser {

    public static final JsonbConfig JSONB_CONFIG = new JsonbConfig().withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);

    private Jsonb jsonb = JsonbBuilder.create(JSONB_CONFIG);

    public Collection<TeamDTO> parseTeams(String json) {
        EnvelopeDTO<TeamsQueryDTO, Collection<TeamDTO>> envelopeDTO = jsonb.fromJson(json, new EnvelopeDTO<TeamsQueryDTO, Collection<TeamDTO>>() {

        }.getClass().getGenericSuperclass());
        return envelopeDTO.getData();
    }

    public List<MatchDTO> parseMatches(String json) {
        EnvelopeDTO<MatchesQueryDTO, Map<Integer, MatchDTO>> envelopeDTO = jsonb.fromJson(json, new EnvelopeDTO<MatchesQueryDTO, Map<Integer, MatchDTO>>() {

        }.getClass().getGenericSuperclass());
        SortedMap sortedMap = new TreeMap(envelopeDTO.getData());
        return new ArrayList<>(sortedMap.values());
    }

    public Collection<LeagueDTO> parseLeagues(String json) {
        EnvelopeDTO<LeaguesQueryDTO, Map<Integer, LeagueDTO>> envelopeDTO = jsonb.fromJson(json, new EnvelopeDTO<LeaguesQueryDTO, Map<Integer, LeagueDTO>>() {

        }.getClass().getGenericSuperclass());
        return envelopeDTO.getData().values();
    }

    public Collection<SeasonDTO> parseSeasons(String json) {
        EnvelopeDTO<SeasonsQueryDTO, Collection<SeasonDTO>> envelopeDTO = jsonb.fromJson(json, new EnvelopeDTO<SeasonsQueryDTO, Collection<SeasonDTO>>() {

        }.getClass().getGenericSuperclass());
        return envelopeDTO.getData();
    }
}
