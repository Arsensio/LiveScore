INSERT INTO protocols(protocol_id, game_id, date_time, team_1_score, team_2_score, team_1_id, team_2_id)
VALUES (2,2,'2023-03-06 00:00:00',1,0,2,1);

INSERT INTO protocols(protocol_id, game_id, date_time, team_1_score, team_2_score, team_1_id, team_2_id)
VALUES (3,3,'2023-03-06 00:00:00',1,0,2,1);


insert into events
values (8,'GOAL','1:0', 11, 8, 2);
insert into events
values (9,'ASSIST','1:0', 11, 2, 2);
insert into events
values (10,'YELLOW_CARD','1:0', 30, 8, 2);

insert into events
values (11,'RED_CARD','0:0', 43, 8, 3);
insert into events
values (12,'GOAL','1:0', 49, 6, 3);
insert into events
values (13,'GOAL','1:1', 67, 5, 3);
insert into events
values (14,'ASSIST','1:1', 67, 7, 3);