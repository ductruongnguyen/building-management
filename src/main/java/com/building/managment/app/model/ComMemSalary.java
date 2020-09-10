package com.building.managment.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComMemSalary {
    private String MA_LV;
    private String MA_NV;
    private String MA_DV;
    private String NGAY_BD;
    private String NGAY_KT;
    private String SO_NGAY;
    private float LUONG;
    private float PT;
    private float LUONG_THUC_TRA;
}
