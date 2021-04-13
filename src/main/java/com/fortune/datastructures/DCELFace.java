package com.fortune.datastructures;

import java.util.ArrayList;
import java.util.List;

public class DCELFace {
    private DCELEdge outerComponent;
    private List<DCELEdge> innerComponent;
    private DCELSite site;
    private int labelIndex;

    public DCELFace(){
        outerComponent = new DCELEdge();
        innerComponent = new ArrayList<>();

    }

    public DCELEdge getOuterComponent() {
        return outerComponent;
    }

    public void setOuterComponent(DCELEdge outerComponent) {
        this.outerComponent = outerComponent;
    }

    public List<DCELEdge> getInnerComponent() {
        return innerComponent;
    }

    public void setInnerComponent(List<DCELEdge> innerComponent) {
        this.innerComponent = innerComponent;
        if (this.innerComponent == null){
            this.innerComponent = new ArrayList<>();
        }
    }

    public DCELSite getSite() {
        return site;
    }

    public void setSite(DCELSite site) {
        this.site = site;
    }

    public int getLabelIndex() {
        return labelIndex;
    }

    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    @Override
    public String toString(){
        return String.format("f%d", getLabelIndex());
    }
}
