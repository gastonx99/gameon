package se.dandel.gameon.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Score {

    private int home;

    private int away;

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getAway() {
        return away;
    }

    public void setAway(int away) {
        this.away = away;
    }
}
