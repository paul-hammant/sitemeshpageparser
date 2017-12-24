package com.paulhammant.sitemeshpageparser.html.rules;

import com.paulhammant.sitemeshpageparser.html.BasicRule;
import com.paulhammant.sitemeshpageparser.html.Tag;

public class ParameterExtractingRule extends BasicRule{

    private final PageBuilder page;

    public ParameterExtractingRule(PageBuilder page) {
        super("parameter");
        this.page = page;
    }

    public void process(Tag tag) {
        context.currentBuffer().delete(tag.getPosition(), tag.getLength());
        page.addProperty("page." + tag.getAttributeValue("name", false), tag.getAttributeValue("value", false));
    }
}
