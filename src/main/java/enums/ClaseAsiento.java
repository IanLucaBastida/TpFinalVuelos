package enums;

public enum ClaseAsiento {
    ECONOMICA(1),
    ECONOMICA_PREMIUM(1.2),
    EJECUTIVA(1.5),
    PRIMERA(2);

    private final double recargo;

    ClaseAsiento(double recargo) {
        this.recargo = recargo;
    }

    public double getRecargo() {
        return recargo;
    }

}
