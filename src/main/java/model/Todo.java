package model;

import java.time.LocalDateTime;

public class Todo {
    // フィールド
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // デフォルトコンストラクタ
    public Todo() {
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }

    // 全フィールド用のコンストラクタ
    public Todo(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }

    // ゲッターとセッター
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // toStringメソッドのオーバーライド
    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    // equalsとhashCodeメソッド
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        return id != null ? id.equals(todo.id) : todo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}