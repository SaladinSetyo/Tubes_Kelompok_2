-- Admin Account Setup Script
-- This script creates an admin user account if it doesn't already exist

-- Check if admin exists first, if not create one
INSERT INTO users (name, email, password, role, points, created_at, updated_at)
SELECT 
    'Administrator',
    'admin@paulquiz.com',
    -- BCrypt hash of 'admin123' (generated with 10 rounds)
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'admin',
    0,
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@paulquiz.com'
);

-- Display confirmation
SELECT 
    CASE 
        WHEN (SELECT COUNT(*) FROM users WHERE email = 'admin@paulquiz.com') > 0 
        THEN 'Admin account created/verified successfully!'
        ELSE 'Failed to create admin account'
    END AS result;

-- Show admin account details (without password)
SELECT id, name, email, role AS admin_role, points, created_at 
FROM users 
WHERE email = 'admin@paulquiz.com';
