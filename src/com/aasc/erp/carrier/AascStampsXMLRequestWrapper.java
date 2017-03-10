package com.aasc.erp.carrier;

import com.aasc.erp.carrier.stampsws.proxy.Address;
import com.aasc.erp.carrier.stampsws.proxy.Credentials;
import com.aasc.erp.carrier.stampsws.proxy.CustomsV2;
import com.aasc.erp.carrier.stampsws.proxy.EltronPrinterDPIType;
import com.aasc.erp.carrier.stampsws.proxy.ImageType;
import com.aasc.erp.carrier.stampsws.proxy.LabelRecipientInfo;
import com.aasc.erp.carrier.stampsws.proxy.NonDeliveryOption;
import com.aasc.erp.carrier.stampsws.proxy.PaperSizeV1;
import com.aasc.erp.carrier.stampsws.proxy.RateV14;
import com.aasc.erp.carrier.stampsws.proxy.ShipmentNotification;
import com.aasc.erp.carrier.stampsws.proxy.Credentials;

public class AascStampsXMLRequestWrapper {
    public AascStampsXMLRequestWrapper() {
    }
    private Credentials credentials;
    private String authenticator;
    private String integratorTxID;
    private String trackingNumber;
    private RateV14 rate;
    private Address from;
    private Address to;
    private String customerID;
    private CustomsV2 customs;
    private Boolean sampleOnly;
    private ImageType imageType;
    private EltronPrinterDPIType eltronPrinterDPIType;
    private String memo;
    private Integer cost_code_id;
    private Boolean deliveryNotification;
    private ShipmentNotification shipmentNotification;
    private Integer rotationDegrees;
    private Integer horizontalOffset;
    private Integer verticalOffset;
    private Integer printDensity;
    private Boolean printMemo;
    private Boolean printInstructions;
    private Boolean requestPostageHash;
    private NonDeliveryOption nonDeliveryOption;
    private Address redirectTo;
    private String originalPostageHash;
    private Boolean returnImageData;
    private String internalTransactionNumber;
    private PaperSizeV1 paperSize;
    private LabelRecipientInfo emailLabelTo;
    private String stampsTaxId;


    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setAuthenticator(String authenticator) {
        this.authenticator = authenticator;
    }

    public String getAuthenticator() {
        return authenticator;
    }

    public void setIntegratorTxID(String integratorTxID) {
        this.integratorTxID = integratorTxID;
    }

    public String getIntegratorTxID() {
        return integratorTxID;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setRate(RateV14 rate) {
        this.rate = rate;
    }

    public RateV14 getRate() {
        return rate;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getFrom() {
        return from;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public Address getTo() {
        return to;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustoms(CustomsV2 customs) {
        this.customs = customs;
    }

    public CustomsV2 getCustoms() {
        return customs;
    }

    public void setSampleOnly(Boolean sampleOnly) {
        this.sampleOnly = sampleOnly;
    }

    public Boolean getSampleOnly() {
        return sampleOnly;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setEltronPrinterDPIType(EltronPrinterDPIType eltronPrinterDPIType) {
        this.eltronPrinterDPIType = eltronPrinterDPIType;
    }

    public EltronPrinterDPIType getEltronPrinterDPIType() {
        return eltronPrinterDPIType;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setCost_code_id(Integer cost_code_id) {
        this.cost_code_id = cost_code_id;
    }

    public Integer getCost_code_id() {
        return cost_code_id;
    }

    public void setDeliveryNotification(Boolean deliveryNotification) {
        this.deliveryNotification = deliveryNotification;
    }

    public Boolean getDeliveryNotification() {
        return deliveryNotification;
    }

    public void setShipmentNotification(ShipmentNotification shipmentNotification) {
        this.shipmentNotification = shipmentNotification;
    }

    public ShipmentNotification getShipmentNotification() {
        return shipmentNotification;
    }

    public void setRotationDegrees(Integer rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }

    public Integer getRotationDegrees() {
        return rotationDegrees;
    }

    public void setHorizontalOffset(Integer horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }

    public Integer getHorizontalOffset() {
        return horizontalOffset;
    }

    public void setVerticalOffset(Integer verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    public Integer getVerticalOffset() {
        return verticalOffset;
    }

    public void setPrintDensity(Integer printDensity) {
        this.printDensity = printDensity;
    }

    public Integer getPrintDensity() {
        return printDensity;
    }

    public void setPrintMemo(Boolean printMemo) {
        this.printMemo = printMemo;
    }

    public Boolean getPrintMemo() {
        return printMemo;
    }

    public void setPrintInstructions(Boolean printInstructions) {
        this.printInstructions = printInstructions;
    }

    public Boolean getPrintInstructions() {
        return printInstructions;
    }

    public void setRequestPostageHash(Boolean requestPostageHash) {
        this.requestPostageHash = requestPostageHash;
    }

    public Boolean getRequestPostageHash() {
        return requestPostageHash;
    }

    public void setNonDeliveryOption(NonDeliveryOption nonDeliveryOption) {
        this.nonDeliveryOption = nonDeliveryOption;
    }

    public NonDeliveryOption getNonDeliveryOption() {
        return nonDeliveryOption;
    }

    public void setRedirectTo(Address redirectTo) {
        this.redirectTo = redirectTo;
    }

    public Address getRedirectTo() {
        return redirectTo;
    }

    public void setOriginalPostageHash(String originalPostageHash) {
        this.originalPostageHash = originalPostageHash;
    }

    public String getOriginalPostageHash() {
        return originalPostageHash;
    }

    public void setReturnImageData(Boolean returnImageData) {
        this.returnImageData = returnImageData;
    }

    public Boolean getReturnImageData() {
        return returnImageData;
    }

    public void setInternalTransactionNumber(String internalTransactionNumber) {
        this.internalTransactionNumber = internalTransactionNumber;
    }

    public String getInternalTransactionNumber() {
        return internalTransactionNumber;
    }

    public void setPaperSize(PaperSizeV1 paperSize) {
        this.paperSize = paperSize;
    }

    public PaperSizeV1 getPaperSize() {
        return paperSize;
    }

    public void setEmailLabelTo(LabelRecipientInfo emailLabelTo) {
        this.emailLabelTo = emailLabelTo;
    }

    public LabelRecipientInfo getEmailLabelTo() {
        return emailLabelTo;
    }

    public void setStampsTaxId(String stampsTaxId) {
        this.stampsTaxId = stampsTaxId;
    }

    public String getStampsTaxId() {
        return stampsTaxId;
    }
}
