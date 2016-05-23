/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andi
 */
public class SecondPlayerNameController implements Initializable {

    @FXML
    Label label;
    
    @FXML
    Button okButton;
    
    @FXML
    TextField userNameTextArea;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            //El kell menteni xml-be a játékos nevét
            GameController.getInstance().addPlayer(userNameTextArea.getText());
            GameController.getInstance().newGame();
            
            //root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test2/view/TableScene.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) okButton.getScene().getWindow();
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.setTitle("Othello");
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FirstPlayerNameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
