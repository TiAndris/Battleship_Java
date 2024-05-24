package battleship;

public class Cell {
    private char symbol;
    private boolean checked = false;
    public static final char SHIP = 'O';
    public static final char EMPTY = '~';
    public static final char HIT = 'X';
    public static final char MISS = 'M';

    public Cell() {
        this.symbol = EMPTY;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    // Neue Methode, um den 'checked' Wert zu setzen
    public void setChecked(boolean value) {
        this.checked = value;
    }

    // Neue Methode, um den 'checked' Wert abzurufen
    public boolean isChecked() {
        return checked;
    }
}
