package it.ldsoftware.commons.services;

import com.mysema.query.types.Predicate;
import it.ldsoftware.commons.dal.base.BaseDAL;
import it.ldsoftware.commons.dal.people.PersonDAL;
import it.ldsoftware.commons.dal.people.UserDAL;
import it.ldsoftware.commons.dal.security.GroupDAL;
import it.ldsoftware.commons.dal.security.RoleDAL;
import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.base.BaseEntity;
import it.ldsoftware.commons.entities.people.Person;
import it.ldsoftware.commons.entities.people.User;
import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.entities.security.Role;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 11/04/16.
 * This class implements the basic functions of the @{link DatabaseService}.
 * An actual implementation is still needed in the final project to register the
 * project custom repositories.
 */
public abstract class AbstractDatabaseService implements DatabaseService {

    @Autowired
    private MessageSource msg;

    private final Map<Class<? extends BaseEntity>, BaseDAL<? extends BaseEntity>> dalMap = new HashMap<>();

    /**
     * Gets the repository that is used to manipulate entities from the specified
     * class type
     *
     * @param eClass class of the entity
     * @param <E>    parameter to guarantee type-safe operations
     * @return the DAL used to manipulate entities of E type.
     */
    @SuppressWarnings("unchecked")
    protected final <E extends BaseEntity> BaseDAL<E> getRepository(Class<E> eClass) {
        return (BaseDAL<E>) dalMap.get(eClass);
    }

    /**
     * Use this function to register repositories for your classes.
     *
     * @param eClass the class you want to register a repository for
     * @param dal    the repository
     * @param <E>    parameter for type-safe operations
     */
    protected final <E extends BaseEntity> void registerRepository(Class<E> eClass, BaseDAL<E> dal) {
        dalMap.put(eClass, dal);
    }


    @Override
    public <E extends BaseEntity> Collection<E> findAll(Class<E> eClass) {
        return getRepository(eClass).findAll();
    }

    @Override
    public <E extends BaseEntity> Collection<E> findAll(Class<E> eClass, Predicate predicate) {
        Iterable<E> it = getRepository(eClass).findAll(predicate);
        List<E> list = new ArrayList<>();
        it.forEach(list::add);
        return unmodifiableCollection(list);
    }

    @Override
    public <E extends BaseEntity> Page<E> findAll(Class<E> eClass, Pageable page) {
        return getRepository(eClass).findAll(page);
    }

    @Override
    public <E extends BaseEntity> Page<E> findAll(Class<E> eClass, Predicate predicate, Pageable page) {
        return getRepository(eClass).findAll(predicate, page);
    }

    @Override
    public <E extends BaseEntity, D extends BaseDTO<E>> Collection<D> findAllDTO(Class<E> eClass, Class<D> dClass, Predicate predicate, Locale l) {
        Collection<E> entityList = (predicate == null ? findAll(eClass) : findAll(eClass, predicate));
        return convertToDTO(eClass, dClass, entityList, l);
    }

    @Override
    public <E extends BaseEntity, D extends BaseDTO<E>> Collection<D> findAllDTO(Class<E> eClass, Class<D> dClass, Predicate predicate, Pageable page, Locale l) {
        Collection<E> entities = (predicate == null ? findAll(eClass, page).getContent()
                : findAll(eClass, predicate, page).getContent());
        return convertToDTO(eClass, dClass, entities, l);
    }

    @Override
    public <E extends BaseEntity> E findOne(Class<E> eClass, long id) {
        return getRepository(eClass).findOne(id);
    }

    @Override
    public <E extends BaseEntity> E findOne(Class<E> eClass, Predicate predicate) {
        return getRepository(eClass).findOne(predicate);
    }

    @Override
    public <E extends BaseEntity> E findFull(Class<E> eClass, long id) {
        return getRepository(eClass).findFull(id);
    }

    @Override
    public <E extends BaseEntity> E save(Class<E> eClass, E entity) {
        return getRepository(eClass).save(entity);
    }

    @Override
    public <E extends BaseEntity> void delete(Class<E> eClass, long id) {
        getRepository(eClass).delete(id);
    }

    /*
    Repositories initialization
     */

    @Autowired
    private void registerPeopleRepository(PersonDAL dal) {
        registerRepository(Person.class, dal);
    }

    @Autowired
    private void registerUserRepository(UserDAL dal) {
        registerRepository(User.class, dal);
    }

    @Autowired
    private void registerRoleRepository(RoleDAL dal) {
        registerRepository(Role.class, dal);
    }

    @Autowired
    private void registerGroupRepository(GroupDAL dal) {
        registerRepository(Group.class, dal);
    }

    private <E extends BaseEntity, D extends BaseDTO<E>> List<D> convertToDTO(Class<E> eClass, Class<D> dClass, Collection<E> entities, Locale l) {
        return entities.stream().map(e -> {
            try {
                return dClass.getConstructor(eClass, Locale.class).newInstance(e, l);
            } catch (Exception ex) {
                try {
                    return dClass.getConstructor(eClass, Locale.class, MessageSource.class).newInstance(e, l, msg);
                } catch (Exception e1) {
                    return null;
                }
            }
        }).collect(toList());
    }
}
