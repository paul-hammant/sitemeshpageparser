package com.paulhammant.sitemeshpageparser.parser;

import com.paulhammant.sitemeshpageparser.SitemeshBuffer;
import com.paulhammant.sitemeshpageparser.SitemeshBufferFragment;
import com.paulhammant.sitemeshpageparser.SitemeshWriter;
import com.paulhammant.sitemeshpageparser.html.rules.PageBuilder;
import com.paulhammant.sitemeshpageparser.html.tokenizer.TagTokenizer;

import java.io.IOException;
import java.io.Writer;

/**
 * HTMLPage implementation that builds itself based on the incoming 'tag' and 'text' tokens fed to it from the
 * HTMLTagTokenizer.
 *
 * @see HTMLPageParser
 * @see TagTokenizer
 *
 * @author Joe Walnes
 */
public class TokenizedHTMLPage extends AbstractHTMLPage implements PageBuilder {

    private SitemeshBufferFragment body;
    private SitemeshBufferFragment head;

    public TokenizedHTMLPage(SitemeshBuffer sitemeshBuffer) {
        super(sitemeshBuffer);
        addProperty("title", "");
    }

    public void setBody(SitemeshBufferFragment body) {
        this.body = body;
    }

    public void setHead(SitemeshBufferFragment head) {
        this.head = head;
    }

    public void writeHead(Writer out) throws IOException {
        if (out instanceof SitemeshWriter) {
            ((SitemeshWriter) out).writeSitemeshBufferFragment(head);
        } else {
            head.writeTo(out);
        }
    }

    public void writeBody(Writer out) throws IOException {
        if (out instanceof SitemeshWriter) {
            ((SitemeshWriter) out).writeSitemeshBufferFragment(body);
        } else {
            body.writeTo(out);
        }
    }

    public String getHead() {
        return head.getStringContent();
    }

    public String getBody() {
        return body.getStringContent();
    }
}
