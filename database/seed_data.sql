-- =====================================================
-- SEED DATA - Paul Quiz (FINAL v2)
-- Populate database dengan sample data untuk testing
-- =====================================================

-- Disable foreign key checks to allow clearing data
SET FOREIGN_KEY_CHECKS = 0;

-- Clear tables (Use DELETE instead of TRUNCATE to avoid FK constraint errors)
DELETE FROM notifications;
DELETE FROM user_progress;
DELETE FROM quiz_attempts;
DELETE FROM answers;
DELETE FROM questions;
DELETE FROM quizzes;
DELETE FROM contents;
DELETE FROM modules;
DELETE FROM users WHERE email != 'admin@paulquiz.com';

-- Reset Auto Increment
ALTER TABLE notifications AUTO_INCREMENT = 1;
ALTER TABLE user_progress AUTO_INCREMENT = 1;
ALTER TABLE quiz_attempts AUTO_INCREMENT = 1;
ALTER TABLE answers AUTO_INCREMENT = 1;
ALTER TABLE questions AUTO_INCREMENT = 1;
ALTER TABLE quizzes AUTO_INCREMENT = 1;
ALTER TABLE contents AUTO_INCREMENT = 1;
ALTER TABLE modules AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;

-- =====================================================
-- USERS
-- =====================================================
-- Reset admin ID if needed, or ensure it exists.
-- We do not delete admin@paulquiz.com above to prevent locking out, but if it doesn't exist, insert it.
INSERT IGNORE INTO users (id, name, email, password, role, points, created_at, updated_at) VALUES
(1, 'Admin User', 'admin@paulquiz.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin', 0, NOW(), NOW());

INSERT INTO users (name, email, password, role, points, created_at, updated_at) VALUES
('Taveve', 'user1@test.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'user', 1500, NOW(), NOW()),
('Deon', 'user2@test.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'user', 1200, NOW(), NOW()),
('Dresta', 'user3@test.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'user', 950, NOW(), NOW());

-- =====================================================
-- MODULES
-- =====================================================
INSERT INTO modules (id, title, description, level, duration_minutes, points, icon, order_index, created_at, updated_at) VALUES
(1, 'Apa itu Fintech?', 'Modul pengenalan dasar tentang Financial Technology dan perannya di era digital modern.', 'Beginner', 10, 50, '<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/></svg>', 1, NOW(), NOW()),
(2, 'Jenis-jenis Fintech', 'Memahami berbagai jenis layanan fintech seperti payment, lending, investment, dan insurance.', 'Beginner', 20, 100, '<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>', 2, NOW(), NOW()),
(3, 'Keamanan Digital', 'Pelajari cara melindungi diri dari penipuan online dan menjaga keamanan transaksi digital.', 'Intermediate', 25, 150, '<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/></svg>', 3, NOW(), NOW()),
(4, 'Investasi untuk Pemula', 'Panduan lengkap memulai investasi dengan bijak menggunakan platform digital.', 'Intermediate', 30, 200, '<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"/></svg>', 4, NOW(), NOW()),
(5, 'Cryptocurrency Basics', 'Dasar-dasar cryptocurrency dan blockchain technology untuk pemula.', 'Advanced', 45, 300, '<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19.428 15.428a2 2 0 00-1.022-.547l-2.384-.477a6 6 0 00-3.86.517l-.318.158a6 6 0 01-3.86.517L6.05 15.21a2 2 0 00-1.806.547M8 4h8l-1 1v5.172a2 2 0 00.586 1.414l5 5c1.26 1.26.367 3.414-1.415 3.414H4.828c-1.782 0-2.674-2.154-1.414-3.414l5-5A2 2 0 009 10.172V5L8 4z"/></svg>', 5, NOW(), NOW());

-- =====================================================
-- QUIZZES
-- =====================================================
INSERT INTO quizzes (id, module_id, title, description, created_at, updated_at) VALUES
(1, 1, 'Kuis Pengenalan Fintech', 'Uji pemahaman Anda tentang dasar-dasar fintech', NOW(), NOW()),
(2, 2, 'Kuis Jenis Fintech', 'Test pengetahuan tentang berbagai layanan fintech', NOW(), NOW()),
(3, 3, 'Kuis Keamanan Digital', 'Seberapa aman transaksi digital Anda?', NOW(), NOW());

-- =====================================================
-- CONTENTS
-- =====================================================

-- Module 1: Apa itu Fintech
INSERT INTO contents (module_id, title, type, body, media_url, `order`, description, is_featured, quiz_id, created_at, updated_at) VALUES
(1, 'Pengenalan Fintech', 'article', 
'<h2>Apa itu Fintech?</h2>
<p>Financial Technology (Fintech) adalah inovasi di bidang jasa keuangan yang memanfaatkan teknologi digital untuk memberikan layanan yang lebih efisien, mudah diakses, dan terjangkau.</p>
<h3>Mengapa Fintech Penting?</h3>
<ul>
<li>Meningkatkan inklusi keuangan</li>
<li>Mempercepat transaksi</li>
<li>Menurunkan biaya operasional</li>
<li>Memberikan akses ke layanan keuangan 24/7</li>
</ul>',
NULL, 1, 'Artikel pengenalan dasar tentang fintech', TRUE, NULL, NOW(), NOW()),

-- Videos
(1, 'Apa itu Fintech? [BARU]', 'video', NULL,
'https://www.youtube.com/embed/a81bXkES-gg', 2, 'Video edukatif tentang apa itu fintech', TRUE, NULL, NOW(), NOW()),

(1, 'Perkembangan Fintech di Indonesia [BARU]', 'video', NULL,
'https://www.youtube.com/embed/t2DBd2FfHCI', 3, 'Video sejarah dan perkembangan fintech', TRUE, NULL, NOW(), NOW()),

(1, 'Jenis-Jenis Fintech [BARU]', 'video', NULL,
'https://www.youtube.com/embed/G9qUhcBcRgY', 4, 'Video penjelasan jenis-jenis fintech', TRUE, NULL, NOW(), NOW()),

-- Infographics
(1, 'Perkembangan Fintech Lending', 'infographic', NULL,
'assets/images/infographic_lending.jpg', 5, 'Data perkembangan pinjaman online di Indonesia.', TRUE, NULL, NOW(), NOW()),

(1, 'IPO Fintech', 'infographic', NULL,
'assets/images/infographic_ipo.jpg', 6, 'Infografis mengenai penawaran saham perdana perusahaan Fintech.', TRUE, NULL, NOW(), NOW()),

(1, 'Mengenal Fintech', 'infographic', NULL,
'assets/images/infographic_general.jpg', 7, 'Penjelasan visual mengenai apa itu Fintech.', TRUE, NULL, NOW(), NOW()),

(1, 'Kuis Pengenalan Fintech', 'quiz', NULL, NULL, 8, 'Kuis interaktif uji pemahaman', TRUE, 1, NOW(), NOW());

-- Module 2: Jenis-jenis Fintech
INSERT INTO contents (module_id, title, type, body, media_url, `order`, description, is_featured, quiz_id, created_at, updated_at) VALUES
(2, 'Payment & E-Wallet', 'article',
'<h2>Payment & E-Wallet</h2>
<p>E-wallet atau dompet digital adalah salah satu jenis fintech yang paling populer di Indonesia. Memungkinkan pengguna untuk melakukan transaksi tanpa uang tunai.</p>
<h3>Contoh E-Wallet:</h3>
<ul>
<li>GoPay, OVO, Dana, ShopeePay</li>
</ul>',
NULL, 1, 'Penjelasan lengkap tentang payment gateway dan e-wallet', FALSE, NULL, NOW(), NOW()),

(2, 'Video: Digital Banking', 'video', NULL,
'https://www.youtube.com/embed/dQw4w9WgXcQ', 2, 'Video tentang perbankan digital', FALSE, NULL, NOW(), NOW()),

(2, 'Kuis Jenis Fintech', 'quiz', NULL, NULL, 3, 'Test wawasan Anda', FALSE, 2, NOW(), NOW());

-- =====================================================
-- QUESTIONS & ANSWERS
-- =====================================================

-- Quiz 1 Questions
INSERT INTO questions (id, quiz_id, question_text, created_at, updated_at) VALUES
(1, 1, 'Apa kepanjangan dari Fintech?', NOW(), NOW()),
(2, 1, 'Manakah yang BUKAN merupakan manfaat fintech?', NOW(), NOW()),
(3, 1, 'Platform fintech yang digunakan untuk pembayaran digital disebut?', NOW(), NOW()),
(4, 1, 'Apa peran OJK dalam industri fintech di Indonesia?', NOW(), NOW()),
(5, 1, 'Contoh fintech payment di Indonesia adalah?', NOW(), NOW());

-- Quiz 1 Answers
INSERT INTO answers (question_id, answer_text, is_correct, created_at, updated_at) VALUES
(1, 'Financial Technology', TRUE, NOW(), NOW()),
(1, 'Finance Technique', FALSE, NOW(), NOW()),
(1, 'Finance Technology', FALSE, NOW(), NOW()),
(2, 'Meningkatkan biaya transaksi', TRUE, NOW(), NOW()),
(2, 'Mempercepat transaksi', FALSE, NOW(), NOW()),
(2, 'Meningkatkan inklusi keuangan', FALSE, NOW(), NOW()),
(3, 'Payment Gateway', TRUE, NOW(), NOW()),
(3, 'Cryptocurrency', FALSE, NOW(), NOW()),
(4, 'Mengawasi dan mengatur layanan fintech', TRUE, NOW(), NOW()),
(4, 'Menyediakan dana untuk startup fintech', FALSE, NOW(), NOW()),
(5, 'GoPay, OVO, Dana', TRUE, NOW(), NOW()),
(5, 'Facebook, Instagram', FALSE, NOW(), NOW());

SET FOREIGN_KEY_CHECKS = 1;
