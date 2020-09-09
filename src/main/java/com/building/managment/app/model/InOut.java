package com.building.managment.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InOut {
    private int MA_RV;
    private String MA_THE;
    private String VI_TRI;
    private String TRANG_THAI;
    private String CHECK_TIME;
}
