package it.ldsoftware.primavera.mapper.base;

import it.ldsoftware.primavera.model.base.LogEntry;
import it.ldsoftware.primavera.presentation.base.LogEntryDTO;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class LogMapper extends BaseMapper<LogEntry, LogEntryDTO> {

    @Override
    public LogEntry getModelInstance(LogEntryDTO view) {
        LogEntry model = new LogEntry();

        model.setLevel(view.getLoggingLevel());
        model.setLogDate(view.getLogDate());
        model.setLogger(view.getLogger());
        model.setMessage(view.getMessage());
        model.setStackTrace(view.getStackTrace());

        return model;
    }

    @Override
    public LogEntryDTO getViewInstance(LogEntry model) {
        LogEntryDTO view = new LogEntryDTO();

        view.setLoggingLevel(model.getLevel());
        view.setLogDate(model.getLogDate());
        view.setLogger(model.getLogger());
        view.setMessage(model.getMessage());
        view.setStackTrace(model.getStackTrace());
        view.setUsername(model.getUser().getUsername());

        return view;
    }
}
