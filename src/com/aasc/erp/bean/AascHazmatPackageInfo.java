package com.aasc.erp.bean;

public class AascHazmatPackageInfo {
    
    private int hazmatCommNo ;
    private String orderNumber;
    private int shipmentPackageId;
    private int packageSequenceId;
    private String voidFlag;
    private String hazMatOverPackFlag;
    private String HazMatClass;
    private double HazMatCharges;
    private double HazMatQty;
    private String HazMatUnit;
    private String HazMatIdNo;
    private String HazMatPkgGroup;
    private String HazMatDOTLabel;
    private String HazMatEmerContactNo;
    private String HazMatEmerContactName;
    private String HazMatId;
    private double hazmatPkgingCnt;
    private String hazmatPkgingUnits;
    private String hazmatTechnicalName;
    private String hazmatSignatureName;
    private String hazmatPackInstructions;
    private String HazMatType;
    private String upsCarrierName;
    
    

    public void setHazMatOverPackFlag(String hazMatOverPackFlag) {
        this.hazMatOverPackFlag = hazMatOverPackFlag;
    }

    public String getHazMatOverPackFlag() {
        return hazMatOverPackFlag;
    }

    

    public void setHazMatClass(String hazMatClass) {
        this.HazMatClass = hazMatClass;
    }

    public String getHazMatClass() {
        return HazMatClass;
    }

    public void setHazMatCharges(double hazMatCharges) {
        this.HazMatCharges = hazMatCharges;
    }

    public double getHazMatCharges() {
        return HazMatCharges;
    }

  
    public void setHazMatQty(double hazMatQty) {
        this.HazMatQty = hazMatQty;
    }

    public double getHazMatQty() {
        return HazMatQty;
    }

    public void setHazMatUnit(String hazMatUnit) {
        this.HazMatUnit = hazMatUnit;
    }

    public String getHazMatUnit() {
        return HazMatUnit;
    }

    public void setHazMatIdNo(String hazMatIdNo) {
        this.HazMatIdNo = hazMatIdNo;
    }

    public String getHazMatIdNo() {
        return HazMatIdNo;
    }

    public void setHazMatPkgGroup(String hazMatPkgGroup) {
        this.HazMatPkgGroup = hazMatPkgGroup;
    }

    public String getHazMatPkgGroup() {
        return HazMatPkgGroup;
    }
    // vikas added code to set and get UpsCarrierName
    public void setUpsCarrierName(String upsCarrierName) {
        this.upsCarrierName = upsCarrierName;
    }

    public String getUpsCarrierName() {
        return upsCarrierName;
    }
    // vikas code ended

    public void setHazMatDOTLabel(String hazMatDOTLabel) {
        this.HazMatDOTLabel = hazMatDOTLabel;
    }

    public String getHazMatDOTLabel() {
        return HazMatDOTLabel;
    }

    public void setHazMatEmerContactNo(String hazMatEmerContactNo) {
        this.HazMatEmerContactNo = hazMatEmerContactNo;
    }

    public String getHazMatEmerContactNo() {
        return HazMatEmerContactNo;
    }

    public void setHazMatEmerContactName(String hazMatEmerContactName) {
        this.HazMatEmerContactName = hazMatEmerContactName;
    }

    public String getHazMatEmerContactName() {
        return HazMatEmerContactName;
    }

    public void setHazMatId(String hazMatId) {
        this.HazMatId = hazMatId;
    }

    public String getHazMatId() {
        return HazMatId;
    }

    public void setHazmatPkgingCnt(double hazmatPkgingCnt) {
        this.hazmatPkgingCnt = hazmatPkgingCnt;
    }

    public double getHazmatPkgingCnt() {
        return hazmatPkgingCnt;
    }

    public void setHazmatPkgingUnits(String hazmatPkgingUnits) {
        this.hazmatPkgingUnits = hazmatPkgingUnits;
    }

    public String getHazmatPkgingUnits() {
        return hazmatPkgingUnits;
    }

    public void setHazmatTechnicalName(String hazmatTechnicalName) {
        this.hazmatTechnicalName = hazmatTechnicalName;
    }

    public String getHazmatTechnicalName() {
        return hazmatTechnicalName;
    }

    public void setHazmatSignatureName(String hazmatSignatureName) {
        this.hazmatSignatureName = hazmatSignatureName;
    }

    public String getHazmatSignatureName() {
        return hazmatSignatureName;
    }

    public void setHazmatPackInstructions(String hazmatPackInstructions) {
        this.hazmatPackInstructions = hazmatPackInstructions;
    }

    public String getHazmatPackInstructions() {
        return hazmatPackInstructions;
    }

    public void setHazmatCommNo(int hazmatCommNo) {
        this.hazmatCommNo = hazmatCommNo;
    }

    public int getHazmatCommNo() {
        return hazmatCommNo;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setShipmentPackageId(int shipmentPackageId) {
        this.shipmentPackageId = shipmentPackageId;
    }

    public int getShipmentPackageId() {
        return shipmentPackageId;
    }

    public void setPackageSequenceId(int packageSequenceId) {
        this.packageSequenceId = packageSequenceId;
    }

    public int getPackageSequenceId() {
        return packageSequenceId;
    }

    public void setVoidFlag(String voidFlag) {
        this.voidFlag = voidFlag;
    }

    public String getVoidFlag() {
        return voidFlag;
    }

    public void setHazMatType(String hazMatType) {
        this.HazMatType = hazMatType;
    }

    public String getHazMatType() {
        return HazMatType;
    }
}
