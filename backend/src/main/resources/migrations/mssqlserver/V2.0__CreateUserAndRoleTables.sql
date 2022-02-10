create table users
(
    id              bigint identity primary key,
    username        nvarchar(255) not null,
    password        nvarchar(255) not null,
    date_registered datetime2 not null
);

go

create table roles
(
    id        bigint identity primary key,
    authority nvarchar(255) not null
);

go

create table users_roles
(
    user_id bigint not null foreign key references users,
    role_id bigint not null foreign key references roles,
);

go

insert into roles (authority)
values ('STANDARD'), ('ADMIN');