package com.example.licagent.Model;

public class AgentClass {
    private String name,email;
    private long phnum,agent_num;

    public AgentClass(String name, String email, long phnum, long agent_num) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhnum() {
        return phnum;
    }

    public void setPhnum(long phnum) {
        this.phnum = phnum;
    }

    public long getAgent_num() {
        return agent_num;
    }

    public void setAgent_num(long agent_num) {
        this.agent_num = agent_num;
    }
}
