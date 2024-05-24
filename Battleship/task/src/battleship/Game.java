package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Field field1;
    private final Field field2;
    public final Field fogField1;
    public final Field fogField2;
    public Scanner scanner = new Scanner(System.in);
    private final List<Ship> ships;

    private boolean skip = true;

    private boolean isPlayer1Turn = true;

    public Game() {
        this.field1 = new Field();
        this.fogField1 = new Field();
        this.field2 = new Field();
        this.fogField2 = new Field();
        this.ships = new ArrayList<>();  // initialize the list
        this.ships.add(new Ship("Aircraft Carrier", 5));
        this.ships.add(new Ship("Battleship", 4));
        this.ships.add(new Ship("Submarine", 3));
        this.ships.add(new Ship("Cruiser", 3));
        this.ships.add(new Ship("Destroyer", 2));
        // rest of the code...
    }

    public void startGame() {
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        for (Ship ship : ships) {
            field1.printField();
            System.out.println();
            placeShip1(ship);
        }
        field1.printField();
        System.out.println();
        promptEnterKey();
        System.out.println("Player 2, place your ships on the game field");
        System.out.println();
        for (Ship ship : ships) {
            field2.printField();
            System.out.println();
            placeShip2(ship);
        }
        field2.printField();
        System.out.println();
        promptEnterKey();
        takeAShot();
//        field.printField();
//        System.out.println();
//        System.out.println("The game starts!");
//        System.out.println();
        //field.printField();
//        fogField.printFogField();
//        takeAShot();
        //field.printField();

    }

    public void placeShip1(Ship ship) {
        while (true) {
            if (skip){
                System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");
            }
            System.out.println();
            String start = scanner.next();
            String end = scanner.next();
            ship.setStartCoordinate(start);
            ship.setEndCoordinate(end);
            if (validPlacement1(ship)) {
                field1.placeShip(ship);
                skip = true;
                break;
            } else {
                System.out.println();
                field1.canPlaceShip(ship);
                skip = false;
            }
        }
        System.out.println();
    }

    public void placeShip2(Ship ship) {
        while (true) {
            if (skip){
                System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");
            }
            System.out.println();
            String start = scanner.next();
            String end = scanner.next();
            ship.setStartCoordinate(start);
            ship.setEndCoordinate(end);
            if (validPlacement2(ship)) {
                field2.placeShip(ship);
                skip = true;
                break;
            } else {
                System.out.println();
                field2.canPlaceShip(ship);
                skip = false;
            }
        }
        System.out.println();
    }

    private boolean validPlacement1(Ship ship) {
        int[] start = field1.translateCoordinate(ship.getStartCoordinate());
        int[] end = field1.translateCoordinate(ship.getEndCoordinate());

        if (Math.abs(start[0] - end[0]) + 1 == ship.getSize() || Math.abs(start[1] - end[1]) + 1 == ship.getSize()) {
            return (field1.hasCorrectLength(ship) && field1.hasValidLocation(ship) && !field1.isTooCloseToAnotherShip(ship));
        }
        return false;
    }

    private boolean validPlacement2(Ship ship) {
        int[] start = field2.translateCoordinate(ship.getStartCoordinate());
        int[] end = field2.translateCoordinate(ship.getEndCoordinate());

        if (Math.abs(start[0] - end[0]) + 1 == ship.getSize() || Math.abs(start[1] - end[1]) + 1 == ship.getSize()) {
            return (field2.hasCorrectLength(ship) && field2.hasValidLocation(ship) && !field2.isTooCloseToAnotherShip(ship));
        }
        return false;
    }

    private void takeAShot(){
        while (true) {
            if (isPlayer1Turn){
                System.out.println();
                fogField2.printFogField();
                System.out.println("---------------------");
                field1.printField();
                System.out.println();
                System.out.println("Player 1, it's your turn: ");
                String shot = scanner.next();
                System.out.println();

                if (isValidShot(shot)){
                    field2.shooting(shot, fogField2);
                    isPlayer1Turn = false;
                    promptEnterKey();
                } else {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    continue; // No need to call takeAShot(), just continue the loop
                }

                if (field2.sankAllShip()) {
                    System.out.println("Player 1 wins!");
                    return;
                }

            } else {
                System.out.println();
                fogField1.printFogField();
                System.out.println("---------------------");
                field2.printField();
                System.out.println();
                System.out.println("Player 2, it's your turn: ");
                String shot = scanner.next();
                System.out.println();

                if (isValidShot(shot)){
                    field1.shooting(shot, fogField1);
                    isPlayer1Turn = true;
                    promptEnterKey();
                } else {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    continue; // No need to call takeAShot(), just continue the loop
                }

                if (field1.sankAllShip()) {
                    System.out.println("Player 2 wins!");
                    return;
                }
            }
        }
    }

    private boolean isValidShot(String shot){
        if (shot.length() < 2 || shot.length() > 3) {
            return false;
        }

        char row = shot.charAt(0);
        String columnStr = shot.substring(1);
        int column;

        if (row < 'A' || row > 'J') {
            return false;
        }

        try {
            column = Integer.parseInt(columnStr);
            if (column < 1 || column > 10) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
