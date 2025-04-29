<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TODOの編集</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>TODOの編集</h1>
        
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/todos" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${todo.id}">
                <div class="form-group">
                    <label for="title">タイトル:</label>
                    <input type="text" id="title" name="title" value="${todo.title}" required>
                </div>
                <div class="form-group">
                    <label for="description">説明:</label>
                    <textarea id="description" name="description" rows="3">${todo.description}</textarea>
                </div>
                <div class="form-group checkbox">
                    <label for="completed">
                        <input type="checkbox" id="completed" name="completed" ${todo.completed ? 'checked' : ''}>
                        完了
                    </label>
                </div>
                <div class="form-buttons">
                    <button type="submit" class="btn update-btn">更新</button>
                    <a href="${pageContext.request.contextPath}/todos" class="btn cancel-btn">キャンセル</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>