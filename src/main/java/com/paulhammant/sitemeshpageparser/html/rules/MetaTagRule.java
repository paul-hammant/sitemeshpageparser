package com.paulhammant.sitemeshpageparser.html.rules;

import com.paulhammant.sitemeshpageparser.html.BasicRule;
import com.paulhammant.sitemeshpageparser.html.Tag;

public class MetaTagRule extends BasicRule {

    private final PageBuilder page;

    public MetaTagRule(PageBuilder page) {
        super("meta");
        this.page = page;
    }

    public void process(Tag tag) {
        if (tag.hasAttribute("name", false)) {
            page.addProperty("meta." + tag.getAttributeValue("name", false), tag.getAttributeValue("content", false));
        } else if (tag.hasAttribute("http-equiv", false)) {
            page.addProperty("meta.http-equiv." + tag.getAttributeValue("http-equiv", false), tag.getAttributeValue("content", false));
        }
    }
}
