package utils;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.rdf.model.impl.ResourceImpl;

/**
 * Created by snadal on 28/06/17.
 */
public class Utils {

    // Short name
    public static String nn(String s) {
        return noNamespace(s);
    }

    public static String noNamespace(String s) {
        return s.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#","")
                .replace("http://www.supersede.eu/","")
                .replace("http://www.BDIOntology.com/global/","")
                .replace("http://schema.org/","")
                .replace("http://www.BDIOntology.com/source/","");
    }

    public static Triple createTripleObject(String s, String p, String o) {
        return new Triple(new ResourceImpl(s).asNode(),
                new PropertyImpl(p).asNode(),
                new ResourceImpl(o).asNode());
    }

}
