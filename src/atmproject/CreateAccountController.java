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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *

*/
public class CreateAccountController implements Initializable {

    
    // Information Required for opening account
    @FXML
    private PasswordField pin;
    @FXML
    private PasswordField pinConfirm;
    @FXML
    private TextField name;
    @FXML
    private TextField nic;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private RadioButton maleGender;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton femaleGender;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private Label information;

    @FXML
    private TextField accountNo;

    private int pin_;
    private int accountnumber_ = 2000000; // Have initialized account number will start from 2000000
    private String name_ = "";
    private String nic_ = "";
    private String phonenumber_ = "";
    private String email_ = "";
    private String gender_ = "";
    private String date = "";

    
    // Setters to assign values to variables with required consitions for assigning values
    public void setPin(String pin_) {
        if (pin_.length() == 4 && pin.getText().equals(pinConfirm.getText())) {
            this.pin_ = Integer.parseInt(pin_);
        } else {
            this.pin_ = 0;
            information.setText("Wrong Pin");
        }
    }
    
    public void setAcoountnumber_() {
        databaseconnection database = new databaseconnection();
        Connection con = database.Connectivity();
        // It will select last account number present in a table and produce new account number for new account
        String query = "select accountNo from user_accounts";
        Statement s = null;
        try {
            s = con.createStatement();
            ResultSet r = s.executeQuery(query);
            while (r.next()) {
                this.accountnumber_ = Integer.parseInt(r.getString(1)) + 1;
            }
            accountNo.setText(String.valueOf(accountnumber_));
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setName_(String name_) {
        char[] n = name_.toCharArray();
        for (int i = 0; i < n.length; i++) 
        {
            // It will not accept any number in name
            if (n[i] == '0' || n[i] == '1' || n[i] == '2' || n[i] == '3' || n[i] == '4' || n[i] == '5' || n[i] == '6' || n[i] == '7' || n[i] == '8' || n[i] == '9') {
                name_ = "";
                information.setText("Wrong Name Inout");
                break;
            } else {
                this.name_ = name_;
            }
        }
    }

    public void setNic_(String nic_) {
        char[] n = nic_.toCharArray();
        if (nic_.length() == 13) 
        {
            // Will not accept any nic number whose length is less than 13 and will not accept any character
            for (int i = 0; i < n.length; i++) {
                if (n[i] < 48 || n[i] > 57) {
                    nic_ = "";
                    information.setText("Wrong NIC Inout");
                    break;
                } else {
                    this.nic_ = nic_;
                }
            }
        }
    }

    public void setPhonenumber_(String phonenumber_) {
        char[] n = phonenumber_.toCharArray();
        // Will not accept any number whose length is less than 11 and will not accept any character
        if (phonenumber_.length() == 11) 
        {
            
            for (int i = 0; i < n.length; i++) {
                if (n[i] < 48 || n[i] > 57) {
                    phonenumber_ = "";
                    information.setText("Wrong Phone Number Inout");
                    break;
                } else {
                    this.phonenumber_ = phonenumber_;
                }
            }
        }

    }

    public void setEmail_(String email_) {
        if (!email.getText().equals("")) {
            this.email_ = email_;
        } else {
            email_ = "";
            information.setText("Enter Email");
        }
    }

    public void setGender_() {
        if (maleGender.isSelected()) {
            this.gender_ = "Male";
        } else if (femaleGender.isSelected()) {
            this.gender_ = "Female";
        } else {
            this.gender_ = "";
            information.setText("Select Gender");
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
    private void createAccount(ActionEvent event) {
        setEmail_(email.getText());
        setGender_();
        setName_(name.getText());
        setNic_(nic.getText());
        setPhonenumber_(phoneNumber.getText());
        date = dateOfBirth.getValue().toString();
        setPin(pin.getText());
        if (!email_.equals("") && !gender_.equals("") && !name_.equals("") && !nic_.equals("") && !phonenumber_.equals("") && !date.equals("") && pin_ != 0) {
            databaseconnection database = new databaseconnection();
            Connection con = database.Connectivity();
            // Query to insert information in user_details table in database
            String query = "insert into user_details (accountNo,Name,NIC,Phone_Number,Email,Date_Of_Birth) values ('" + accountnumber_ + "','" + name_ + "','" + nic_ + "','" + phonenumber_ + "','" + email_ + "','" + date + "')";
            // Query to insert information in user_account table in database and setting balancing zero for new account
            String query1 = "insert into user_accounts (accountNo,accountBalance) values ('" + accountnumber_ + "','" + 0 + "')";
            // Query to insert login information in users table in database
            String query2 = "insert into users (accountNo,pin) values ('" + accountnumber_ + "','" + pin_ + "')";

            Statement s = null;
            try {
                s = con.createStatement();
                s.executeUpdate(query);
                s.executeUpdate(query1);
                s.executeUpdate(query2);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Account Successfully created \n Account Number: " + accountnumber_ + "\n PIN: " + pin_);
                alert.setTitle("Success");
                alert.show();
                
                // After opening account it will go back to the login screen
                FXMLLoader fm = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                Parent root = null;
                root = fm.load();
                Stage stage = new Stage();
                Scene scene1 = new Scene(root);
                stage.setScene(scene1);
                stage.setTitle("ATM MACHINE BY ABC");
                stage.show();
                email.getScene().getWindow().hide();
            } catch (SQLException ex) {
                Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Wrong data ");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    private void BackButton(ActionEvent event)
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
            Parent root = null;
        try {
            root = fm.load();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
