# primavera-entities
This artifact contains classes, repositories, query utilities and
auto-configuration that is necessary to start designing an application database.

The entities were created with multi-language in mind, the database tables
and queries are kept as optimised as possible to allow fast multilanguage
information retrieval.

## Entities

All the classes are ready to be used or extended, and contain the basic
fields that might be needed to have enough starting information.

Follows the basic database schema, divided by logical scope

## Repositories
The repositories use spring-data-jpa and querydsl to allow easy access
and querying the datasource. Please refer to their official documentation
to understand how to query the database.
You can use the included DatabaseService to proxy the calls to the various
repositories in a typesafe manner.

## DTOs
The main DatabaseService always returns DTO objects, not the entities. This
was made to easen the development of the front-end part, giving out
data that is ready to be viewed, especially the multilanguage data, that
in the entities comes as a map of languages, while in the view layer it
should (and it is so) only have the language that the user is interested in.

### How to use the repositories
Normally in your project you just need to implement a basic DatabaseService
that extends the AbstractDatabaseService. In there you will need to
properly inject your additional repositories and register them with the
proper class, so that you can call the default methods of the DatabaseService.

### Creating new entities
The artifact gives basic entities to start a database, so to add new classes
the following steps must be followed (although it is pretty straightforward
as the project enables the automatic configuration of all the entities and
repositories)

##TODO
- database graph
- entities instructions
- repository instructions and code