package com.aasc.erp.bean;

public class AascMonthlyCarrierDetails {
    public AascMonthlyCarrierDetails() {
    }
    
    String carrier_name;
    String carrier_code;
    String total_freight_charges;
    String shipments_count;

    public void setCarrier_name(String carrier_name) {
        this.carrier_name = carrier_name;
    }

    public String getCarrier_name() {
        return carrier_name;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setTotal_freight_charges(String total_freight_charges) {
        this.total_freight_charges = total_freight_charges;
    }

    public String getTotal_freight_charges() {
        return total_freight_charges;
    }

    public void setShipments_count(String shipments_count) {
        this.shipments_count = shipments_count;
    }

    public String getShipments_count() {
        return shipments_count;
    }
}
