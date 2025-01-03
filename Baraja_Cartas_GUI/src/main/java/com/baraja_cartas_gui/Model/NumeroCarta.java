package com.baraja_cartas_gui.Model;

public class NumeroCarta extends Carta {

    private int num;
    /**
     * Constructor para crear las Cartas con su PaloCarta y su valor llamando a un metodo helpper
     *
     * @param num --> Numero de carta
     * @param palo --> Palo de la carta
     */
    public NumeroCarta(int num, Palo palo) {
        this.num = num;
        super.palo = palo;
    }

    /**
     *  ToString method to display card data
     * @return --> Num, Palo i valor
     */
    @Override
    public String toString() {
        return super.toString(String.valueOf(this.num));
    }

    @Override
    public String obtenerCodigoCarta() {
        return this.num+"_"+ palo;
    }

}
