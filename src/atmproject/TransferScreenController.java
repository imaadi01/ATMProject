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
 */
public class TransferScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label accountNo;
    @FXML
    private Label name;
    @FXML
    private Label error;
    @FXML
    private TextField amount;
    @FXML
    private TextField transferaccountNo;

    int account;
    int transferaccount;
    int amountCash;
    String nameUser;
    
    // Constructor
    public void setAccount(int account) {
        this.account = account;
        this.accountNo.setText(String.valueOf(account));
    }

    // Setter Methods to assign values to the variable
    public void setNameUser(String nameUser) 
    {
        this.nameUser = nameUser;
        this.name.setText(nameUser);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
   public String Date_Input()
    {
   String s = java.time.LocalDate.now().toString();
        System.out.println(s);
       return s;
    }

   
   // Main method for working of transfer button
    @FXML
    private void Transfer(ActionEvent event) 
    {
        int accountAvailable = 0; 
        amountCash = Integer.parseInt(amount.getText());
        transferaccount = Integer.parseInt(transferaccountNo.getText());
        int currentAmount = 0 ;
        
        databaseconnection database = new databaseconnection(); 
        Connection con = database.Connectivity();   // To get connection from our database
        Statement statement = null;
        String query = "select accountNo from user_accounts where accountNo='"+ transferaccount +"'";   // Query to verify whether accout number to whom we are transferring money exist or not
        ResultSet result = null;
        try {
            statement = con.createStatement();
        result = statement.executeQuery(query);
            while (result.next()) {                
                accountAvailable = Integer.parseInt(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransferScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // To verify whether entered amount is greater than zero or not
        if(amountCash>0)
        {
        // to verify whether accout number to whom we are transferring money exist or not    
            if(accountAvailable!=0)
            {
        query = "select accountBalance from user_accounts where accountNo='"+ account +"'";
        try
        {
        statement = con.createStatement();
        result = statement.executeQuery(query);
        while(result.next())
        {
            currentAmount = Integer.parseInt(result.getString(1));
            System.out.println(Integer.parseInt(result.getString(1)));
            
        }
        // to verify that amount that we are transfering is not greater than amount prresent currently
        if(amountCash<=currentAmount)
        {
        query = "select accountBalance from user_accounts where accountNo='"+ transferaccount +"'";
        statement = con.createStatement();
        result = statement.executeQuery(query);
        while(result.next())
        {
            currentAmount = Integer.parseInt(result.getString(1));
            System.out.println(Integer.parseInt(result.getString(1)));
            
        }
        // Query to add amount to transfer account number
        query = "update user_accounts set accountBalance='"+ (currentAmount+amountCash) +"' where accountNo='"+transferaccount+"'";
            PreparedStatement s = con.prepareStatement(query);
            s.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(amountCash+"Rs Transfered");
        alert.show();
         String activity = "Transfered "+amountCash+" on "+Date_Input();
         
         // To record the transaction happened
        query = "insert into transactions (accountNo,activity) values ('"+ account +"','"+ activity +"')";
            s = con.prepareStatement(query);
            s.executeUpdate();
            
        
        
        query = "select accountBalance from user_accounts where accountNo='"+ account +"'";
        statement = null;
        statement = con.createStatement();
        result = statement.executeQuery(query);
        while(result.next())
        {
            currentAmount = Integer.parseInt(result.getString(1));
            
        }
        if(amountCash<=currentAmount)
        {   
            // deduct amount transfered from our account
        query = "update user_accounts set accountBalance='"+ (currentAmount-amountCash) +"' where accountNo='"+account+"'";
            s = con.prepareStatement(query);
            s.executeUpdate();
        }
        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed");
        alert.setContentText("Amount is High then available Balance");
        alert.show();
        }
        
        }
        catch (SQLException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
            
            else
            {
            error.setText("Incorrect Amount Number");
            }
        }
        else
            error.setText("Incorrect Amount");
    }
    
    // Method for back button for going back to the main screen

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
            amount.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
