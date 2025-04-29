package web;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Todo;
import service.TodoService;
import util.DatabaseConnection;

@WebServlet("/todos/*")
public class TodoServlet extends HttpServlet {
    private TodoService todoService;

    @Override
    public void init() throws ServletException {
        // データベースを初期化
        DatabaseConnection.initDatabase();
        // TodoServiceのインスタンス化
        todoService = new TodoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        // pathInfoがnullの場合はルートへのアクセス
        if (pathInfo == null || pathInfo.equals("/")) {
            // 全Todoリストを表示
            listTodos(request, response);
            return;
        }
        
        // /edit/{id} パスへのアクセス
        if (pathInfo.startsWith("/edit/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(6));
                Todo todo = todoService.getTodoById(id);
                request.setAttribute("todo", todo);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                return;
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/todos");
                return;
            }
        }
        
        // その他のパスの場合はリダイレクト
        response.sendRedirect(request.getContextPath() + "/todos");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/todos");
            return;
        }
        
        switch (action) {
            case "add":
                addTodo(request, response);
                break;
            case "update":
                updateTodo(request, response);
                break;
            case "delete":
                deleteTodo(request, response);
                break;
            case "toggle":
                toggleTodoStatus(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/todos");
        }
    }
    
    private void listTodos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Todo> todos = todoService.getAllTodos();
        request.setAttribute("todos", todos);
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }
    
    private void addTodo(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        System.out.println("addTodo: タイトル=" + title + ", 説明=" + description);
        
        if (title != null && !title.trim().isEmpty()) {
            Todo todo = new Todo();
            todo.setTitle(title);
            todo.setDescription(description);
            Todo addedTodo = todoService.addTodo(todo);
            
            if (addedTodo != null) {
                System.out.println("Todoアイテムが正常に追加されました: ID=" + addedTodo.getId());
            } else {
                System.err.println("Todoアイテムの追加に失敗しました");
            }
        } else {
            System.err.println("タイトルが空のため、Todoアイテムを追加できません");
        }
        
        response.sendRedirect(request.getContextPath() + "/todos");
    }
    
    private void updateTodo(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            boolean completed = "on".equals(request.getParameter("completed"));
            
            Todo todo = todoService.getTodoById(id);
            if (todo != null) {
                todo.setTitle(title);
                todo.setDescription(description);
                todo.setCompleted(completed);
                todoService.updateTodo(todo);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/todos");
    }
    
    private void deleteTodo(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            todoService.deleteTodo(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/todos");
    }
    
    private void toggleTodoStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            todoService.toggleTodoStatus(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/todos");
    }
}