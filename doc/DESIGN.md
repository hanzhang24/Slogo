# DESIGN Document for SLogo

## NAME(s) Alec, Aryan, Han, Jack, Yegor

## Role(s)

* Alec
    * Designer/Debugger/Refactorer of:
        * Model
        * View update pipeline
            * ChangeLog
            * ViewCommand
            * ViewPayload
            * ViewController
    * Integrated various parts of MVC design
* Aryan
* Han
* Jack
* Yegor

## Design Goals

* Easy to add features:
    * Model
        * New instance variables to describe an Avatar
            * Can be Strings, Booleans, Doubles
            * In the future, could add names, birthdays, hunger, etc.
    * View
        * New ViewCommands to modify the View
            * Can quickly add new View commands that combine other commands or introduce new
              functionality
    * Controller
        * New commands/syntax

## High-Level Design

## Assumptions or Simplifications

## Changes from the Plan

## How to Add New Features