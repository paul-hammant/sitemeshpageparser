package com.paulhammant.sitemeshpageparser.html.rules;

import com.paulhammant.sitemeshpageparser.SitemeshBuffer;
import com.paulhammant.sitemeshpageparser.SitemeshBufferFragment;
import com.paulhammant.sitemeshpageparser.html.BlockExtractingRule;

public class HeadExtractingRule extends BlockExtractingRule {

    private final SitemeshBufferFragment.Builder head;

    public HeadExtractingRule(SitemeshBufferFragment.Builder head) {
        super(false, "head");
        this.head = head;
    }

    protected SitemeshBufferFragment.Builder createBuffer(SitemeshBuffer sitemeshBuffer) {
        return head;
    }

}
