package com.example.nidal.project;

public class AccidentActivityObject {


    private String needBandaid;
    private String bleeding;
    private String critical;
    private String emergency;
    private String callAmbulance;
    String longitude, latitude;
    String userId;
    String time;
    String people,spinal;


    public AccidentActivityObject() {


    }


    public AccidentActivityObject(String userId, String Bleeding, String needBandaid, String Critical,
                                  String callAmbulance, String latitude, String longitude, String time, String emergency,
                                  String people, String spinal) {
        this.userId = userId;
        this.needBandaid = needBandaid;
        this.bleeding = Bleeding;
        this.critical = Critical;
        this.callAmbulance = callAmbulance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.emergency = emergency;
        this.people = people;
        this.spinal = spinal;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNeedBandaid() {
        return needBandaid;
    }
    public void setNeedBandaid(String needBandaid) {
        this.needBandaid = needBandaid;
    }

    public String getBleeding() {
        return bleeding;
    }
    public void setBleeding(String bleeding) {
        this.bleeding = bleeding;
    }

    public String getCritical() {
        return critical;
    }
    public void setCritical(String critical) {
        critical = critical;
    }

    public String getCallAmbulance() {
        return callAmbulance;
    }
    public void setCallAmbulance(String callAmbulance) {
        this.callAmbulance = callAmbulance;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmergency() { return emergency; }
    public void setEmergency(String emergency) { this.emergency = emergency; }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }


    public String getSpinal() {
        return spinal;
    }

    public void setSpinal(String spinal) {
        this.spinal = spinal;
    }

}

