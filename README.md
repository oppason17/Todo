Javaを使用したWebベースのTODOリスト管理アプリケーションです。ユーザーはタスクを作成、編集、削除、完了としてマークすることができます。

開発環境: Eclipse  
データベース: H2 Database  
Webフレームワーク: Jakarta Servlet/JSP  
UI: HTML, CSS, JSP  


このアプリケーションはMVC（Model-View-Controller）アーキテクチャを採用しています

Model: Todo.java - TODOアイテムを表すデータモデル  
View: JSPファイル（list.jsp, edit.jspなど）  
Controller: TodoServlet.java - HTTPリクエストを処理  
DAO: TodoDAO.java, TodoDAOImpl.java - データベースアクセスを抽象化  
Service: TodoService.java - ビジネスロジックを集約  


------------------------------------------------------


主要な機能


1. TODOリストの表示  
パス: /todos  
メソッド: GET  
実装: TodoServlet.listTodos()  
サービス: TodoService.getAllTodos()  


2. 新規TODOアイテムの追加  
パス: /todos  
メソッド: POST (action=add)  
実装: TodoServlet.addTodo()  
サービス: TodoService.addTodo()  


3. TODOアイテムの編集フォーム表示  
パス: /todos/edit/{id}  
メソッド: GET  
実装: TodoServlet.doGet() 内の編集フォーム表示ロジック  


4. TODOアイテムの更新  
パス: /todos  
メソッド: POST (action=update)  
実装: TodoServlet.updateTodo()  
サービス: TodoService.updateTodo()  


5. TODOアイテムの削除  
パス: /todos  
メソッド: POST (action=delete)  
実装: TodoServlet.deleteTodo()  
サービス: TodoService.deleteTodo()  


6. TODOアイテムの完了状態切替  
パス: /todos  
メソッド: POST (action=toggle)  
実装: TodoServlet.toggleTodoStatus()  
サービス: TodoService.toggleTodoStatus()  

---------------------------------------------


主要コンポーネントの説明


1. モデル（Model）  
Todo.java  
TODOアイテムの基本構造を定義するクラス。以下のフィールドを持っています  
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
TodoDAOの操作をラップ  
toggleTodoStatus(): TODOアイテムの完了状態を切り替え  


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
