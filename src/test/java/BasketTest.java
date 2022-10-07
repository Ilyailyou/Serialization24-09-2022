
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

@DisplayName("Тестирование: корзина")
public class BasketTest extends Assertions {

    public Basket basketTest;

    @BeforeEach
    public void setUpBasket() {
        System.out.println("Создание корзины");
        basketTest =  new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});

    }
    @AfterEach
    public void tearDownBasket() {
        basketTest = null;
        System.out.println("Очистка корзины");
    }

    @AfterAll
    public static void tearDownAll() {
        File file = new File("test.txt");
        if (file.delete()) {
            System.out.println("Файл test.txt успешно удалён.");
        }
        else {
            System.out.println("Не удалось удалить test.txt.");
        }
        File fileJ = new File("test.json");
        if (fileJ.delete()) {
            System.out.println("Файл test.json успешно удалён.");
        }
        else {
            System.out.println("Не удалось удалить test.json.");
        }
    }


    @Test
    @DisplayName("Тест: сохранение в json")
    public void testJson() throws IOException, ParseException {
        File fileSave = new File("test.json");
        basketTest.saveJson(fileSave);
        Basket newBasket = Basket.loadFromJson(fileSave);
        assertEquals(basketTest.toString(), newBasket.toString());
    }

    @Test
    @DisplayName("Тест: сохранение в txt")
    public void testTxt() throws IOException {
        File fileSave = new File("test.txt");
        basketTest.saveTxt(fileSave);
        Basket newBasket = Basket.loadFromTxtFile(fileSave);
        assertEquals(basketTest.toString(), newBasket.toString());

    }
    @Test
    @DisplayName("Тест: покупки")
    public void testShop() {
        basketTest.addToCart(0, 1);
        basketTest.addToCart(1, 1);
        basketTest.addToCart(2, 1);
        assertEquals("Хлеб Яблоки Молоко 100 200 300 1 1 1", basketTest.toString());

    }


}
