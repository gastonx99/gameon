package se.dandel.gameon.domain.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TestTeamFactory {
    private static final List<String> TEAMS_ENGLAND_2021 = List.of(
            "Arsenal",
            "Aston Villa",
            "Brighton & Hove Albion",
            "Burnley",
            "Chelsea",
            "Crystal Palace",
            "Everton",
            "Fulham",
            "Leeds United",
            "Leicester City",
            "Liverpool",
            "Manchester City",
            "Manchester United",
            "Newcastle United",
            "Sheffield United",
            "Southampton",
            "Tottenham Hotspur",
            "West Bromwich Albion",
            "West Ham United",
            "Wolverhampton Wanderers"
    );

    private static final List<String> TEAMS_WORLDCUP_2018 = List.of(
            "Argentina",
            "Australia",
            "Belgium",
            "Brazil",
            "Colombia",
            "Costa Rica",
            "Croatia",
            "Denmark",
            "Egypt",
            "England",
            "France",
            "Germany",
            "Iceland",
            "Iran",
            "Japan",
            "Mexico",
            "Morocco",
            "Nigeria",
            "Panama",
            "Peru",
            "Poland",
            "Portugal",
            "Russia",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "South Korea",
            "Spain",
            "Sweden",
            "Switzerland",
            "Tunisia",
            "Uruguay"
    );

    private static final List<String> TEAMS_EURO_2020 = List.of(
            "Austria",
            "Belgium",
            "Croatia",
            "Czech Republic",
            "Denmark",
            "England",
            "Finland",
            "France",
            "Germany",
            "Hungary",
            "Italy",
            "Netherlands",
            "North Macedonia",
            "Poland",
            "Portugal",
            "Russia",
            "Scotland",
            "Slovakia",
            "Spain",
            "Sweden",
            "Switzerland",
            "Turkey",
            "Ukraine",
            "Wales"
    );

    private static final AtomicLong REMOTE_KEY = new AtomicLong(1);

    public static Team createTeam(String name) {
        String remoteKey = String.valueOf(REMOTE_KEY.getAndIncrement());
        return createTeam(remoteKey, name);
    }

    public static Team createTeam(String name, Country country) {
        RemoteKey remoteKey = RemoteKey.of(REMOTE_KEY.getAndIncrement());
        return createTeam(remoteKey, name, country);
    }

    public static Team createTeam(String remoteKey, String name) {
        Country country = TestCountryFactory.createCountrySweden();
        return createTeam(RemoteKey.of(remoteKey), name, country);
    }

    private static Team createTeam(RemoteKey remoteKey, String name, Country country) {
        Team team = new Team();
        team.setRemoteKey(remoteKey);
        team.setName(name);
        team.setCountry(country);
        return team;
    }

    public static List<Team> createTeamsEngland2021() {
        Country country = TestCountryFactory.createCountry("en", "England", "Europe");
        return TEAMS_ENGLAND_2021.stream().map(name -> createTeam(name, country)).collect(Collectors.toList());
    }

    public static List<Team> createTeamsWorldcup2018() {
        Country world = TestCountryFactory.createCountry(null, "World", "World");
        return TEAMS_WORLDCUP_2018.stream().map(name -> createTeam(name, world)).collect(Collectors.toList());
    }

    public static List<Team> createTeamsEuro2020() {
        Country world = TestCountryFactory.createCountry(null, "World", "World");
        return TEAMS_EURO_2020.stream().map(name -> createTeam(name, world)).collect(Collectors.toList());
    }
}
