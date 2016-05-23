package test2.model;

public class GameState {

    private Player players[] = new Player[2];
    private int nextPlayerId = 0;
    private Figure[][] figures = new Figure[8][8];
    private boolean[][] choices = new boolean[8][8];
    private boolean active = true;
    private int winner = -1;

    public int countFigures(FigureType figureType) {
        int count = 0;
        for (int i = 0; i < figures.length; i++) {
            for (int j = 0; j < figures[i].length; j++) {
                if (figures[i][j].getFigureType().equals(figureType)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public GameState() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                FigureType type = FigureType.NONE;
                if ((i == 3 || i == 4) && (j == 3 || j == 4)) {
                    type = (i == j) ? FigureType.WHITE : FigureType.BLACK;
                }
                figures[i][j] = new Figure(type);
            }
        }
    }

    public int getNextPlayerId() {
        return nextPlayerId;
    }

    public Player getNextPlayer() {
        return players[nextPlayerId];
    }

    public void setNextPlayerId(int nextPlayer) {
        this.nextPlayerId = nextPlayer;
    }

    public Figure[][] getFigures() {
        return figures;
    }

    public boolean[][] getChoices() {
        return choices;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setPlayer(int id, Player player) {
        this.players[id] = player;
    }
}
