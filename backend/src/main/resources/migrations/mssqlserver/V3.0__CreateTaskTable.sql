create table tasks
(
    id          bigint identity primary key,
    description nvarchar(255) not null,
    in_progress bit       not null,
    due_date    datetime2,
    create_date datetime2 not null,
    user_id     bigint    not null foreign key references users
)

go
