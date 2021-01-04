package plugin.midorin.info.trade.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import plugin.midorin.info.trade.TradePlugin;

import java.io.File;
import java.io.IOException;

public class DynamicDataSource
{
    private HikariConfig config = new HikariConfig();

    public DynamicDataSource(boolean preferMySQL) throws ClassNotFoundException
    {
        if (preferMySQL)
        {
            String ip = TradePlugin.getPlugin().getConfiguration().getString("MySQL.IP");
            String dbName = TradePlugin.getPlugin().getConfiguration().getString("MySQL.DB-Name");
            String usrName = TradePlugin.getPlugin().getConfiguration().getString("MySQL.Username");
            String password = TradePlugin.getPlugin().getConfiguration().getString("MySQL.Password");
            String properties = TradePlugin.getPlugin().getConfiguration().getString("MySQL.Properties");
            int port = TradePlugin.getPlugin().getConfiguration().getInt("MySQL.Port");

            Class.forName("com.mysql.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?"+properties);
            config.setUsername(usrName);
            config.setPassword(password);
        }
        else
        {
            String path = TradePlugin.getPlugin().getDataFolder().getPath() + "/databases/";
            File dataFolder = new File(path);
            if (!dataFolder.exists()) dataFolder.mkdirs();
            File databaseFile = new File(dataFolder, "main.db");
            if (!databaseFile.exists())
            {
                try { databaseFile.createNewFile(); }
                catch (IOException e) { e.printStackTrace(); }
            }
            String driverClassName = "org.sqlite.JDBC";
            Class.forName(driverClassName);
            config.setDriverClassName(driverClassName);
            config.setJdbcUrl("jdbc:sqlite:" + path + "main.db");
        }
    }

    public HikariDataSource generateDataSource() { return new HikariDataSource(config); }
}