import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("Basket.bin");
        Basket basket = loadFromBinFile(file);
        if(basket == null) {
            basket = new Basket(new String[] {"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});
        }
        String[] products = basket.getNames();
        int[] prices = basket.getPrices();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " " + "руб/шт");
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
            basket.saveBin(file);
            //basket.saveTxt(file);
            basket.printCart();

        }
        basket.printCart();
    }
    public static Basket loadFromTxtFile(File textFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] names = reader.readLine().split(" ");
            int[] prices = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] count = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            return new Basket(names, prices, count);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    public static Basket loadFromBinFile(File file){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Basket basket = (Basket) in.readObject();
            in.close();
            return basket;
        }catch (Exception e) {
            return null;
        }
    }
}

