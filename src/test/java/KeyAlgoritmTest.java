
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KeyAlgoritmTest {

    String numberValidKet;
    String numberNotValidKey;
    String bik;


    @BeforeAll
    protected void setUp() throws Exception {
        numberValidKet = "40602810700000000025";
        numberNotValidKey = "40602810800000000025";
        bik="049805746";
    }

    @Test
    @Tag("keyVilid")
    @DisplayName("Валидный ключ")
    public void testIsKeyValid (){
        assertTrue(KeyAlgoritm.isKeyValid(numberValidKet,bik));
    }

    @Test
    @Tag("keyVilid")
    @DisplayName("Не валидный ключ")
    public void testIsKeyNotValid (){
        assertFalse(KeyAlgoritm.isKeyValid(numberNotValidKey,bik));
    }

    @Test
    @Tag("keyVilid")
    @DisplayName("Возвращение валидного номера")
    public void testGetValidAccountNumber (){
        String actual = KeyAlgoritm.getValidAccountNumber(numberNotValidKey,bik);
        assertTrue(actual.contains("40602810700000000025"));
    }


}
