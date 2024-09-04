create table ticket (
        id int identity not null,
        qr_code varchar(max),
        house_id uniqueidentifier,
		user_id uniqueidentifier,
        started_at datetime2,
        ended_at datetime2,
		created_at datetime2,
        primary key (id)
    )


create table postulate (
        id int identity not null,
        postulate varchar(15),
        created_at datetime2,
        primary key (id)
    )


create table house_postulate (
        id int identity not null,
        postulate varchar(15),
        house_id uniqueidentifier,
        created_at datetime2,
        primary key (id)
    )