package ser322.JdbcLab;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class Export {

    //replace file with Country.xml

    public static void main(String[] args) {
        final String JDBCURL = args[3];
        final String JDBCDRIVER = args[0];
        final String CUSTOMERQUERY = "select * from customer";
        final String DEPTQUERY = "select * from dept";
        final String EMPQUERY = "select * from emp";
        final String PRODUCTQUERY = "select * from product";
        String OUTPUTFILE = args[4];

        try {

            /** Step 1 : Making a JDBC Connection with database" **/
            Class.forName(JDBCURL);
            Connection conn = DriverManager.getConnection(JDBCDRIVER, args[1], args[2]);

            /** Step 2 : Retrieve the customer data from database **/
            Statement statement1 = conn.createStatement();
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            ResultSet customerRS = statement1.executeQuery(CUSTOMERQUERY);
            ResultSet employeeRS = statement2.executeQuery(EMPQUERY);
            ResultSet deptRS = statement3.executeQuery(DEPTQUERY);
            ResultSet productRS = statement4.executeQuery(PRODUCTQUERY);

            /** Step 3 : Build customer XML DOM **/
            Document xmlDoc = buildXML(customerRS, employeeRS, deptRS, productRS);

            /** Step 4 : Write output to a file **/
            File outputFile = new File(OUTPUTFILE);
            printDOM(xmlDoc, outputFile);

            conn.close(); /*Connection close*/
        } catch (FileAlreadyExistsException f) {
            System.out.println("file already present at this location");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }//Main

    /*Build XML Document from database. The XML object is returned to main method where it is written to flat file.*/
    private static Document buildXML(ResultSet _customerRS, ResultSet _employeeRS,
                                     ResultSet _deptRS, ResultSet _productRS) throws Exception {
        Document xmlDoc = new DocumentImpl();
        /* Creating the root element */
        Element rootElement = xmlDoc.createElement("JDBCLabTable");
        xmlDoc.appendChild(rootElement);

        // Build Customer Data
        while (_customerRS.next()) {
            Element customer = xmlDoc.createElement("customer");

            /* Creating elements within customer DOM*/
            Element custID = xmlDoc.createElement("custid");
            Element pid = xmlDoc.createElement("pid");
            Element name = xmlDoc.createElement("name");
            Element quantity = xmlDoc.createElement("quantity");

            /* Populating Customer DOM with Data*/
            custID.appendChild(xmlDoc.createTextNode(_customerRS.getString("custid")));
            pid.appendChild(xmlDoc.createTextNode(_customerRS.getString("pid")));
            name.appendChild(xmlDoc.createTextNode(_customerRS.getString("name")));
            quantity.appendChild(xmlDoc.createTextNode(_customerRS.getString("quantity")));

            /* Adding elements to the customer Element*/
            customer.appendChild(custID);
            customer.appendChild(pid);
            customer.appendChild(name);
            customer.appendChild(quantity);

            /* Appending customer to the Root Class*/
            rootElement.appendChild(customer);
        }

        // Build Employee Data
        while (_employeeRS.next()) {
            Element emp = xmlDoc.createElement("employee");

            /* Creating elements within employee DOM*/
            Element empNo = xmlDoc.createElement("empno");
            Element empName = xmlDoc.createElement("ename");
            Element job = xmlDoc.createElement("job");
            Element mgr = xmlDoc.createElement("mgr");
            Element sal = xmlDoc.createElement("sal");
            Element comm = xmlDoc.createElement("comm");
            Element deptno = xmlDoc.createElement("deptno");

            /* Populating Employee DOM with Data*/
            empNo.appendChild(xmlDoc.createTextNode(_employeeRS.getString("empno")));
            empName.appendChild(xmlDoc.createTextNode(_employeeRS.getString("ename")));
            job.appendChild(xmlDoc.createTextNode(_employeeRS.getString("job")));
            mgr.appendChild(xmlDoc.createTextNode(_employeeRS.getString("mgr")));
            sal.appendChild(xmlDoc.createTextNode(_employeeRS.getString("sal")));
            comm.appendChild(xmlDoc.createTextNode(_employeeRS.getString("comm")));
            deptno.appendChild(xmlDoc.createTextNode(_employeeRS.getString("deptno")));

            /* Adding the elements to the Employee Element*/
            emp.appendChild(empName);
            emp.appendChild(empNo);
            emp.appendChild(job);
            emp.appendChild(mgr);
            emp.appendChild(sal);
            emp.appendChild(comm);
            emp.appendChild(deptno);

            /* Appending emp to the Root Class*/
            rootElement.appendChild(emp);
        }

        // Build Department Data
        while (_deptRS.next()) {
            Element dept = xmlDoc.createElement("department");

            /* Creating elements within department DOM*/
            Element deptno = xmlDoc.createElement("deptno");
            Element dname = xmlDoc.createElement("dname");
            Element loc = xmlDoc.createElement("loc");

            /* Populating Department DOM with Data*/
            deptno.appendChild(xmlDoc.createTextNode(_deptRS.getString("deptno")));
            dname.appendChild(xmlDoc.createTextNode(_deptRS.getString("dname")));
            loc.appendChild(xmlDoc.createTextNode(_deptRS.getString("loc")));

            /* Adding the elements to the Department Element*/
            dept.appendChild(deptno);
            dept.appendChild(dname);
            dept.appendChild(loc);

            /* Appending dept to the Root Class*/
            rootElement.appendChild(dept);
        }

        // Build Product Data
        while (_productRS.next()) {
            Element product = xmlDoc.createElement("product");

            /* Creating elements within product DOM*/
            Element prodid = xmlDoc.createElement("prodid");
            Element price = xmlDoc.createElement("price");
            Element madeBy = xmlDoc.createElement("made_by");
            Element descrip = xmlDoc.createElement("descrip");

            /* Populating Product DOM with Data*/
            prodid.appendChild(xmlDoc.createTextNode(_productRS.getString("prodid")));
            price.appendChild(xmlDoc.createTextNode(_productRS.getString("price")));
            madeBy.appendChild(xmlDoc.createTextNode(_productRS.getString("made_by")));
            descrip.appendChild(xmlDoc.createTextNode(_productRS.getString("descrip")));

            /* Adding the elements to the Product Element*/
            product.appendChild(prodid);
            product.appendChild(price);
            product.appendChild(madeBy);
            product.appendChild(descrip);

            /* Appending product to the Root Class*/
            rootElement.appendChild(product);
        }

        return xmlDoc;
    }

    /* printDOM will write the contents of xml document passed onto it out to a file*/
    private static void printDOM(Document _xmlDoc, File _outputFile) throws Exception {
        OutputFormat outputFormat = new OutputFormat("XML", "UTF-8", true);
        FileWriter fileWriter = new FileWriter(_outputFile);

        XMLSerializer xmlSerializer = new XMLSerializer(fileWriter, outputFormat);

        xmlSerializer.asDOMSerializer();

        xmlSerializer.serialize(_xmlDoc.getDocumentElement());
    }
}
