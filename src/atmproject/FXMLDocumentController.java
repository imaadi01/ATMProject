/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private PasswordField pinPassword;
    @FXML
    private RadioButton adminUser;
    @FXML
    private ToggleGroup userAccount;
    @FXML
    private RadioButton normalUser;
    @FXML
    private TextField accountNo;
    
    
    String account;
    String pass;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logIn(ActionEvent event) 
    {
        account = accountNo.getText();
        databaseconnection database = new databaseconnection();
        Connection con = database.Connectivity();
        String query = "SELECT pin FROM users where accountNo='"+ account +"'";
        Statement statement = null;
        try
        {
        statement = con.createStatement();
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
        pass = result.getString(1);
        }
        
        if(pass.equals(pinPassword.getText()))
        {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
            Parent root = null;
            try {
            root = fm.load();
            MainscreenController main =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            main.setAccountNo(Integer.parseInt(account));
            main.setName(account);
            accountNo.getScene().getWindow().hide();
        }
        catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        }
        catch(SQLException ex)
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Failed");
        alert.show();
        }
        
        
    }

    @FXML
    private void createAnAccount(MouseEvent event)
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("createAccount.fxml"));
            Parent root = null;
        try {
            root = fm.load();
            CreateAccountController create =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            create.setAcoountnumber_();
            accountNo.getScene().getWindow().hide();
        }
        catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
