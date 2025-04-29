package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Todo;
import util.DatabaseConnection;

public class TodoDAOImpl implements TodoDAO {
    // TODOアイテムの追加
	@Override
	public Todo addTodo(Todo todo) {
	    System.out.println("addTodo: タイトル=" + todo.getTitle() + ", 説明=" + todo.getDescription());
	    String sql = "INSERT INTO todos (title, description, completed) VALUES (?, ?, ?)";
	    
	    try (Connection conn = DatabaseConnection.getConnection()) {
	        System.out.println("データベース接続取得成功");
	        
	        // テーブル存在確認
	        try (ResultSet rs = conn.getMetaData().getTables(null, null, "TODOS", null)) {
	            System.out.println("TODOSテーブルは" + (rs.next() ? "存在します" : "存在しません"));
	        }
	        
	        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            pstmt.setString(1, todo.getTitle());
	            pstmt.setString(2, todo.getDescription());
	            pstmt.setBoolean(3, todo.isCompleted());
	            
	            int rows = pstmt.executeUpdate();
	            System.out.println("INSERT実行結果: " + rows + "行挿入");
	            
	            // 自動生成されたIDを取得
	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    long id = generatedKeys.getLong(1);
	                    todo.setId(id);
	                    System.out.println("生成されたID: " + id);
	                } else {
	                    System.err.println("生成されたIDが取得できませんでした");
	                }
	            }
	            
	            return todo;
	        }
	    } catch (SQLException e) {
	        System.err.println("Todo追加エラー: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}
	
	// IDによるTODOアイテムの取得
    @Override
    public Todo getTodoById(Long id) {
        String sql = "SELECT * FROM todos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Todo todo = new Todo();
                    todo.setId(rs.getLong("id"));
                    todo.setTitle(rs.getString("title"));
                    todo.setDescription(rs.getString("description"));
                    todo.setCompleted(rs.getBoolean("completed"));
                    
                    return todo;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // 全TODOアイテムの取得
    @Override
    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todos ORDER BY created_at DESC";
        
        System.out.println("getAllTodos: クエリ実行: " + sql);
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("データベース接続取得成功");
            
            try (Statement stmt = conn.createStatement()) {
                // テーブル存在確認
                try (ResultSet tables = conn.getMetaData().getTables(null, null, "TODOS", null)) {
                    System.out.println("TODOSテーブルは" + (tables.next() ? "存在します" : "存在しません"));
                }
                
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("クエリ実行成功");
                    int count = 0;
                    
                    while (rs.next()) {
                        Todo todo = new Todo();
                        todo.setId(rs.getLong("id"));
                        todo.setTitle(rs.getString("title"));
                        todo.setDescription(rs.getString("description"));
                        todo.setCompleted(rs.getBoolean("completed"));
                        
                        todos.add(todo);
                        count++;
                        System.out.println("取得したTodo: ID=" + todo.getId() + ", タイトル=" + todo.getTitle());
                    }
                    
                    System.out.println("取得したTodo数: " + count);
                }
            }
        } catch (SQLException e) {
            System.err.println("Todoリスト取得エラー: " + e.getMessage());
            e.printStackTrace();
        }
        
        return todos;
    }
    
    // TODOアイテムの更新
    @Override
    public boolean updateTodo(Todo todo) {
        String sql = "UPDATE todos SET title = ?, description = ?, completed = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, todo.getTitle());
            pstmt.setString(2, todo.getDescription());
            pstmt.setBoolean(3, todo.isCompleted());
            pstmt.setLong(4, todo.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // TODOアイテムの削除
    @Override
    public boolean deleteTodo(Long id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}