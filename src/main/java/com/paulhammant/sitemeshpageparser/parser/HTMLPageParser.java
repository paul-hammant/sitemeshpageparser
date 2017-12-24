package com.paulhammant.sitemeshpageparser.parser;

import com.paulhammant.sitemeshpageparser.DefaultSitemeshBuffer;
import com.paulhammant.sitemeshpageparser.Page;
import com.paulhammant.sitemeshpageparser.PageParser;
import com.paulhammant.sitemeshpageparser.SitemeshBuffer;
import com.paulhammant.sitemeshpageparser.SitemeshBufferFragment;
import com.paulhammant.sitemeshpageparser.html.HTMLProcessor;
import com.paulhammant.sitemeshpageparser.html.State;
import com.paulhammant.sitemeshpageparser.html.StateTransitionRule;
import com.paulhammant.sitemeshpageparser.html.rules.BodyTagRule;
import com.paulhammant.sitemeshpageparser.html.rules.ContentBlockExtractingRule;
import com.paulhammant.sitemeshpageparser.html.rules.FramesetRule;
import com.paulhammant.sitemeshpageparser.html.rules.HeadExtractingRule;
import com.paulhammant.sitemeshpageparser.html.rules.HtmlAttributesRule;
import com.paulhammant.sitemeshpageparser.html.rules.MSOfficeDocumentPropertiesRule;
import com.paulhammant.sitemeshpageparser.html.rules.MetaTagRule;
import com.paulhammant.sitemeshpageparser.html.rules.ParameterExtractingRule;
import com.paulhammant.sitemeshpageparser.html.rules.TitleExtractingRule;
import com.paulhammant.sitemeshpageparser.html.rules.PageBuilder;

import java.io.IOException;

/**
 * <p>Builds an HTMLPage object from an HTML document. This behaves
 * similarly to the FastPageParser, however it's a complete rewrite that is simpler to add custom features to such as
 * extraction and transformation of elements.</p>
 *
 * <p>To customize the rules used, this class can be extended and have the userDefinedRules() methods overridden.</p>
 *
 * @author Joe Walnes
 *
 * @see HTMLProcessor
 */
public class HTMLPageParser implements PageParser {

    public Page parse(char[] buffer) throws IOException {
        return parse(new DefaultSitemeshBuffer(buffer));
    }

    public Page parse(SitemeshBuffer buffer) throws IOException {
        SitemeshBufferFragment.Builder head = SitemeshBufferFragment.builder().setBuffer(buffer).setLength(0);
        SitemeshBufferFragment.Builder body = SitemeshBufferFragment.builder().setBuffer(buffer);
        TokenizedHTMLPage page = new TokenizedHTMLPage(buffer);
        HTMLProcessor processor = new HTMLProcessor(buffer, body);
        State html = processor.defaultState();

        // Core rules for SiteMesh to be functional.
        html.addRule(new HeadExtractingRule(head)); // contents of <head>
        html.addRule(new BodyTagRule(page, body)); // contents of <body>
        html.addRule(new TitleExtractingRule(page)); // the <title>
        html.addRule(new FramesetRule(page)); // if the page is a frameset

        // Additional rules - designed to be tweaked.
        addUserDefinedRules(html, page);

        processor.process();
        page.setBody(body.build());
        page.setHead(head.build());
        return page;
    }

    protected void addUserDefinedRules(State html, PageBuilder page) {
        // Ensure that while in <xml> tag, none of the other rules kick in.
        // For example <xml><book><title>hello</title></book></xml> should not change the affect the title of the page.
        State xml = new State();
        html.addRule(new StateTransitionRule("xml", xml));

        // Useful properties
        html.addRule(new HtmlAttributesRule(page));         // attributes in <html> element
        html.addRule(new MetaTagRule(page));                // all <meta> tags
        html.addRule(new ParameterExtractingRule(page));    // <parameter> blocks
        html.addRule(new ContentBlockExtractingRule(page)); // <content> blocks

        // Capture properties written to documents by MS Office (author, version, company, etc).
        // Note: These properties are from the xml state, not the html state.
        xml.addRule(new MSOfficeDocumentPropertiesRule(page));
    }

}
