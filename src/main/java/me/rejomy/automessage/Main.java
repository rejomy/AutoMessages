package me.rejomy.automessage;

import me.rejomy.automessage.command.AutoMessageCommand;
import me.rejomy.automessage.task.SendMessageTask;
import me.rejomy.automessage.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
    private static Main instance;
    public static Main getInstance() {
        return instance;
    }
    public boolean hasPlaceholderAPI;
    public List<Map<?, ?>> sections;
    private int taskId;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        sections = getConfig().getMapList("messages");
    }

    @Override
    public void onEnable() {
        hasPlaceholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        runTask();
        getCommand("AutoMessage").setExecutor(new AutoMessageCommand());
    }

    @Override
    public void onDisable() {
        stopTask();
    }
    public void stopTask() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
    public void runTask() {
        TimeUtil timeUtil = new TimeUtil();

        long repeatTicks = timeUtil.getTimeInMillis(getConfig().getString("timer")) / 50;

        taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new SendMessageTask(),
                repeatTicks, repeatTicks);
    }

}
