package com.building.managment.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Working {
    private int MA_LV;
    private String MA_DV;
    private String MA_NV;
    private String VI_TRI;
    private float RATE_LUONG;
    private String NGAY_BD;
    private String NGAY_KT;
}
