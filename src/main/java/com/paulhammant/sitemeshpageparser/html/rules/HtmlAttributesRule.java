package com.paulhammant.sitemeshpageparser.html.rules;

import com.paulhammant.sitemeshpageparser.html.BasicRule;
import com.paulhammant.sitemeshpageparser.html.Tag;

public class HtmlAttributesRule extends BasicRule{

    private final PageBuilder page;

    public HtmlAttributesRule(PageBuilder page) {
        super("html");
        this.page = page;
    }

    public void process(Tag tag) {
        if (tag.getType() == Tag.OPEN) {
            context.currentBuffer().markStart(tag.getPosition() + tag.getLength());
            for (int i = 0; i < tag.getAttributeCount(); i++) {
                page.addProperty(tag.getAttributeName(i), tag.getAttributeValue(i));
            }
        } else {
            context.currentBuffer().end(tag.getPosition());
        }
    }

}
