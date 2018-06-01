package icesi.dmi.easylibdos;

public class Coordenada {

    public int x;
    public int y;
    public int state;
    public String ids;
    public int inicio, fin;
    public boolean hasReserva;
    public String filter;

    public Coordenada() {

    }

    public Coordenada(int x, int y, int state, boolean hasReserva) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.inicio = inicio;
        this.fin = fin;
        this.hasReserva = hasReserva;
    }
}
