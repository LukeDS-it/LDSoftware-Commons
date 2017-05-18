package it.ldsoftware.primavera.services.interfaces;

import com.querydsl.core.types.Predicate;
import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.model.base.BaseEntity;
import org.springframework.data.domain.Page;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.awt.print.Pageable;
import java.util.List;

/**
 * Base interface of a generic business service.
 * <p>
 * A business service is typed with the base entity it works with, but
 * must only work with the DTO, as the database structure must not be
 * exposed by the business layer.
 * <p>
 * The BusinessServer should be first of all extended by an interface that
 * can expose its own methods, then a service implementation must be provided.
 * If no methods must be overridden, it should simply extend
 * {@link it.ldsoftware.primavera.services.AbstractBusinessService}.
 *
 * @author Luca Di Stefano
 */
public interface BusinessService<D extends BaseDTO> {

    D findOne(@NotNull Long id);

    List<D> findAll();

    List<D> findBy(@NotNull Predicate predicate);

    Page<D> findBy(@NotNull Predicate predicate, @Nullable Pageable pageable);

    D save(@NotNull D toSave);

    void delete(@NotNull Long id);

}
