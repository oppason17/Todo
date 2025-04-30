package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static boolean initialized = false;

    // データベース設定の初期化
    static {
        try {
            // Tomcatの作業ディレクトリを取得
            String catalinaBase = System.getProperty("catalina.base");
            String dbFilePath = new File(catalinaBase, "webapps/TODO/database/todo").getAbsolutePath();

            DB_URL = "jdbc:h2:file:" + dbFilePath + ";AUTO_SERVER=TRUE";
            DB_USER = "sa";
            DB_PASSWORD = "";

            System.out.println("データベースパスを設定: " + DB_URL);

            // db.propertiesファイルから設定を読み込み
            Properties props = new Properties();
            InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties");

            if (input != null) {
                props.load(input);
                input.close();

                Class.forName(props.getProperty("db.driver", "org.h2.Driver"));
                DB_URL = props.getProperty("db.url", DB_URL);
                DB_USER = props.getProperty("db.user", DB_USER);
                DB_PASSWORD = props.getProperty("db.password", DB_PASSWORD);

                System.out.println("プロパティファイルからデータベース設定を読み込みました: " + DB_URL);
            } else {
                System.err.println("db.propertiesファイルが見つかりません。デフォルト設定を使用します。");
                Class.forName("org.h2.Driver");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("データベース初期化エラー: " + e.getMessage());
            e.printStackTrace();
            DB_URL = "jdbc:h2:./database/todo;AUTO_SERVER=TRUE";
            DB_USER = "sa";
            DB_PASSWORD = "";
        }
    }

    // 通常のDB接続取得（初期化はしない）
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }

    // DBの初期化（明示的にInitDatabaseServletなどから呼ぶ）
    public static void initDatabase() {
        if (initialized) return;
        initialized = true;

        System.out.println("データベース初期化開始: URL=" + DB_URL);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            boolean tableExists = tableExists(conn, "TODOS");
            System.out.println("TODOSテーブルは" + (tableExists ? "存在します。" : "存在しません。"));

            if (!tableExists) {
                try (Statement stmt = conn.createStatement()) {
                    String createTableSQL =
                        "CREATE TABLE todos (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "description VARCHAR(1000), " +
                        "completed BOOLEAN DEFAULT FALSE, " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
                    stmt.execute(createTableSQL);
                    System.out.println("TODOSテーブルを作成しました。");

                    String insertSQL =
                        "INSERT INTO todos (title, description) VALUES " +
                        "('サンプルタスク1', 'これはサンプルタスクの説明です'), " +
                        "('サンプルタスク2', 'もう一つのサンプルタスクです')";
                    stmt.execute(insertSQL);
                    System.out.println("サンプルデータを挿入しました。");
                }
            }

        } catch (SQLException e) {
            System.err.println("データベース初期化中のエラー: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
