use db_server;


-- 自动生成的admin账户，密码123456
insert into t_account(account_name, password, role, name) values
('admin', '$2a$10$nn/cW3ezkYnFbcgomdUOWOzdVV/y1IzV/LFMancE.UfTtl1AFoUJq', 0, 'default-admin');
