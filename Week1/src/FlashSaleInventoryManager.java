import java.util.*;

public class FlashSaleInventoryManager {

    static HashMap<String, Integer> stockMap = new HashMap<>();
    static HashMap<String, LinkedHashMap<Integer, Integer>> waitingList = new HashMap<>();

    public static String checkStock(String productId) {

        int stock = stockMap.getOrDefault(productId, 0);

        return stock + " units available";
    }

    public static synchronized String purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {

            stockMap.put(productId, stock - 1);

            return "Success, " + (stock - 1) + " units remaining";

        } else {

            waitingList.putIfAbsent(productId, new LinkedHashMap<>());

            LinkedHashMap<Integer, Integer> queue = waitingList.get(productId);

            queue.put(userId, queue.size() + 1);

            return "Added to waiting list, position #" + queue.size();
        }
    }

    public static void main(String[] args) {

        stockMap.put("IPHONE15_256GB", 100);

        System.out.println("checkStock(\"IPHONE15_256GB\") → " + checkStock("IPHONE15_256GB"));

        System.out.println("purchaseItem(\"IPHONE15_256GB\", userId=12345) → "
                + purchaseItem("IPHONE15_256GB", 12345));

        System.out.println("purchaseItem(\"IPHONE15_256GB\", userId=67890) → "
                + purchaseItem("IPHONE15_256GB", 67890));

        for (int i = 0; i < 98; i++) {
            purchaseItem("IPHONE15_256GB", 20000 + i);
        }

        System.out.println("purchaseItem(\"IPHONE15_256GB\", userId=99999) → "
                + purchaseItem("IPHONE15_256GB", 99999));
    }
}