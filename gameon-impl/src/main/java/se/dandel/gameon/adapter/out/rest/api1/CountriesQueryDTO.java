package se.dandel.gameon.adapter.out.rest.api1;

public class CountriesQueryDTO extends AbstractQueryDTO {
    private String continent;

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

}
