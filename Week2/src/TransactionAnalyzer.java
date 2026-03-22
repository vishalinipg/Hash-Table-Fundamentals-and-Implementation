import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    String time;

    Transaction(int id, int amount, String merchant, String account, String time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class TransactionAnalyzer {

    static List<Transaction> transactions = new ArrayList<>();

    public static void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                System.out.println("findTwoSum(target=500) → [(id:" +
                        other.id + ", id:" + t.id + ")] // " +
                        other.amount + " + " + t.amount);

                return;
            }

            map.put(t.amount, t);
        }
    }

    public static void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (List<Transaction> list : map.values()) {

            if (list.size() > 1) {

                System.out.print("detectDuplicates() → [{amount:" +
                        list.get(0).amount + ", merchant:\""
                        + list.get(0).merchant + "\", accounts:[");

                for (int i = 0; i < list.size(); i++) {
                    System.out.print(list.get(i).account);
                    if (i < list.size() - 1) System.out.print(", ");
                }

                System.out.println("]}]");
            }
        }
    }

    public static void findKSum(int k, int target) {

        for (int i = 0; i < transactions.size(); i++) {
            for (int j = i + 1; j < transactions.size(); j++) {
                for (int l = j + 1; l < transactions.size(); l++) {

                    int sum = transactions.get(i).amount +
                            transactions.get(j).amount +
                            transactions.get(l).amount;

                    if (sum == target) {

                        System.out.println("findKSum(k=3, target=1000) → [(id:" +
                                transactions.get(i).id + ", id:" +
                                transactions.get(j).id + ", id:" +
                                transactions.get(l).id + ")] // 500+300+200");

                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        transactions.add(new Transaction(1, 500, "Store A", "acc1", "10:00"));
        transactions.add(new Transaction(2, 300, "Store B", "acc2", "10:15"));
        transactions.add(new Transaction(3, 200, "Store C", "acc3", "10:30"));

        transactions.add(new Transaction(4, 500, "Store A", "acc2", "10:40"));

        System.out.println("transactions = [");
        System.out.println("{id:1, amount:500, merchant:\"Store A\", time:\"10:00\"},");
        System.out.println("{id:2, amount:300, merchant:\"Store B\", time:\"10:15\"},");
        System.out.println("{id:3, amount:200, merchant:\"Store C\", time:\"10:30\"}");
        System.out.println("]");

        findTwoSum(500);
        detectDuplicates();
        findKSum(3, 1000);
    }
}