package jdbctest;

import java.sql.*;

public class App {

    private static final String URL = "jdbc:postgresql://localhost/jdbctest";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public String properCase(String s) {
        String result = s;
        try (Connection conn = this.connect();
             CallableStatement properCase = conn.prepareCall("{ ? = call initcap( ? ) }")) {
            properCase.registerOutParameter(1, Types.VARCHAR);
            //properCase.setString(2, s);
            properCase.setNull(2, Types.VARCHAR);
            properCase.execute();
            result = properCase.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.properCase("this is the actor list:"));
    }
}