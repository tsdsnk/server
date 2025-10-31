drop database if exists db_server;
create database db_server character set utf8mb4 collate utf8mb4_unicode_ci;
use db_server;

create table t_account(
    account_name varchar(32) primary key ,
    password varchar(128) not null ,
    role int not null ,
    name varchar(32) not null
);

create table t_host(
    ip varchar(45) primary key ,
    name varchar(32) not null ,
    port int not null ,
    gpu_type varchar(64) not null ,
    gpu_memory int not null ,
    gpu_number int not null ,
    cuda_version varchar(16) not null ,
    description varchar(512) ,
    admin_port int not null ,
    admin_account_name varchar(32) not null ,
    password varchar(128) not null
);

create table t_application(
    application_id int auto_increment primary key,
    account_name varchar(32) not null,
    ip varchar(45) not null ,
    status int not null ,
    apply_account_name varchar(32) ,
    apply_password varchar(128) ,
    message varchar(512) ,
    last_update timestamp not null default current_timestamp on update current_timestamp ,

    unique key uk_account_ip (account_name, ip),

    constraint fk_application_account_name
        foreign key (account_name)
        references t_account(account_name)
        on delete cascade
        on update cascade ,

    constraint fk_ip
        foreign key (ip)
        references t_host(ip)
        on delete cascade
        on update cascade
);

create table t_jwt(
    id int auto_increment primary key,
    account_name varchar(32) unique not null ,
    jwt varchar(512) not null ,
    expire_time timestamp not null ,

    constraint fk_jwt_account_name
        foreign key (account_name)
        references t_account(account_name)
        on delete cascade
        on update cascade
);