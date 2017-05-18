package it.ldsoftware.primavera.dal.people;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.model.people.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import static it.ldsoftware.primavera.model.people.Person.PERSON_GRAPH;
import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         DAL for people search
 */
@Repository
public interface PersonDAL extends BaseDAL<Person> {

    @Override
    @EntityGraph(value = PERSON_GRAPH, type = LOAD)
    Person findFullById(Long id);

}
