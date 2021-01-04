package plugin.midorin.info.trade;

import jp.jyn.jecon.Jecon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import plugin.midorin.info.trade.listeners.BlockPlace;
import plugin.midorin.info.trade.listeners.InventoryClick;
import plugin.midorin.info.trade.listeners.PlayerInteract;
import plugin.midorin.info.trade.manager.Database;
import plugin.midorin.info.trade.util.CustomConfig;

public class TradePlugin extends AbstractTradePlugin
{
    public static TradePlugin plugin;
    public static Jecon jecon;
    public static String PREFIX = ChatColor.WHITE + "[" + ChatColor.YELLOW + "Trade" + ChatColor.WHITE + "] ";

    public static Inventory shop = Bukkit.createInventory(null, 9, ChatColor.GREEN + "<player>" + ChatColor.WHITE + "の店");

    private CustomConfig configuration;

    @Override
    public void onEnable()
    {
        plugin = this;
        Plugin JPlugin = Bukkit.getPluginManager().getPlugin("Jecon");
        if (JPlugin == null || !JPlugin.isEnabled()) getLogger().warning("Jecon is not available.");
        jecon = (Jecon) JPlugin;

        setup();
        this.configuration = new CustomConfig(this, resolveConfig());

        registerListeners(
                new BlockPlace(),
                new InventoryClick(),
                new PlayerInteract()
        );
    }

    @Override
    public void onDisable()
    {
        Database.get().shutdown();
        this.configuration.save();
    }

    public void setup()
    {
        try { Database.get().setup(false); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    public static TradePlugin getPlugin() { return plugin; }
    public CustomConfig getConfiguration() { return configuration; }
    // public Path getConfigDirectory() { return getDataFolder().toPath().toAbsolutePath(); }
}