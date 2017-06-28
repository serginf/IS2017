package main;

import org.apache.jena.query.*;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import scenarios.SUPERSEDE;
import scenarios.SUPERSEDEWithVersions;
import utils.MappingsFinder;
import utils.SimpleRunningExample;

/**
 * Created by snadal on 6/06/17.
 */
public class Main {

    public static String prefixes =
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "PREFIX sup: <http://www.supersede.eu/> " +
            "PREFIX G: <http://www.BDIOntology.com/global/> " +
            "PREFIX sc: <http://schema.org/> ";

    public static void main(String[] args) {
        //Dataset O = SUPERSEDE.getOntology();
        //String query = SUPERSEDE.getQuery1();

        //Dataset O = SUPERSEDEWithVersions.getOntology();
        //String query = SUPERSEDEWithVersions.getQuery1();

        Dataset O = SimpleRunningExample.getOntology();
        String query = SimpleRunningExample.getQuery1();

        Query q = QueryFactory.create(query);

        Op op = Algebra.compile(q);

        MappingsFinder mf = new MappingsFinder(O);
        mf.walker(op);

    }
}
