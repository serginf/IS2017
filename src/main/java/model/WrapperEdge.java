package model;

import org.jgrapht.graph.DefaultEdge;

import java.util.List;

/**
 * Created by snadal on 26/06/17.
 */
public class WrapperEdge<V> extends DefaultEdge {

        private List<Wrapper> wrappers;

        public WrapperEdge(List<Wrapper> wrappers) {
            this.wrappers = wrappers;
        }

    public List<Wrapper> getWrappers() {
        return wrappers;
    }

    public void setWrappers(List<Wrapper> wrappers) {
        this.wrappers = wrappers;
    }

    @Override
    public String toString() {
        return "WrapperEdge{" +
                "wrappers=" + wrappers +
                '}';
    }
}
