-- sql scripts to fill database in case of change in events

drop schema public cascade;

create schema public;

insert into teams values (1, 'def', 'Avangar');
insert into teams values (2, 'def', 'Barabar');
insert into teams values (3, 'def', 'Evolution');

-- После того как смержу с Арсеном, поменять местами
insert into players values (1, 'Ravil', '0', 'STRIKER', 'A', 1);
insert into players values (2, 'Arsen', '7', 'STRIKER', 'U', 2);
insert into players values (3, 'Nurzhan', '28', 'GOALKEEPER', 'K', 1);
insert into players values (4, 'Zhandos', '43', 'DEFENDER', 'B', 2);

insert into protocols values (1, '2022-12-07 14:43:07.000000', 0, 0, 1, 2);
insert into protocols values (2, '2022-12-08 21:17:14.000000', 0, 0, 1, 2);

insert into events values (1, 'GOAL', '11', 1, 1);
insert into events values (2, 'ASSIST', '11', 2, 2);
insert into events values (3, 'YELLOW_CARD', '11', 3, 1);
insert into events values (4, 'RED_CARD', '11', 4, 2);


alter sequence teams_team_id_seq restart with 4;
alter sequence players_player_id_seq restart with 5;
alter sequence protocols_protocol_id_seq restart with 3;
alter sequence events_event_id_seq restart with 5;
