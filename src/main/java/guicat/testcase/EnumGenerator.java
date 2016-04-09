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
            if (GCConfig.isEnumWidget(methodName) || GCConfig.isEnumWidget(methodName)){
                String vString = gcEntry.initString;
                String delims = ":";
                String[] tokens = vString.split(delims);
                System.out.println(tokens);
                List<Integer> enumValue = new ArrayList<>();
                int min = Integer.parseInt(tokens[0]);
                int max = Integer.parseInt(tokens[1]);
                for (int val = min; val <= max; val++) {
                    enumValue.add(Integer.valueOf(val));
                }
                String enumKey = nameIdBind.get(gcEntry.accessibleName);
                if (enumKey == null) {
                    System.out.println("config error, no such accessibleName: " + gcEntry.methodName);
                }
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
            NodeList steps = doc.getElementsByTagName("Step");
            List<Node> myNodeList = new ArrayList<>();
            for (int i = 0; i < steps.getLength(); i++)
                myNodeList.add(steps.item(i));

            enumCounter = 0;
            writeParaRecurse(myNodeList, testcase);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeParaRecurse(List<Node> steps, String testcase) {
        if (steps.size() == 0) {
            String tmp = new File(testcase).getName();
            String fileName =  enumGuitarTestcaseDir + "/" + tmp.substring(0,tmp.length()-4) + "_" + enumCounter++ +".tst";
            writeToFile (fileName);
            return;
        }

        //devide to head and tail, for recurse
        Node headStep = steps.get(0);
        List<Node> tailSteps =  steps.subList(1, steps.size());
        String eventId = "";
        NodeList eleNodes = headStep.getChildNodes();
        for (int j = 0; j < eleNodes.getLength(); j++) {
            Node ele =  eleNodes.item(j);
            if (ele.getNodeName().equals("EventId")) {
                eventId = ele.getTextContent();
                break;
            }
        }

        if (getEnumConfig(guiFile).containsKey(eventId)) {
            for (int i : getEnumConfig(guiFile).get(eventId)) {
                addParam(headStep, i);
                writeParaRecurse(tailSteps, testcase);
            }
        } else {
            writeParaRecurse(tailSteps, testcase);
        }

    }


    public  void addParam(Node step, int par) {
        NodeList elements = step.getChildNodes();
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if (element instanceof Element && element.getNodeName().equals("Parameter"))
                step.removeChild(element);
        }
        Element parameterElement = doc.createElement("Parameter");
        parameterElement.appendChild(doc.createTextNode(String.valueOf(par)));
        step.appendChild(parameterElement);

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
/*
        System.setProperty("guicat.conf", "./conf/workout/guicat.properties");
        String guiFile = "./log/workout/workout.GUI";
        String guitarTestcaseDir = "./log/workout/testcases";
        String enumGuitarTestcaseDir = "./log/workout/enumtestcases";

        System.out.println(GCConfig.getInstance().config.toString());
        System.out.println(SymbolicTable.getInstance().toString());
        System.out.println(EnumGenerator.getEnumConfig(guiFile));
*/
        /*
        System.setProperty("guicat.conf", "./conf/barad-ticket/guicat.properties");
        String guiFile = "./log/barad-ticket/barad-ticket.GUI";
        String guitarTestcaseDir = "./log/barad-ticket/testcases";
        String enumGuitarTestcaseDir = "./log/barad-ticket/enumtestcases";

        System.out.println(GCConfig.getInstance().config.toString());
        System.out.println(SymbolicTable.getInstance().toString());
        System.out.println(EnumGenerator.getEnumConfig(guiFile));

*/
        assert args.length == 3 : "args for enumGenerator error";
        String guitarTestcaseDir = args[0];
        String enumGuitarTestcaseDir = args[1];
        String guiFile = args[2];


        EnumGenerator enumGenerator = new EnumGenerator(guitarTestcaseDir, enumGuitarTestcaseDir, guiFile);
        enumGenerator.processTestcases();
    }
}
