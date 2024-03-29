package se.dandel.gameon.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RemoteKey {
    @Column(name = "REMOTEKEY")
    private String remoteKey;

    public static RemoteKey of(String remoteKey) {
        RemoteKey key = new RemoteKey();
        key.setRemoteKey(remoteKey);
        return key;
    }

    public static RemoteKey of(long remoteKey) {
        RemoteKey key = new RemoteKey();
        key.setRemoteKey(String.valueOf(remoteKey));
        return key;
    }

    public String getRemoteKey() {
        return remoteKey;
    }

    public void setRemoteKey(String remoteKey) {
        this.remoteKey = remoteKey;
    }

    @Override
    public String toString() {
        return "RemoteKey{" +
                "remoteKey='" + remoteKey + '\'' +
                '}';
    }
}
