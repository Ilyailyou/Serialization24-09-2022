import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

@DisplayName("Тестирование: корзина")
public class BasketTest extends Assert {

    /*public static Basket basketTest = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});

    @BeforeEach
    public static void setUpBasket() {
        System.out.println("Создание корзины");
        basketTest =  new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});

    }
    @AfterEach
    public static void tearDownBasket() {
        basketTest = null;
        System.out.println("Очистка корзины");
    }*/
    @Test
    @DisplayName("Тест: сохранение в json")
    public void testJson() throws IOException {
        Basket basketTest = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});
        File fileSave = new File("test.json");
        String test = basketTest.saveJson(fileSave);
        assertEquals("{\"names\":[\"Хлеб\",\"Яблоки\",\"Молоко\"],\"prices\":[100,200,300],\"count\":[0,0,0]}", test);
    }

    @Test
    @DisplayName("Тест: сохранение в txt")
    public void testTxt() throws IOException {
        Basket basketTest = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});
        File fileSave = new File("test.txt");
        String test = basketTest.saveTxt(fileSave);
        assertEquals("Хлеб Яблоки Молоко\n100 200 300\n0 0 0\n", test);

    }
    @Test
    @DisplayName("Тест: покупки")
    public void testShop() {
        Basket basketTest = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});
        basketTest.addToCart(0, 1);
        basketTest.addToCart(1, 1);
        basketTest.addToCart(2, 1);
        int sum = basketTest.printCart();
        assertEquals("600", String.valueOf(sum));
    }


}
