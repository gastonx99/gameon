package se.dandel.gameon.domain;

public class GameonRuntimeException extends RuntimeException {
    public GameonRuntimeException(String message, Object... args) {
        super(String.format(message, args));
    }
}
