package com.quvideo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Book {
    private Long id;
    private String type;
    private String name;
    private String description;
    private Timestamp createAt;
}
