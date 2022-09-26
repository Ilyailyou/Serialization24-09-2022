import java.io.File;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Basket implements Serializable {
    private String[] names;
    private int[] prices;
    private int[] count;

    public Basket() {

    }

    public Basket(String[] names, int[] prices) {
        this.names = names;
        this.prices = prices;
        this.count = new int[names.length];
    }

    public Basket(String[] names, int[] prices, int[] count) {
        this.names = names;
        this.prices = prices;
        this.count = count;
    }

    public String[] getNames() {
        return names;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getCount() {
        return count;
    }

    public void addToCart(int productNum, int amount) {
        this.count[productNum] += amount;
        if (count[productNum] < 0) {
            this.count[productNum] = 0;
        }
    }

    public void printCart() {
        int sum = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < names.length; i++) {
            if (count[i] != 0) {
                System.out.println((i + 1) + ". " + names[i] + " (" + count[i] + "шт.),всего " + (count[i] * prices[i]) + " рублей");
                sum += count[i] * prices[i];
            }
        }
        System.out.println("Итого:" + sum + " рублей.");
    }

    /*public void saveTxt(File textFile) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
            writer.write(String.join(" ", names) + "\n");
            String pricesTxt = Arrays.stream(prices)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "));
            writer.write(pricesTxt+"\n");
            String countTxt = Arrays.stream(count)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "));
            writer.write(countTxt+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void saveBin(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromBinFile(File file) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Basket basket = (Basket) in.readObject();
            in.close();
            return basket;
        } catch (Exception e) {
            return null;
        }
    }
}
