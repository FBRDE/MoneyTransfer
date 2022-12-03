package com.openclassrooms.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TransferForm {
    private int receiverId;
    private String description;
    private Double amount;
}
