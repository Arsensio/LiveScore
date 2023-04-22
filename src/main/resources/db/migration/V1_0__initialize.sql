-- sql script to create tables in database

create table tournaments
(
    tournament_id   bigserial
        primary key,
    tournament_name varchar(255),
    tournament_type varchar(255),
    tournament_logo varchar(3000),
    tournament_status varchar(255)
);

create table groups
(
    group_id      bigserial
        primary key,
    group_name    varchar(255),
    group_order   bigint,
    is_playoff    boolean,
    tournament_id bigint,
    constraint fk_tournament
        foreign key (tournament_id)
            references tournaments (tournament_id)
);

create table teams
(
    team_id   bigserial
        primary key,
    team_logo varchar(3000),
    team_name varchar(255)
);

CREATE TYPE game_state AS ENUM ('NOT_STARTED','STARTED','ENDED');

create table games
(
    game_id    bigserial
        primary key,
    game_state game_state default 'NOT_STARTED',
    group_id   bigint,
    constraint fk_group
        foreign key (group_id)
            references groups (group_id)
);

create table protocols
(
    protocol_id  bigserial
        primary key,
    game_id      bigint,
    date_time    timestamp,
    team_1_score integer,
    team_2_score integer,
    team_1_id    bigint,
    team_2_id    bigint,
    constraint fk_team_1
        foreign key (team_1_id)
            references teams (team_id),
    constraint fk_team_2
        foreign key (team_1_id)
            references teams (team_id),
    constraint fk_game
        foreign key (game_id)
            references games (game_id)
);

create table players
(
    player_id     bigserial
        primary key,
    name          varchar(255),
    player_number integer,
    role          varchar(255),
    surname       varchar(255),
    team_id       bigint,
    constraint fk_team
        foreign key (team_id)
            references teams (team_id)
);

create table player_statistics
(
    assists       bigint,
    goals         bigint,
    match_played  bigint,
    red_card      bigint,
    yellow_card   bigint,
    tournament_id bigint not null,
    player_id     bigint not null,
    constraint fk_group
        foreign key (tournament_id)
            references tournaments (tournament_id),
    constraint fk_player
        foreign key (player_id)
            references players (player_id),
    primary key (tournament_id, player_id)
);

create table team_statistics
(
    draw_count    integer,
    game_played   integer,
    goal_count    integer,
    goal_missed   integer,
    lose_count    integer,
    points        integer,
    win_count     integer,
    tournament_id bigint not null,
    team_id       bigint not null,
    constraint fk_group
        foreign key (tournament_id)
            references tournaments (tournament_id),
    constraint fk_teams
        foreign key (team_id)
            references teams (team_id),
    primary key (tournament_id, team_id)
);

create table events
(
    event_id    bigserial
        primary key,
    game_score  varchar(255),
    minute      integer,
    protocol_id bigint,
    constraint fk_protocol
        foreign key (protocol_id)
            references protocols (protocol_id)
);
