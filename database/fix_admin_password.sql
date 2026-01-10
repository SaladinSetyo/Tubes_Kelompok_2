-- Fix Admin Account - Updated Password Hash
-- This script will delete old admin and create new one with correct password

-- Delete existing admin if any
DELETE FROM users WHERE email = 'admin@paulquiz.com';

-- Create admin with correct BCrypt hash for 'admin123'
-- Using jBCrypt online hash generator: admin123 -> $2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5UDQPWBNOxu8.
INSERT INTO users (name, email, password, role, points, created_at, updated_at)
VALUES (
    'Administrator',
    'admin@paulquiz.com',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5UDQPWBNOxu8.',
    'admin',
    0,
    NOW(),
    NOW()
);

-- Verify creation
SELECT id, name, email, role, points, created_at 
FROM users 
WHERE email = 'admin@paulquiz.com';

-- Also create a test regular user for testing
INSERT INTO users (name, email, password, role, points, created_at, updated_at)
VALUES (
    'Test User',
    'test@paulquiz.com',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5UDQPWBNOxu8.',
    'user',
    0,
    NOW(),
    NOW()
);

-- Show all users
SELECT id, name, email, role, points FROM users;
