package pl.matczak;

public class Main {
    public static void main(String[] args) {
        Game game1 = new Game(Game.DEFAULT_SIZE);
        game1.move(Symbol.O, 2, 2);
        game1.move(Symbol.X, 1, 2);
        System.out.println(game1);
        game1.checkWinner();
    }
}
