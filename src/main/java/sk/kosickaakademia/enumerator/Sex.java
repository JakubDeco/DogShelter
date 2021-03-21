package sk.kosickaakademia.enumerator;

public enum Sex {
    MALE(0), FEMALE(1);

    private int value;

    Sex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
