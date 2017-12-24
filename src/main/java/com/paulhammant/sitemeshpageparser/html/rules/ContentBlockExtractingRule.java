package com.paulhammant.sitemeshpageparser.html.rules;

import com.paulhammant.sitemeshpageparser.html.BlockExtractingRule;
import com.paulhammant.sitemeshpageparser.html.Tag;

public class ContentBlockExtractingRule extends BlockExtractingRule {

    private final PageBuilder page;

    private String contentBlockId;

    public ContentBlockExtractingRule(PageBuilder page) {
        super(false, "content");
        this.page = page;
    }

    protected void start(Tag tag) {
        contentBlockId = tag.getAttributeValue("tag", false);
    }

    protected void end(Tag tag) {
        page.addProperty("page." + contentBlockId, getCurrentBufferContent());
    }

}
