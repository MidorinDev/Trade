package plugin.midorin.info.trade.manager;

import com.zaxxer.hikari.HikariDataSource;
import plugin.midorin.info.trade.util.SQLQuery;
import plugin.midorin.info.trade.util.Utils;
import plugin.midorin.info.trade.util.DynamicDataSource;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database
{
    private HikariDataSource dataSource;
    private boolean useMySQL;
    private RowSetFactory factory;
    private static Database instance = null;

    /**
     * コマンドマネージャのインスタンスを取得します
     *
     * @return データベースマネージャのインスタンス
     */
    public static Database get() { return instance == null ? instance = new Database() : instance; }

    /**
     * 最初にデータベースに接続し、必要なテーブルを設定
     *
     * @param useMySQLServer MySQL を使用するかどうか (フォールバックとして sqlite を使用)
     */
    public void setup(boolean useMySQLServer)
    {
        useMySQL = useMySQLServer;
        try { dataSource = new DynamicDataSource(useMySQL).generateDataSource(); }
        catch (ClassNotFoundException ex) { return; }
        executeStatement(SQLQuery.CREATE_TABLE_SHOPS);
        executeStatement(SQLQuery.CREATE_TABLE_ITEMS);
    }

    /**
     * 使用されている場合は、SQLITE をシャットダウンします。
     */
    public void shutdown()
    {
        if (!useMySQL)
        {
            try(Connection connection = dataSource.getConnection(); final PreparedStatement statement = connection.prepareStatement("SHUTDOWN")){ statement.execute(); }
            catch (SQLException | NullPointerException exc){ exc.printStackTrace(); }
        }
        dataSource.close();
    }

    private CachedRowSet createCachedRowSet() throws SQLException
    {
        if (factory == null) factory = RowSetProvider.newFactory();
        return factory.createCachedRowSet();
    }

    public void executeStatement(SQLQuery sql, Object... parameters) { executeStatement(sql, false, parameters); }
    public ResultSet executeResultStatement(SQLQuery sql, Object... parameters) { return executeStatement(sql, true, parameters); }
    private ResultSet executeStatement(SQLQuery sql, boolean result, Object... parameters) { return executeStatement(sql.toString(), result, parameters); }

    private synchronized ResultSet executeStatement(String sql, boolean result, Object... parameters)
    {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql))
        {
            for (int i = 0; i < parameters.length; i++) statement.setObject(i + 1, parameters[i]);
            if (result)
            {
                CachedRowSet results = createCachedRowSet();
                results.populate(statement.executeQuery());
                return results;
            }
            statement.execute();
        }
        catch (SQLException ex)
        {
            Utils.log(
                    "データベースのステートメントを実行すると予期しないエラーが発生しました。\n"
                            + "plugins/Trade/logs/latest.logファイルをチェックして報告してください。 "
            );
            Utils.debug("Query: \n" + sql);
            Utils.debugSqlException(ex);
        }
        catch (NullPointerException ex) { ex.printStackTrace(); }
        return null;
    }
    public boolean isConnectionValid() { return dataSource.isRunning(); }
    public boolean isUseMySQL() { return useMySQL; }
}