create table users
(
    user_id       bigint primary key,
    username      varchar(64),
    user_password varchar(255),
    user_role     varchar(16),
    block_flag    bool
);

insert into users
values (1, 'admin', '$2a$12$xJeEdFk/wX2kmMj5eH0Kou9EHyGypeggvsuLm7nODgQjsT7r1sLmW', 'ADMIN', false);