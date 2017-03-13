package com.shanawaz.flink.flink.model;


import java.util.Date;

public class JobApplied extends ErrorManger {

    private String id_job;

    private String username;
    private String jobid;
    private String adate;
    private String status_job;

    private String jobtitle;

    public String getId_job() {
        return id_job;
    }

    public void setId_job(String id_job) {
        this.id_job = id_job;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getStatus_job() {
        return status_job;
    }

    public void setStatus_job(String status_job) {
        this.status_job = status_job;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }
}
