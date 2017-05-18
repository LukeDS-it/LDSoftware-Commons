package it.ldsoftware.primavera.presentation.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by luca on 11/05/16.
 * DTO for a log entry
 */
@Getter @Setter
public class LogEntryDTO extends BaseDTO {

    private static final String FIELD_USERNAME = "username", FIELD_FULL_NAME = "fullName",
            FIELD_MESSAGE = "message", FIELD_STACK_TRACE = "stackTrace", FIELD_LOGGER = "logger",
            FIELD_LOG_DATE = "logDate", FIELD_LEVEL = "level";

    private String username, fullName, message, stackTrace, logger, loggingLevel;
    private Calendar logDate;

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_USERNAME, FIELD_FULL_NAME,
                FIELD_MESSAGE, FIELD_STACK_TRACE, FIELD_LOGGER,
                FIELD_LOG_DATE, FIELD_LEVEL));
        return tmp;
    }

}
