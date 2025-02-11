# sjes
Import this library, extend Aggregate, Command, Event, State, InitialCommand.  
Implement EventStore, extend if necessary.  
Use DomainService to create and apply events.  
Implement EventStore saving all events in one transaction.  
Use outbox pattern to emit events to message broker.  
Comply CQRS principles.  
Use projections for querying data.  
Update projections based on events consumed from message broker.