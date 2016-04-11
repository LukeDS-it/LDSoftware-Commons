package it.ldsoftware.commons.services.interfaces;

import com.mysema.query.types.Predicate;
import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by luca on 11/04/16.
 * This is the base interface for the database service which
 * runs searches on data.
 * <p>
 * There is an abstract implementation in @{link AbstractDatabaseService}
 * which provides access to all common data access layer in the "commons"
 * library, thus making an actual implementation needed in the final project.
 */
public interface DatabaseService {
    /**
     * Returns a collection of all entities in the
     * database for the specified class type
     *
     * @param eClass the class that represents the entity in the database
     * @param <E>    the parameter enables to return type-safe collections
     * @return a @{link Collection} of entities
     */
    <E extends BaseEntity> Collection<E> findAll(Class<E> eClass);

    /**
     * Returns a collection of all entities in the database that
     * belong to the specified class type and meet the provided QueryDSL predicate
     *
     * @param eClass    the class that represents the entity in the database
     * @param predicate the predicate to filter the elements
     * @param <E>       the parameter enables to return type-safe collections
     * @return a @{link Collection} of entities
     */
    <E extends BaseEntity> Collection<E> findAll(Class<E> eClass, Predicate predicate);

    /**
     * Returns a page of the entities in the database
     * that belong to the specified class type and meet the page request.
     *
     * @param eClass the class that represents the entity in the database
     * @param page   the predicate to filter the elements
     * @param <E>    the parameter enables to return type-safe pages
     * @return a @{link Page} of entries
     */
    <E extends BaseEntity> Page<E> findAll(Class<E> eClass, Pageable page);

    /**
     * Returns a page of the entities in the database
     * that belong to the specified class type and meet the page request.
     *
     * @param eClass    the class that represents the entity in the database
     * @param predicate the predicate to match entities
     * @param page      the predicate to filter the elements
     * @param <E>       the parameter enables to return type-safe pages
     * @return a @{link Page} of entries
     */
    <E extends BaseEntity> Page<E> findAll(Class<E> eClass, Predicate predicate, Pageable page);

    /**
     * This function takes all the entities matching given class and predicate, and converts
     * them into the respective DTO type, initializing the DTO with given locale.
     *
     * @param eClass    the entity class
     * @param dClass    the presentation (DTO) class
     * @param predicate the predicate to match the entities (use null for all entities)
     * @param l         the locale in which to translate the entities
     * @param <E>       parameter to return type-safe collections
     * @param <D>       parameter to return type-safe collections
     * @return a list of DTO matching given entity and predicate.
     */
    <E extends BaseEntity, D extends BaseDTO<E>> Collection<D> findAllDTO(Class<E> eClass, Class<D> dClass, Predicate predicate, Locale l);

    /**
     * This function takes all the entities matching given class, predicate and page,
     * and converts them into the respective DTO tupe, initializing the DTO with given locale.
     *
     * @param eClass    the entity class
     * @param dClass    the presentation (DTO) class
     * @param predicate the predicate to match the entities (use null for all entities)
     * @param page      the page to fetch
     * @param l         the locale in which to translate the entities
     * @param <E>       parameter to return type-safe collections
     * @param <D>       parameter to return type-safe collections
     * @return a list of DTO matching given entity, predicate and page.
     */
    <E extends BaseEntity, D extends BaseDTO<E>> Collection<D> findAllDTO(Class<E> eClass, Class<D> dClass, Predicate predicate, Pageable page, Locale l);

    /**
     * Returns a single entity with the given ID
     *
     * @param eClass the class that represents the entity in the database
     * @param id     the unique identifier of that entity
     * @param <E>    the parameter enables to return type-safe elements
     * @return a @{link BaseEntity} of type E where its id == the given id.
     */
    <E extends BaseEntity> E findOne(Class<E> eClass, long id);

    /**
     * Saves the given entity
     *
     * @param eClass the class that represents the entity in the database
     * @param entity the entity to save
     * @param <E>    the parameter enables type-safe operations
     * @return the entity just saved
     */
    <E extends BaseEntity> E save(Class<E> eClass, E entity);

    /**
     * Finds an entity using the defined function "findFull" of the relative DAL
     * which in turn should load the entity using a named entity graph to
     * load all the needed lazy relationships
     *
     * @param eClass the class that represents the entity in the database
     * @param id     the id of the entity
     * @param <E>    the parameter enables to return type-safe entities
     * @return a @{link BaseEntity} of type E where its id == the given id, with
     * the relative named entity graph loaded if the DAL&lt;E&gt; overrides the default
     * findFull function.
     */
    <E extends BaseEntity> E findFull(Class<E> eClass, long id);

    /**
     * Deletes the entity with given id
     *
     * @param eClass the class that represents the entity in the database
     * @param id     the id of the entity to delete
     * @param <E>    the parameter enables type-safe operations
     */
    <E extends BaseEntity> void delete(Class<E> eClass, long id);
}
