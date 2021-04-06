package se.dandel.gameon.adapter.out.rest.api1;

import org.apache.commons.collections4.SetUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import se.dandel.gameon.domain.model.*;

import java.util.Set;

@Mapper(componentModel = "cdi")
public interface DtoMapper {
    @Mapping(source = "countryId", target = "remoteKey.remoteKey")
    Country fromDTO(CountryDTO source);

    @Mapping(source = "teamId", target = "remoteKey.remoteKey")
    Team fromDTO(TeamDTO source);

    @Mapping(target = "type", constant = "LEAGUE")
    @Mapping(source = "leagueId", target = "remoteKey.remoteKey")
    @Mapping(source = "countryId", target = "country.remoteKey.remoteKey")
    Tournament fromDTO(LeagueDTO source);

    @Mapping(source = "seasonId", target = "remoteKey.remoteKey")
    Season fromDTO(SeasonDTO source);

    @Mapping(source = "matchId", target = "remoteKey.remoteKey")
    @Mapping(target = "matchStart", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "finalScore.home", source = "stats.homeScore")
    @Mapping(target = "finalScore.away", source = "stats.awayScore")
    @Mapping(target = "status", source = "statusCode", qualifiedByName = "statusCodeToStatus")
    @Mapping(target = "statustext", source = "statusCode", qualifiedByName = "statusCodeToStatustext")
    @Mapping(target = "stage", source = "stage.name")
    @Mapping(target = "group", source = "group.groupName")
    @Mapping(target = "round", source = "round.name")
    Match fromDTO(MatchDTO source);

    Set<Integer> STATUSCODE_NOT_STARTED = SetUtils.hashSet(0, 2, 4, 9, 17, 10);
    Set<Integer> STATUSCODE_INPLAY = SetUtils.hashSet(1, 6, 7, 8, 11, 12, 13, 14);
    Set<Integer> STATUSCODE_ENDED = SetUtils.hashSet(3, 15, 31, 32);
    Set<Integer> STATUSCODE_CANCELLED = SetUtils.hashSet(5);

    @Named("statusCodeToStatus")
    default MatchStatus statusCodeToStatus(int statusCode) {
        if (STATUSCODE_INPLAY.contains(statusCode)) {
            return MatchStatus.INPLAY;
        } else if (STATUSCODE_NOT_STARTED.contains(statusCode)) {
            return MatchStatus.NOT_STARTED;
        } else if (STATUSCODE_ENDED.contains(statusCode)) {
            return MatchStatus.ENDED;
        } else if (STATUSCODE_CANCELLED.contains(statusCode)) {
            return MatchStatus.CANCELLED;
        }
        throw new IllegalArgumentException("Unknown status code " + statusCode);
    }

    @Named("statusCodeToStatustext")
    default String statusCodeToStatustext(int statusCode) {
        switch (statusCode) {
            case 0:
                return "The event has not started";
            case 1:
                return "The event is inplay";
            case 11:
                return "The event is in half-time";
            case 12:
                return "The event is in extra time";
            case 13:
                return "The event is in penalties because extra time didn't determinate a winner.";
            case 14:
                return "Event is in break waiting for extra time or penalties.";
            case 15:
                return "Awarding of a victory to a contestant because there are no other contestants.";
            case 2:
                return "Event will be updated later.";
            case 3:
                return "Event has ended after 90 minutes.";
            case 31:
                return "Event has ended after penalty shootout.";
            case 32:
                return "The event has finished after extra time.";
            case 4:
                return "The event has been Postponed.";
            case 5:
                return "The event has been Cancelled.";
            case 6:
                return "The event has abandoned and will continue at a later time or day.";
            case 7:
                return "The event has been interrupted. Can be due to bad weather for example.";
            case 8:
                return "The event has been suspended.";
            case 9:
                return "Winner is beeing decided externally.";
            case 10:
                return "The event is delayed.";
            case 17:
                return "The event has not been verified yet.";
        }
        throw new IllegalArgumentException("Unknown status code " + statusCode);
    }

}
