-- sql script to populate database with some data for testing purposes

insert into tournaments
values (1, 'SFL', 'LEAGUE');
insert into tournaments
values (2, 'SFC', 'CUP');

insert into groups
values (1, 'Group A', false, 1);

insert into teams
values (1, 'https://upload.wikimedia.org/wikipedia/ru/9/98/Real_Madrid.png', 'Avangar');
insert into teams
values (2,
        'https://static.dezeen.com/uploads/2017/01/juventus-logo-design-graphics-football_products_dezeen_2364_col_7.jpg',
        'Barabar');
insert into teams
values (3,
        'https://upload.wikimedia.org/wikipedia/ru/thumb/7/7a/Manchester_United_FC_crest.svg/1200px-Manchester_United_FC_crest.svg.png',
        'Evolution');

insert into players
values (1, 'Ravil', '0', 'MIDDLE_DEFENDER', 'Amangeldiuly', 1);
insert into players
values (2, 'Arsen', '7', 'MIDDLE_DEFENDER', 'Ulykbekov', 2);
insert into players
values (3, 'Aruzan', '28', 'GOALKEEPER', 'Boranbay', 1);
insert into players
values (4, 'Timur', '43', 'GOALKEEPER', 'Mergenov', 2);
insert into players
values (5, 'Karim', '4', 'STRIKER', 'Benzema', 1);
insert into players
values (6, 'Lionel', '1', 'STRIKER', 'Messi', 2);
insert into players
values (7, 'Ronaldinho', '5', 'STRIKER', '', 1);
insert into players
values (8, 'Van', '21', 'STRIKER', 'Persie', 2);
insert into players
values (9, 'Zinedine', '9', 'DEFENDER', 'Zidane', 1);
insert into players
values (10, 'Zlatan', '12', 'DEFENDER', 'Ibrahimovic', 2);
insert into players
values (11, 'Emiliano', '31', 'DEFENDER', 'Sala', 1);
insert into players
values (12, 'Jesus', '22', 'DEFENDER', 'Navas', 2);

insert into protocols
values (1, '2022-12-07 14:43:07.000000', 1, 2, 1, 2);

insert into games
values (1, true, 1, 1);
insert into games
values (2, false, 1);
insert into games
values (3, false, 1);

insert into events
values (1, 'GOAL', 11, 8, 1);
insert into events
values (2, 'ASSIST', 11, 2, 1);
insert into events
values (3, 'YELLOW_CARD', 30, 8, 1);
insert into events
values (4, 'RED_CARD', 43, 8, 1);
insert into events
values (5, 'GOAL', 49, 6, 1);
insert into events
values (6, 'GOAL', 67, 5, 1);
insert into events
values (7, 'ASSIST', 67, 7, 1);

insert into player_statistics
values (0, 0, 1, 0, 0, 1, 1);
insert into player_statistics
values (1, 0, 1, 0, 0, 1, 2);
insert into player_statistics
values (0, 0, 1, 0, 0, 1, 3);
insert into player_statistics
values (0, 0, 1, 0, 0, 1, 4);
insert into player_statistics
values (0, 1, 1, 0, 0, 1, 5);
insert into player_statistics
values (0, 1, 1, 0, 0, 1, 6);
insert into player_statistics
values (1, 0, 1, 0, 0, 1, 7);
insert into player_statistics
values (0, 1, 1, 1, 1, 1, 8);
insert into player_statistics
values (0, 0, 1, 0, 0, 1, 9);
insert into player_statistics
values (0, 0, 1, 0, 0, 1, 10);
insert into player_statistics
values (0, 0, 1, 0, 0, 1, 11);
insert into player_statistics
values (0, 0, 1, 0, 0, 1, 12);

insert into team_statistics
values (0, 1, 1, 2, 1, 1, 0, 1, 1);
insert into team_statistics
values (0, 1, 2, 1, 0, 3, 1, 1, 2);
insert into team_statistics
values (0, 0, 0, 0, 0, 0, 0, 1, 3);
