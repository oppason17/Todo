<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TODO リスト</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>TODOリスト</h1>
        
        <!-- 新規タスク追加フォーム -->
        <div class="add-form">
            <h2>新規タスク追加</h2>
            <form action="${pageContext.request.contextPath}/todos" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="title">タイトル:</label>
                    <input type="text" id="title" name="title" required>
                </div>
                <div class="form-group">
                    <label for="description">説明:</label>
                    <textarea id="description" name="description" rows="3"></textarea>
                </div>
                <button type="submit" class="btn-primary">追加</button>
            </form>
        </div>
        
        <!-- タスク一覧 -->
        <div class="todo-list">
            <h2>タスク一覧</h2>
            
            <c:if test="${empty todos}">
                <p>タスクはありません。</p>
            </c:if>
            
            <c:if test="${not empty todos}">
                <c:forEach var="todo" items="${todos}">
                    <div class="todo-item ${todo.completed ? 'completed' : ''}">
                        <div class="todo-content">
                            <h3>${todo.title}</h3>
                            <p>${todo.description}</p>
                        </div>
                        <div class="todo-actions">
                            <form action="${pageContext.request.contextPath}/todos" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="toggle">
                                <input type="hidden" name="id" value="${todo.id}">
                                <button type="submit" class="btn-toggle">
                                    ${todo.completed ? '未完了に戻す' : '完了にする'}
                                </button>
                            </form>
                            <a href="${pageContext.request.contextPath}/todos/edit/${todo.id}" class="btn-edit">編集</a>
                            <form action="${pageContext.request.contextPath}/todos" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${todo.id}">
                                <button type="submit" class="btn-delete" onclick="return confirm('本当に削除しますか？')">削除</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
    
    <!-- デバッグ情報（開発中のみ表示） -->
    <div class="debug-info">
        <h3>デバッグ情報</h3>
        <p>取得したタスク数: ${todos.size()}</p>
    </div>
</body>
</html>