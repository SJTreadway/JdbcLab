package ser322.JdbcLab2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import java.io.File;

public class ReadXMLFile {

    public static void main(String args[]) {

        if (args.length != 1)
        {
            System.out.println("USAGE: java ser322.JdbcLab2.ReadXMLFile <DeptNo>");
            System.exit(0);
        }

        try {
            File fXmlFile = new File("dummy.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            XPathExpression expr = xpath.compile("/JDBCLabTable/product[made_by=" + args[0] + "]");
            // Only prints the first returned value :(
            System.out.println(expr.evaluate(doc, XPathConstants.STRING));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
