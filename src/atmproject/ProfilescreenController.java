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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class ProfilescreenController implements Initializable {

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

    
    int account;

    public void setAccount(int account) {
        this.account = account;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    // // Method to fetch data from database
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
        }
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
            main.setAccountNo(Integer.parseInt(accountno.getText()));
            main.setName(accountno.getText());
            name.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void EditButton(ActionEvent event) 
    {
        FXMLLoader fm = new FXMLLoader(getClass().getResource("profileEdit.fxml"));
            Parent root = null;
        try {
            root = fm.load();
            ProfileEditController edit = fm.getController();
            Stage stage = new Stage();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.setTitle("ATM MACHINE BY ABC");
            stage.show();
            edit.Fetchinfo(account);
            edit.setAccountnumber(account);
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
