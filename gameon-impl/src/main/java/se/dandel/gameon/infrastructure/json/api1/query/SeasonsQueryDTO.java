package se.dandel.gameon.infrastructure.json.api1.query;

public class SeasonsQueryDTO extends AbstractQueryDTO {

    private int leagueId;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }
}
