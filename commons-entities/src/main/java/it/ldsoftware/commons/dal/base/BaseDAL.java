package it.ldsoftware.commons.dal.base;

import com.mysema.query.types.Predicate;
import it.ldsoftware.commons.entities.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 11/04/16.
 * Base Data Access Layer object.
 */
@Repository
public interface BaseDAL<E extends BaseEntity> extends JpaRepository<E, Long>, QueryDslPredicateExecutor<E> {

    default E findFull(Long id) {
        return findOne(id);
    }

    default E findFull(Predicate predicate) {
        return findOne(predicate);
    }

}
