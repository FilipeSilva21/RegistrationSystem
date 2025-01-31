package com.DTOs;

import java.math.BigDecimal;

public record CreateUserDTO (String name, String email, int age, double height) {
}
