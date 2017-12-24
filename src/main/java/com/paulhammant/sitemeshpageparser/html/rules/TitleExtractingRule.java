package com.paulhammant.sitemeshpageparser.html.rules;

import com.paulhammant.sitemeshpageparser.html.BlockExtractingRule;
import com.paulhammant.sitemeshpageparser.html.Tag;

public class TitleExtractingRule extends BlockExtractingRule {

    private final PageBuilder page;

    private boolean seenTitle;

    public TitleExtractingRule(PageBuilder page) {
        super(false, "title");
        this.page = page;
    }

    protected void end(Tag tag) {
        if (!seenTitle) {
            page.addProperty("title", getCurrentBufferContent());
            seenTitle = true;
        }
    }
}
