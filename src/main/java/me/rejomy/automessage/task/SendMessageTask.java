package me.rejomy.automessage.task;

import me.rejomy.automessage.Main;
import me.rejomy.automessage.util.ColorUtil;
import me.rejomy.automessage.util.ConditionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class SendMessageTask extends BukkitRunnable {

    private final HashMap<Player, Integer> MAP = new HashMap<>();
    private byte handleCount = 0;

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            int element = MAP.getOrDefault(player, 0);
            handle(player, element);
            handleCount = 0;
        }
    }

    private boolean handle(Player player, int element) {
        int saveElement = element;
        boolean isVerified;

        try {
            ConditionUtil conditionUtil = new ConditionUtil(player,
                    (List<String>) Main.getInstance().sections.get(element).get("conditions"));

            isVerified = conditionUtil.isVerified();
        } catch (ClassCastException ignored) {
            isVerified = true;
        }

        handleCount += 1;

        if(handleCount > element + Main.getInstance().sections.size()) {
            return false;
        }

        element+=1;

        if(Main.getInstance().sections.size() - 1 < element) {
            element = 0;
        }

        MAP.put(player, element);

        if(isVerified) {
            String message = ColorUtil.toColor((String) Main.getInstance().sections.get(saveElement).get("message"));

            player.sendMessage(message);

            return true;
        } else {
            handle(player, element);
        }

        return false;
    }

}
