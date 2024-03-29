package com.inventoryxpert.application.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerName;
    private String customerAddress;
    private String paymentTerms;

    private String invoiceNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate invoiceDate;

    @Temporal(TemporalType.DATE)
    private LocalDate expiryDate;

    private String contactPerson;

    // One invoice can have many invoice lines
    // The cascade attribute ensures that all operations made on an Invoice
    // are cascaded to the InvoiceLines


    @OneToMany(cascade = CascadeType.ALL)
    private List<InvoiceLine> invoiceLines;

    private Double totalSales;

    // No-arg constructor, getters and setters...
    public Invoice() {
    }

    public Invoice(Long id, String customerName, String customerAddress, String paymentTerms, String invoiceNumber,
            LocalDate invoiceDate, LocalDate expiryDate, String contactPerson, List<InvoiceLine> invoiceLines,
            Double totalSales) {
        this.id = id;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.paymentTerms = paymentTerms;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.expiryDate = expiryDate;
        this.contactPerson = contactPerson;
        this.invoiceLines = invoiceLines;
        this.totalSales = totalSales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }
}
