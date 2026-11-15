package enums;

public enum Destinos {
    TOKYO(10000),
    BUENOS_AIRES(2500),
    PARIS(7000),
    ROMA(7000),
    MADRID(5250);

    private final double precioBase;

    Destinos(double precioBase) {
        this.precioBase = precioBase;
    }

    public double getPrecioBase() {
        return precioBase;
    }
}
