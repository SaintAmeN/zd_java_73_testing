package com.sda.zd_javapol_73.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResponse {
    private LocalDateTime time;
    private String message;
}
