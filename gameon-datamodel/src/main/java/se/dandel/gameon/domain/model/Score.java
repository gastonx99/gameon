package se.dandel.gameon.domain.model;

import javax.persistence.Embeddable;

@Embeddable
public class Score {

    private int home;
    private int away;

    public int getHome() {
        return home;
    }

    public void setHome(int homeGoal) {
        this.home = homeGoal;
    }

    public int getAway() {
        return away;
    }

    public void setAway(int awayGoal) {
        this.away = awayGoal;
    }
}
