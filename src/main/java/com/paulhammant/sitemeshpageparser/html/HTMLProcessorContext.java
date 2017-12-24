package com.paulhammant.sitemeshpageparser.html;

import com.paulhammant.sitemeshpageparser.SitemeshBuffer;
import com.paulhammant.sitemeshpageparser.SitemeshBufferFragment;

public interface HTMLProcessorContext {

    SitemeshBuffer getSitemeshBuffer();

    State currentState();
    void changeState(State newState);

    void pushBuffer(SitemeshBufferFragment.Builder fragment);
    SitemeshBufferFragment.Builder currentBuffer();
    SitemeshBufferFragment.Builder popBuffer();
}
