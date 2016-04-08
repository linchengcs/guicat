package guicat.testcase;

import guicat.config.GCConfig;
import guicat.config.GCEntry;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

/**
 * Created by Lin Cheng on 27/03/16.
 */
public class Generator {
   // String appConf = "";
   // String guicatConf = "";
    String guiFile = "";
    String guitarTestcaseDir = "";
    String branchDir = "";
    String guicatTestcaseDir = "";

    private static Map<String, String> bind = null;
    synchronized public static Map<String, String> getGUINameIDBindMap(String guiFile) {
        if (bind == null)
            bind = getNameIDMap(guiFile);
        System.out.println("_____" + bind.toString());
        return bind;
    }

    public Generator( String guiFile, String guitarTestcaseDir, String branchDir, String guicatTestcaseDir) {
       // this.appConf = appConf;
      //  this.guicatConf = guicatConf;
        this.guiFile = guiFile;
        this.guitarTestcaseDir = guitarTestcaseDir;
        this.branchDir = branchDir;
        this.guicatTestcaseDir = guicatTestcaseDir;
    }

    private static Map<String, String> getNameIDMap(String guiFile) {
        String title = "";
        String ID = "";
        String key = "";
        String value = "";
        Map<String, String> tmp = new HashMap<>();
        Map<String, String> ret = new HashMap<String, String>();
        try {
            File inputFile = new File(guiFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList aList = doc.getElementsByTagName("Attributes");
            System.out.println("----------------------------");
            for (int temp = 0; temp < aList.getLength(); temp++) {
                tmp.clear();
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
                            //   System.out.println("\n----" + element.getNodeName() + " : " + element.getTextContent().trim());
                            if (element.getNodeName().trim().equals("Name") ) {
                                key = element.getTextContent();
                            }
                            if (element.getNodeName().trim().equals("Value")) {
                                value = element.getTextContent();
                            }
                        }
                    }
                    tmp.put(key, value);
                }
                if (tmp.containsKey("ID") && tmp.containsKey("Title") && tmp.containsKey("Class")  ){
                    if ( ! tmp.get("Class").equals("javax.swing.JLabel")) {
                        ret.put(tmp.get("Title"), tmp.get("ID"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<String> getBranches(String testcase) {
        List<String> ret = new ArrayList<>();
        File tcFile = new File(testcase);
        if (!tcFile.isFile())
            return null;
        try {
            String[] parts = tcFile.getName().split("[\\.]");
            assert parts.length == 2;
            String regex = parts[0] + "_\\d+" + ".tst";
            System.out.println(regex);

            File branchDir = new File(this.branchDir);
            File[] branches = branchDir.listFiles();
            for (File branch : branches) {
                if (branch.isFile()) {
                    String branchName = branch.getName();
                    if (branchName.matches(regex)) {
                        ret.add(branch.getPath());
                    }
                }
            }
        } catch (Exception ex) {}
        return ret;
    }

    public Map<String, String> readBranch(String branch) {
        Map<String, String> ret = new LinkedHashMap<String, String>();
        LinkedList<String> branchValues = new LinkedList<>();
        try {
            File branchFile = new File(branch);
            BufferedReader br = new BufferedReader(new FileReader(branch));
            String line = null;
            while ((line = br.readLine() ) != null) {
                branchValues.add(line);
            }
        } catch (Exception ex){}

        ArrayList<String> accessibleNames = new ArrayList<>(GCConfig.getInstance().config.keySet());

        int size = branchValues.size();
        assert size == accessibleNames.size();


        for(int i = 0; i < size; i++) {
            ret.put(accessibleNames.get(i), branchValues.get(i));
        }

        return ret;
    }

    public List<String> getEventIDToBeDeleted(String testcase) {
        List<String> ret = new ArrayList<String>();
        for (Map.Entry<String, String> entry : getGUINameIDBindMap(guiFile).entrySet()) {
            String key = entry.getKey();
            if (GCConfig.getInstance().config.keySet().contains(key)) {
                /* !!!! caution !!! */
                GCEntry gcEntry = GCConfig.getInstance().config.get(key);
                if (!gcEntry.methodName.equals("JComboBox") && !gcEntry.methodName.equals("JCheckBox")) {
                    ret.add(entry.getValue());
                }
            }
        }
        return ret;
    }

    public void processTestcase(String testcase) {
        try {

            List<String> branchFiles = getBranches(testcase);
            for (String branchFile : branchFiles) {
                File tcFile = new File(testcase);
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dbBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = dbBuilder.parse(tcFile);
                doc.getDocumentElement().normalize();

                List<String> eventsToBeDeleted = getEventIDToBeDeleted(testcase);
                System.out.println(eventsToBeDeleted.toString());
                NodeList eventIds = doc.getElementsByTagName("EventId");
                for (int i = 0; i < eventIds.getLength(); i++) {
                    Node eventNode = eventIds.item(i);
                    if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) eventNode;
                        if (element.getNodeName().trim().equals("EventId")) {
                            String eventId = element.getTextContent().trim();
                            String widgetId = "w" + eventId.substring(1, eventId.length());
                            System.out.println(eventId);
                            if (eventsToBeDeleted.contains(widgetId)) {
                                Node stepNode = element.getParentNode();
                                stepNode.getParentNode().removeChild(stepNode);
                            }
                        }
                    }
                }

                Map<String, String> branchMap = readBranch(branchFile);
                for (Map.Entry<String, String> entry : branchMap.entrySet()) {
                    String accessibleName = entry.getKey();
                    String branchValue = entry.getValue();
                    String widgetId = getGUINameIDBindMap(guiFile).get(accessibleName);
                    String eventId = "e" + widgetId.substring(1, widgetId.length());
                    Element root = doc.getDocumentElement();
                    Element stepElement = doc.createElement("Step");
                    root.appendChild(stepElement);

                    Element eventIdElement = doc.createElement("EventId");
                    eventIdElement.appendChild(doc.createTextNode(eventId));
                    stepElement.appendChild(eventIdElement);

                    Element reashingStepElement = doc.createElement("ReachingStep");
                    reashingStepElement.appendChild(doc.createTextNode("false"));
                    stepElement.appendChild(reashingStepElement);

                    Element parameterElement = doc.createElement("Parameter");
                    parameterElement.appendChild(doc.createTextNode(branchValue));
                    stepElement.appendChild(parameterElement);

                }
                // write the content into xml file
                // String writeTo = "./log/ticket/gctestcase-test/test.xml";
                String writeTo = this.guicatTestcaseDir + "/" + (new File(branchFile)).getName();

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(writeTo));

                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);

                transformer.transform(source, result);

            }
        } catch (Exception ex) {}
    }

    public void processTestcases() {
        File gDir = new File(guicatTestcaseDir);
        if (gDir.exists() && gDir.isDirectory()) {
            for (File file : new File(guicatTestcaseDir).listFiles()) file.delete();
        } else {
            gDir.mkdir();
        }

        File testcaseDir = new File(this.guitarTestcaseDir);
        File[] testcases = testcaseDir.listFiles();
        for (File testcase : testcases) {
            if (testcase.isFile()) {
                processTestcase(testcase.getPath());
            }
        }
    }

    public static void main(String[] args) {
/*
        String appConf = "./conf/ticket.conf";
       // String guicatConf = "./conf/ticket/guicat.properties";
        String guitarTestCaseDir = "./log/ticket/testcases";
        String branchDir = "./log/ticket/branches.bak";
        String guicatTestcaseDir = "./log/ticket/gctestcases-test";

        String guiFile = "./log/ticket/ticket.GUI";
        String testcase = "./log/ticket/testcases/te426421920.tst";
        System.setProperty("guicat.conf", "./conf/ticket/guicat.properties");
*/

//        System.out.println(args[0]);


        assert args.length == 4;
        String guiFile = args[0];
        String guitarTestCaseDir = args[1];
        String branchDir = args[2];
        String guicatTestcaseDir = args[3];

        Generator gen = new Generator(guiFile, guitarTestCaseDir, branchDir, guicatTestcaseDir);
        Generator.getGUINameIDBindMap(guiFile);
        gen.processTestcases();
    }
}
