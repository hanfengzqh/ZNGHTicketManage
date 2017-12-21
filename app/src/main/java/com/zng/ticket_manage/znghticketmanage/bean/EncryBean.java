package com.zng.ticket_manage.znghticketmanage.bean;

import java.io.Serializable;
import java.util.List;

public class EncryBean implements Serializable {
    private String takerEmail;
    private String buyerTel;
    private String casherName;
    private String buyerBankAccount;
    private String takerName;
    private String sellerAddress;
    private String buyerName;
    private String takerTel;
    private String buyerAddress;
    private String reviewerName;
    private String invoiceReqSerialNo;
    private String buyerBankName;
    private String sellerBankAccount;
    private String buyerTaxpayerNum;
    private String sellerTel;
    private String drawerName;
    private String taxpayerNum;
    private String sellerBankName;
    private List<ItemList> itemList;

    //红票冲红
    private String invoiceCode;
    private String invoiceNo;
    private String redReason;
    private String amount;

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getRedReason() {
        return redReason;
    }

    public void setRedReason(String redReason) {
        this.redReason = redReason;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTakerEmail() {
        return takerEmail;
    }

    public void setTakerEmail(String takerEmail) {
        this.takerEmail = takerEmail;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getCasherName() {
        return casherName;
    }

    public void setCasherName(String casherName) {
        this.casherName = casherName;
    }

    public String getBuyerBankAccount() {
        return buyerBankAccount;
    }

    public void setBuyerBankAccount(String buyerBankAccount) {
        this.buyerBankAccount = buyerBankAccount;
    }

    public String getTakerName() {
        return takerName;
    }

    public void setTakerName(String takerName) {
        this.takerName = takerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getTakerTel() {
        return takerTel;
    }

    public void setTakerTel(String takerTel) {
        this.takerTel = takerTel;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getInvoiceReqSerialNo() {
        return invoiceReqSerialNo;
    }

    public void setInvoiceReqSerialNo(String invoiceReqSerialNo) {
        this.invoiceReqSerialNo = invoiceReqSerialNo;
    }

    public String getBuyerBankName() {
        return buyerBankName;
    }

    public void setBuyerBankName(String buyerBankName) {
        this.buyerBankName = buyerBankName;
    }

    public String getSellerBankAccount() {
        return sellerBankAccount;
    }

    public void setSellerBankAccount(String sellerBankAccount) {
        this.sellerBankAccount = sellerBankAccount;
    }

    public String getBuyerTaxpayerNum() {
        return buyerTaxpayerNum;
    }

    public void setBuyerTaxpayerNum(String buyerTaxpayerNum) {
        this.buyerTaxpayerNum = buyerTaxpayerNum;
    }

    public String getSellerTel() {
        return sellerTel;
    }

    public void setSellerTel(String sellerTel) {
        this.sellerTel = sellerTel;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public String getTaxpayerNum() {
        return taxpayerNum;
    }

    public void setTaxpayerNum(String taxpayerNum) {
        this.taxpayerNum = taxpayerNum;
    }

    public String getSellerBankName() {
        return sellerBankName;
    }

    public void setSellerBankName(String sellerBankName) {
        this.sellerBankName = sellerBankName;
    }

    public List<ItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

    public class ItemList implements Serializable {

        private String unitPrice;
        private String taxRateValue;
        private String specificationModel;
        private String quantity;
        private String discountRateValue;
        private String taxClassificationCode;
        private String invoiceAmount;
        private String goodsName;
        private String meteringUnit;
        private String includeTaxFlag;
        private String deductionAmount;

        public String getDeductionAmount() {
            return deductionAmount;
        }

        public void setDeductionAmount(String deductionAmount) {
            this.deductionAmount = deductionAmount;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getTaxRateValue() {
            return taxRateValue;
        }

        public void setTaxRateValue(String taxRateValue) {
            this.taxRateValue = taxRateValue;
        }

        public String getSpecificationModel() {
            return specificationModel;
        }

        public void setSpecificationModel(String specificationModel) {
            this.specificationModel = specificationModel;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDiscountRateValue() {
            return discountRateValue;
        }

        public void setDiscountRateValue(String discountRateValue) {
            this.discountRateValue = discountRateValue;
        }

        public String getTaxClassificationCode() {
            return taxClassificationCode;
        }

        public void setTaxClassificationCode(String taxClassificationCode) {
            this.taxClassificationCode = taxClassificationCode;
        }

        public String getInvoiceAmount() {
            return invoiceAmount;
        }

        public void setInvoiceAmount(String invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getMeteringUnit() {
            return meteringUnit;
        }

        public void setMeteringUnit(String meteringUnit) {
            this.meteringUnit = meteringUnit;
        }

        public String getIncludeTaxFlag() {
            return includeTaxFlag;
        }

        public void setIncludeTaxFlag(String includeTaxFlag) {
            this.includeTaxFlag = includeTaxFlag;
        }
    }
}