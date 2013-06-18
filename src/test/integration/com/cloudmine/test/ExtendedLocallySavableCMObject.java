package com.cloudmine.test;

import com.cloudmine.api.db.LocallySavableCMObject;

/**
 * Created with IntelliJ IDEA.
 * User: johnmccarthy
 * Date: 6/5/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedLocallySavableCMObject extends LocallySavableCMObject {

    private String name;
    private boolean isAwesome;
    private ExtendedCMObject subobject;
    private int numberOfHighFives;

    public ExtendedLocallySavableCMObject() {}

    public ExtendedLocallySavableCMObject(String name, boolean awesome, ExtendedCMObject subobject, int numberOfHighFives) {
        this.name = name;
        isAwesome = awesome;
        this.subobject = subobject;
        this.numberOfHighFives = numberOfHighFives;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAwesome() {
        return isAwesome;
    }

    public void setAwesome(boolean awesome) {
        isAwesome = awesome;
    }

    public ExtendedCMObject getSubobject() {
        return subobject;
    }

    public void setSubobject(ExtendedCMObject subobject) {
        this.subobject = subobject;
    }

    public int getNumberOfHighFives() {
        return numberOfHighFives;
    }

    public void setNumberOfHighFives(int numberOfHighFives) {
        this.numberOfHighFives = numberOfHighFives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtendedLocallySavableCMObject that = (ExtendedLocallySavableCMObject) o;

        if (isAwesome != that.isAwesome) return false;
        if (numberOfHighFives != that.numberOfHighFives) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (subobject != null ? !subobject.equals(that.subobject) : that.subobject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (isAwesome ? 1 : 0);
        result = 31 * result + (subobject != null ? subobject.hashCode() : 0);
        result = 31 * result + numberOfHighFives;
        return result;
    }
}
