package it.ldsoftware.rest.mapper.security;

import it.ldsoftware.primavera.entities.security.Role;
import it.ldsoftware.rest.mapper.base.LookupMapper;
import it.ldsoftware.rest.payload.security.RolePayload;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper extends LookupMapper<Role, RolePayload> {
    @Override
    public Role newEntity() {
        return new Role();
    }

    @Override
    public RolePayload newPayload() {
        return new RolePayload();
    }

    @Override
    public void initLookup(Role entity, RolePayload payload) {
        // No additional fields required
    }

    @Override
    public void initLookupPayload(RolePayload payload, Role entity) {
        // No additional fields required
    }
}
