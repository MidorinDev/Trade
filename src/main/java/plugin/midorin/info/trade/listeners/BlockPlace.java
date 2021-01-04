package plugin.midorin.info.trade.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import plugin.midorin.info.trade.TradePlugin;
import plugin.midorin.info.trade.trade.Trade;

public class BlockPlace implements Listener
{
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if (e.getPlayer() == null || e.getBlock() == null) return;
        Player p = e.getPlayer();
        if ((e.getBlock() != null ? e.getBlock().getType() : null) == Material.GOLD_BLOCK)
        {
            Location loc = e.getBlock().getLocation().clone();
            loc.add(0, -1, 0);
            if (loc.getBlock().getType().equals(Material.CHEST))
            {
                Trade.createShop(p.getUniqueId(), p.getLocation());
                p.sendMessage(TradePlugin.PREFIX + ChatColor.GREEN + "ショップを設置しました。");
            }
        }
    }
}
