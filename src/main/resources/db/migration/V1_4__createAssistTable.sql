Create table assists
(
    id        bigint not null,
    player_id bigint not null,
    constraint fk_group
        foreign key (id)
            references events (event_id),
    primary key (id)
);



