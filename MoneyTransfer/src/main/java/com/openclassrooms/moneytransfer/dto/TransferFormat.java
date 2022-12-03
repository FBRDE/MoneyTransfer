package com.openclassrooms.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TransferFormat {
    private int id;
    private String connection;
    private String description;
    private Double amount;
    private String date;
}
