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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import test2.model.HighScoreEntry;

/**
 * FXML Controller class
 *
 * @author Andi
 */
public class HighScoreDialogController implements Initializable {

    @FXML
    TableView tableView;
    
    @FXML
    TableColumn nameColumn;
    
    @FXML
    TableColumn winsColumn;
    
    @FXML
    TableColumn lossesColumn;
    
    @FXML
    TableColumn bestScoreColumn;
    
    @FXML
    Button okButton;
    
    ObservableList<HighScoreEntry> data;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<HighScoreEntry> entries = HighScoreManager.getInstance().getEntries();
        
        data = FXCollections.observableArrayList();
        
        for (HighScoreEntry entry: entries){
            data.add(entry);
        }
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
        bestScoreColumn.setCellValueFactory(new PropertyValueFactory<>("bestScore"));
        
        tableView.setItems(data);
    }    
    
}
