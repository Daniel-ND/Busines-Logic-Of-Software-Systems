package ru.itmo.blss1.data.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PinToDashboardDTO {
    public String dashboardName;

    @ApiModelProperty(hidden = true)
    public String userLogin;

    public int pinId;
}
