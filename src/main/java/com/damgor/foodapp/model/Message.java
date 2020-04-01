package com.damgor.foodapp.model;

import lombok.Data;
import org.springframework.hateoas.Link;

import javax.persistence.Transient;

@Data
public class Message {
    private String message;
    private Link link;

}
