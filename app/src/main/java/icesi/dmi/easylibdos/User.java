package icesi.dmi.easylibdos;

public class User {

    public String uid;
    public boolean hasBooking;
    public int inicio, fin;
    public String lugar;

    public User(){}

    public User(String uid, boolean hasBooking,int inicio, int fin){
        this.uid = uid;
        this.hasBooking = hasBooking;
        this.inicio = inicio;
        this.fin = fin;
    }
}
