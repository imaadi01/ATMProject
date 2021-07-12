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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class MainscreenController implements Initializable {

    @FXML
    private Label nameOfTheUser;
    
    private int accountNo;
    private String Name;
    
    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public void setName(String account) {
        databaseconnection database = new databaseconnection();
        Connection con = database.Connectivity();
        String query = "SELECT Name FROM user_details where accountNo='"+ account +"'";
        Statement statement = null;
        try
        {
        statement = con.createStatement();
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
        Name = result.getString(1);
        }
        
        nameOfTheUser.setText(Name);
        }
        catch(SQLException e)
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Failed");
        alert.show();
        }
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void DepositButton(ActionEvent event) 
    {
            FXMLLoader fm = new FXMLLoader(getClass().getResource("depositscreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            DepositscreenController deposit =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            deposit.setAccount(accountNo);
            deposit.setNameUser(Name);
            nameOfTheUser.getScene().getWindow().hide();
    }

    @FXML
    private void WithdrawButton(ActionEvent event) {
    FXMLLoader fm = new FXMLLoader(getClass().getResource("withdrawScreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            WithdrawScreenController withdraw =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            withdraw.setAccount(accountNo);
            withdraw.setNameUser(Name);
            nameOfTheUser.getScene().getWindow().hide();
    }

    @FXML
    private void CheckBalanceButton(ActionEvent event) {
    FXMLLoader fm = new FXMLLoader(getClass().getResource("checkBalanceScreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            CheckBalanceScreenController check =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            check.setAccount(accountNo);
            check.setNameUser(Name);
            check.checkBalance();
            nameOfTheUser.getScene().getWindow().hide();
    }

    @FXML
    private void TransferButton(ActionEvent event) {
    FXMLLoader fm = new FXMLLoader(getClass().getResource("transferScreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            TransferScreenController transfer =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            transfer.setAccount(accountNo);
            transfer.setNameUser(Name);
            nameOfTheUser.getScene().getWindow().hide();
    }

    @FXML
    private void MobileTopupButton(ActionEvent event) {
    FXMLLoader fm = new FXMLLoader(getClass().getResource("mobileTopupScreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            MobileTopupScreenController mobile =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            mobile.setAccount(accountNo);
            mobile.setNameUser(Name);
            nameOfTheUser.getScene().getWindow().hide();
    }

    @FXML
    private void TransactionHistoryButton(ActionEvent event) {
    FXMLLoader fm = new FXMLLoader(getClass().getResource("transactionHistoryScreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            TransactionHistoryScreenController transcation =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            transcation.setAccount(accountNo);
            transcation.setNameUser(Name);
            transcation.setTransactions();
            nameOfTheUser.getScene().getWindow().hide();
    }

    // Method for Log out button for going back to the login screen
    @FXML
    private void LogOutButton(ActionEvent event) {
    FXMLLoader fm = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = null;
        try {
            root = fm.load();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            nameOfTheUser.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @FXML
    private void ProfileButton(ActionEvent event) 
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("profilescreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
            ProfilescreenController profile = fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            profile.setAccount(accountNo);
            profile.Fetchinfo(accountNo);
            nameOfTheUser.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void BillPaymentButton(ActionEvent event) 
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("billPayment.fxml"));
            Parent root = null;
        try {
            root = fm.load();
            BillPaymentController bills = fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            bills.setAccount(accountNo);
            bills.setNameUser(Name);
            nameOfTheUser.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void BillsHistoryButton(ActionEvent event) 
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("billshistory.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            BillshistoryController bills =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            bills.setAccount(accountNo);
            bills.setNameUser(Name);
            bills.setBillsTransactions();
            nameOfTheUser.getScene().getWindow().hide();
    }

    @FXML
    private void DeleteButton(ActionEvent event) 
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("deleteaccount.fxml"));
            Parent root = null;
        try {
            root = fm.load();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            DeleteaccountController delete =fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            delete.setAccount(accountNo);
            delete.setNameUser(Name);
            nameOfTheUser.getScene().getWindow().hide();
    }
    
}
