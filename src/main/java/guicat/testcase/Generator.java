package guicat.testcase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lin Cheng on 27/03/16.
 */
public class Generator {
    String appConf = "";
    String guicatConf = "";
    String guitarTestcaseDir = "";
    String branchDir = "";
    String guicatTestcaseDir = "";

    public Generator(String appConf, String guicatConf, String guitarTestcaseDir, String branchDir, String guicatTestcaseDir) {
        this.appConf = appConf;
        this.guicatConf = guicatConf;
        this.guitarTestcaseDir = guitarTestcaseDir;
        this.branchDir = branchDir;
        this.guicatTestcaseDir = guicatTestcaseDir;
    }

    public static Map<String, String> getNameIDMap(String guiFile) {
        String title = "";
        String ID = "";
        String key = "";
        Map<String, String> ret = new HashMap<String, String>();
      try {
         File inputFile = new File(guiFile);
         DocumentBuilderFactory dbFactory
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :"
            + doc.getDocumentElement().getNodeName());
         NodeList aList = doc.getElementsByTagName("Attributes");
         System.out.println("----------------------------");
         for (int temp = 0; temp < aList.getLength(); temp++) {
            Node aNode = aList.item(temp);  //Attributes
         //   System.out.println("\nCurrent Element :" + nNode.getNodeName());
             NodeList pList = aNode.getChildNodes();
             for (int i = 0; i < pList.getLength(); i++) {
                 Node pNode = pList.item(i); //Property
                 NodeList elements = pNode.getChildNodes();
                 for (int j = 0; j < elements.getLength(); j++) {
                     Node eNode = elements.item(j);
                     if (eNode.getNodeType() == Node.ELEMENT_NODE) {
                         Element element = (Element) eNode;
                         System.out.println("\n----" + element.getNodeName() + " : " + element.getTextContent().trim());
                         if (element.getNodeName().trim().equals("Name") ) {
                             key = element.getTextContent();
                         }
                         if (element.getNodeName().trim().equals("Value")) {
                             if (key.equals("ID")) {
                                 ID = element.getTextContent();
                             }
                             if (key.equals("Title")) {
                                 title = element.getTextContent();
                             }
                         }
                     } else {
                       //  throw new Exception("parse gui fail!");
                     }
                 }
                 if (!title.isEmpty() && ID.startsWith("w")) {
                     ret.put(title, ID);
                     key = "";
                     title = "";
                     ID = "";
             }
             }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
        return ret;
   }



    public static void main(String[] args) {
        String appConf = "./conf/ticket.conf";
        String guicatConf = "./conf/guicat.properties";
        String guitarTestCaseDir = "./log/ticket/testcases";
        String branchDir = "./log/ticket/branches";
        String guicatTestcaseDir = "./log/ticket/gctestcases";

   //    String guiFile = "./log/ticket/ticket.GUI";
        String guiFile = "./log/argouml/argouml.GUI";
        Map map = Generator.getNameIDMap(guiFile);
        System.out.println(map.toString());

        Generator gen = new Generator(appConf, guicatConf, guitarTestCaseDir, branchDir, guicatTestcaseDir);
    }
}
