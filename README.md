# Primavera framework
This project was thought to provide a series of useful and solid ground
to begin writing database management software.

The project is structured in the following modules:

## primavera-entities
The primavera-entities module contains what in a MVC pattern would be the View layer.

The artifact aims to be as sleek and lightweight as possible, with no dependencies, so any application that
needs to interface to any primavera-powered backend can import just the entities. This also allows for
separation of the business logic layer.

## primavera-business
The primavera-business module contains the foundation of a database, with
what were thought would be the basic requirements of every application
that involves database management. See the readme in the project to learn
more about the entities.

The project is multi-language oriented, it helps you to create in the
most effective way a database containing translatable data with its own
translation, and to get the language data in an easy way.

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

## primavera-vaadin
This module depends from the entities, and provides both abstract
pre-designed forms that help you design your own forms to input data
in the database, and also some forms that are ready to use and can be
directly imported using Spring beans to instantiate them into Vaadin UIs.

## primavera-vaadin-widgetset
The compiled widgetset with all the components used by the primavera-vaadin
project. Import this jar as-is to incrementally build your own widgetset.