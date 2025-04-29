package service;

import java.util.List;

import dao.TodoDAO;
import dao.TodoDAOImpl;
import model.Todo;

public class TodoService {
    private TodoDAO todoDAO;

    public TodoService() {
        this.todoDAO = new TodoDAOImpl();
    }

    // 全Todoアイテムの取得
    public List<Todo> getAllTodos() {
        return todoDAO.getAllTodos();
    }

    // IDによるTodoの取得
    public Todo getTodoById(Long id) {
        return todoDAO.getTodoById(id);
    }

    // Todoの追加
    public Todo addTodo(Todo todo) {
        return todoDAO.addTodo(todo);
    }

    // Todoの更新
    public boolean updateTodo(Todo todo) {
        return todoDAO.updateTodo(todo);
    }

    // Todoの削除
    public boolean deleteTodo(Long id) {
        return todoDAO.deleteTodo(id);
    }

    // Todoの完了ステータス変更
    public boolean toggleTodoStatus(Long id) {
        Todo todo = getTodoById(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
            return updateTodo(todo);
        }
        return false;
    }
}