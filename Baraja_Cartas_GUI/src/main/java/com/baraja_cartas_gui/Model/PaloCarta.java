package com.baraja_cartas_gui.Model;

public class PaloCarta extends Carta {

    private Figura figura;

    public PaloCarta(Figura figura, Palo pal) {
        super.palo = pal;
        this.figura = figura;
    }

    /**
     *  ToString method to display card data
     * @return --> Num, Pal i valor
     */
    @Override
    public String toString() {
        return super.toString(this.figura.toString());
    }

    @Override
    public String obtenerCodigoCarta() {
        return this.figura.toString()+"_"+ palo;
    }


}
