package learn.wwsh.domain;

public class Validations {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNullOrZero(int value) {
        return value <= 0;
    }

    public static boolean isNullOrNegative(int value) {
        return value < 0;
    }

    public static boolean isNullOrNegative(double value) {
        return value < 0;
    }
}
