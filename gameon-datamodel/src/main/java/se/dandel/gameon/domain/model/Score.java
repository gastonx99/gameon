package se.dandel.gameon.domain.model;

import javax.persistence.Embeddable;

@Embeddable
public class Score {

    private int home;

    private int away;

    private Score() {
        super();
    }

    public Score(int home, int away) {
        this.home = home;
        this.away = away;
    }

    public int getHome() {
        return home;
    }

    public int getAway() {
        return away;
    }
}
