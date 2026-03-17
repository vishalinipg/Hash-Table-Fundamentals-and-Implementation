import java.util.*;

public class UsernameChecker {

    static HashMap<String, Integer> userMap = new HashMap<>();
    static HashMap<String, Integer> attemptFrequency = new HashMap<>();

    public static boolean checkAvailability(String username) {

        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);

        if (userMap.containsKey(username)) {
            return false;
        }

        return true;
    }

    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    public static String getMostAttempted() {

        String mostAttempted = "";
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        userMap.put("john_doe", 101);
        userMap.put("admin", 102);
        userMap.put("user123", 103);

        System.out.println("checkAvailability(\"john_doe\") → " + checkAvailability("john_doe"));
        System.out.println("checkAvailability(\"jane_smith\") → " + checkAvailability("jane_smith"));

        System.out.println("suggestAlternatives(\"john_doe\") → " + suggestAlternatives("john_doe"));

        checkAvailability("admin");
        checkAvailability("admin");
        checkAvailability("admin");

        System.out.println("getMostAttempted() → " + getMostAttempted());
    }
}