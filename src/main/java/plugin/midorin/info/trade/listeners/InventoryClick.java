package plugin.midorin.info.trade.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import plugin.midorin.info.trade.TradePlugin;

public class InventoryClick implements Listener
{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getWhoClicked() == null || e.getView() == null) return;
        Player p = (Player) e.getWhoClicked();
        Location loc = p.getLocation();
        InventoryView iv = e.getView();

        if (iv.getTitle().equalsIgnoreCase(TradePlugin.shop.getTitle()))
        {
            switch (e.getCurrentItem().getType())
            {
            }
        }
    }
}
