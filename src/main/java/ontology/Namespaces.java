package ontology;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by snadal on 6/06/17.
 */
public enum Namespaces {

    S("http://www.BDIOntology.com/source/"),
    G("http://www.BDIOntology.com/global/"),
    M("http://www.BDIOntology.com/mappings/"),

    owl("http://www.w3.org/2002/07/owl#"),
    rdf("http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
    rdfs("http://www.w3.org/2000/01/rdf-schema#"),
    dct("http://purl.org/dc/terms/"),
    dcat("http://www.w3.org/ns/dcat#"),
    sup("http://www.supersede.eu/"),
    duv("http://www.w3.org/ns/duv#"),
    sc("http://schema.org/");

    private String element;

    Namespaces(String element) {
        this.element = element;
    }

    public String val() {
        return element;
    }
}
