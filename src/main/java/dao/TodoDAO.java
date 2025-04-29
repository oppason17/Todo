package dao;

import java.util.List;

import model.Todo;

public interface TodoDAO {
    Todo addTodo(Todo todo);
    Todo getTodoById(Long id);
    List<Todo> getAllTodos();
    boolean updateTodo(Todo todo);
    boolean deleteTodo(Long id);
}