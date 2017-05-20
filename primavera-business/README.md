# primavera-business
This artifact contains a basic business logic layer with database classes, repositories, query utilities and
auto-configuration that is necessary to start designing an application's database that can correctly store
and show the primavera-entities.

The entities were created with multi-language in mind, the database tables
and queries are kept as optimised as possible to allow fast multi-language
information retrieval.

## Entities

All the classes are ready to be used or extended, and contain the basic
fields that might be needed to have enough starting information.

Follows the basic database schema, divided by logical scope

## Repositories
The repositories use `spring-data-jpa` and querydsl to allow easy access
and querying the datasource. Please refer to their official documentation
to understand how to query the database.
You can use the included DatabaseService to proxy the calls to the various
repositories in a typesafe manner.

### How to use the repositories
This project helps you focus on the single-responsibility principle and encourages you to use the MVC
model: the CRUD repositories are already made `with spring-data-jpa`, and the business logic to save
complex entities should be written inside the `BusinessService`. A basic implementation of the service
is provided as an `AbstractBusinessService`, to allow you to simply extend that class in the case that
you're working with entities that don't need additional logic in the CRUD operations.

### Creating new entities
The artifact gives basic entities to start a database, so to add new classes
the following steps must be followed (although it is pretty straightforward
as the project enables the automatic configuration of all the entities and
repositories)

##TODO
- database graph
- entities instructions
- repository instructions and code