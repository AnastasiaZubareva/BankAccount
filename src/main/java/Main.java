import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;


public class Main {
    private static Logger logger;
    public static void main(String[] args) {
        logger= LogManager.getRootLogger();

        String pathJson = "/Users/temior/IdeaProjects/BankAccount/src/main/java/resources/typeAccount.json";
        String pathXml = "/Users/temior/IdeaProjects/BankAccount/src/main/java/resources/curcod.xml";

        System.out.println("введите номер счета");
        Scanner sc = new Scanner(System.in);
        String accountNumber = sc.next().toString();

        System.out.println("введите бик банка");
        String bik = sc.next().toString();

        Verification vrfy = new Verification();

        System.out.println("Проверка валидации длинны введеного номера.");
        if (vrfy.isValidLength(accountNumber)) {
            System.out.println("номер введен корректно");
        } else {
            System.out.println("номер введен некорректно, проверьте количество символов");
        }

        System.out.println("Проверка корректности введенных символов");
        if (vrfy.isValidSymbols(accountNumber)) {
            System.out.println("номер введен корректно");
        } else {
            System.out.println("номер введен некорректно, проверьте количество символов");
        }

        System.out.println("проверка валюты номера");
        System.out.println(vrfy.curAccountPath(accountNumber, pathXml));
        System.out.println("проверка принадлежности номера");
        System.out.println(vrfy.typeAccountPath(accountNumber, ParserType.getJsonFile(pathJson)));
        if (KeyAlgoritm.isKeyValid(accountNumber, bik)) System.out.println("Номер счета корректен");
        else {
            System.out.println("Проверьте правильность ввода номера счета и БИК банка");
            System.out.println("Корректный ключ для этого бик");
            System.out.println(KeyAlgoritm.getValidAccountNumber(accountNumber, bik));
        }

    }
}
