Javaを使用したWebベースのTODOリスト管理アプリケーションです。
AIを使用して作成しました。
タスクの作成と編集、削除ができます。完了したものは分かりやすく表示します。

開発環境: Eclipse  
データベース: H2 Database  
Webフレームワーク: Jakarta Servlet/JSP  
UI: HTML, CSS, JSP  
使用AI：Claude 3.7Sonnet

このアプリケーションはMVC（Model-View-Controller）アーキテクチャを採用しています

Model: Todo.java - TODOアイテムを表すデータモデル  
View: JSPファイル（list.jsp, edit.jspなど）  
Controller: TodoServlet.java - HTTPリクエストを処理  
DAO: TodoDAO.java, TodoDAOImpl.java - データベースアクセスを抽象化  
Service: TodoService.java - ビジネスロジックを集約  

------------------------------------------------------
![title](https://github.com/user-attachments/assets/3943a10b-38f1-4661-9e06-fb0accec5b3c)
![task3](https://github.com/user-attachments/assets/b3628690-7fe1-4f4e-a71f-0c120ef6a921)

------------------------------------------------------


主要な機能


1. TODOリストの表示  
2. 新規TODOアイテムの追加  
3. TODOアイテムの編集フォーム表示  
4. TODOアイテムの更新  
5. TODOアイテムの削除  
6. TODOアイテムの完了状態切替  

---------------------------------------------


主要コンポーネントの説明


1. モデル（Model）  
Todo.java  
TODOアイテムの基本構造を定義するクラス。  
id: TODOアイテムの一意識別子  
title: TODOアイテムのタイトル  
description: 詳細説明  
completed: 完了状態（true/false）  
createdAt: 作成日時  
updatedAt: 更新日時  


2. データアクセスオブジェクト（DAO）  
TodoDAO.java（インターフェース）  
データアクセス操作を定義するインターフェース  
addTodo(): 新しいTODOアイテムの追加  
getTodoById(): 指定IDのTODOアイテム取得  
getAllTodos(): 全TODOアイテムの取得  
updateTodo(): TODOアイテムの更新  
deleteTodo(): TODOアイテムの削除  
TodoDAOImpl.java（実装クラス）  
TodoDAOインターフェースの実装。H2データベースへのSQL操作を行います。  


3. サービスレイヤー（Service）  
TodoService.java  
ビジネスロジックを含むサービスクラス。DAOレイヤーとコントローラー間の仲介役として機能します。  


4. データベース接続（Util）  
DatabaseConnection.java  
データベース接続を管理するユーティリティクラス  
getConnection(): DB接続の取得  
closeConnection(): DB接続を閉じる  
initDatabase(): DBの初期化（テーブル作成、サンプルデータ挿入）  

5. コントローラー（Servlet）  
InitDatabaseServlet.java  
アプリケーション起動時にデータベースを初期化するサーブレット。  
@WebServlet アノテーションで /init パスにマッピング  
loadOnStartup=1 でアプリケーション起動時に実行  
TodoServlet.java  
TODOリストのメイン操作を処理するサーブレット。  
@WebServlet("/todos/*") パスにマッピング  
doGet(): GETリクエスト処理（リスト表示、編集フォーム表示）  
doPost(): POSTリクエスト処理（追加、更新、削除、ステータス切替）  

6. ビュー（JSP）
list.jsp - TODOリスト一覧表示と新規追加フォーム
edit.jsp - 既存TODOアイテムの編集フォーム
index.jsp - アプリケーションのエントリーポイント（TODOリストにリダイレクト）
error.jsp - エラー表示ページ

---------------------------------------------
![task2](https://github.com/user-attachments/assets/f925d3d9-3963-4cce-b18e-4831b56e178e)  
文字の長さによってタスク一覧の形式がボタンを含めて崩れる
