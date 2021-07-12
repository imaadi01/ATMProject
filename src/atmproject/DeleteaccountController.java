/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aadikumar
 */
public class DeleteaccountController implements Initializable {

    @FXML
    private TextField password;
    @FXML
    private Label accountNo;
    @FXML
    private Label name;
    @FXML
    private Label error;

    
    int account;
    int amountCash;
    String nameUser;
    String pass;

    public void setAccount(int account) {
        this.account = account;
        this.accountNo.setText(String.valueOf(account));
    }

    public void setNameUser(String nameUser) 
    {
        this.nameUser = nameUser;
        this.name.setText(nameUser);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void Delete(ActionEvent event) 
    {
        databaseconnection db = new databaseconnection();
        Connection con = db.Connectivity();
        String query = "select pin from users where accountNo='"+account+"'";
        try {
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery(query);
            while (r.next()) {                
                pass = r.getString(1);
            }
            if(pass.equals(password.getText()))
            {
            query = "Delete from users where accountNo='"+account+"'";
                PreparedStatement p = con.prepareStatement(query);
                p.executeUpdate();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Done");
                a.setContentText(" Account Deleted");
                a.show();
                query = "Delete from user_details where accountNo='"+account+"'";
                p = con.prepareStatement(query);
                p.executeUpdate();
                query = "Delete from user_accounts where accountNo='"+account+"'";
                p = con.prepareStatement(query);
                p.executeUpdate();
                FXMLLoader fm = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = null;
                try {
                    root = fm.load();
            FXMLDocumentController main = fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
                } catch (Exception e) 
                {
                    System.out.println(e);
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteaccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void BackButton(ActionEvent event) 
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
            MainscreenController main = fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            main.setAccountNo(account);
            main.setName(String.valueOf(account));
            password.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
