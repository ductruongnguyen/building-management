package com.building.managment.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CompanyBill {
    private String MA_CT;
    private String TEN_CT;
    private String TEN_DV;
    private String NGAY_BD;
    private String NGAY_KT;
    private float DON_GIA_CS;
    private double MAT_BANG;
    private double THUC_TRA;
}
