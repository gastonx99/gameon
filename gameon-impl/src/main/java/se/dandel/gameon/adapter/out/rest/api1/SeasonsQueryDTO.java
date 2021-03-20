package se.dandel.gameon.adapter.out.rest.api1;

public class SeasonsQueryDTO extends AbstractQueryDTO {

    private int leagueId;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }
}
