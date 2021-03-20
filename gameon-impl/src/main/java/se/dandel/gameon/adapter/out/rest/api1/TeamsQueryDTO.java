package se.dandel.gameon.adapter.out.rest.api1;

public class TeamsQueryDTO extends AbstractQueryDTO {
    private String countryId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

}
