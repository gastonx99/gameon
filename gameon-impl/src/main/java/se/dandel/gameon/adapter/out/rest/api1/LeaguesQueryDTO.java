package se.dandel.gameon.adapter.out.rest.api1;

public class LeaguesQueryDTO extends AbstractQueryDTO {
    private int countryId;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

}
