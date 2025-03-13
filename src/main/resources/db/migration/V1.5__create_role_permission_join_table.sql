create table if not exists registration_db.role_permission (
    role_id integer not null ,
    permission_id integer not null ,
    primary key (role_id, permission_id) ,
    foreign key (role_id) references registration_db.role(id) on delete cascade ,
    foreign key (permission_id) references registration_db.permission(id) on delete cascade
)