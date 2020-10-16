package com.example.licagent.Model;

public class AgentClass {
    private String name,email;
    private int phnum,agent_num;

    public AgentClass(String name, String email, int phnum, int agent_num) {
        this.name = name;
        this.email = email;
        this.phnum = phnum;
        this.agent_num = agent_num;
    }

    public AgentClass() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPhnum() {
        return phnum;
    }

    public int getAgent_num() {
        return agent_num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhnum(int phnum) {
        this.phnum = phnum;
    }

    public void setAgent_num(int agent_num) {
        this.agent_num = agent_num;
    }
}
