package it.ldsoftware.primavera.services;

import com.querydsl.core.types.Predicate;
import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.services.interfaces.BusinessService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Abstract business service with basic implementations. If the business logic
 * does not differ from simple CRUD operations, just leave the implementation alone.
 * <br /><br />
 * Note that it defines a default constructor to allow DAL and Mapper injection
 * <br />
 * Override methods when necessary.
 *
 * @author Luca Di Stefano
 */
public abstract class AbstractBusinessService<D extends BaseDTO, E extends BaseEntity> implements BusinessService<D> {

    @Getter
    private final BaseDAL<E> dal;

    @Getter
    private final Mapper<E, D> mapper;

    @Autowired
    public AbstractBusinessService(BaseDAL<E> dal, Mapper<E, D> mapper) {
        this.dal = dal;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public D findOne(Long id) {
        return mapper.convertToView(dal.findOne(id));
    }

    @Override
    @Transactional
    public D findOne(Predicate predicate) {
        return mapper.convertToView(dal.findOne(predicate));
    }

    @Override
    @Transactional
    public List<D> findAll() {
        return dal.findAll().stream().map(mapper::convertToView).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<D> findBy(Predicate predicate) {
        return StreamSupport.stream(dal.findAll(predicate).spliterator(), false)
                .map(mapper::convertToView).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Page<D> findBy(Predicate predicate, @Nullable Pageable pageable) {
        return dal.findAll(predicate, pageable).map(mapper::convertToView);
    }

    @Override
    @Transactional
    public D save(D toSave) {
        return mapper.convertToView(dal.save(mapper.convertToModel(toSave)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        dal.delete(id);
    }
}
