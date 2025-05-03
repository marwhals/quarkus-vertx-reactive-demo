# Notes

## What is Quarkus
- Cloud native JVM Framework using GraalVM
- Imperative and Reactive powered by Vertx
- Vert.x toolkit vs Quarkus framework - choose what fits best for you use case

## Quarkus Reactive Architecture

How does Quarkus enable Reactive?
- Under the hood, Quarkus has a reactive engine. This engine, powered by Eclipse, Vert.x and Netty handles the non-blocking I/O interactions.

See Diagram

See guide: https://quarkus.io/guides/quarkus-reactive-architecture

## Quarkus Reactive
- Uses Mutiny as its central reactive programming model. It supports returning Mutiny types (Uni and Multi) from HTTP endpoints
  - https://quarkus.io/
- Hibernate Reactive is a reactive API for Hibernate ORM supporting non-blocking database drivers and a reactive style of interaction with the database
  - http://hibernate.org/reactive/