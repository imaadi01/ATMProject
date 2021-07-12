package atmproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class databaseconnection {
    Connection con;
    public Connection Connectivity() 
    {
        String user = "root";
        String pass = "";
        String URL = "jdbc:mysql://localhost:3306/atmmachine?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(URL,user,pass);
        } catch (ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("database function Failed ");
        alert.setTitle("Connection Error");
        alert.show();
        }
        return con;
    }
}
