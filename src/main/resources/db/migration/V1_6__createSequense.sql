create table group_info
(
    group_info_id   bigserial primary key,
    group_name      varchar,
    team_name       varchar,
    status          varchar,
    team_logo       varchar(3000),
    tournament_logo varchar(3000),
    draw_count      integer,
    game_played     integer,
    goal_count      integer,
    goal_missed     integer,
    lose_count      integer,
    points          integer,
    win_count       integer,
    tournament_id   bigint not null,
    group_id        bigint not null,
    team_id         bigint not null,
    foreign key (tournament_id)
        references tournaments (tournament_id),
    foreign key (team_id)
        references teams (team_id),
    foreign key (group_id)
        references groups (group_id)
);

insert into group_info
values (1, 'Almaty', 'Avangard', 'IN_PROGRESS', 'https://upload.wikimedia.org/wikipedia/ru/9/98/Real_Madrid.png',
        'https://upload.wikimedia.org/wikipedia/commons/9/9f/New_logo_SDU.jpg', 1, 1, 1, 2, 0, 1, 0, 1, 1, 1);

insert into group_info
values (2, 'Almaty', 'Barabar', 'IN_PROGRESS',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'https://upload.wikimedia.org/wikipedia/commons/9/9f/New_logo_SDU.jpg', 0, 1, 2, 1, 0, 3, 1, 1, 1, 2);

insert into group_info
values (3, 'Almaty', 'Evolution', 'IN_PROGRESS',
        'https://upload.wikimedia.org/wikipedia/ru/thumb/7/7a/Manchester_United_FC_crest.svg/1200px-Manchester_United_FC_crest.svg.png',
        'https://upload.wikimedia.org/wikipedia/commons/9/9f/New_logo_SDU.jpg', 0, 0, 0, 0, 0, 0, 0, 1, 1, 3);

alter sequence group_info_group_info_id_seq restart with 4;