package profile;

public class KeyConverter {
    public static int getBlackKey(int key) {
        return switch (key) {
            case 0 -> 37;
            case 1 -> 39;
            case 2 -> 42;
            case 3 -> 44;
            case 4 -> 46;
            case 5 -> 49;
            case 6 -> 51;
            case 7 -> 54;
            case 8 -> 56;
            case 9 -> 58;
            case 10 -> 61;
            case 11 -> 63;
            case 12 -> 66;
            case 13 -> 68;
            case 14 -> 70;
            case 15 -> 73;
            case 16 -> 75;
            case 17 -> 78;
            case 18 -> 80;
            case 19 -> 82;
            default -> 0;
        };
    }

    public static int getWhiteKey(int key) {
        return switch (key) {
            case 0 -> 36;
            case 1 -> 38;
            case 2 -> 40;
            case 3 -> 41;
            case 4 -> 43;
            case 5 -> 45;
            case 6 -> 47;
            case 7 -> 48;
            case 8 -> 50;
            case 9 -> 52;
            case 10 -> 53;
            case 11 -> 55;
            case 12 -> 57;
            case 13 -> 59;
            case 14 -> 60;
            case 15 -> 62;
            case 16 -> 64;
            case 17 -> 65;
            case 18 -> 67;
            case 19 -> 69;
            case 20 -> 71;
            case 21 -> 72;
            case 22 -> 74;
            case 23 -> 76;
            case 24 -> 77;
            case 25 -> 79;
            case 26 -> 81;
            case 27 -> 83;
            default -> 0;
        };
    }
}
