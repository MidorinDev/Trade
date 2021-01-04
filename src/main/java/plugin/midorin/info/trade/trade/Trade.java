package plugin.midorin.info.trade.trade;

import org.bukkit.Location;
import org.bukkit.World;
import plugin.midorin.info.trade.manager.Database;
import plugin.midorin.info.trade.util.SQLQuery;
import plugin.midorin.info.trade.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Trade
{
    private final UUID uuid;
    private final World world;
    private final double x, y, z;

    private int id;
    public Trade(int id, UUID uuid, Location location)
    {
        this.id = id;
        this.uuid = uuid;
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public static void createShop(UUID uuid, Location location)
    {
        new Trade(1, uuid, location).createShop();
    }

    public void createShop()
    {
        //if (id == -1) return;
        if (uuid == null) return;
        try
        {
            Database.get().executeStatement(SQLQuery.INSERT_SHOPS, uuid, world.getName(), x, y, z, "test");
            try (ResultSet rs = Database.get().executeResultStatement(SQLQuery.SELECT_EXACT_SHOPS, uuid, world.getName(), x, y, z, "test"))
            {
                if (!rs.next())
                {
                    Utils.log("!! IDを更新できない! サーバーを再起動して解決してください");
                    Utils.log("!! Failed at: " + toString());
                }
                else id = rs.getInt("id");
            }
        }
        catch (SQLException ex) { ex.printStackTrace(); }
    }
}
