package ru.netology.web.sqlentities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEntity {
    String id;
    String created;
    String creditId;
    String payment_id;
}
