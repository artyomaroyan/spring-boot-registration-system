package am.registration.system.demo.model.enums;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
public enum Permissions {

    // Guest can have view-only permission
    VIEW_PUBLIC_CONTENT,

    // User permissions include guest permissions
    UPDATE_PROFILE,
    CREATE_CONTENT,
    VIEW_OWN_CONTENT,

    // Manager permissions include user permissions
    APPROVE_CONTENT,
    REJECT_CONTENT,
    MANAGE_ORDERS,
    VIEW_REPORTS,
    MODERATE_USERS,
    ASSIGN_TASKS,

    // Admin permissions include manager permissions
    MANAGE_USERS,
    ASSIGN_ROLES,
    DELETE_CONTENT,
    CONFIGURE_SYSTEM,
    ACCESS_LOGS,
    MANAGE_SECURITY
}