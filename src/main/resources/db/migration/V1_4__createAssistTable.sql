Create table assists
(
    event_id  bigint not null,
    player_id bigint not null,
    constraint fk_group
        foreign key (event_id)
            references events (event_id),
    primary key (event_id)
);



