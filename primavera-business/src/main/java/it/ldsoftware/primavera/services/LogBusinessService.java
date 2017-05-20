package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.base.LogEntry;
import it.ldsoftware.primavera.presentation.base.LogEntryDTO;
import it.ldsoftware.primavera.services.interfaces.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class LogBusinessService extends AbstractBusinessService<LogEntryDTO, LogEntry> implements BusinessService<LogEntryDTO> {

    @Autowired
    public LogBusinessService(BaseDAL<LogEntry> dal, Mapper<LogEntry, LogEntryDTO> mapper) {
        super(dal, mapper);
    }

}
