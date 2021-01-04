package plugin.midorin.info.trade;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class AbstractTradePlugin extends JavaPlugin implements Listener
{
    protected File resolveConfig()
    {
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists())
        {
            this.getDataFolder().mkdirs();
            this.saveResource("config.yml", false);
        }
        return configFile;
    }

    protected void registerListeners(Listener... listeners)
    {
        for(Listener listener : listeners) getServer().getPluginManager().registerEvents(listener, this);
    }
}
