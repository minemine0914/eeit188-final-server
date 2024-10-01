USE [eeit188final];

-- 刪除名為 robot1 到 robot100 的假用戶
DELETE FROM  [dbo].[user]
WHERE name LIKE 'robot%';
