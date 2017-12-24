/*
 * Title:        AbstractHTMLPage
 * Description:
 *
 * This software is published under the terms of the OpenSymphony Software
 * License version 1.1, of which a copy has been included with this
 * distribution in the LICENSE.txt file.
 */

package com.paulhammant.sitemeshpageparser.parser;

import com.paulhammant.sitemeshpageparser.HTMLPage;
import com.paulhammant.sitemeshpageparser.SitemeshBuffer;

import java.io.IOException;
import java.io.Writer;

/**
 * Abstract implementation of {@link HTMLPage}.
 *
 * <p>Adds to {@link AbstractPage} some HTML methods.
 * To implement, follow guidelines of super-class, and implement the 2
 * abstract methods states below.</p>
 *
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @version $Revision: 1.5 $
 *
 * @see AbstractPage
 * @see HTMLPage
 */
public abstract class AbstractHTMLPage extends AbstractPage implements HTMLPage {

    protected AbstractHTMLPage(SitemeshBuffer sitemeshBuffer) {
        super(sitemeshBuffer);
    }

    /**
     * Write data of html <code>&lt;head&gt;</code> tag.
     *
     * <p>Must be implemented. Data written should not actually contain the
     * head tags, but all the data in between.
     */
    public abstract void writeHead(Writer out) throws IOException;

    public boolean isFrameSet() {
        return isPropertySet("frameset") && getProperty("frameset").equalsIgnoreCase("true");
    }

    public void setFrameSet(boolean frameset) {
        if (frameset)
        {
            addProperty("frameset", "true");
        }
        else if (isPropertySet("frameset"))
        {
            addProperty("frameset", "false");
        }
    }
}