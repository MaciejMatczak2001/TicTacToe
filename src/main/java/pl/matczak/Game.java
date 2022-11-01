package pl.matczak;

import java.util.Arrays;

public class Game {
    public static final int DEFAULT_SIZE = 3;
    public final Symbol[][] board;
    private final int size;

    public Game(int size) {
        this.size = size;
        this.board = new Symbol[size][size];
    }

    public Game() {
        this(DEFAULT_SIZE);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            String rowString = Arrays.toString(board[i]);
            result += rowString + "\n";
        }
        return result;
    }

    public void move(Symbol symbol, int x, int y) {
        if (x < 0 || x >= size) {
            throw new GameException("A ten x to chyba nie taki");
        }
        if (y < 0 || y >= size) {
            throw new GameException("A ten y to chyba nie taki");
        }
        if (symbol == null) {
            throw new GameException("Ale że czemu null");
        }
        if (board[x][y] != null) {
            throw new GameException("Zajęte");
        }

        board[x][y] = symbol;
    }

    //Metoda sprawdza czy wszystkie elementy tablicy sa takie same i różne od null, zwraca symbol reprezentujacy zwyciezce
    private Symbol check(Symbol[] arr) {
        Symbol symbol = arr[0];
        if (symbol == null) {
            return null;
        }
        for (int i = 0; i < arr.length; i++) {
            if (symbol != arr[i]) {
                return null;
            }
        }
        return symbol;
    }

    private GameStatus mapSymbolToGameStatus(Symbol symbol) {
        return switch (symbol) {
            case X -> GameStatus.X;
            case O -> GameStatus.O;
        };
    }

    private GameStatus checkInRow() {
        for (int i = 0; i < size; i++) {
            Symbol[] row = board[i];
            Symbol result = check(row);
            if (result != null) {
                return mapSymbolToGameStatus(result);
            }
        }
        return GameStatus.NO_WINNER;
    }

    private GameStatus checkInColumn() {
        Symbol[] column = new Symbol[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                column[j] = board[j][i];
            }
            Symbol result = check(column);
            if (result != null) {
                return mapSymbolToGameStatus(result);
            }
        }
        return GameStatus.NO_WINNER;
    }

    private GameStatus checkInDiagonal1() {
        Symbol[] diagonal1 = new Symbol[size];
        for (int i = 0; i < size; i++) {
            diagonal1[i] = board[i][i];
        }
        Symbol result = check(diagonal1);
        if (result != null) {
            return mapSymbolToGameStatus(result);
        }
//        Symbol[] diagonal1 = {board[0][0], board[1][1], board[2][2]};
        return GameStatus.NO_WINNER;
    }

    private GameStatus checkInDiagonal2() {
//        Symbol[] diagonal2 = {board[0][2], board[1][1], board[2][0]};
        Symbol[] diagonal2 = new Symbol[size];
        for (int i = 0; i < size; i++) {
            diagonal2[i] = board[size - 1 - i][i];
        }
        Symbol result = check(diagonal2);
        if (result != null) {
            return mapSymbolToGameStatus(result);
        }
        return GameStatus.NO_WINNER;
    }

    public GameStatus checkWinner() {
        GameStatus winnerRow = checkInRow();
        GameStatus winnerColumn = checkInColumn();
        GameStatus winnerDiagonal1 = checkInDiagonal1();
        GameStatus winnerDiagonal2 = checkInDiagonal2();
        GameStatus draw = checkDraw();

        if (winnerRow != GameStatus.NO_WINNER) {
            System.out.println("Player " + winnerRow + " won");
        } else if (winnerColumn != GameStatus.NO_WINNER) {
            System.out.println("Player " + winnerColumn + " won");
        } else if (winnerDiagonal1 != GameStatus.NO_WINNER) {
            System.out.println("Player + " + winnerDiagonal1 + " won");
        } else if (winnerDiagonal2 != GameStatus.NO_WINNER) {
            System.out.println("Player " + winnerDiagonal2 + " won");
        } else if (draw != GameStatus.NO_WINNER) {
            System.out.println("It's a draw");
        } else {
            System.out.println("Game in progress");
        }
        return null;
    }

    private GameStatus checkDraw() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    return GameStatus.NO_WINNER;
                }
            }
        }
        return GameStatus.DRAW;
    }
}
