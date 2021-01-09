package com.example.licagent.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ClientClass implements Serializable {

    private String name, address;
    private long phnum, plan, polyno, polyterm, payterm, assSum, totPrem;
    private Date  dob, datecomm, datemat, dueDate, lastDate;
    ArrayList<Date> premDates = new ArrayList<>();

    public ClientClass() {
    }

    public ClientClass(String name, String address, long phnum, long plan, long polyno, long polyterm, long payterm, long assSum, long totPrem, Date dob, Date datecomm, Date datemat, Date dueDate, Date lastDate) {
        this.name = name;
        this.address = address;
        this.phnum = phnum;
        this.plan = plan;
        this.polyno = polyno;
        this.polyterm = polyterm;
        this.payterm = payterm;
        this.assSum = assSum;
        this.totPrem = totPrem;
        this.dob = dob;
        this.datecomm = datecomm;
        this.datemat = datemat;
        this.dueDate = dueDate;
        this.lastDate = lastDate;
    }


    public ArrayList<Date> getPremDates() {
        return premDates;
    }

    public void setPremDates(ArrayList<Date> premDates) {
        this.premDates = premDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDatecomm() {
        return datecomm;
    }

    public void setDatecomm(Date datecomm) {
        this.datecomm = datecomm;
    }

    public Date getDatemat() {
        return datemat;
    }

    public void setDatemat(Date datemat) {
        this.datemat = datemat;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
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
