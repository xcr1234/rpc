package com.rpc.io.meta;

import java.io.Serializable;

public class Version implements Serializable,Comparable<Version> {
    private static final long serialVersionUID = 7853085479531003756L;

    private int manor;

    private int major;

    public Version(){
        this.manor = 1;
        this.major = 0;
    }

    public Version(int manor, int major) {
        this.manor = manor;
        this.major = major;
    }

    public int getManor() {
        return manor;
    }

    public void setManor(int manor) {
        this.manor = manor;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }


    @Override
    public String toString() {
        return "Version" + manor + "." + major;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        if (manor != version.manor) return false;
        return major == version.major;
    }

    @Override
    public int hashCode() {
        int result = manor;
        result = 31 * result + major;
        return result;
    }

    @Override
    public int compareTo(Version o) {
        Integer a = manor * 1000 + major;
        Integer b = o.manor * 1000 + o.major;
        return a.compareTo(b);
    }
}
