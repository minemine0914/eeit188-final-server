create table ticket (
        id uniqueidentifier not null,
        qr_code varchar(max),
        house_id uniqueidentifier,
		user_id uniqueidentifier,
        started_at datetime2,
        ended_at datetime2,
		created_at datetime2,
        primary key (id)
    )


create table postulate (
        id uniqueidentifier not null,
        postulate varchar(15),
        created_at datetime2,
        primary key (id)
    )


create table house_postulate (
        postulate_id uniqueidentifier not null,
		house_id uniqueidentifier not null,
        postulate varchar(15),
        created_at datetime2,
        primary key (house_id, postulate_id)
    )


create table cart (
        user_id uniqueidentifier not null,
        house_id uniqueidentifier not null,
        created_at datetime2,
        primary key (house_id, user_id)
    )