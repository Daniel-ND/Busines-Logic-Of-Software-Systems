package ru.itmo.blss1.data.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PinDTO {
    @NotBlank
    public String url;

    @ApiModelProperty(hidden = true)
    public String uploadedBy;
}
