package icesi.dmi.easylibdos;

public class User {

    String UID;
    boolean hasBooking;

    public User(){ }

    public User(String UID, boolean hasBooking){
        this.UID = UID;
        this.hasBooking = hasBooking;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public boolean isHasBooking() {
        return hasBooking;
    }

    public void setHasBooking(boolean hasBooking) {
        this.hasBooking = hasBooking;
    }
}
