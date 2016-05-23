/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import test2.model.Figure;
import test2.model.FigureType;
import test2.model.GameState;

public class TableSceneController implements Initializable {

    @FXML
    MenuBar menuBar;

    @FXML
    Menu gameMenu;

    @FXML
    MenuItem newGame;

    @FXML
    MenuItem score;

    @FXML
    MenuItem exit;

    @FXML
    GridPane buttonGrid;

    Image BLACK_IMAGE;
    Image WHITE_IMAGE;
    Image EMPTY_IMAGE;

    @FXML
    private void handleNewGameAction(ActionEvent event) {
        GameController.getInstance().newGame();
        refreshView();
    }

    @FXML
    private void handleScoreAction(ActionEvent event) {
        try {
            //root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test2/view/HighScoreDialog.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) buttonGrid.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Othello");
            
            TableView tableView = (TableView) scene.lookup("#messageLabel");

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FirstPlayerNameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        Stage stage;

        stage = (Stage) menuBar.getScene().getWindow();

        stage.close();
    }

    @FXML
    private void handleTableButtonAction(ActionEvent event) {
        List<Node> children = buttonGrid.getChildren();

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                int id = i * 8 + j;
                if (children.get(id).equals(event.getSource())) {
                    GameController.getInstance().handleButtonClick(j, i);
                }
            }
        }

        if (GameController.getInstance().getGameState().isActive()) {
            refreshView();
        } else {
            try {
                //root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/test2/view/VictoryScene.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) buttonGrid.getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("Othello");

                int winner = GameController.getInstance().getGameState().getWinner();
                String text = GameController.getInstance().getPlayers()[winner].getName() + " nyert!";

                Label label = (Label) scene.lookup("#messageLabel");
                label.setText(text);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FirstPlayerNameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshView() {
        GameState gameState = GameController.getInstance().getGameState();
        Figure[][] figures = gameState.getFigures();
        boolean[][] choices = gameState.getChoices();

        List<Node> children = buttonGrid.getChildren();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                int id = i * 8 + j;
                Button button = ((Button) children.get(id));
                button.setVisible(true);

                ImageView img = (ImageView) button.getChildrenUnmodifiable().get(0);
                switch (figures[j][i].getFigureType()) {
                    case BLACK:
                        img.setImage(BLACK_IMAGE);
                        break;
                    case WHITE:
                        img.setImage(WHITE_IMAGE);
                        break;
                    case NONE:
                        img.setImage(EMPTY_IMAGE);
                        break;
                }
                if (choices[j][i]) {
                    button.setStyle("-fx-background-color: #00FF00;");
                } else {
                    button.setStyle("-fx-background-color: #FF0000;");
                    button.setStyle("-fx-background-color: #397D42;");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BLACK_IMAGE = new Image(FigureType.BLACK.getTexturePath());
        WHITE_IMAGE = new Image(FigureType.WHITE.getTexturePath());
        EMPTY_IMAGE = new Image(FigureType.NONE.getTexturePath());

        //refreshView();
    }
}
