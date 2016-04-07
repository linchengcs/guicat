package guicat.testcase;

import guicat.SymbolicTable;
import guicat.config.GCConfig;
import guicat.config.GCEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

/**
 * Created by oliver on 06/04/16.
 * bad design, should spit dirs and testcase operation
 */
public class EnumGenerator {
    private String guitarTestcaseDir = "";
    private String enumGuitarTestcaseDir = "";
    private String guiFile = "";

    private int enumCounter = 0;

    private static Hashtable<String, List<Integer>> enumConfig ;

    private Document doc;

    public EnumGenerator(String guitarTestcaseDir, String enumGuitarTestcaseDir, String guiFile) {
        this.guitarTestcaseDir = guitarTestcaseDir;
        this.enumGuitarTestcaseDir = enumGuitarTestcaseDir;
        this.guiFile = guiFile;
    }

    synchronized public static Hashtable<String, List<Integer>> getEnumConfig(String guiFile) {
        if (enumConfig == null)
            buildEnumConfig(guiFile);
        return enumConfig;
    }

    public static void buildEnumConfig(String guiFile) {
        enumConfig = new Hashtable<String, List<Integer>>();
        Map<String, String> nameIdBind = Generator.getGUINameIDBindMap(guiFile);
        for (GCEntry gcEntry : GCConfig.getInstance().config.values()) {
            String methodName = gcEntry.methodName;
            if (methodName.equals("JComboBox") || methodName.equals("JCheckBox")) {
                String vString = gcEntry.initString;
                String delims = ":";
                String[] tokens = vString.split(delims);
                System.out.println(tokens);
                List<Integer> enumValue = new ArrayList<>();
                enumValue.add(Integer.valueOf(Integer.parseInt(tokens[0])));
                enumValue.add(Integer.valueOf(Integer.parseInt(tokens[1])));
                String enumKey = nameIdBind.get(gcEntry.accessibleName);
                String enumKey1 = "e" + enumKey.substring(1, enumKey.length());
                if (enumKey != null)
                    enumConfig.put(enumKey1, enumValue);
            }
        }
    }



    public void processTestcase(String testcase){
        int counter = 1;
        try {
            File tcFile = new File(testcase);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = dbBuilder.parse(tcFile);
            doc.getDocumentElement().normalize();

            ArrayList<String> enumEvents = new ArrayList<>();
            NodeList eventIds = doc.getElementsByTagName("EventId");
            for (int i = 0; i < eventIds.getLength(); i++) {
                Node event = eventIds.item(i);
                String eventId = event.getTextContent();
                if (getEnumConfig(guiFile).containsKey(eventId)) {
                    //        System.out.println("from ptc" + event.getTextContent());
                    enumEvents.add(eventId);
                }
            }

            enumCounter = 0;
            writeParaRecurse(enumEvents, testcase);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeParaRecurse(List<String> enumEvents, String testcase) {
        if (enumEvents.size() == 0) {
            String fileName =  enumGuitarTestcaseDir + "/" + new File(testcase).getName() + "_" + enumCounter;
            writeToFile (fileName);
            return;
        }
        List<String> tail = (List<String>)enumEvents.subList(1,enumEvents.size());
        for (int i : getEnumConfig(guiFile).get(enumEvents.get(0))) {
            //  addParam(doc, eventId, intP);
            addParam(enumEvents.get(0), i);
            enumCounter++;

            System.out.println(enumCounter);
            writeParaRecurse(tail, testcase);
        }

    }

    public  void writeToFile(String file) {
        try {
    //        System.out.println(doc.getDocumentElement().getTextContent());
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void addParam(String eventId, int par) {
        NodeList eventIds = doc.getElementsByTagName("EventId");
        for (int i = 0; i < eventIds.getLength(); i++) {
            Node eventIdNode = eventIds.item(0);
            if (eventIdNode.getTextContent().trim().equals(eventId)){
                Element parameterElement = doc.createElement("Parameter");
                parameterElement.appendChild(doc.createTextNode(String.valueOf(par+999)));
                Node stepNode = eventIdNode.getParentNode();
                stepNode.appendChild(parameterElement);
                break;
            }
        }

    }

    public void processTestcases() {
        File gDir = new File(enumGuitarTestcaseDir);
        if (gDir.exists() && gDir.isDirectory()) {
            for (File file : new File(enumGuitarTestcaseDir).listFiles()) file.delete();
        } else {
            gDir.mkdir();
        }

        File testcaseDir = new File(this.guitarTestcaseDir);
        File[] testcases = testcaseDir.listFiles();
        for (File testcase : testcases) {
            if (testcase.isFile()) {
                processTestcase(testcase.getPath());
            }
            //    break;
        }
    }

    public static void main(String[] args) {
        System.setProperty("guicat.conf", "./conf/barad-ticket/guicat.properties");
        String guiFile = "./log/barad-ticket/barad-ticket.GUI";
        String guitarTestcaseDir = "./log/barad-ticket/testcases";
        String enumGuitarTestcaseDir = "./log/barad-ticket/enumtestcases";

        System.out.println(GCConfig.getInstance().config.toString());
        System.out.println(SymbolicTable.getInstance().toString());
        System.out.println(EnumGenerator.getEnumConfig(guiFile));
        EnumGenerator enumGenerator = new EnumGenerator(guitarTestcaseDir, enumGuitarTestcaseDir, guiFile);
        enumGenerator.processTestcases();
    }
}
