package ru.itmo.blss1.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.blss1.data.entity.Role;

import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorizationDTO {

//    private static final long serialVersionUID = -2137824490351989146L;

    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    private Long id;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    @JsonInclude(NON_NULL)
    private String username;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    @JsonInclude(NON_NULL)
    private String password;

    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    private String token;

    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSXXX", timezone = "UTC")
    private Instant expiresAt;

    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    private Role role;
}
