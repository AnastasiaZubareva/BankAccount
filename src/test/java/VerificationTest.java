
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationTest {

    String numberFull;
    String numberFinUsd;
    static String numberATS;
    static String numberNotFoundType;
    static String numberNotFoundCur;
    static String numberNotFoundCurZero;
    static String numberValidKet;
    static String numberNotValidKey;
    static String numberOtherSymbol;
    static String pathXml = "/Users/temior/IdeaProjects/BankAccount/src/main/java/resources/curcod.xml";
    static String pathJson = "/Users/temior/IdeaProjects/BankAccount/src/main/java/resources/typeAccount.json";

    @BeforeAll
    protected void setUp() throws Exception {
        numberFull = "40501840912312312311";
        numberOtherSymbol = "4O5O1840912312312311";// 0 replasing "O"
        numberFinUsd = "40501840912312312311";
        numberNotFoundType = "00001840912312312311";
        numberNotFoundCurZero = "00001000912312312311";
        numberValidKet = "40602810700000000025";
        numberNotValidKey = "40602810800000000025";
        numberNotFoundCur = "40602044700000000025";
        numberATS = "40602040700000000025";
    }

    @Test
    @Tag("isValidLength")
    @DisplayName("Валидная длина номера")
    public void testIsValidLengthFull() {
        assertTrue(Verification.isValidLength(numberFull));
    }

    @Test
    @Tag("isValidLength")
    @DisplayName("Не валидная длина номера. Пустой")
    public void testIsValidLengthNull() {
        assertFalse(Verification.isValidLength(""));

    }

    @Test
    @Tag("isValidLength")
    @DisplayName("Не валидная длина номера. Мало символов")
    public void testIsValidLengthPart() {
        assertFalse(Verification.isValidLength(numberFull.substring(0,18)));
    }

    @Test
    @Tag("isValidLength")
    @DisplayName("Не валидная длина номера. Много символов")
    public void testIsValidLengthMany() {
        assertFalse(Verification.isValidLength(numberFull+numberATS));
    }

    @Test
    @Tag("isValidSymbols")
    @DisplayName("Валидные символы")
    public void testIsValidSymbols() {
        assertTrue(Verification.isValidSymbols(numberFull));
    }

    @Test
    @Tag("isValidSymbols")
    @DisplayName("Не валидные символы")
    public void testIsNotValidSymbols() {
        assertFalse(Verification.isValidSymbols(numberOtherSymbol));
    }

    @Test
    @Tag("TypeAccountPath")
    @DisplayName("Корректный тип")
    public void testTypeAccountPath() {
        String actual = Verification.typeAccountPath(numberFinUsd, ParserType.getJsonFile(pathJson));
        // assertEquals(expected, actual);
        assertTrue(actual.contains("Финансовые организации"));
    }

    @Test
    @Tag("TypeAccountPath(")
    @DisplayName("Не корректный тип")
    public void testTypeAccountPathNotFound() {
        String actual = Verification.typeAccountPath(numberNotFoundType, ParserType.getJsonFile(pathJson));
        // assertEquals(expected, actual);
        assertTrue(actual.contains("Тип счета не найден в справочнике"));
    }

    @Test
    @Tag("curAccountPath")
    @DisplayName("Корректная валюта")
    public void testCurAccountPath() {
        String actual = Verification.curAccountPath(numberFinUsd, pathXml);
        assertTrue(actual.contains("Доллар США"));
    }

    @Test
    @Tag("curAccountPath")
    @DisplayName("Не корректная валюта '000'. не найдена в справочнике")
    public void testCurAccountPathNotFound() {
        String actual = Verification.curAccountPath(numberNotFoundCurZero, pathXml);
        assertTrue(actual.contains("валюта счета не найдена в справочнике"));
    }

    @Test
    @Tag("curAccountPath")
    @DisplayName("Не корректная валюта '000'. не найдена в справочнике")
    public void testCurAccountPathNotFoundCur() {
        String actual = Verification.curAccountPath(numberNotFoundCur, pathXml);
        assertTrue(actual.contains("валюта счета не найдена в справочнике"));
    }

    @Test
    @Tag("curAccountPath")
    @DisplayName("Корректная валюта")
    public void testCurAccountPathAts() {
        String actual = Verification.curAccountPath(numberATS, pathXml);
        assertTrue(actual.contains("Австрийский "));
    }
}
