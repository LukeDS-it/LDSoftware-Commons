package it.ldsoftware.primavera.entities.base;

import it.ldsoftware.primavera.entities.people.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by luca on 11/05/16.
 * This entity represents a log entry.
 *
 * You can set up the application to normally write the logs on the zz_log table
 * or, if for some reason you cannot apply that, use the provided Primavera
 * logging service.
 */
@Entity
@Table(name = "zz_log")
public class LogEntry extends BaseEntity {

    /**
     * Type timestamp, date and time the error has occurred
     */
    @NotNull
    @Temporal(TIMESTAMP)
    private Calendar logDate;

    /**
     * If available, the user that found the error
     */
    @ManyToOne
    private User user;

    /**
     * Short message of the error, found by using
     * the function getMessage on any exception
     */
    @NotNull
    @Column(length = 1000)
    private String message;

    /**
     * Full stacktrace of the exception if available
     */
    @Lob
    private String stackTrace;

    /**
     * Log severity
     */
    @NotNull
    @Enumerated(STRING)
    private LoggingLevel level;

    /**
     * Should be the name of the logger that inserted the record
     */
    @NotNull
    private String logger;

    public Calendar getLogDate() {
        return logDate;
    }

    public void setLogDate(Calendar logDate) {
        this.logDate = logDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LoggingLevel getLevel() {
        return level;
    }

    public void setLevel(LoggingLevel level) {
        this.level = level;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    /**
     * The six available logging levels are
     * DEBUG, ERROR, FATAL, INFO, TRACE, WARN
     */
    public static enum LoggingLevel {
        DEBUG, ERROR, FATAL, INFO, TRACE, WARN
    }
}
