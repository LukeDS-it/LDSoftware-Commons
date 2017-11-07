package it.ldsoftware.primavera.services.interfaces;

import it.ldsoftware.primavera.presentation.security.GroupDTO;

/**
 * @author Luca Di Stefano
 */
public interface GroupService extends BusinessService<GroupDTO> {
    boolean existsByCode(String code);

    /**
     * Initializes the basic groups
     */
    void initGroups();
}
