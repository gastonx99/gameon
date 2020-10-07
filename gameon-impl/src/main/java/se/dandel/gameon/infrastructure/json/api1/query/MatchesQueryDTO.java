package se.dandel.gameon.infrastructure.json.api1.query;

public class MatchesQueryDTO extends AbstractQueryDTO {
    private String countryId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

}
