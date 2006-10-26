package com.sun.xml.bind.v2.runtime.output;

import com.sun.xml.bind.v2.runtime.AssociationMap;
import com.sun.xml.bind.marshaller.SAX2DOMEx;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 
 * @author Kohsuke Kawaguchi
 */
public final class InPlaceDOMOutput extends SAXOutput {
    private final AssociationMap assoc;

    public InPlaceDOMOutput(Node node, AssociationMap assoc) {
        super(new SAX2DOMEx(node));
        this.assoc = assoc;
        assert assoc!=null;
    }

    private SAX2DOMEx getBuilder() {
        return (SAX2DOMEx)out;
    }

    public void endStartTag() throws SAXException {
        super.endStartTag();

        Object op = nsContext.getCurrent().getOuterPeer();
        if(op!=null)
            assoc.addOuter( getBuilder().getCurrentElement(), op );

        Object ip = nsContext.getCurrent().getInnerPeer();
        if(ip!=null)
            assoc.addInner( getBuilder().getCurrentElement(), ip );
    }
}