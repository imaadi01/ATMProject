/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmproject;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 */
public class ProfileEditController implements Initializable {

    @FXML
    private TextField accountno;
    @FXML
    private TextField name;
    @FXML
    private TextField nic;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField email;
    @FXML
    private TextField dob;

    int accountnumber;
    String name_;
    String phonenumber_;
    String email_;
    @FXML
    private TextField changepass;
    @FXML
    private TextField currentpass;
    
    int pin;
    @FXML
    private Label information;

    public void setAccountnumber(int accountnumber) {
        this.accountnumber = accountnumber;
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void Fetchinfo(int account)
    {
    databaseconnection database = new databaseconnection();
        Connection con = database.Connectivity();
        String query = "SELECT Name,NIC,Phone_Number,Email,Date_Of_Birth FROM user_details where accountNo='"+ account +"'";
        Statement statement = null;
        try
        {
        statement = con.createStatement();
        ResultSet result = statement.executeQuery(query); // Returns result srt of values from database
        accountno.setText(String.valueOf(account));
        while(result.next())
        {
            // Setting fields with thewir respective values
            name.setText(result.getString(1));
            nic.setText(result.getString(2));
            phonenumber.setText(result.getString(3));
            email.setText(result.getString(4));
            dob.setText(result.getString(5));
        }
    }
        catch(SQLException e)
        {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setContentText("Profile class Error");
        a.show();
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

    @FXML
    private void SubmitButton(ActionEvent event) 
    {
        accountnumber = Integer.parseInt(accountno.getText());
        setName_(name.getText());
        setPhonenumber_(phonenumber.getText());
        email_=email.getText();
        databaseconnection db = new databaseconnection();
        Connection con = db.Connectivity();
        if(!name_.equals("") && !phonenumber_.equals(""))
        {
        String query = "update user_details set Name='"+name_+"',Phone_Number='"+phonenumber_+"',Email='"+email_+"' where accountNo='"+accountnumber+"'";
        try {
            PreparedStatement s = con.prepareStatement(query);
            int aa=s.executeUpdate();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Success");
        a.setContentText("Profile Updated");
        a.show();
        accountno.getScene().getWindow().hide();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
    }
    
    public void setPin(String pin_) {
        if (pin_.length() == 4) {
            this.pin = Integer.parseInt(pin_);
        } else {
            this.pin = 0;
            information.setText("Pin can only be 4 digit");
        }
    }

    @FXML
    private void ChangePasswordButton(ActionEvent event) 
    {
        String pass = null;
        setPin(changepass.getText());
        databaseconnection database = new databaseconnection();
        Connection con = database.Connectivity();
        String query = "SELECT pin FROM users where accountNo='"+ accountnumber +"'";
        Statement statement = null;
        try
        {
        statement = con.createStatement();
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
        pass = result.getString(1);
        }
        
        if(pass.equals(currentpass.getText()) && pin!=0)
        {
            query = "update users set pin='"+pin+"' where accountNo='"+accountnumber+"'";
            PreparedStatement s1 = con.prepareStatement(query);
            int aa=s1.executeUpdate();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Success");
        a.setContentText("Password Updated");
        a.show();
            }
            else
            {
            Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setContentText("Current Password is wrong");
        a.show();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
