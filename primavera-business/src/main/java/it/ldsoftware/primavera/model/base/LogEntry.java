package it.ldsoftware.primavera.model.base;

import it.ldsoftware.primavera.model.people.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

/**
 * Created by luca on 11/05/16.
 * This entity represents a log entry.
 *
 * You can set up the application to normally write the logs on the zz_log table
 * or, if for some reason you cannot apply that, use the provided Primavera
 * logging service.
 */
@Entity
@Getter @Setter
@Table(name = "zz_log")
public class LogEntry extends BaseEntity {

    /**
     * Type timestamp, date and time the error has occurred
     */
    @NotNull
    private LocalDateTime logDate;

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
    private String level;

    /**
     * Should be the name of the logger that inserted the record
     */
    @NotNull
    private String logger;

}
