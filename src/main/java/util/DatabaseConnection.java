package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    static {
        try {
            // プロパティファイルの読み込み
            Properties props = new Properties();
            InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties");
            
            if (input != null) {
                props.load(input);
                input.close();

                // H2データベースドライバーの登録
                Class.forName(props.getProperty("db.driver"));
                DB_URL = props.getProperty("db.url");
                DB_USER = props.getProperty("db.user");
                DB_PASSWORD = props.getProperty("db.password");
            } else {
                System.err.println("db.propertiesファイルが見つかりません");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // データベース接続の取得
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // コネクションのクローズ
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // H2データベースのテーブル初期化メソッド
 // util/DatabaseConnection.java の initDatabase メソッドを修正
    public static void initDatabase() {
        try {
            System.out.println("データベース初期化開始: URL=" + DB_URL);
            
            try (Connection conn = getConnection()) {
                System.out.println("データベース接続成功");
                
                // テーブル存在確認
                boolean tableExists = false;
                try (ResultSet rs = conn.getMetaData().getTables(null, null, "TODOS", null)) {
                    tableExists = rs.next();
                }
                
                System.out.println("TODOSテーブルは" + (tableExists ? "既に存在します" : "存在しません"));
                
                if (!tableExists) {
                    // テーブルの作成
                    conn.createStatement().execute(
                        "CREATE TABLE todos (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "description VARCHAR(1000), " +
                        "completed BOOLEAN DEFAULT FALSE, " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ")");
                    System.out.println("TODOSテーブルを作成しました");
                    
                    // サンプルデータ挿入
                    conn.createStatement().execute(
                        "INSERT INTO todos (title, description) VALUES " +
                        "('サンプルタスク1', 'これはサンプルタスクの説明です'), " +
                        "('サンプルタスク2', 'もう一つのサンプルタスクです')"
                    );
                    System.out.println("サンプルデータを挿入しました");
                }
                
                // テーブル構造確認
                try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM todos LIMIT 1")) {
                    System.out.println("TODOSテーブルの構造:");
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        System.out.println("  " + rs.getMetaData().getColumnName(i) + " (" + 
                                          rs.getMetaData().getColumnTypeName(i) + ")");
                    }
                } catch (SQLException e) {
                    System.err.println("テーブル構造確認エラー: " + e.getMessage());
                }
                
                // 既存データ件数確認
                try (ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM todos")) {
                    if (rs.next()) {
                        System.out.println("TODOSテーブルの既存データ件数: " + rs.getInt(1));
                    }
                } catch (SQLException e) {
                    System.err.println("データ件数確認エラー: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("データベース初期化エラー: " + e.getMessage());
            e.printStackTrace();
        }
    }    }