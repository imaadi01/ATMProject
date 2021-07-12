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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class BillPaymentController implements Initializable {

    @FXML
    private TextField amount;
    @FXML
    private Label accountNo;
    @FXML
    private Label name;
    @FXML
    private Label error;
    @FXML
    private ComboBox billtype;

    
    int account;
    int amountCash;
    String nameUser;
    @FXML
    private TextField bill_id;

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
        billtype.setItems(FXCollections.observableArrayList("Electricity","Water","Gas"));
    }    
    public String Date_Input()
    {
    String s = java.time.LocalDate.now().toString();
        System.out.println(s);
       return s;
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
            amount.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Pay(ActionEvent event) 
    {
        String billnumber = bill_id.getText(); // to get mobile number entered
        String bill_type = billtype.getValue().toString();
        int currentAmount = 0;
        amountCash = Integer.parseInt(amount.getText()); // To get amount entered for easyload
        if(amountCash>0)
        {
        databaseconnection database = new databaseconnection();
        Connection con = database.Connectivity();
        String query = "select accountBalance from user_accounts where accountNo='"+ account +"'";
        Statement statement = null;
        try
        {
        statement = con.createStatement();
        ResultSet result = statement.executeQuery(query); 
        while(result.next())
        {
            currentAmount = Integer.parseInt(result.getString(1));
            
        }
        // To verify that amount entered for easyload is less than available balance
        if(amountCash<currentAmount)
        { 
            // To deduct amount easyloaded from the current balance.
        query = "update user_accounts set accountBalance='"+ (currentAmount-amountCash) +"' where accountNo='"+account+"'";
            PreparedStatement s = con.prepareStatement(query);
            s.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(bill_type+" Bill Paid");
        alert.show();
        
        // to record transaction
        query = "insert into bill_payments (bill_id,accountNo,amount,bill_type,date) values ('"+ Integer.parseInt(billnumber) +"','"+ account +"','"+ amountCash +"','"+ bill_type +"','"+ Date_Input() +"')";
            s = con.prepareStatement(query);
            s.executeUpdate();
        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sorry");
        alert.setContentText(amountCash+" is greater than available balance");
        alert.show();
        }
        }
        catch (SQLException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
}
