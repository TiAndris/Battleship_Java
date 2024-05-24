package battleship;

public class Field {
    private final Cell[][] cells = new Cell[10][10];
    private final Cell[][] fogCells = new Cell[10][10];

    public Field() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell();
                fogCells[i][j] = new Cell();
            }
        }
    }

    public int[] translateCoordinate(String coordinate) {
        int row = coordinate.charAt(0) - 'A';
        int column = Integer.parseInt(coordinate.substring(1)) - 1;
        return new int[]{row, column};
    }

    public void placeShip(Ship ship) {
        int[] start = translateCoordinate(ship.getStartCoordinate());
        int[] end = translateCoordinate(ship.getEndCoordinate());

        for (int i = Math.min(start[0], end[0]); i <= Math.max(start[0], end[0]); i++) {
            for (int j = Math.min(start[1], end[1]); j <= Math.max(start[1], end[1]); j++) {
                cells[i][j].setSymbol(Cell.SHIP);
            }
        }
    }

    public void shooting(String shot, Field fogField) {
        int[] target = translateCoordinate(shot);
        if (cells[target[0]][target[1]].getSymbol() == Cell.SHIP) {
            cells[target[0]][target[1]].setSymbol(Cell.HIT);
            fogField.fogCells[target[0]][target[1]].setSymbol(Cell.HIT);
            //printField(); //print my field
            //printFogField();
            System.out.println();
            if (sankAllShip()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else if (sankShip()) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!");
            }

        } else if (cells[target[0]][target[1]].getSymbol() == Cell.EMPTY) {
            cells[target[0]][target[1]].setSymbol(Cell.MISS);
            fogField.fogCells[target[0]][target[1]].setSymbol(Cell.MISS);
            //printField(); // print my field
            //printFogField();
            System.out.println();
            System.out.println("You missed.");
        } else if (cells[target[0]][target[1]].getSymbol() == Cell.HIT || cells[target[0]][target[1]].getSymbol() == Cell.MISS) {
            //printFogField();
            System.out.println();
            System.out.println("You have already tried this. Try again:");
        }
    }

//    public void getShot(String shot){
//        int[] target = translateCoordinate(shot);
//        if (cells[target[0]][target[1]].getSymbol() == Cell.SHIP) {
//            System.out.println("Take a shot!");
//        } else {
//            System.out.println("You missed!");
//        }
//    }

    // Prüft, ob die Schiffslänge korrekt ist
    public boolean hasCorrectLength(Ship ship) {
        int[] start = translateCoordinate(ship.getStartCoordinate());
        int[] end = translateCoordinate(ship.getEndCoordinate());
        boolean sameRow = start[0] == end[0];
        boolean sameColumn = start[1] == end[1];

        if (sameRow) {
            return Math.abs(end[1] - start[1]) + 1 == ship.getSize();
        } else if (sameColumn) {
            return Math.abs(end[0] - start[0]) + 1 == ship.getSize();
        }
        return false;
    }

    // Prüft, ob das Schiff entweder horizontal oder vertikal ist, aber nicht diagonal
    public boolean hasValidLocation(Ship ship) {
        int[] start = translateCoordinate(ship.getStartCoordinate());
        int[] end = translateCoordinate(ship.getEndCoordinate());
        return start[0] == end[0] || start[1] == end[1];
    }

    // Prüft, ob das Schiff zu nahe an einem anderen Schiff platziert ist
    public boolean isTooCloseToAnotherShip(Ship ship) {
        int[] start = translateCoordinate(ship.getStartCoordinate());
        int[] end = translateCoordinate(ship.getEndCoordinate());

        // Dynamische Zuweisung von Start und Ende basierend auf ihren Werten
        int startRow = Math.min(start[0], end[0]);
        int endRow = Math.max(start[0], end[0]);
        int startCol = Math.min(start[1], end[1]);
        int endCol = Math.max(start[1], end[1]);

        int startRowCheck = Math.max(0, startRow - 1);
        int endRowCheck = Math.min(9, endRow + 1);
        int startColCheck = Math.max(0, startCol - 1);
        int endColCheck = Math.min(9, endCol + 1);

        for (int i = startRowCheck; i <= endRowCheck; i++) {
            for (int j = startColCheck; j <= endColCheck; j++) {
                // Überprüfen, ob die Zelle nicht zu dem aktuell platzierten Schiff gehört
                if (!((i >= startRow && i <= endRow) && (j >= startCol && j <= endCol))) {
                    if (cells[i][j].getSymbol() == Cell.SHIP) {
                        return true;  // Ein Schiff befindet sich zu nahe
                    }
                }
            }
        }
        return false;
    }

    public void canPlaceShip(Ship ship) {
        if (!hasValidLocation(ship)) {
            System.out.println("Error! Wrong ship location! Try again:");

        } else if (!hasCorrectLength(ship)) {
            System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");

        } else if (isTooCloseToAnotherShip(ship)) {
            System.out.println("Error! You placed it too close to another one. Try again:");

        }
    }

    public void printField() {
        System.out.print("  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(cells[i][j].getSymbol() + " ");
            }
            System.out.println();
        }
    }

    public void printFogField() {
        System.out.print("  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(fogCells[i][j].getSymbol() + " ");
            }
            System.out.println();
        }
    }

    public boolean sankAllShip() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j].getSymbol() == Cell.SHIP) {
                    return false;  // At least one ship is still floating
                }
            }
        }
        return true;  // No ships left
    }

    //    public boolean sankShip(){
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                if (cells[i][j].getSymbol() == Cell.HIT) {
//                    // Checking cells around for SHIP symbol
//                    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
//                    for (int[] direction : directions) {
//                        int newRow = i + direction[0];
//                        int newCol = j + direction[1];
//                        if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10 && cells[newRow][newCol].getSymbol() == Cell.SHIP) {
//                            return false; // Ship is not fully sunk yet
//                        }
//                    }
//                }
//            }
//        }
//        return true; // Ship is sunk
//    }
//    public boolean sankShip() {
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                if (cells[i][j].getSymbol() == Cell.HIT) {
//                    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
//                    for (int[] direction : directions) {
//                        int newRow = i + direction[0];
//                        int newCol = j + direction[1];
//                        if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10 && cells[newRow][newCol].getSymbol() == Cell.SHIP) {
//                            return false; // Ship is not fully sunk yet
//                        }
//                    }
//                }
//            }
//        }
//        return true; // Ship is sunk
//    }
    public boolean sankShip() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j].getSymbol() == Cell.HIT && !cells[i][j].isChecked()) {
                    if (dfs(i, j)) {
                        return true; // Ein Schiff wurde versenkt
                    }
                }
            }
        }
        return false; // Kein Schiff wurde in diesem Schuss versenkt
    }

    private boolean dfs(int row, int col) {
        if (row < 0 || row >= 10 || col < 0 || col >= 10 || cells[row][col].isChecked()) {
            return true;
        }

        if (cells[row][col].getSymbol() == Cell.SHIP) {
            return false; // Ein Teil des Schiffes wurde nicht getroffen
        }

        cells[row][col].setChecked(true); // Markiere die Zelle als überprüft

        if (cells[row][col].getSymbol() != Cell.HIT) {
            return true; // Wenn es kein HIT ist, beenden wir die DFS hier
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            if (!dfs(row + direction[0], col + direction[1])) {
                return false;
            }
        }
        return true;
    }
}
