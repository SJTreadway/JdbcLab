package ser322.JdbcLab;

import java.sql.*;

public class Dml1
{
    public static void main(String[] args)
    {
        PreparedStatement stmt = null;
        Connection conn = null;

        if (args.length != 8)
        {
            System.out.println("USAGE: java ser322.JdbcLab.Dml1 <url> <user> <passwd> <driver> <customer id> <product id> <name> <quantity>");
            System.exit(0);
        }
        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("INSERT INTO CUSTOMER VALUES(?, ?, ?, ?);");
            stmt.setInt(1, new Integer(args[4]));
            stmt.setInt(2, new Integer(args[5]));
            stmt.setString(3, args[6]);
            stmt.setInt(4, new Integer(args[7]));

            // Step 4: Make a query
            stmt.executeUpdate();

            System.out.println("SUCCESS! Added Customer: <" + args[4] + ", " + args[5] + ", " + args[6] + ", " + args[7] + ">");
        }
        catch (SQLException sqlexc)
        {
            sqlexc.printStackTrace();
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        finally {  // ALWAYS clean up your DB resources
            try {
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
