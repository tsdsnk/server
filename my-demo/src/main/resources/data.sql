use db_server;

insert into t_account(account_name, password, role, name) values
('admin', '$2a$10$nn/cW3ezkYnFbcgomdUOWOzdVV/y1IzV/LFMancE.UfTtl1AFoUJq', 0, 'root'),
('user1', '$2a$10$T1q7Nu5tZsjMZLfvnzT8e.QkfLiMc9DOegd1VcGbPvhbk5.lGLooy', 1, 'userName1'),
('user2', '$2a$10$CXmdQDeQZD4BAADxl1SaL.7/yHcN464qEvfd25D1t.AVDfOXULvn2', 1, 'userName2');

insert into t_host(ip, name, port, gpu_type, gpu_memory, gpu_number, cuda_version, description, admin_port, admin_account_name, password) values
('219.223.251.65', '65服务器', 10025, 'V100', 32, 8, '9.0', '', 22, '65admin', 'vLAtUQOMaiFBU9wipBN+kA=='),
('10.249.187.136', 'H800', 10122, 'H800', 80, 3, '12.0', 'dasdf', 22, 'H800admin', ' n1zL5ucBMAkmG+nrgDlpjA=='),
('10.249.45.11', '5090', 10380, '5090', 32, 3, '12.0', 'spring', 22, 'asda', 'vLAtUQOMaiFBU9wipBN+kA==');
--
insert into t_application(account_name, ip, status, apply_account_name, apply_password, message) values
('user1', '219.223.251.65', 0, 'applying_name1', ' 2E988elGY1pWGQuK6ciQww== ', ''),
('user1', '10.249.187.136', 1, 'applying_name1', ' 2E988elGY1pWGQuK6ciQww== ', 'message'),
('user1', '10.249.45.11', 2, 'applying_name1', ' 2E988elGY1pWGQuK6ciQww== ', ''),
('user2', '10.249.187.136', 0, 'applying_name2', ' 2E988elGY1pWGQuK6ciQww== ', ''),
('user2', '10.249.45.11', 0, 'applying_name2', ' 2E988elGY1pWGQuK6ciQww== ', '');