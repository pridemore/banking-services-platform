package com.example.customersupport.persistance;

import com.example.customersupport.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket,Long> {
}
