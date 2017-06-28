package utils;

import main.Main;
import ontology.GlobalOntology;
import ontology.Namespaces;
import ontology.SourceOntology;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;

/**
 * Created by snadal on 6/06/17.
 */
public class SimpleRunningExample {

    public static Dataset getOntology() {
        Dataset dataset = TDBFactory.createDataset();
        dataset.begin(ReadWrite.WRITE);

        /*Model model = dataset.getNamedModel("scenarios.SUPERSEDEWithVersions");*/
        OntModel o = ModelFactory.createOntologyModel();

        /*
        Global Ontology
         */
        // Concepts
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        // Relations between concepts
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1", Namespaces.sup.val()+"hasC2", Namespaces.sup.val()+"C2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2", Namespaces.sup.val()+"hasC3", Namespaces.sup.val()+"C3");

        // Features
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1_id", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1_F1", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1_F2", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1_F3", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2_id", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2_F1", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2_F2", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2_F3", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3_id", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3_F1", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3_F2", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3_F3", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1_id", Namespaces.rdfs.val()+"subClassOf", Namespaces.sc.val()+"identifier");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2_id", Namespaces.rdfs.val()+"subClassOf", Namespaces.sc.val()+"identifier");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3_id", Namespaces.rdfs.val()+"subClassOf", Namespaces.sc.val()+"identifier");

        // hasFeature edges
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_F1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_F2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_F3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_F1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_F2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_F3");

        /*
        Source Ontology
         */
        // Data Sources
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1", Namespaces.rdf.val()+"type", SourceOntology.DATA_SOURCE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2", Namespaces.rdf.val()+"type", SourceOntology.DATA_SOURCE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3", Namespaces.rdf.val()+"type", SourceOntology.DATA_SOURCE.val());

        // Wrappers
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_V2", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_V3", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W1_V2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W1_V3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W3");

        // Attributes
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_id", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_id_v2", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a1", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a1_v2", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a2", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a3", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a3_v2", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_fk", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_fk_v2", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());


        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a_id", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a1", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a2", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a3", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a_fk", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a_id", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a1", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a2", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a3", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());

        // hasAttribute edges
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a_fk");

        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_V2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a_id_v2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_V2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a1_v2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_V2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_V2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a3_v2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_V2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/a_fk_v2");

        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D2/a_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D2/a1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D2/a2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D2/a3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D2/a_fk");


        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D3/a_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D3/a1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D3/a2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D3/a3");


        /*
        sameAs relationships between Source and Global ontologies
         */
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_id", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C1_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_id_v2", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C1_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a1", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C1_F1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a1_v2", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C1_F1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a2", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C1_F2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a3", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C1_F3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a3_v2", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C1_F3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_fk", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C2_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/a_fk_v2", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C2_id");


        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a_id", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C2_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a1", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C2_F1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a2", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C2_F2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a3", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C2_F3");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/a_fk", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C3_id");

        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a_id", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C3_id");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a1", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C3_F1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a2", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C3_F2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/a3", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"C3_F3");

        dataset.addNamedModel(Namespaces.sup.val()+"bdi_ontology",o);

        /*
        namedGraphs for mappings
         */
        OntModel w1 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(w1, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_id");
        RDFUtil.addTriple(w1, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F1");
        RDFUtil.addTriple(w1, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F2");
        RDFUtil.addTriple(w1, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F3");
        RDFUtil.addTriple(w1, Namespaces.sup.val()+"C1", Namespaces.sup.val()+"hasC2", Namespaces.sup.val()+"C2");
        RDFUtil.addTriple(w1, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_id");
        dataset.addNamedModel(Namespaces.sup.val()+"W1",w1);

        OntModel w1_v2 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(w1_v2, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_id");
        RDFUtil.addTriple(w1_v2, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F1");
        RDFUtil.addTriple(w1_v2, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F2");
        RDFUtil.addTriple(w1_v2, Namespaces.sup.val()+"C1", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C1_F3");
        RDFUtil.addTriple(w1_v2, Namespaces.sup.val()+"C1", Namespaces.sup.val()+"hasC2", Namespaces.sup.val()+"C2");
        RDFUtil.addTriple(w1_v2, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_id");
        dataset.addNamedModel(Namespaces.sup.val()+"W1_V2",w1_v2);


        OntModel w2 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(w2, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_id");
        RDFUtil.addTriple(w2, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_F1");
        RDFUtil.addTriple(w2, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_F2");
        RDFUtil.addTriple(w2, Namespaces.sup.val()+"C2", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C2_F3");
        RDFUtil.addTriple(w2, Namespaces.sup.val()+"C2", Namespaces.sup.val()+"hasC3", Namespaces.sup.val()+"C3");
        RDFUtil.addTriple(w2, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_id");
        dataset.addNamedModel(Namespaces.sup.val()+"W2",w2);

        OntModel w3 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(w3, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_id");
        RDFUtil.addTriple(w3, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_F1");
        RDFUtil.addTriple(w3, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_F2");
        RDFUtil.addTriple(w3, Namespaces.sup.val()+"C3", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"C3_F3");
        dataset.addNamedModel(Namespaces.sup.val()+"W3",w3);

        return dataset;
    }

    public static String getQuery1() {
        // Lag ratio for SUPERSEDE app
        String Q = Main.prefixes +
                "SELECT ?a ?b ?c ?d ?e ?f \n" +
                "WHERE {\n" +
                "\t?con1 rdf:type sup:C1 .\n" +
                "\t?con1 G:hasFeature ?a .\n" +
                "\t?a rdf:type sup:C1_F1 .\n" +
                "\t?con1 G:hasFeature ?b .\n" +
                "\t?b rdf:type sup:C1_F2 .\n" +
                "\t?con1 G:hasFeature ?c .\n" +
                "\t?c rdf:type sup:C1_F3 ."+
                "\t?con1 sup:hasC2 ?con2 .\n" +
                "\t?con2 rdf:type sup:C2 .\n" +
                "\t?con2 G:hasFeature ?d .\n" +
                "\t?d rdf:type sup:C2_F1 .\n" +
                "\t?con2 G:hasFeature ?e .\n" +
                "\t?e rdf:type sup:C2_F2 .\n" +
                "\t?con2 G:hasFeature ?f .\n" +
                "\t?f rdf:type sup:C2_F3 .\n" +
                "}";
        return Q;
    }
}
