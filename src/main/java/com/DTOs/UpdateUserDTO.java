package com.DTOs;

import java.math.BigDecimal;

public record UpdateUserDTO (String name, String email, int age, BigDecimal height){
}
