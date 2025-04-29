<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>エラー</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .error-container {
            text-align: center;
            padding: 40px 20px;
        }
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #dc3545;
        }
        .error-message {
            font-size: 24px;
            margin: 20px 0;
        }
        .back-btn {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container error-container">
        <div class="error-code">
            <%= response.getStatus() %>
        </div>
        <div class="error-message">
            <% if(response.getStatus() == 404) { %>
                ページが見つかりません。
            <% } else { %>
                エラーが発生しました。
            <% } %>
        </div>
        <p>申し訳ありませんが、リクエストの処理中に問題が発生しました。</p>
        <a href="${pageContext.request.contextPath}/todos" class="back-btn">TODOリストに戻る</a>
    </div>
</body>
</html>