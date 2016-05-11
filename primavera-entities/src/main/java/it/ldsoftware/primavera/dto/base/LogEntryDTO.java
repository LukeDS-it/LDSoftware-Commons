package it.ldsoftware.primavera.dto.base;

import it.ldsoftware.primavera.entities.base.LogEntry;
import it.ldsoftware.primavera.entities.base.LogEntry.LoggingLevel;

import java.util.Calendar;

/**
 * Created by luca on 11/05/16.
 * DTO for a log entry
 */
public class LogEntryDTO extends BaseDTO<LogEntry> {

    private String username, fullName, message, stackTrace, logger;
    private Calendar logDate;
    private LoggingLevel level;

    public LogEntryDTO(LogEntry entity) {
        super(entity);
        if (entity.getUser() != null) {
            username = entity.getUser().getUsername();
            fullName = entity.getUser().getFullName();
        }
        message = entity.getMessage();
        stackTrace = entity.getStackTrace();
        logger = entity.getLogger();
        logDate = entity.getLogDate();
        level = entity.getLevel();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public Calendar getLogDate() {
        return logDate;
    }

    public void setLogDate(Calendar logDate) {
        this.logDate = logDate;
    }

    public LoggingLevel getLevel() {
        return level;
    }

    public void setLevel(LoggingLevel level) {
        this.level = level;
    }
}
