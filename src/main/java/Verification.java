import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class Verification {


    public static boolean isValidLength(String scanNumber) {
        if (scanNumber.isEmpty()) return false;
        else {
            if (scanNumber.length() == 20 ) return true;
            else return false;
        }
    }

    public static boolean isValidSymbols(String scanNumber){
       boolean result = scanNumber.matches("[0-9]+");
       return result;

    }

    public static String typeAccountPath(String scanNumber, String getPathFile) {
        DocumentContext context = JsonPath.parse(getPathFile);
        String scanNumberCut = scanNumber.substring(0, 5);
        try {
            String codeWay = "$[?(@.code == '" + scanNumberCut + "')]";
            String nameType = context.read(codeWay).toString();
            if (nameType.equals("[]") )
            return "Тип счета не найден в справочнике\n";
            return nameType;
        } catch (Exception ex) {
            return ex.toString() + "Тип счета не найден в справочнике\n";
        }
    }

    public static String curAccountPath(String scanNumber, String fileContent) {
        String scanNumberCut = scanNumber.substring(5, 8);
        if (scanNumberCut.equals("000")) {
            return "валюта счета не найдена в справочнике";
        }
        while (scanNumberCut.charAt(0) == '0') {
            scanNumberCut = scanNumberCut.substring(1);
            //либо, можно сюда добавить проверку не пустая ли подстрока. Если пустая выводить "номер некооректен"
        }
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(fileContent);

            XPathFactory pathFactory = XPathFactory.newInstance();
            XPath xpath = pathFactory.newXPath();
            String numCode = "//Valuta/Item[ISO_Num_Code='" + scanNumberCut + "']/Name";
            XPathExpression expr = xpath.compile(numCode);

            /*NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET); //юзаем если результатов больше чем 1
            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                System.out.println("Value:" + n.getTextContent());
            }*/

            if (!expr.evaluate(document).isEmpty())
                return (expr.evaluate(document));
            else return "валюта счета не найдена в справочнике\n";
        } catch (Exception ex) {
            return ex.toString();
        }
    };
}