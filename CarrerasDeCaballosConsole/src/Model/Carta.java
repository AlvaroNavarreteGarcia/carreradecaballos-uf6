package Model;

public class Carta {
    private TipoCarta tipoCarta; // Tipo de Carta (Bastos, Oros, Copas y Espadas)
    private int numero; // numero de la carta

    // Constructor para crear una nueva carta con un tipo y número específicos
    public Carta(TipoCarta tipoCarta, int numero) {
        this.tipoCarta = tipoCarta;
        this.numero = numero;
    }
    // Método para obtener el tipo de carta
    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    @Override
    public String toString() {
        String tipo = "";
        switch (tipoCarta) {
            case BASTOS:
                tipo = "Bastos";
                break;
            case OROS:
                tipo = "Oros";
                break;
            case ESPADAS:
                tipo = "Espadas";
                break;
            case COPAS:
                tipo = "Copas";
                break;
            default:
                break;
        }
        return  numero + " de " + tipo;
    }
}
