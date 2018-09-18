package ser322.JdbcLab;

import java.sql.*;

public class Dml1
{
    public static void main(String[] args)
    {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        if (args.length != 8)
        {
            System.out.println("USAGE: java ser322.JdbcLab <url> <user> <passwd> <driver> Dml1 <customer id> <product id> <name> <quantity>");
            System.exit(0);
        }
        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("INSERT INTO CUSTOMER('CUSTID', 'PID', 'NAME', 'QUANTITY') VALUES(?, ?, ?, ?, ?);");
            stmt.setString(1, args[4]);
            stmt.setString(2, args[5]);
            stmt.setString(3, args[6]);
            stmt.setString(4, args[7]);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            System.out.println("SUCCESS!");
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        finally {  // ALWAYS clean up your DB resources
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
