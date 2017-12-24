package com.paulhammant.sitemeshpageparser.html.rules;

import com.paulhammant.sitemeshpageparser.SitemeshBufferFragment;
import com.paulhammant.sitemeshpageparser.html.BasicRule;
import com.paulhammant.sitemeshpageparser.html.Tag;

public class BodyTagRule extends BasicRule {

    private final PageBuilder page;
    private final SitemeshBufferFragment.Builder body;

    public BodyTagRule(PageBuilder page, SitemeshBufferFragment.Builder body) {
        super("body");
        this.page = page;
        this.body = body;
    }

    public void process(Tag tag) {
        if (tag.getType() == Tag.OPEN || tag.getType() == Tag.EMPTY) {
            context.currentBuffer().setStart(tag.getPosition() + tag.getLength());
            for (int i = 0; i < tag.getAttributeCount(); i++) {
                page.addProperty("body." + tag.getAttributeName(i), tag.getAttributeValue(i));
            }
            body.markStart(tag.getPosition() + tag.getLength());
        } else {
            body.end(tag.getPosition());
            context.pushBuffer(SitemeshBufferFragment.builder()); // unused buffer: everything after </body> is discarded.
        }
    }

}
