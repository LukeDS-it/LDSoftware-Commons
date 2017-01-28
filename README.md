# Primavera framework
This project was thought to provide a series of useful and solid ground
to begin writing database management software.

The project is structured in the following modules:

## primavera-entities
The primavera-entities module contains the foundation of a database, with
what were thought would be the basic requirements of every application
that involves database management. See the readme in the project to learn
more about the entities.

The project is multilanguage oriented, it helps you to create in the
most effective way a database containing translatable data with its own
translation, and to get the language data in an easy way.

## primavera-vaadin
This module depends from the entities, and provides both abstract
pre-designed forms that help you design your own forms to input data
in the database, and also some forms that are ready to use and can be
directly imported using Spring beans to instantiate them into Vaadin UIs.

## primavera-vaadin-widgetset
The compiled widgetset with all the components used by the primavera-vaadin
project. Import this jar as-is to incrementally build your own widgetset.

## primavera-language
This module provides basic configuration classes to make multi language UIs.
It is an independent module.

## primavera-text-compiler
This project extends the commons entities project, adding classes that are
useful to mimic mail-merge functions within your web based programs.
It gives entities such as basic compilable text (also multilanguage oriented)
and methods to take a text in the user's language and replace occurrences
of placeholders directly from database entities.

## primavera-rest
If your application needs to expose REST webservices, this project provides
some basic implementations of the services that return the data of the base
entities in the primavera framework

## primavera-rest-payload
Any application that uses a REST webservice and the primavera-rest project
will expose, with the REST service, payloads that conform to this artifact's
objects.

This artifact does not depend on any other primavera artifact, in order to
make it lightweight to import in the final application that needs to just
consume the services and does not need to know the implementation of the
database entities, nor need the persistence layer.
