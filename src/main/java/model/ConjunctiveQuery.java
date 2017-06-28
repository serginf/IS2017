package model;

import com.google.common.collect.Sets;
import scala.Tuple2;
import utils.Utils;

import java.util.Set;

/**
 * Created by snadal on 28/06/17.
 */
public class ConjunctiveQuery {

    public ConjunctiveQuery() {
        this.projections = Sets.newHashSet();
        this.selections = Sets.newHashSet();
        this.sources = Sets.newHashSet();
    }

    public ConjunctiveQuery(Set<Tuple2<String, Attribute>> projections, Set<Tuple2<Attribute,Attribute>> selections, Set<Wrapper> sources) {
        this.projections = projections;
        this.selections = selections;
        this.sources = sources;
    }

    // Tuple2 because we'll encode Feature (in G) and Attribute (in S)
    private Set<Tuple2<String,Attribute>> projections;
    private Set<Tuple2<Attribute,Attribute>> selections;
    private Set<Wrapper> sources;

    public Set<Tuple2<String, Attribute>> getProjections() {
        return projections;
    }

    public void setProjections(Set<Tuple2<String, Attribute>> projections) {
        this.projections = projections;
    }

    public Set<Tuple2<Attribute, Attribute>> getSelections() {
        return selections;
    }

    public void setSelections(Set<Tuple2<Attribute, Attribute>> selections) {
        this.selections = selections;
    }

    public Set<Wrapper> getSources() {
        return sources;
    }

    public void setSources(Set<Wrapper> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "ConjunctiveQuery{" +
                "projections=" + projections +
                ", selections=" + selections +
                ", sources=" + sources +
                '}';
    }

    public String relationalAlgebraPrint() {
        String out = "[";
        for (Tuple2<String,Attribute> p : projections) {
            out += Utils.nn(p._2().getWrapper().getName())+"."+ Utils.nn(p._2().getURI()) + ", ";
        }
        out = out.substring(0,out.length()-2);
        out += "(";
        for (Tuple2<Attribute,Attribute> a : selections) {
            out += Utils.nn(a._1().getWrapper().getName())+"."+ Utils.nn(a._1().getURI()) + "=" +
                   Utils.nn(a._2().getWrapper().getName())+"."+ Utils.nn(a._2().getURI()) + ", ";
        }
        out = out.substring(0,out.length()-2);
        out += "(";
        for (Wrapper w : sources) {
            out += Utils.nn(w.getName())+", ";
        }
        out = out.substring(0,out.length()-2);
        out += "))";
        out += "]";

        return out;
    }
}
