package com.example.money.entity;

/**
 * Created by su on 2014/11/14.
 */
public class VersionEntity {

    private String title;
    private String description;
    private boolean force;
    private boolean newVersion;
    private String url;
    private String version;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public boolean isNewVersion() {
        return newVersion;
    }

    public void setNewVersion(boolean newVersion) {
        this.newVersion = newVersion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "VersionEntity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", force=" + force +
                ", newVersion=" + newVersion +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
