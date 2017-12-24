package com.paulhammant.sitemeshpageparser.parser;

import com.paulhammant.sitemeshpageparser.HTMLPage;
import com.paulhammant.sitemeshpageparser.SitemeshBuffer;
import com.paulhammant.sitemeshpageparser.SitemeshBufferFragment;
import com.paulhammant.sitemeshpageparser.SitemeshWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PartialPageParserHtmlPage extends AbstractPage implements HTMLPage
{
    private final SitemeshBufferFragment head;
    private final SitemeshBufferFragment body;
    private final SitemeshBuffer sitemeshBuffer;

    public PartialPageParserHtmlPage(SitemeshBuffer sitemeshBuffer)
    {
        this(sitemeshBuffer, null, null, null, null, null, null);
    }

    public PartialPageParserHtmlPage(SitemeshBuffer sitemeshBuffer, SitemeshBufferFragment body, Map<String, String> bodyProperties)
    {
        this(sitemeshBuffer, body, bodyProperties, null, null, null, null);
    }

    /**
     * @param sitemeshBuffer The buffer for the page
     * @param body           The body fragment
     * @param bodyProperties The properties of the body
     * @param head           The head section
     * @param title          The title
     * @param metaAttributes The meta attributes found in the head section
     * @param pageProperties The page properties extracted from the head section
     */
    public PartialPageParserHtmlPage(SitemeshBuffer sitemeshBuffer, SitemeshBufferFragment body, Map<String, String> bodyProperties,
                                     SitemeshBufferFragment head, String title, Map<String, String> metaAttributes, Map<String, String> pageProperties)
    {
        super(sitemeshBuffer);
        this.sitemeshBuffer = sitemeshBuffer;
        this.head = head;
        this.body = body;
        if (title == null)
        {
            title = "";
        }
        addProperty("title", title);
        addProperties(metaAttributes, "meta.");
        addProperties(bodyProperties, "body.");
        addProperties(pageProperties, "page.");
    }

    private void addProperties(Map<String, String> properties, String prefix)
    {
        if (properties != null)
        {
            for (Map.Entry<String, String> property : properties.entrySet())
            {
                addProperty(prefix + property.getKey(), property.getValue());
            }
        }
    }

    public void writeHead(Writer out) throws IOException
    {
        if (head != null)
        {
            head.writeTo(out);
        }
    }

    public String getHead()
    {
        if (head != null)
        {
            StringWriter headString = new StringWriter();
            try
            {
                head.writeTo(headString);
            }
            catch (IOException e)
            {
                throw new RuntimeException("IOException occured while writing to buffer?", e);
            }
            return headString.toString();
        }
        else
        {
            return "";
        }
    }

    @Override
    public void writeBody(Writer out) throws IOException
    {
        if (body != null)
        {
            if (out instanceof SitemeshWriter)
            {
                ((SitemeshWriter) out).writeSitemeshBufferFragment(body);
            }
            else
            {
                ///
                // for the record its possible that the body buffer has SOME of the body but not all of it
                // because its in secondary storage.  So IF we have secondary storage then we know the body
                // was pretty big
                //
                body.writeTo(out);
            }
        }
        else
        {
            //
            // if we have no body fragment it means that the whole thing is a body or there is no html or body tags.  Either way
            // we want to write everything out as is.
            //
            sitemeshBuffer.writeTo(out, 0, sitemeshBuffer.getBufferLength());
        }
    }

    @Override
    public String getBody()
    {
        return super.getBody();
    }

    @Override
    public void writePage(Writer out) throws IOException
    {
        sitemeshBuffer.writeTo(out, 0, sitemeshBuffer.getBufferLength());
    }

    public boolean isFrameSet()
    {
        return false;
    }

    public void setFrameSet(boolean frameset)
    {
        throw new UnsupportedOperationException();
    }
}
