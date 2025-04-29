package web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DatabaseConnection;

@WebServlet(name = "InitDatabaseServlet", urlPatterns = {"/init"}, loadOnStartup = 1)
public class InitDatabaseServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        try {
            System.out.println("データベース初期化を開始します...");
            DatabaseConnection.initDatabase();
            System.out.println("データベース初期化が完了しました");
        } catch (Exception e) {
            System.err.println("データベース初期化中にエラーが発生しました");
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().println("Database initialized successfully");
        response.getWriter().println("Check server logs for details");
    }
}