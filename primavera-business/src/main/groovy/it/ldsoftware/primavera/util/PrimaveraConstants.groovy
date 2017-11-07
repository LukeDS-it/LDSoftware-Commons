package it.ldsoftware.primavera.util

import it.ldsoftware.primavera.presentation.enums.PersonType
import it.ldsoftware.primavera.presentation.people.UserDTO
import it.ldsoftware.primavera.presentation.security.GroupDTO
import it.ldsoftware.primavera.presentation.security.RoleDTO

import static it.ldsoftware.primavera.util.UserUtil.*

/**
 * This class contains the initial data to populate the database. It contains sensible roles, profiles
 * and properties.
 *
 * @author Luca Di Stefano
 */
@SuppressWarnings(["GroovyUnusedDeclaration"])
abstract class PrimaveraConstants {
    /**
     * Base roles any application should have. These roles can be saved by calling the custom init method
     * in the {@link it.ldsoftware.primavera.services.interfaces.RoleService}
     * <p>
     * The roles are as follows:
     * <table>
     *     <tr>
     *         <th>Role name</th>
     *         <th>Description</th>
     *     </tr>
     *     <tr>
     *         <td>ROLE_SUPERADMIN</td>
     *         <td>
     *             Accessing administration of an application. Normally it's the almighty user that can do
     *             anything.
     *         </td>
     *     </tr>
     *     <tr>
     *         <td>ROLE_DB_CONSOLE</td>
     *         <td>Can access the H2 management console (given by the primavera framework)</td>
     *     </tr>
     *     <tr>
     *         <td>ROLE_USER_ADMIN</td>
     *         <td>Can access anything related to user management</td>
     *     </tr>
     *     <tr>
     *         <td>ROLE_PEOPLE_ADMIN</td>
     *         <td>Can access anything related to people management (e.g. contacts)</td>
     *     </tr>
     *     <tr>
     *         <td>ROLE_PERMISSION_ADMIN</td>
     *         <td>Can access anything related to application roles administration</td>
     *     </tr>
     *     <tr>
     *         <td>ROLE_GROUP_ADMIN</td>
     *         <td>Can access anything related to group administration</td>
     *     </tr>
     * </table>
     */
    public static final List<RoleDTO> BASE_ROLES = [
            new RoleDTO(code: ROLE_SUPERADMIN),
            new RoleDTO(code: ROLE_DB_CONSOLE),
            new RoleDTO(code: ROLE_USER_ADMIN),
            new RoleDTO(code: ROLE_PEOPLE_ADMIN),
            new RoleDTO(code: ROLE_PERMISSION_ADMIN),
            new RoleDTO(code: ROLE_GROUP_ADMIN)
    ]

    /**
     * Base groups to make it easier to create users with a default set of permissions.
     *
     * The groups are as follows:
     * <ul>
     *     <li>
     *         GROUP_SYSTEM_ADMIN: groups for system administrators. Can access the DB console,
     *         manage users, roles, groups and people
     *     </li>
     *     <li>GROUP_ADDRESS_BOOK: group that allows reading/writing to the address book.</li>
     *     <li>GROUP_ADDRESS_BOOK_RO: group that allows reading the address book.</li>
     * </ul>
     */
    public static final List<GroupDTO> BASE_GROUPS = [
            new GroupDTO(code: "GROUP_SYSTEM_ADMIN", roles: [
                    new RoleDTO(code: ROLE_DB_CONSOLE),

                    new RoleDTO(code: ROLE_USER_ADMIN),
                    new RoleDTO(code: insertVariant(ROLE_USER_ADMIN)),
                    new RoleDTO(code: editVariant(ROLE_USER_ADMIN)),
                    new RoleDTO(code: deleteVariant(ROLE_USER_ADMIN)),

                    new RoleDTO(code: ROLE_PEOPLE_ADMIN),
                    new RoleDTO(code: insertVariant(ROLE_PEOPLE_ADMIN)),
                    new RoleDTO(code: editVariant(ROLE_PEOPLE_ADMIN)),
                    new RoleDTO(code: deleteVariant(ROLE_PEOPLE_ADMIN)),

                    new RoleDTO(code: ROLE_PERMISSION_ADMIN),
                    new RoleDTO(code: insertVariant(ROLE_PERMISSION_ADMIN)),
                    new RoleDTO(code: editVariant(ROLE_PERMISSION_ADMIN)),
                    new RoleDTO(code: deleteVariant(ROLE_PERMISSION_ADMIN)),

                    new RoleDTO(code: ROLE_GROUP_ADMIN),
                    new RoleDTO(code: insertVariant(ROLE_GROUP_ADMIN)),
                    new RoleDTO(code: editVariant(ROLE_GROUP_ADMIN)),
                    new RoleDTO(code: deleteVariant(ROLE_GROUP_ADMIN)),
            ]),
            new GroupDTO(code: "GROUP_ADDRESS_BOOK", roles: [
                    new RoleDTO(code: ROLE_PEOPLE_ADMIN),
                    new RoleDTO(code: insertVariant(ROLE_PEOPLE_ADMIN)),
                    new RoleDTO(code: editVariant(ROLE_PEOPLE_ADMIN)),
                    new RoleDTO(code: deleteVariant(ROLE_PEOPLE_ADMIN))
            ]),
            new GroupDTO(code: "GROUP_ADDRESS_BOOK_RO", roles: [
                    new RoleDTO(code: ROLE_PEOPLE_ADMIN)
            ])
    ]

    /**
     * Basic users that might be needed to test a simple application.
     * Includes the almighty superadmin with default password as "password", and an user with no permissions.
     */
    public static final List<UserDTO> BASE_USERS = [
            new UserDTO(
                    username: "superadmin",
                    password: "password",
                    enabled: true,
                    roles: [new RoleDTO(code: ROLE_SUPERADMIN)],
                    personType: PersonType.REAL
            ),
            new UserDTO(
                    username: "user",
                    password: "password",
                    enabled: true,
                    personType: PersonType.REAL
            )
    ]
}
