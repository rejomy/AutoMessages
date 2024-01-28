package me.rejomy.automessage.util;

import me.rejomy.automessage.Main;

public class TimeUtil {

    public long getTimeInMillis(String time) {
        if(time.isEmpty()) {
            Main.getInstance().getLogger().severe("Time is empty.");
            return 0;
        }

        try {
            int seconds = Integer.parseInt(time);
            return seconds * 1000L;
        } catch (NumberFormatException exception) {

            char timeKey = time.charAt(time.length() - 1);

            String rep = time.replaceAll("\\D", "");

            int value = Integer.parseInt(rep);

            switch (timeKey) {
                case 'h': return value * 60 * 60 * 1000L;
                case 's': return value * 1000L;
                case 'm': return value * 60 * 1000L;
                case 'd': return value * 24 * 60 * 60 * 1000L;
            }

        }

        return 0;
    }

}
