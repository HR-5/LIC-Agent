package com.example.licagent.Model;

import java.io.Serializable;

public class ClientClass implements Serializable {

    private String name, dob, datecomm, datemat, dueDate, lastDate, address;
    private long phnum, plan, polyno, polyterm, payterm, assSum, totPrem;

    public ClientClass() {
    }

    public ClientClass(String name, String dob, String datecomm, String datemat, String dueDate, String lastDate, String address, long phnum, long plan, long polyno, long polyterm, long payterm, long assSum, long totPrem) {
        this.name = name;
        this.dob = dob;
        this.datecomm = datecomm;
        this.datemat = datemat;
        this.dueDate = dueDate;
        this.lastDate = lastDate;
        this.address = address;
        this.phnum = phnum;
        this.plan = plan;
        this.polyno = polyno;
        this.polyterm = polyterm;
        this.payterm = payterm;
        this.assSum = assSum;
        this.totPrem = totPrem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDatecomm() {
        return datecomm;
    }

    public void setDatecomm(String datecomm) {
        this.datecomm = datecomm;
    }

    public String getDatemat() {
        return datemat;
    }

    public void setDatemat(String datemat) {
        this.datemat = datemat;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhnum() {
        return phnum;
    }

    public void setPhnum(long phnum) {
        this.phnum = phnum;
    }

    public long getPlan() {
        return plan;
    }

    public void setPlan(long plan) {
        this.plan = plan;
    }

    public long getPolyno() {
        return polyno;
    }

    public void setPolyno(long polyno) {
        this.polyno = polyno;
    }

    public long getPolyterm() {
        return polyterm;
    }

    public void setPolyterm(long polyterm) {
        this.polyterm = polyterm;
    }

    public long getPayterm() {
        return payterm;
    }

    public void setPayterm(long payterm) {
        this.payterm = payterm;
    }

    public long getAssSum() {
        return assSum;
    }

    public void setAssSum(long assSum) {
        this.assSum = assSum;
    }

    public long getTotPrem() {
        return totPrem;
    }

    public void setTotPrem(long totPrem) {
        this.totPrem = totPrem;
    }
}
