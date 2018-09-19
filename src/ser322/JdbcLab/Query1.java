package ser322.JdbcLab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Query1
{
    public static void main(String[] args)
    {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        if (args.length != 4)
        {
            System.out.println("USAGE: java ser322.JdbcLab <url> <user> <passwd> <driver>");
            System.exit(0);
        }
        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.createStatement();

            // Step 4: Make a query
            rs = stmt.executeQuery("SELECT E.EMPNO, E.ENAME, D.DNAME FROM EMP E, DEPT D WHERE E.DEPTNO = D.DEPTNO;");

            // Step 5: Display the results
            while (rs.next()) {
                System.out.print(rs.getInt("empno") + "\t");
                System.out.print(rs.getString("ename") + "\t ");
                System.out.print(rs.getString("dname") + "\n");
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
