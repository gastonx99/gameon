package se.dandel.gameon.adapter.in.rest;

import java.util.Collection;

public class BettingGameUserWidgetModel {

    public Collection<BettingGameUserModel> games;

    public static class BettingGameUserModel {
        public long pk;

        public String name;
    }
}
