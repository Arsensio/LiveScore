create table users
(
    user_id       bigserial
        primary key,
    role          varchar(16),
    user_password varchar(255),
    username      varchar(64)
);

insert into users
values (1, 'ADMIN', '$2a$10$g3mt6rf3.Cnhb37A4094buH.ThjZgg9sys5IHmHpx1d7u5vRSuzV.', 'admin');