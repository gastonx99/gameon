package se.dandel.gameon.infrastructure.json.api1.query;

public class LeaguesQueryDTO extends AbstractQueryDTO {
    private int countryId;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

}