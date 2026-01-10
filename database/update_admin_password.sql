-- SIMPLE SQL UPDATE - Copy Paste This Entire Block
-- Replace HASH_FROM_DEBUG_PAGE with the actual hash from Test 1

UPDATE users 
SET password = '$2a$10$NYX8ByJm3sRuoNMEP7ugP.aR8tngLOv.mvAKcLs/dAocyQHErthj2'
WHERE email = 'admin@paulquiz.com';

-- Verify the update
SELECT id, name, email, LEFT(password, 20) as password_preview, role 
FROM users 
WHERE email = 'admin@paulquiz.com';
