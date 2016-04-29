# LDSoftware-Commons
This project was thought to provide a series of useful and solid ground from where to begin writing "management" software.

The project is structured in the following modules:

## Commons entities
The commons-entities module is the core of the module. This module is required to use all the other modules.
It contains a basic entity structure tree that can be used to create the application database.

The commons-entities is multilanguage oriented, it helps you to create in the
most effective way a database containing translatable data with its own translation, and to get
the language data in an easy way.

## Commons Vaadin
This module depends from the commons entities and provides both abstract pre-designed forms
that help you design your own form to input data in the database, and also some forms that are
ready to use and can be imported using Spring beans to instantiate them into Vaadin UIs.

## Commons language
This module provides basic configuration classes to make multi language UIs. It is an independent module.

## Commons text compiler
This project extends the commons entities project, adding classes that are useful to mimic
mail-merge functions within your web based programs. It gives entities such as basic
compilable text (also multilanguage oriented) and methods to take a text in the user's language
and replace occurrences of placeholders directly from database entities.

This readme will soon contain more info on how to get started, as soon as I manage to finalise the first version.
