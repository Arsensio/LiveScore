create table event_info
(
    goal_info_id   bigserial
        primary key,
    player_surname varchar,
    player_number  bigint,
    team_name      varchar,
    team_logo      varchar(3000),
    event_name     varchar,
    player_name    varchar,
    player_id      bigint,
    team_id        bigint,
    event_id       bigint,
    constraint fk_event
        foreign key (event_id)
            references events (event_id),
    constraint fk_team
        foreign key (team_id)
            references teams (team_id),
    constraint fk_player
        foreign key (player_id)
            references players (player_id)
);

insert into event_info
values (1, 'Megenov', 43, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'GOAL',
        'Timur', 4, 2, 1);
insert into event_info
values (2, 'Ulykbekov', 7, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'ASSIST',
        'Arsen', 2, 2, 1);
insert into event_info
values (3, 'Van', 21, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'YELLOW_CARD',
        'Persi', 8, 2, 2);
insert into event_info
values (4, 'Van', 21, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'RED_CARD',
        'Persi', 8, 2, 3);
insert into event_info
values (5, 'Lionel', 1, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'GOAL',
        'Messi', 6, 2, 4);
insert into event_info
values (6, 'Karim', 4, 'Avangard',
        'https://upload.wikimedia.org/wikipedia/ru/9/98/Real_Madrid.png',
        'GOAL',
        'Benzema', 5, 1, 5);
insert into event_info
values (7, 'Ronaldinho', 5, 'Avangard',
        'https://upload.wikimedia.org/wikipedia/ru/9/98/Real_Madrid.png',
        'GOAL',
        '', 7, 1, 5);
insert into event_info
values (8, 'Van', 21, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'GOAL',
        'Persi', 8, 2, 6);
insert into event_info
values (9, 'Ulykbekov', 5, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'ASSIST',
        'Arsen', 2, 2, 6);
insert into event_info
values (10, 'Van', 21, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'YELLOW_CARD',
        'Persi', 8, 2, 7);
insert into event_info
values (11, 'Van', 21, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'RED_CARD',
        'Persi', 8, 2, 8);
insert into event_info
values (12, 'Lionel', 21, 'Barabar',
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'GOAL',
        'Messi', 8, 2, 9);
insert into event_info
values (13, 'Karim', 21, 'Avangard',
        'https://upload.wikimedia.org/wikipedia/ru/9/98/Real_Madrid.png',
        'GOAL',
        'Benzema', 5, 1, 10);
insert into event_info
values (14, 'Ronaldinho', 21, 'Avangard',
        'https://upload.wikimedia.org/wikipedia/ru/9/98/Real_Madrid.png',
        'ASSIST',
        '', 7, 1, 10);


alter sequence event_info_goal_info_id_seq restart with 15;