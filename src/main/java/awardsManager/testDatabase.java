package awardsManager;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testDatabase {

    private Connection connect() {
        // SQLite connection string
        String url = "instert path to database here";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll(){
        String sql = "SELECT id, name, capacity FROM warehouses";
        try (Connection c = this.connect();
             Statement statement  = c.createStatement();
             ResultSet rs    = statement.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("example") +  "\t" + 
                                   rs.getString("example") + "\t" +
                                   rs.getDouble("example"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

    public static void main(String[] args) {
        //SelectApp app = new SelectApp();
        //app.selectAll();
    }

}
