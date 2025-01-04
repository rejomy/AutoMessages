package me.rejomy.automessage.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rejomy.automessage.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionUtil {
    boolean verify;
    List<Object> parseResult = new ArrayList<>();

    public ConditionUtil(Player player, List<String> conditions) {
        // Conditions for send message does not exists, we can send message freely
        if (conditions == null) {
            verify = true;
            return;
        }

        for (String condition : conditions) {

            if (condition.contains("perm")) {
                String permission = condition.replaceAll("perm:", "");
                verify = player.hasPermission(permission);
            } else if (condition.matches("[><=!]")) {

                String operation = condition.replaceAll("^[><=!]", "");

                String[] values = condition.split("(!?=|[<>]=?)");

                // Check if condition is incorrect.
                if(values.length == 0) continue;

                values = Arrays.stream(values)
                        .map(word ->
                                Main.getInstance().hasPlaceholderAPI && word.matches("%\\W+%") ?
                                        PlaceholderAPI.setPlaceholders(player, word) : word)
                        .toArray(String[]::new);

                boolean isNumber = parseFloat(values[0]) && parseFloat(values[1]);

                switch (operation.charAt(0)) {
                    case '!':
                        verify = parseResult.get(0) != parseResult.get(1);
                        break;
                    case '=':
                        verify = parseResult.get(0) == parseResult.get(1);
                        break;
//                        < > operations
                    default:
                        if (!isNumber) {
                            continue;
                        }

                        boolean equals = operation.contains("=");

                        float r1 = (float) parseResult.get(0), r2 = (float) parseResult.get(1);

                        if(operation.charAt(0) == '<') {
                            verify = equals? r1 <= r2 : r1 < r2;
                        } else {
                            verify = equals? r1 >= r2 : r1 > r2;
                        }
                }

            }

            if (!verify) {
                break;
            }

        }

    }

    private boolean parseFloat(String value) {

        try {
            parseResult.add(Float.parseFloat(value));
            return true;
        } catch (NumberFormatException exception) {
            parseResult.add(value);
            return false;
        }
    }

    public boolean isVerified() {
        return verify;
    }

}

