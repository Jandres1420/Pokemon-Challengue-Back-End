package com.endava.pokemon_challengue.models.dto.ForgotPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {

    private String email;
    private String question_answer;
    private String newPassword;
    private String confirmPassword;
}
