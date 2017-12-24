/*
 * Title:        AbstractPage
 * Description:
 *
 * This software is published under the terms of the OpenSymphony Software
 * License version 1.1, of which a copy has been included with this
 * distribution in the LICENSE.txt file.
 */

package com.paulhammant.sitemeshpageparser.parser;

import com.paulhammant.sitemeshpageparser.Page;
import com.paulhammant.sitemeshpageparser.SitemeshBuffer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation of {@link Page} .
 *
 * <p>Contains base methods for storing and accessing page properties.
 * *
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @version $Revision: 1.6 $
 *
 * @see Page
 */
public abstract class AbstractPage implements Page {
    /**
     * Map of all properties.
     * Key is String. Value is java.util.List of multiple String values.
     */
    private final Map properties = new HashMap();

    /** Date of page contents. */
    private final SitemeshBuffer sitemeshBuffer;

    protected AbstractPage(SitemeshBuffer sitemeshBuffer) {
        this.sitemeshBuffer = sitemeshBuffer;
    }

    public void writePage(Writer out) throws IOException {
        sitemeshBuffer.writeTo(out, 0, sitemeshBuffer.getBufferLength());
    }

    public String getPage() {
        try {
            StringWriter writer = new StringWriter();
            writePage(writer);
            return writer.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Could not get page " + e.getMessage(), e);
        }
    }

    /**
     * Write data of html <code>&lt;body&gt;</code> tag.
     *
     * <p>Must be implemented. Data written should not actually contain the
     * body tags, but all the data in between.
     */
    public abstract void writeBody(Writer out) throws IOException;

    public String getBody() {
        try {
            StringWriter writer = new StringWriter();
            writeBody(writer);
            return writer.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Could not get body " + e.getMessage(), e);
        }
    }

    /** Return title of from "title" property. Never returns null. */
    public String getTitle() {
        return noNull(getProperty("title"));
    }

    public String getProperty(String name) {
        if (!isPropertySet(name)) return null;
        return (String)properties.get(name);
    }

    public int getIntProperty(String name) {
        try {
            return Integer.parseInt(noNull(getProperty(name)));
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public long getLongProperty(String name) {
        try {
            return Long.parseLong(noNull(getProperty(name)));
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean getBooleanProperty(String name) {
        String property = getProperty(name);
        if (property == null || property.trim().length() == 0) return false;
        switch (property.charAt(0)) {
            case '1':
            case 't':
            case 'T':
            case 'y':
            case 'Y':
                return true;
            default:
                return false;
        }
    }

    public boolean isPropertySet(String name) {
        return properties.containsKey(name);
    }

    public String[] getPropertyKeys() {
        synchronized(properties) {
            Set keys = properties.keySet();
            return (String[])keys.toArray(new String[keys.size()]);
        }
    }

    public Map getProperties() {
        return properties;
    }

    /**
     * Add a property to the properties list.
     *
     * @param name Name of property
     * @param value Value of property
     */
    public void addProperty(String name, String value) {
        properties.put(name, value);
    }

    /** Return String as is, or "" if null. (Prevents NullPointerExceptions) */
    protected static String noNull(String in) {
        return in == null ? "" : in;
    }

}
