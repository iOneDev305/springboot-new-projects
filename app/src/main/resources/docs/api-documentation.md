# API Documentation

This document provides information on the API endpoints for managing the permission system.

All endpoints are prefixed with `/api/`.

## 1. Permission Groups (`/api/permission-groups`)

### Create a Permission Group

-   **Endpoint:** `POST /api/permission-groups/create`
-   **Description:** Creates a new permission group.
-   **Request:** `form-data`
    -   `groupName`: The name of the permission group (String, Required)
    -   `isActive`: Whether the group is active (boolean, Required)
-   **Example (using curl):**
    ```bash
    curl -X POST http://localhost:8080/api/permission-groups/create \
    -F "groupName=Admin" \
    -F "isActive=true"
    ```

### Get All Permission Groups

-   **Endpoint:** `GET /api/permission-groups`
-   **Description:** Retrieves all permission groups.
-   **Example (using curl):**
    ```bash
    curl http://localhost:8080/api/permission-groups
    ```

### Get Permission Group by ID

-   **Endpoint:** `GET /api/permission-groups/{id}`
-   **Description:** Retrieves a specific permission group by its ID.
-   **Path Variable:**
    -   `id`: The ID of the permission group (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl http://localhost:8080/api/permission-groups/{id}
    ```

### Update a Permission Group

-   **Endpoint:** `PUT /api/permission-groups/update/{id}`
-   **Description:** Updates an existing permission group.
-   **Path Variable:**
    -   `id`: The ID of the permission group to update (Long, Required)
-   **Request:** `form-data`
    -   `groupName`: The new name of the permission group (String, Required)
    -   `isActive`: The new active status (boolean, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X PUT http://localhost:8080/api/permission-groups/update/{id} \
    -F "groupName=SuperAdmin" \
    -F "isActive=true"
    ```

### Delete a Permission Group

-   **Endpoint:** `DELETE /api/permission-groups/{id}`
-   **Description:** Deletes a permission group by its ID.
-   **Path Variable:**
    -   `id`: The ID of the permission group to delete (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X DELETE http://localhost:8080/api/permission-groups/{id}
    ```

## 2. Admin Users (`/api/admin-users`)

### Create an Admin User

-   **Endpoint:** `POST /api/admin-users/create`
-   **Description:** Creates a new admin user linked to a permission group.
-   **Request:** `form-data`
    -   `username`: The username for the admin user (String, Required)
    -   `permissionGroupId`: The ID of the permission group for the user (Long, Required)
-   **Example (using curl, replace {permissionGroupId} with actual ID):**
    ```bash
    curl -X POST http://localhost:8080/api/admin-users/create \
    -F "username=admin_user_1" \
    -F "permissionGroupId={permissionGroupId}"
    ```

### Get All Admin Users

-   **Endpoint:** `GET /api/admin-users`
-   **Description:** Retrieves all admin users.
-   **Example (using curl):**
    ```bash
    curl http://localhost:8080/api/admin-users
    ```

### Get Admin User by ID

-   **Endpoint:** `GET /api/admin-users/{id}`
-   **Description:** Retrieves a specific admin user by ID.
-   **Path Variable:**
    -   `id`: The ID of the admin user (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl http://localhost:8080/api/admin-users/{id}
    ```

### Update an Admin User

-   **Endpoint:** `PUT /api/admin-users/update/{id}`
-   **Description:** Updates an existing admin user.
-   **Path Variable:**
    -   `id`: The ID of the admin user to update (Long, Required)
-   **Request:** `form-data`
    -   `username`: The new username (String, Required)
    -   `permissionGroupId`: The new permission group ID (Long, Required)
-   **Example (using curl, replace {id} and {newPermissionGroupId} with actual IDs):**
    ```bash
    curl -X PUT http://localhost:8080/api/admin-users/update/{id} \
    -F "username=updated_admin" \
    -F "permissionGroupId={newPermissionGroupId}"
    ```

### Delete an Admin User

-   **Endpoint:** `DELETE /api/admin-users/{id}`
-   **Description:** Deletes an admin user by ID.
-   **Path Variable:**
    -   `id`: The ID of the admin user to delete (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X DELETE http://localhost:8080/api/admin-users/{id}
    ```

## 3. Modules (`/api/modules`)

### Create a Module

-   **Endpoint:** `POST /api/modules/create`
-   **Description:** Creates a new module.
-   **Request:** `form-data`
    -   `moduleName`: The name of the module (String, Required)
    -   `isView`: Whether the module is viewable (boolean, Required)
-   **Example (using curl):**
    ```bash
    curl -X POST http://localhost:8080/api/modules/create \
    -F "moduleName=Users" \
    -F "isView=true"
    ```

### Get All Modules

-   **Endpoint:** `GET /api/modules`
-   **Description:** Retrieves all modules.
-   **Example (using curl):**
    ```bash
    curl http://localhost:8080/api/modules
    ```

### Get Module by ID

-   **Endpoint:** `GET /api/modules/{id}`
-   **Description:** Retrieves a specific module by ID.
-   **Path Variable:**
    -   `id`: The ID of the module (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl http://localhost:8080/api/modules/{id}
    ```

### Update a Module

-   **Endpoint:** `PUT /api/modules/update/{id}`
-   **Description:** Updates an existing module.
-   **Path Variable:**
    -   `id`: The ID of the module to update (Long, Required)
-   **Request:** `form-data`
    -   `moduleName`: The new name of the module (String, Required)
    -   `isView`: The new viewable status (boolean, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X PUT http://localhost:8080/api/modules/update/{id} \
    -F "moduleName=Articles" \
    -F "isView=false"
    ```

### Delete a Module

-   **Endpoint:** `DELETE /api/modules/{id}`
-   **Description:** Deletes a module by ID.
-   **Path Variable:**
    -   `id`: The ID of the module to delete (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X DELETE http://localhost:8080/api/modules/{id}
    ```

## 4. Group Actions (`/api/group-actions`)

### Create a Group Action

-   **Endpoint:** `POST /api/group-actions/create`
-   **Description:** Creates a new group action.
-   **Request:** `form-data`
    -   `actionName`: The name of the action (String, Required)
-   **Example (using curl):**
    ```bash
    curl -X POST http://localhost:8080/api/group-actions/create \
    -F "actionName=read"
    ```

### Get All Group Actions

-   **Endpoint:** `GET /api/group-actions`
-   **Description:** Retrieves all group actions.
-   **Example (using curl):**
    ```bash
    curl http://localhost:8080/api/group-actions
    ```

### Get Group Action by ID

-   **Endpoint:** `GET /api/group-actions/{id}`
-   **Description:** Retrieves a specific group action by ID.
-   **Path Variable:**
    -   `id`: The ID of the group action (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl http://localhost:8080/api/group-actions/{id}
    ```

### Update a Group Action

-   **Endpoint:** `PUT /api/group-actions/update/{id}`
-   **Description:** Updates an existing group action.
-   **Path Variable:**
    -   `id`: The ID of the group action to update (Long, Required)
-   **Request:** `form-data`
    -   `actionName`: The new name of the action (String, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X PUT http://localhost:8080/api/group-actions/update/{id} \
    -F "actionName=write"
    ```

### Delete a Group Action

-   **Endpoint:** `DELETE /api/group-actions/{id}`
-   **Description:** Deletes a group action by ID.
-   **Path Variable:**
    -   `id`: The ID of the group action to delete (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X DELETE http://localhost:8080/api/group-actions/{id}
    ```

## 5. Group Module Actions (`/api/group-module-actions`)

### Create a Group Module Action (Assign Permission)

-   **Endpoint:** `POST /api/group-module-actions/create`
-   **Description:** Creates a new association between a group, module, and action (assigns a permission).
-   **Request:** `form-data`
    -   `groupId`: The ID of the permission group (Long, Required)
    -   `moduleId`: The ID of the module (Long, Required)
    -   `actionId`: The ID of the group action (Long, Required)
-   **Example (using curl, replace IDs):**
    ```bash
    curl -X POST http://localhost:8080/api/group-module-actions/create \
    -F "groupId={groupId}" \
    -F "moduleId={moduleId}" \
    -F "actionId={actionId}"
    ```

### Get All Group Module Actions

-   **Endpoint:** `GET /api/group-module-actions`
-   **Description:** Retrieves all group module actions.
-   **Example (using curl):**
    ```bash
    curl http://localhost:8080/api/group-module-actions
    ```

### Get Group Module Action by ID

-   **Endpoint:** `GET /api/group-module-actions/{id}`
-   **Description:** Retrieves a specific group module action by ID.
-   **Path Variable:**
    -   `id`: The ID of the group module action (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl http://localhost:8080/api/group-module-actions/{id}
    ```

### Update a Group Module Action

-   **Endpoint:** `PUT /api/group-module-actions/update/{id}`
-   **Description:** Updates an existing group module action.
-   **Path Variable:**
    -   `id`: The ID of the group module action to update (Long, Required)
-   **Request:** `form-data`
    -   `groupId`: The new ID of the permission group (Long, Required)
    -   `moduleId`: The new ID of the module (Long, Required)
    -   `actionId`: The new ID of the group action (Long, Required)
-   **Example (using curl, replace IDs):**
    ```bash
    curl -X PUT http://localhost:8080/api/group-module-actions/update/{id} \
    -F "groupId={newGroupId}" \
    -F "moduleId={newmoduleId}" \
    -F "actionId={newActionId}"
    ```

### Delete a Group Module Action

-   **Endpoint:** `DELETE /api/group-module-actions/{id}`
-   **Description:** Deletes a group module action by ID.
-   **Path Variable:**
    -   `id`: The ID of the group module action to delete (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X DELETE http://localhost:8080/api/group-module-actions/{id}
    ```

## 6. Menus (`/api/menus`)

### Create a Menu

-   **Endpoint:** `POST /api/menus/create`
-   **Description:** Creates a new menu.
-   **Request:** `form-data`
    -   `menuName`: The name of the menu (String, Required)
    -   `isActive`: Whether the menu is active (boolean, Required)
-   **Example (using curl):**
    ```bash
    curl -X POST http://localhost:8080/api/menus/create \
    -F "menuName=Dashboard" \
    -F "isActive=true"
    ```

-   **Example: Creating 'Articles Management' Menu**
    ```bash
    curl -X POST http://localhost:8080/api/menus/create \
    -F "menuName=Articles Management" \
    -F "isActive=true"
    ```

-   **Example: Creating 'Categories Management' Menu**
    ```bash
    curl -X POST http://localhost:8080/api/menus/create \
    -F "menuName=Categories Management" \
    -F "isActive=true"
    ```

### Get All Menus

-   **Endpoint:** `GET /api/menus`
-   **Description:** Retrieves all menus.
-   **Example (using curl):**
    ```bash
    curl http://localhost:8080/api/menus
    ```

### Get Menu by ID

-   **Endpoint:** `GET /api/menus/{id}`
-   **Description:** Retrieves a specific menu by ID.
-   **Path Variable:**
    -   `id`: The ID of the menu (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl http://localhost:8080/api/menus/{id}
    ```

### Update a Menu

-   **Endpoint:** `PUT /api/menus/update/{id}`
-   **Description:** Updates an existing menu.
-   **Path Variable:**
    -   `id`: The ID of the menu to update (Long, Required)
-   **Request:** `form-data`
    -   `menuName`: The new name of the menu (String, Required)
    -   `isActive`: The new active status (boolean, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X PUT http://localhost:8080/api/menus/update/{id} \
    -F "menuName=Reports" \
    -F "isActive=true"
    ```

### Delete a Menu

-   **Endpoint:** `DELETE /api/menus/{id}`
-   **Description:** Deletes a menu by ID.
-   **Path Variable:**
    -   `id`: The ID of the menu to delete (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X DELETE http://localhost:8080/api/menus/{id}
    ```

## 7. Sub Menus (`/api/sub-menus`)

### Create a Sub Menu

-   **Endpoint:** `POST /api/sub-menus/create`
-   **Description:** Creates a new sub menu linked to a menu.
-   **Request:** `form-data`
    -   `menuId`: The ID of the parent menu (Long, Required)
    -   `routeName`: The route name for the sub menu (String, Required)
-   **Example (using curl, replace {menuId} with actual ID):**
    ```bash
    curl -X POST http://localhost:8080/api/sub-menus/create \
    -F "menuId={menuId}" \
    -F "routeName=/dashboard/overview"
    ```

-   **Example: Creating 'View Articles' Sub Menu for 'Articles Management' (replace {articlesMenuId} with actual ID):**
    ```bash
    curl -X POST http://localhost:8080/api/sub-menus/create \
    -F "menuId={articlesMenuId}" \
    -F "routeName=/articles/view"
    ```

-   **Example: Creating 'Create Article' Sub Menu for 'Articles Management' (replace {articlesMenuId} with actual ID):**
    ```bash
    curl -X POST http://localhost:8080/api/sub-menus/create \
    -F "menuId={articlesMenuId}" \
    -F "routeName=/articles/create"
    ```

-   **Example: Creating 'View Categories' Sub Menu for 'Categories Management' (replace {categoriesMenuId} with actual ID):**
    ```bash
    curl -X POST http://localhost:8080/api/sub-menus/create \
    -F "menuId={categoriesMenuId}" \
    -F "routeName=/categories/view"
    ```

-   **Example: Creating 'Create Category' Sub Menu for 'Categories Management' (replace {categoriesMenuId} with actual ID):**
    ```bash
    curl -X POST http://localhost:8080/api/sub-menus/create \
    -F "menuId={categoriesMenuId}" \
    -F "routeName=/categories/create"
    ```

### Get All Sub Menus

-   **Endpoint:** `GET /api/sub-menus`
-   **Description:** Retrieves all sub menus.
-   **Example (using curl):**
    ```bash
    curl http://localhost:8080/api/sub-menus
    ```

### Get Sub Menu by ID

-   **Endpoint:** `GET /api/sub-menus/{id}`
-   **Description:** Retrieves a specific sub menu by ID.
-   **Path Variable:**
    -   `id`: The ID of the sub menu (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl http://localhost:8080/api/sub-menus/{id}
    ```

### Update a Sub Menu

-   **Endpoint:** `PUT /api/sub-menus/update/{id}`
-   **Description:** Updates an existing sub menu.
-   **Path Variable:**
    -   `id`: The ID of the sub menu to update (Long, Required)
-   **Request:** `form-data`
    -   `menuId`: The new ID of the parent menu (Long, Required)
    -   `routeName`: The new route name (String, Required)
-   **Example (using curl, replace IDs):**
    ```bash
    curl -X PUT http://localhost:8080/api/sub-menus/update/{id} \
    -F "menuId={newMenuId}" \
    -F "routeName=/reports/summary"
    ```

### Delete a Sub Menu

-   **Endpoint:** `DELETE /api/sub-menus/{id}`
-   **Description:** Deletes a sub menu by ID.
-   **Path Variable:**
    -   `id`: The ID of the sub menu to delete (Long, Required)
-   **Example (using curl, replace {id} with actual ID):**
    ```bash
    curl -X DELETE http://localhost:8080/api/sub-menus/{id}
    ```

## Example Workflow to Get Menus for an Admin User

This is a conceptual workflow and requires the permission service layer to be implemented.

1.  **Create a Permission Group for Admins:** Use `POST /api/permission-groups/create` with `groupName=Admin` and `isActive=true`. Get the ID of the created group.
2.  **Create Modules:** Create modules relevant to your news application, e.g., 'Articles' and 'Categories'.
    *   Use `POST /api/modules/create` with `moduleName=Articles` and `isView=true`. Get the Articles Module ID.
    *   Use `POST /api/modules/create` with `moduleName=Categories` and `isView=true`. Get the Categories Module ID.
3.  **Create Group Actions:** Create actions relevant to your news application, e.g., 'view' and 'create'.
    *   Use `POST /api/group-actions/create` with `actionName=view`. Get the View Action ID.
    *   Use `POST /api/group-actions/create` with `actionName=create`. Get the Create Action ID.
4.  **Assign Permissions to Admin Group:** Assign view and create permissions on Articles and Categories modules to the Admin group.
    *   Assign view permission on Articles: Use `POST /api/group-module-actions/create` with the IDs of the Admin group, Articles module, and View action.
    *   Assign create permission on Articles: Use `POST /api/group-module-actions/create` with the IDs of the Admin group, Articles module, and Create action.
    *   Assign view permission on Categories: Use `POST /api/group-module-actions/create` with the IDs of the Admin group, Categories module, and View action.
    *   Assign create permission on Categories: Use `POST /api/group-module-actions/create` with the IDs of the Admin group, Categories module, and Create action.
5.  **Create Menus:** Create the main menus.
    *   Use `POST /api/menus/create` with `menuName=Articles Management` and `isActive=true`. Get the Articles Menu ID.
    *   Use `POST /api/menus/create` with `menuName=Categories Management` and `isActive=true`. Get the Categories Menu ID.
6.  **Create Sub Menus:** Create sub-menus linked to the main menus.
    *   For 'Articles Management' (using its ID): Create 'View Articles' (`routeName=/articles/view`) and 'Create Article' (`routeName=/articles/create`).
    *   For 'Categories Management' (using its ID): Create 'View Categories' (`routeName=/categories/view`) and 'Create Category' (`routeName=/categories/create`).
7.  **Link Menus/Sub-menus to Modules/Permissions (Conceptual):** *This step requires the permission service logic to be implemented. A common approach would be to associate Menus with Modules. For example, the 'Articles Management' menu is associated with the 'Articles' module.* The service would then check if the user has the necessary permissions (e.g., 'view' action on the 'Articles' module) to see the 'Articles Management' menu and its sub-menus.
8.  **Create an Admin User:** Use `POST /api/admin-users/create` with a username and the ID of the Admin permission group.
9.  **Retrieve Accessible Menus (Requires Service Implementation):** A new endpoint would be needed (e.g., `GET /api/menus/accessible?username={username}`) that calls the permission service to get the filtered list of menus and sub-menus for the given user based on their group's permissions on modules.

This document provides a starting point. You can expand it with more details, request/response examples, and error handling as you build out your application. 