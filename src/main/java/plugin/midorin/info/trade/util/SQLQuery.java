package plugin.midorin.info.trade.util;

import plugin.midorin.info.trade.manager.Database;

public enum  SQLQuery
{
    CREATE_TABLE_SHOPS(
            "CREATE TABLE IF NOT EXISTS `Shops` ("+
                    "`id` int NOT NULL AUTO_INCREMENT," +
                    "`uuid` VARCHAR(35) NULL DEFAULT NULL," +
                    "`world` VARCHAR(16) NULL DEFAULT NULL," +
                    "`x` BIGINT," +
                    "`y` BIGINT," +
                    "`z` BIGINT," +
                    "`shop` BIGINT," +
                    "PRIMARY KEY (`id`))",

            "CREATE TABLE IF NOT EXISTS Shops (" +
                    "id INTEGER PRIMARY KEY," +
                    "uuid VARCHAR(35)," +
                    "world VARCHAR(16)," +
                    "x BIGINT," +
                    "y BIGINT," +
                    "z BIGINT" +
                    "shop BIGINT" +
                    ")"
    ),
    CREATE_TABLE_ITEMS(
            "CREATE TABLE IF NOT EXISTS 'Items' (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "itemId VARCHAR(16) NOT NULL," +
                    "shop BIGINT NOT NULL," +
                    "item VARCHAR(128) NOT NULL," +
                    ")",

            "CREATE TABLE IF NOT EXISTS Items (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "itemId VARCHAR(16)," +
                    "shop BIGINT," +
                    "item VARCHAR(128)" +
                    ")"
    ),
    INSERT_SHOPS(
            "INSERT INTO `Shops` " +
                    "(`id`, `uuid`, `world`, `x`, `y`, `z`, `shop`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)",
            "INSERT INTO Shops " +
                    "(id, uuid, world, x, y, z, shop) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
    ),
    INSERT_ITEMS(
            "INSERT INTO `Items` " +
                    "(`id`, `itemId`, `shop`, `item`) " +
                    "VALUES (?, ?, ?, ?)",
            "INSERT INTO Items " +
                    "(id, itemId, shop, item) " +
                    "VALUES (?, ?, ?, ?)"
    ),
    DELETE_SHOPS(
            "DELETE FROM `Shops` WHERE `id` = ?",
            "DELETE FROM Shops WHERE id = ?"
    ),
    DELETE_ITEMS(
            "DELETE FROM `Items` WHERE `itemId` = ?",
            "DELETE FROM Items WHERE itemId = ?"
    ),
    SELECT_EXACT_SHOPS(
            "SELECT * FROM `Shops` WHERE `uuid` = ? AND `world` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ? AND `shop` = ?",

            "SELECT * FROM Shops WHERE uuid = ? AND world = ? AND world = ? AND x = ? AND y = ? AND z = ? AND shop = ?"
    )
    /*
    ,
    SELECT_PROTECTED_BLOCK(
            "SELECT * FROM `Protected_blocks` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",

            "SELECT * FROM Protected_blocks WHERE world = ? AND x = ? AND y = ? AND z = ?"
    ),
    SELECT_PROTECTED_BLOCK_MEMBERS(
            "SELECT * FROM `Protected_block_Members` WHERE `protected_blockId` = ?",

            "SELECT * FROM Protected_block_Members WHERE protected_blockId = ?"
    ),
    SELECT_PROTECTED_PLAYER_BLOCK_LIST(
            "SELECT * FROM `Protected_blocks` WHERE `uuid` = ?",

            "SELECT * FROM Protected_blocks WHERE uuid = ?"
    )
    */
    ;


    private String mysql;
    private String sqlite;

    SQLQuery(String mysql, String sqlite)
    {
        this.mysql = mysql;
        this.sqlite = sqlite;
    }

    @Override
    public String toString() { return Database.get().isUseMySQL() ? mysql : sqlite; }
}