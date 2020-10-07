package se.dandel.gameon.infrastructure.json.api1.query;

public class TeamsQueryDTO extends AbstractQueryDTO {
    private String countryId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

}
