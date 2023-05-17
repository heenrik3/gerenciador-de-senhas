package henrik3.passwordgenerator;

import java.security.SecureRandom;
import java.util.ArrayList;

public class PasswordGenerator {

    public static String create(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        String letters = "abcdefghijklmnopqrstuvwxyz", numbers = "0123456789", symbols = "!@#$%^&*()-_=+";

        ArrayList<String> chars = new ArrayList<String>() {{
            add(letters);
            add(numbers);
            add(symbols);
        }};

        for (int i = 0; i < length; i++) {
            // Picks an object: letters, numbers, symbols
            int where = random.nextInt(chars.size());
            String obj = chars.get(where);

            // Picks a character
            char character = obj.charAt(random.nextInt(obj.length()));

            // Uppercase
            if ((where == 0) && random.nextBoolean()) character = Character.toUpperCase(character);

            password.append(character);
        }

        return password.toString();
    }
}
