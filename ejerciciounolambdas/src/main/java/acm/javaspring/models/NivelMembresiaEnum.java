package acm.javaspring.models;

public enum NivelMembresiaEnum {
    BASICO, PREMIUM, INVESTIGADOR;
    public static long diasPrestamoSegunMembresia(NivelMembresiaEnum nivel){
        return switch (nivel){
            case BASICO -> 14L;
            case PREMIUM -> 30L;
            case INVESTIGADOR -> 60L;
        };
    }
}
