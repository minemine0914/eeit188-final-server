USE [eeit188final];

-- 使用 FOR LOOP 生成 400 名假用戶
DECLARE @i INT = 1;

WHILE @i <= 400
BEGIN
    INSERT INTO [dbo].[user] (id, name, role, gender, birthday, phone, mobile_phone, address, email, password, about, created_at)
    VALUES (
        NEWID(),
        'robot' + CAST(@i AS VARCHAR(3)), 
        'normal',
        -- 根據 @i 設定 gender
        CASE 
            WHEN @i BETWEEN 1 AND 100 THEN '暫不提供'
            WHEN @i BETWEEN 101 AND 200 THEN '男性'
            WHEN @i BETWEEN 201 AND 300 THEN '女性'
            ELSE '其他'
        END,
        -- 生成隨機生日日期
        DATEADD(DAY, ABS(CHECKSUM(NEWID())) % DATEDIFF(DAY, '1960-01-01', '2004-12-31'), '1960-01-01'),
        -- 生成 '02' 開頭，後面 8 位隨機數字的電話號碼
        '02' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000000, '00000000'),
        -- 生成 '09' 開頭，後面 8 位隨機數字的手機號碼
        '09' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000000, '00000000'),
        'lorem',                         
        'robot' + CAST(@i AS VARCHAR(3)) + '@example.com', 
        'P@ssw0rd',                     
        'lorem',
        DATEADD(DAY, ABS(CHECKSUM(NEWID())) % DATEDIFF(DAY, '2010-01-01', '2024-09-26'), '2010-01-01')                         
    );

    SET @i = @i + 1;
END;
