-- DEBUG: Temporary Plain Password for Testing
-- This is ONLY for debugging - we'll switch back to BCrypt after

-- Update admin to use PLAIN password temporarily
UPDATE users 
SET password = 'admin123'
WHERE email = 'admin@paulquiz.com';

-- Verify
SELECT id, name, email, password, role FROM users WHERE email = 'admin@paulquiz.com';

-- NOTE: After we confirm login works with plain password,
-- we'll know the issue is with BCrypt and can fix accordingly
