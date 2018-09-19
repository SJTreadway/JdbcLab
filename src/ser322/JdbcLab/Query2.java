package ser322.JdbcLab;

import java.sql.*;

public class Query2
{
    public static void main(String[] args)
    {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        if (args.length != 5)
        {
            System.out.println("USAGE: java ser322.JdbcLab <url> <user> <passwd> <driver> <DeptNo>");
            System.exit(0);
        }
        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("SELECT D.DNAME, C.NAME, ROUND(P.PRICE * C.QUANTITY, 2) as TOTAL_SPENT FROM Dept D, Customer C, Product P WHERE C.PID = P.PRODID AND P.MADE_BY = D.DEPTNO AND D.DEPTNO = ?;");
            stmt.setString(1, args[4]);
            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.print(rs.getString("dname") + "\t");
                System.out.print(rs.getString("name") + "\t ");
                System.out.print(rs.getInt(3));
                System.out.println();
            }
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
