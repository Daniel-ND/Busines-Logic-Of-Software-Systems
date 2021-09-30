package ru.itmo.blss1.data.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDTO {
    public String name;

    @ApiModelProperty(hidden = true)
    public String ownerLogin;
}
