package meteor.plugins.autologhop;

public enum Method {
    LOGOUT("Logout"),
    HOP("Hop"),
    LOGOUT_HOP("Logout, Hop"),
    TELEPORT("Teleport");

    private final String name;

    Method(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}