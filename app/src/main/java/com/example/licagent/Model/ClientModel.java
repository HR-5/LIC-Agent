package com.example.licagent.Model;

import java.util.Date;

public class ClientModel {
    ClientClass client;
    Date nextPrem;

    public ClientClass getClient() {
        return client;
    }

    public void setClient(ClientClass client) {
        this.client = client;
    }

    public Date getNextPrem() {
        return nextPrem;
    }

    public void setNextPrem(Date nextPrem) {
        this.nextPrem = nextPrem;
    }
}
