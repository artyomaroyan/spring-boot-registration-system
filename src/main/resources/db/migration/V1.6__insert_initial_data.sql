-- Insert roles
insert into registration_db.role (created_date, updated_date, roles) VALUES
       (current_timestamp, current_timestamp, 'GUEST'),
       (current_timestamp, current_timestamp, 'USER'),
       (current_timestamp, current_timestamp, 'MANAGER'),
       (current_timestamp, current_timestamp, 'ADMIN')
on conflict (roles) do nothing;

-- Insert permissions
insert into registration_db.permission (created_date, updated_date, permissions) VALUES
        (current_timestamp, current_timestamp, 'VIEW_PUBLIC_CONTENT'),
        (current_timestamp, current_timestamp, 'UPDATE_PROFILE'),
        (current_timestamp, current_timestamp, 'CREATE_CONTENT'),
        (current_timestamp, current_timestamp, 'VIEW_OWN_CONTENT'),
        (current_timestamp, current_timestamp, 'APPROVE_CONTENT'),
        (current_timestamp, current_timestamp, 'REJECT_CONTENT'),
        (current_timestamp, current_timestamp, 'MANAGE_ORDERS'),
        (current_timestamp, current_timestamp, 'VIEW_REPORTS'),
        (current_timestamp, current_timestamp, 'MODERATE_USERS'),
        (current_timestamp, current_timestamp, 'ASSIGN_TASKS'),
        (current_timestamp, current_timestamp, 'MANAGE_USERS'),
        (current_timestamp, current_timestamp, 'ASSIGN_ROLES'),
        (current_timestamp, current_timestamp, 'DELETE_CONTENT'),
        (current_timestamp, current_timestamp, 'CONFIGURE_SYSTEM'),
        (current_timestamp, current_timestamp, 'ACCESS_LOGS'),
        (current_timestamp, current_timestamp, 'MANAGE_SECURITY')
on conflict (permissions) do nothing;

-- Associate permissions with roles
-- GUEST
insert into registration_db.role_permission (role_id, permission_id) VALUES
        ((select id from registration_db.role where roles = 'GUEST'),
         (select id from registration_db.permission where permissions = 'VIEW_PUBLIC_CONTENT'))
on conflict (role_id, permission_id) do nothing;

-- Associate permissions with roles
-- USER
insert into registration_db.role_permission (role_id, permission_id) VALUES
        ((select id from registration_db.role where roles = 'USER'),
         (select id from registration_db.permission where permissions = 'UPDATE_PROFILE')),
        ((select id from registration_db.role where roles = 'USER'),
         (select id from registration_db.permission where permissions = 'CREATE_CONTENT')),
        ((select id from registration_db.role where roles = 'USER'),
         (select id from registration_db.permission where permissions = 'VIEW_OWN_CONTENT'))
on conflict (role_id, permission_id) do nothing;

-- Associate permissions with roles
-- MANAGER
insert into registration_db.role_permission (role_id, permission_id) VALUES
        ((select id from registration_db.role where roles = 'MANAGER'),
         (select id from registration_db.permission where permissions = 'APPROVE_CONTENT')),
        ((select id from registration_db.role where roles = 'MANAGER'),
         (select id from registration_db.permission where permissions = 'REJECT_CONTENT')),
        ((select id from registration_db.role where roles = 'MANAGER'),
         (select id from registration_db.permission where permissions = 'MANAGE_ORDERS')),
        ((select id from registration_db.role where roles = 'MANAGER'),
         (select id from registration_db.permission where permissions = 'VIEW_REPORTS')),
        ((select id from registration_db.role where roles = 'MANAGER'),
         (select id from registration_db.permission where permissions = 'MODERATE_USERS')),
        ((select id from registration_db.role where roles = 'MANAGER'),
         (select id from registration_db.permission where permissions = 'ASSIGN_TASKS'))
on conflict (role_id, permission_id) do nothing;

-- Associate permissions with roles
-- ADMIN
insert into registration_db.role_permission (role_id, permission_id) VALUES
        ((select id from registration_db.role where roles = 'ADMIN'),
         (select id from registration_db.permission where permissions = 'MANAGE_USERS')),
        ((select id from registration_db.role where roles = 'ADMIN'),
         (select id from registration_db.permission where permissions = 'ASSIGN_ROLES')),
        ((select id from registration_db.role where roles = 'ADMIN'),
         (select id from registration_db.permission where permissions = 'DELETE_CONTENT')),
        ((select id from registration_db.role where roles = 'ADMIN'),
         (select id from registration_db.permission where permissions = 'CONFIGURE_SYSTEM')),
        ((select id from registration_db.role where roles = 'ADMIN'),
         (select id from registration_db.permission where permissions = 'ACCESS_LOGS')),
        ((select id from registration_db.role where roles = 'ADMIN'),
         (select id from registration_db.permission where permissions = 'MANAGE_SECURITY'))
on conflict (role_id, permission_id) do nothing;