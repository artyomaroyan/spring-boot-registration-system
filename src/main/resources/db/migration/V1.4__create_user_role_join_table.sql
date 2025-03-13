create table if not exists registration_db.user_role (
    user_id integer not null ,
    role_id integer not null ,
    primary key (user_id, role_id) ,
    foreign key (user_id) references registration_db.usr(id) on delete cascade ,
    foreign key (role_id) references registration_db.role(id) on delete cascade
)