package com.building.managment.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String MA_CT;
    private String TEN_CT;
    private String MST;
    private String LINH_VUC;
    private String DIA_CHI;
    private String SDT;
    private int SO_NV;
    private float DIEN_TICH;
}
