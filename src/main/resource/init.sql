-- TODOテーブルの作成
CREATE TABLE IF NOT EXISTS todos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- サンプルデータの挿入（オプション）
INSERT INTO todos (title, description) VALUES 
    ('サンプルタスク1', 'これはサンプルタスクの説明です'),
    ('サンプルタスク2', 'もう一つのサンプルタスクです');