
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static boolean loadEnabled = false;
    public static String loadFileName;
    public static String loadFormat;
    public static boolean saveEnabled = true;
    public static String saveFileName = "Basket.json";
    public static String saveFormat = "json";
    public static boolean logEnabled = true;
    public static String logFileName = "Client.csv";

    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {

        loadConfig();


        Basket basket = null;
        if(loadEnabled) {
            File file = new File(loadFileName);
            if(loadFormat.equals("json")) {
                basket.loadFromJson(file);
            } else if (loadFormat.equals("txt")) {
                basket.loadFromTxtFile(file);
            }
        }

        if (basket == null) {
            basket = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});
        }
        String[] products = basket.getNames();
        int[] prices = basket.getPrices();
        ClientLog clientLog = new ClientLog();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            int productNumber = 0;
            int productCount = 0;

            System.out.println("Выберите товар и количество или введите 'end' ");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] amount = input.split(" ");
            productNumber = Integer.parseInt(amount[0]) - 1;
            productCount = Integer.parseInt(amount[1]);
            basket.addToCart(productNumber, productCount);
            if(saveEnabled) {
                File fileSave = new File(saveFileName);
                if(saveFormat.equals("json")) {
                    System.out.println("Гаврюшка ко мне");
                    basket.saveJson(fileSave);
                } else if (saveFormat.equals("txt")) {
                    basket.saveTxt(fileSave);
                }
            }
            basket.printCart();
            clientLog.log(productNumber + 1, productCount);
        }
        basket.printCart();
        if(logEnabled) {
            File logFile = new File(logFileName);
            clientLog.exportAsCSV(logFile);
        }
    }
    public static void loadConfig() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");
        Node root = doc.getDocumentElement();
        Element rootElement = (Element) root;

        NodeList list = root.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node currentNode = list.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                if (currentNode.getNodeName().equals("load")) {
                    Element element = (Element) currentNode;
                    Main.loadEnabled = element.getElementsByTagName("enabled").item(0).getTextContent().equals("true") ? true : false;
                    Main.loadFileName = element.getElementsByTagName("fileName").item(0).getTextContent();
                    Main.loadFormat = element.getElementsByTagName("format").item(0).getTextContent();
                } else if (currentNode.getNodeName().equals("save")) {
                    Element element = (Element) currentNode;
                    Main.saveEnabled = element.getElementsByTagName("enabled").item(0).getTextContent().equals("true") ? true : false;
                    Main.saveFileName = element.getElementsByTagName("fileName").item(0).getTextContent();
                    Main.saveFormat = element.getElementsByTagName("format").item(0).getTextContent();
                } else if (currentNode.getNodeName().equals("log")) {
                    Element element = (Element) currentNode;
                    Main.logEnabled = element.getElementsByTagName("enabled").item(0).getTextContent().equals("true") ? true : false;
                    Main.logFileName = element.getElementsByTagName("fileName").item(0).getTextContent();

                }
            }
        }
        /*Element load = (Element) rootElement.getElementsByTagName("load");
        Main.loadEnabled = load.getElementsByTagName("enabled").item(0).getTextContent().equals("true");
        Main.loadFileName = load.getElementsByTagName("fileName").item(0).getTextContent();
        Main.loadFormat = load.getElementsByTagName("format").item(0).getTextContent();

        Element save = (Element) rootElement.getElementsByTagName("save");
        Main.saveEnabled = save.getElementsByTagName("enabled").item(0).getTextContent().equals("true");
        Main.saveFileName = save.getElementsByTagName("fileName").item(0).getTextContent();
        Main.saveFormat = save.getElementsByTagName("format").item(0).getTextContent();

        Element log = (Element) rootElement.getElementsByTagName("log");
        Main.logEnabled = log.getElementsByTagName("enabled").item(0).getTextContent().equals("true");
        Main.logFileName = log.getElementsByTagName("fileName").item(0).getTextContent();*/

    }
}

