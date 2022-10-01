
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("Basket.json");
        File logFile = new File("log.csv");
        Basket basket = Basket.loadFromJson(file);
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
            basket.saveJson(file);
            basket.printCart();
            clientLog.log(productNumber + 1, productCount);
        }
        basket.printCart();
        clientLog.exportAsCSV(logFile);
    }
}

