package test2.controller;

import test2.model.Figure;
import test2.model.FigureType;
import test2.model.GameState;
import test2.model.HighScoreEntry;
import test2.model.Player;

public class GameController {

    private GameState gameState;
    private Player players[] = new Player[]{new Player("", FigureType.BLACK), new Player("", FigureType.WHITE)};

    public void addPlayer(String name) {
        int id = -1;
        for (int i = 0; i < 2; i++) {
            if (players[i].getName().equals("")) {
                id = i;
                break;
            }
        }

        if (id == -1) {
            throw new RuntimeException("There are already two players in game");
        }
        players[id].setName(name);
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void newGame() {
        this.gameState = new GameState();
        this.gameState.setPlayer(0, players[0]);
        this.gameState.setPlayer(1, players[1]);
        setTurnPlayer(0);

        markChoices();
    }

    private void endGame() {
        updateHighScoreEntry(players[0]);
        updateHighScoreEntry(players[1]);
    }

    private void updateHighScoreEntry(Player player) {
        FigureType playerFigure = player.getFigureType();
        Figure[][] figures = gameState.getFigures();
        int ally = 0, enemy = 0;

        for (int i = 0; i < figures.length; i++) {
            for (int j = 0; j < figures[i].length; j++) {
                if (figures[i][j].getFigureType().equals(playerFigure)) {
                    ally++;
                } else {
                    enemy++;
                }
            }
        }

        HighScoreEntry entry = new HighScoreEntry();
        entry.setName(player.getName());
        entry.setWins(ally>enemy?1:0);
        entry.setLosses(ally<=enemy?1:0);
        entry.setBestScore(ally);

        HighScoreManager.getInstance().insertOrUpdateEntry(entry);
    }

    private void flipFigures(FigureType playerFigure, int x, int y, int dirX, int dirY) {
        final int originalX = x, originalY = y;
        Figure[][] figures = gameState.getFigures();
        boolean flip = false;

        while (true) {
            x += dirX;
            y += dirY;

            if (x < 0 || y < 0 || x >= 8 || y >= 8) {
                break;
            }

            if (figures[x][y].getFigureType().equals(FigureType.NONE)) {
                break;
            } else if (figures[x][y].getFigureType().equals(playerFigure)) {
                flip = true;
                break;
            }
        }

        if (!flip) {
            return;
        }

        x = originalX;
        y = originalY;

        while (true) {
            x += dirX;
            y += dirY;

            if (figures[x][y].getFigureType().equals(playerFigure)) {
                break;
            }
            figures[x][y].setFigureType(playerFigure);
        }
    }

    public void move(int x, int y) {
        FigureType playerFigure = gameState.getNextPlayer().getFigureType();

        gameState.getFigures()[x][y].setFigureType(playerFigure);

        flipFigures(playerFigure, x, y, -1, -1);
        flipFigures(playerFigure, x, y, 0, -1);
        flipFigures(playerFigure, x, y, 1, -1);
        flipFigures(playerFigure, x, y, -1, 0);
        flipFigures(playerFigure, x, y, 1, 0);
        flipFigures(playerFigure, x, y, -1, 1);
        flipFigures(playerFigure, x, y, 0, 1);
        flipFigures(playerFigure, x, y, 1, 1);
    }

    public void setTurnPlayer(int playerId) {
        gameState.setNextPlayerId(playerId);
    }

    public void switchTurns() {
        gameState.setNextPlayerId(1 - gameState.getNextPlayerId());
    }

    private void findValidField(FigureType playerFigure, int x, int y, int dirX, int dirY) {
        Figure[][] figures = gameState.getFigures();
        boolean[][] choices = gameState.getChoices();
        boolean enemyFound = false;

        while (true) {
            x += dirX;
            y += dirY;

            if (x < 0 || y < 0 || x >= 8 || y >= 8) {
                break;
            }

            if (figures[x][y].getFigureType().equals(FigureType.NONE)) {
                if (enemyFound) {
                    choices[x][y] = true;
                }
                break;
            } else if (!figures[x][y].getFigureType().equals(playerFigure)) {
                enemyFound = true;
            } else {
                break;
            }
        }
    }

    private void findValidFields(FigureType playerFigure, int x, int y) {
        findValidField(playerFigure, x, y, -1, -1);
        findValidField(playerFigure, x, y, 0, -1);
        findValidField(playerFigure, x, y, 1, -1);
        findValidField(playerFigure, x, y, -1, 0);
        findValidField(playerFigure, x, y, 1, 0);
        findValidField(playerFigure, x, y, -1, 1);
        findValidField(playerFigure, x, y, 0, 1);
        findValidField(playerFigure, x, y, 1, 1);
    }

    public void flushChoices() {
        boolean[][] choices = gameState.getChoices();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                choices[i][j] = false;
            }
        }

    }

    public void markChoices() {
        Figure[][] figures = gameState.getFigures();
        FigureType playerFigure = gameState.getNextPlayer().getFigureType();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (figures[i][j].getFigureType().equals(playerFigure)) {
                    findValidFields(playerFigure, i, j);
                }
            }
        }
    }

    public boolean checkPlayerMoveAbility() {
        boolean[][] choices = gameState.getChoices();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (choices[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public int findWinner() {
        int black = gameState.countFigures(FigureType.BLACK);
        int white = gameState.countFigures(FigureType.WHITE);

        int winner = black > white ? 0 : 1;
        return winner;
    }

    public void handleButtonClick(int col, int row) {
        if (gameState.getChoices()[col][row] == false) {
            return;
        }

        move(col, row);
        flushChoices();
        switchTurns();
        markChoices();

        if (!checkPlayerMoveAbility()) {
            switchTurns();
            markChoices();

            if (!checkPlayerMoveAbility()) {
                gameState.setActive(false);
            }
        }

        if (!gameState.isActive()) {
            gameState.setWinner(findWinner());
            endGame();
        }
    }

    private static GameController instance = new GameController();

    public static GameController getInstance() {
        return instance;
    }

    private GameController() {

    }
}
