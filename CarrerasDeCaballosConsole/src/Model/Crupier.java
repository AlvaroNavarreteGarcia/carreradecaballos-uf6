package Model;

public class Crupier {
    private Baraja baraja;


    public Crupier() {
        this.baraja = new Baraja();
    }

    public Baraja getBaraja() {
        return this.baraja;
    }

    public Carta sacarCarta() {
        return baraja.sacarCarta();
    }
}
