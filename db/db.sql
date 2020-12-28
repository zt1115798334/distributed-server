drop database if exists student_db_0;
create database if not exists student_db_0;
use student_db_0;
drop table if exists t_user;
create table if not exists t_user
(
    id           bigint      not null
    primary key,
    account      varchar(50) null,
    password     varchar(50) null,
    salt         varchar(20) null,
    user_name    varchar(50) null,
    phone        varchar(50) null,
    email        varchar(50) null,
    created_time datetime    null,
    delete_state tinyint     not null
    );

drop table if exists t_book_0;
create table if not exists t_book_0
(
    id           bigint      not null
    primary key,
    book_name    varchar(30) null,
    user_id       bigint      null,
    created_time datetime    null,
    delete_state tinyint     not null
    );
drop table if exists t_book_1;
create table if not exists t_book_1
(
    id           bigint      not null
    primary key,
    book_name    varchar(30) null,
    user_id       bigint      null,
    created_time datetime    null,
    delete_state tinyint     not null
    );
drop table if exists t_book_2;
create table if not exists t_book_2
(
    id           bigint      not null
    primary key,
    book_name    varchar(30) null,
    user_id       bigint      null,
    created_time datetime    null,
    delete_state tinyint     not null
    );

drop database if exists student_db_1;
create database if not exists student_db_1;
use student_db_1;
drop table if exists t_user;
create table if not exists t_user
(
    id           bigint      not null
    primary key,
    account      varchar(50) null,
    password     varchar(50) null,
    salt         varchar(20) null,
    user_name    varchar(50) null,
    phone        varchar(50) null,
    email        varchar(50) null,
    created_time datetime    null,
    delete_state tinyint     not null
    );

drop table if exists t_book_0;
create table if not exists t_book_0
(
    id           bigint      not null
    primary key,
    book_name    varchar(30) null,
    user_id       bigint      null,
    created_time datetime    null,
    delete_state tinyint     not null
    );
drop table if exists t_book_1;
create table if not exists t_book_1
(
    id           bigint      not null
    primary key,
    book_name    varchar(30) null,
    user_id       bigint      null,
    created_time datetime    null,
    delete_state tinyint     not null
    );
drop table if exists t_book_1;
create table if not exists t_book_1
(
    id           bigint      not null
    primary key,
    book_name    varchar(30) null,
    user_id       bigint      null,
    created_time datetime    null,
    delete_state tinyint     not null
    );
drop table if exists t_book_2;
create table if not exists t_book_2
(
    id           bigint      not null
    primary key,
    book_name    varchar(30) null,
    user_id       bigint      null,
    created_time datetime    null,
    delete_state tinyint     not null
    );


