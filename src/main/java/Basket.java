import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Basket {
    private String[] names;
    private int[] prices;
    private int[] count;

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

    public int printCart() {
        int sum = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < names.length; i++) {
            if (count[i] != 0) {
                System.out.println((i + 1) + ". " + names[i] + " (" + count[i] + "шт.),всего " + (count[i] * prices[i]) + " рублей");
                sum += count[i] * prices[i];
            }
        }
        System.out.println("Итого:" + sum + " рублей.");
        return sum;
    }

    public String saveTxt(File textFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
            String finalString = "";
            writer.write(String.join(" ", names) + "\n");
            finalString += String.join(" ", names) + "\n";
            String pricesTxt = Arrays.stream(prices)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "));
            writer.write(pricesTxt + "\n");
            finalString += pricesTxt + "\n";
            String countTxt = Arrays.stream(count)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "));
            writer.write(countTxt + "\n");
            finalString += countTxt + "\n";
            return finalString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
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
    public String saveJson(File textFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(textFile, this);
        return mapper.writeValueAsString(this);

        /*JSONObject basketJson = new JSONObject();
        basketJson.put("names", String.join(" ", names));
        String pricesTxt = Arrays.stream(prices)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
        basketJson.put("prices", pricesTxt);
        String countTxt = Arrays.stream(count)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
        basketJson.put("count", countTxt);

        try(FileWriter file = new FileWriter(textFile)){
            file.write(basketJson.toJSONString());
        }*/
    }
    public static Basket loadFromJson(File textFile) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(textFile, Basket.class);

        /*JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(textFile));
        JSONObject jsonObject = (JSONObject) obj;
        String namesString = (String) jsonObject.get("names");
        String[] names = namesString.split(" ");
        String pricesString = (String) jsonObject.get("prices");
        int[] prices = Arrays.stream(pricesString.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        String countString = (String) jsonObject.get("count");
        int[] count = Arrays.stream(countString.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        return new Basket(names, prices, count);*/

    }
}
