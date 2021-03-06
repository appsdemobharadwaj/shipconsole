// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy;


public class RateV14 implements java.io.Serializable {
    protected java.lang.String fromZIPCode;
    protected java.lang.String toZIPCode;
    protected java.lang.String toCountry;
    protected java.math.BigDecimal amount;
    protected java.math.BigDecimal maxAmount;
    protected com.aasc.erp.carrier.stampsws.proxy.ServiceType serviceType;
    protected java.lang.String printLayout;
    protected java.lang.String deliverDays;
    protected java.lang.Double weightLb;
    protected java.lang.Double weightOz;
    protected com.aasc.erp.carrier.stampsws.proxy.PackageTypeV6 packageType;
    protected com.aasc.erp.carrier.stampsws.proxy.ArrayOfAddOnTypeV6[] requiresAllOf;
    protected double length;
    protected double width;
    protected double height;
    protected java.util.Calendar shipDate;
    protected java.util.Calendar deliveryDate;
    protected java.math.BigDecimal insuredValue;
    protected java.math.BigDecimal registeredValue;
    protected java.math.BigDecimal CODValue;
    protected java.math.BigDecimal declaredValue;
    protected java.lang.Boolean nonMachinable;
    protected java.lang.Boolean rectangularShaped;
    protected java.lang.String prohibitions;
    protected java.lang.String restrictions;
    protected java.lang.String observations;
    protected java.lang.String regulations;
    protected java.lang.String GEMNotes;
    protected java.lang.String maxDimensions;
    protected java.lang.String dimWeighting;
    protected com.aasc.erp.carrier.stampsws.proxy.AddOnV6[] addOns;
    protected java.lang.Integer effectiveWeightInOunces;
    protected java.lang.Boolean isIntraBMC;
    protected java.lang.Integer zone;
    protected java.lang.Integer rateCategory;
    protected java.lang.String toState;
    protected java.lang.Boolean cubicPricing;
    
    public RateV14() {
    }
    
    public java.lang.String getFromZIPCode() {
        return fromZIPCode;
    }
    
    public void setFromZIPCode(java.lang.String fromZIPCode) {
        this.fromZIPCode = fromZIPCode;
    }
    
    public java.lang.String getToZIPCode() {
        return toZIPCode;
    }
    
    public void setToZIPCode(java.lang.String toZIPCode) {
        this.toZIPCode = toZIPCode;
    }
    
    public java.lang.String getToCountry() {
        return toCountry;
    }
    
    public void setToCountry(java.lang.String toCountry) {
        this.toCountry = toCountry;
    }
    
    public java.math.BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    
    public java.math.BigDecimal getMaxAmount() {
        return maxAmount;
    }
    
    public void setMaxAmount(java.math.BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.ServiceType getServiceType() {
        return serviceType;
    }
    
    public void setServiceType(com.aasc.erp.carrier.stampsws.proxy.ServiceType serviceType) {
        this.serviceType = serviceType;
    }
    
    public java.lang.String getPrintLayout() {
        return printLayout;
    }
    
    public void setPrintLayout(java.lang.String printLayout) {
        this.printLayout = printLayout;
    }
    
    public java.lang.String getDeliverDays() {
        return deliverDays;
    }
    
    public void setDeliverDays(java.lang.String deliverDays) {
        this.deliverDays = deliverDays;
    }
    
    public java.lang.Double getWeightLb() {
        return weightLb;
    }
    
    public void setWeightLb(java.lang.Double weightLb) {
        this.weightLb = weightLb;
    }
    
    public java.lang.Double getWeightOz() {
        return weightOz;
    }
    
    public void setWeightOz(java.lang.Double weightOz) {
        this.weightOz = weightOz;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.PackageTypeV6 getPackageType() {
        return packageType;
    }
    
    public void setPackageType(com.aasc.erp.carrier.stampsws.proxy.PackageTypeV6 packageType) {
        this.packageType = packageType;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.ArrayOfAddOnTypeV6[] getRequiresAllOf() {
        return requiresAllOf;
    }
    
    public void setRequiresAllOf(com.aasc.erp.carrier.stampsws.proxy.ArrayOfAddOnTypeV6[] requiresAllOf) {
        this.requiresAllOf = requiresAllOf;
    }
    
    public double getLength() {
        return length;
    }
    
    public void setLength(double length) {
        this.length = length;
    }
    
    public double getWidth() {
        return width;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public java.util.Calendar getShipDate() {
        return shipDate;
    }
    
    public void setShipDate(java.util.Calendar shipDate) {
        this.shipDate = shipDate;
    }
    
    public java.util.Calendar getDeliveryDate() {
        return deliveryDate;
    }
    
    public void setDeliveryDate(java.util.Calendar deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public java.math.BigDecimal getInsuredValue() {
        return insuredValue;
    }
    
    public void setInsuredValue(java.math.BigDecimal insuredValue) {
        this.insuredValue = insuredValue;
    }
    
    public java.math.BigDecimal getRegisteredValue() {
        return registeredValue;
    }
    
    public void setRegisteredValue(java.math.BigDecimal registeredValue) {
        this.registeredValue = registeredValue;
    }
    
    public java.math.BigDecimal getCODValue() {
        return CODValue;
    }
    
    public void setCODValue(java.math.BigDecimal CODValue) {
        this.CODValue = CODValue;
    }
    
    public java.math.BigDecimal getDeclaredValue() {
        return declaredValue;
    }
    
    public void setDeclaredValue(java.math.BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
    }
    
    public java.lang.Boolean getNonMachinable() {
        return nonMachinable;
    }
    
    public void setNonMachinable(java.lang.Boolean nonMachinable) {
        this.nonMachinable = nonMachinable;
    }
    
    public java.lang.Boolean getRectangularShaped() {
        return rectangularShaped;
    }
    
    public void setRectangularShaped(java.lang.Boolean rectangularShaped) {
        this.rectangularShaped = rectangularShaped;
    }
    
    public java.lang.String getProhibitions() {
        return prohibitions;
    }
    
    public void setProhibitions(java.lang.String prohibitions) {
        this.prohibitions = prohibitions;
    }
    
    public java.lang.String getRestrictions() {
        return restrictions;
    }
    
    public void setRestrictions(java.lang.String restrictions) {
        this.restrictions = restrictions;
    }
    
    public java.lang.String getObservations() {
        return observations;
    }
    
    public void setObservations(java.lang.String observations) {
        this.observations = observations;
    }
    
    public java.lang.String getRegulations() {
        return regulations;
    }
    
    public void setRegulations(java.lang.String regulations) {
        this.regulations = regulations;
    }
    
    public java.lang.String getGEMNotes() {
        return GEMNotes;
    }
    
    public void setGEMNotes(java.lang.String GEMNotes) {
        this.GEMNotes = GEMNotes;
    }
    
    public java.lang.String getMaxDimensions() {
        return maxDimensions;
    }
    
    public void setMaxDimensions(java.lang.String maxDimensions) {
        this.maxDimensions = maxDimensions;
    }
    
    public java.lang.String getDimWeighting() {
        return dimWeighting;
    }
    
    public void setDimWeighting(java.lang.String dimWeighting) {
        this.dimWeighting = dimWeighting;
    }
    
    public com.aasc.erp.carrier.stampsws.proxy.AddOnV6[] getAddOns() {
        return addOns;
    }
    
    public void setAddOns(com.aasc.erp.carrier.stampsws.proxy.AddOnV6[] addOns) {
        this.addOns = addOns;
    }
    
    public java.lang.Integer getEffectiveWeightInOunces() {
        return effectiveWeightInOunces;
    }
    
    public void setEffectiveWeightInOunces(java.lang.Integer effectiveWeightInOunces) {
        this.effectiveWeightInOunces = effectiveWeightInOunces;
    }
    
    public java.lang.Boolean getIsIntraBMC() {
        return isIntraBMC;
    }
    
    public void setIsIntraBMC(java.lang.Boolean isIntraBMC) {
        this.isIntraBMC = isIntraBMC;
    }
    
    public java.lang.Integer getZone() {
        return zone;
    }
    
    public void setZone(java.lang.Integer zone) {
        this.zone = zone;
    }
    
    public java.lang.Integer getRateCategory() {
        return rateCategory;
    }
    
    public void setRateCategory(java.lang.Integer rateCategory) {
        this.rateCategory = rateCategory;
    }
    
    public java.lang.String getToState() {
        return toState;
    }
    
    public void setToState(java.lang.String toState) {
        this.toState = toState;
    }
    
    public java.lang.Boolean getCubicPricing() {
        return cubicPricing;
    }
    
    public void setCubicPricing(java.lang.Boolean cubicPricing) {
        this.cubicPricing = cubicPricing;
    }
}
