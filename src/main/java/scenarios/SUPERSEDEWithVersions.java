package scenarios;

import main.Main;
import ontology.GlobalOntology;
import ontology.Namespaces;
import ontology.SourceOntology;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;
import utils.RDFUtil;

/**
 * Created by snadal on 6/06/17.
 */
public class SUPERSEDEWithVersions {

    public static Dataset getOntology() {
        Dataset dataset = TDBFactory.createDataset();
        dataset.begin(ReadWrite.WRITE);

        /*Model model = dataset.getNamedModel("scenarios.SUPERSEDEWithVersions");*/
        OntModel o = ModelFactory.createOntologyModel();

        /*
        Global Ontology
         */
        // Concepts
        RDFUtil.addTriple(o, Namespaces.sup.val()+"DataGatheringTool", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        RDFUtil.addTriple(o, Namespaces.sc.val()+"SoftwareApplication", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"Monitor", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"FeedbackGathering", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"InfoMonitor", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        RDFUtil.addTriple(o, Namespaces.duv.val()+"UserFeedback", Namespaces.rdf.val()+"type", GlobalOntology.CONCEPT.val());
        // Concept subclasses
        RDFUtil.addTriple(o, Namespaces.sup.val()+"Monitor", Namespaces.rdfs.val()+"subClassOf", Namespaces.sup.val()+"DataGatheringTool");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"FeedbackGathering", Namespaces.rdf.val()+"subClassOf", Namespaces.sup.val()+"DataGatheringTool");
        // Relations between concepts
        RDFUtil.addTriple(o, Namespaces.sc.val()+"SoftwareApplication", Namespaces.sup.val()+"hasMonitor", Namespaces.sup.val()+"Monitor");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"Monitor", Namespaces.sup.val()+"generatesQoS", Namespaces.sup.val()+"InfoMonitor");
        RDFUtil.addTriple(o, Namespaces.sc.val()+"SoftwareApplication", Namespaces.sup.val()+"hasFG", Namespaces.sup.val()+"FeedbackGathering");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"FeedbackGathering", Namespaces.sup.val()+"generatesOpinion", Namespaces.duv.val()+"UserFeedback");

        // Features
        RDFUtil.addTriple(o, Namespaces.sc.val()+"name", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"LagRatio", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"idMonitor", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"idApplication", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"idFeedbackGathering", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.dct.val()+"description", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());
        RDFUtil.addTriple(o, Namespaces.sc.val()+"identifier", Namespaces.rdf.val()+"type", GlobalOntology.FEATURE.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"idMonitor", Namespaces.rdfs.val()+"subClassOf", Namespaces.sc.val()+"identifier");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"idApplication", Namespaces.rdfs.val()+"subClassOf", Namespaces.sc.val()+"identifier");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"idFeedbackGathering", Namespaces.rdfs.val()+"subClassOf", Namespaces.sc.val()+"identifier");

        // hasFeature edges
        RDFUtil.addTriple(o, Namespaces.sc.val()+"SoftwareApplication", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idApplication");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"Monitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idMonitor");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"Monitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sc.val()+"name");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"InfoMonitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"LagRatio");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"FeedbackGathering", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idFeedbackGathering");
        RDFUtil.addTriple(o, Namespaces.duv.val()+"UserFeedback", GlobalOntology.HAS_FEATURE.val(), Namespaces.dct.val()+"description");

        /*
        Source Ontology
         */
        // Data Sources
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1", Namespaces.rdf.val()+"type", SourceOntology.DATA_SOURCE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2", Namespaces.rdf.val()+"type", SourceOntology.DATA_SOURCE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3", Namespaces.rdf.val()+"type", SourceOntology.DATA_SOURCE.val());

        // Wrappers
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_1", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_2", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", Namespaces.rdf.val()+"type", SourceOntology.WRAPPER.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W1_1");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W1_2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W2");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3", SourceOntology.HAS_WRAPPER.val(), Namespaces.sup.val()+"W3");

        // Attributes
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/lagRatio", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/VoDMonitorId", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/lag_ratio", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/MonitorId", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/TargetApp", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/FeedbackId", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());

        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/feedbackGatheringId", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/tweet", Namespaces.rdf.val()+"type", SourceOntology.ATTRIBUTE.val());

        // hasAttribute edges
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_1", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/lagRatio");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_1", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/VoDMonitorId");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/lag_ratio");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W1_2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D1/VoDMonitorId");

        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D3/MonitorId");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D3/TargetApp");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W3", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D3/FeedbackId");

        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D2/feedbackGatheringId");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"W2", SourceOntology.HAS_ATTRIBUTE.val(), Namespaces.sup.val()+"D2/tweet");

        /*
        sameAs relationships between Source and Global ontologies
         */
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/lagRatio", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"LagRatio");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/lag_ratio", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"LagRatio");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D1/VoDMonitorId", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"idMonitor");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/MonitorId", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"idMonitor");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/TargetApp", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"idApplication");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D3/FeedbackId", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"idFeedbackGathering");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/feedbackGatheringId", Namespaces.owl.val()+"sameAs", Namespaces.sup.val()+"idFeedbackGathering");
        RDFUtil.addTriple(o, Namespaces.sup.val()+"D2/tweet", Namespaces.owl.val()+"sameAs", Namespaces.dct.val()+"description");

        dataset.addNamedModel(Namespaces.sup.val()+"bdi_ontology",o);

        /*
        namedGraphs for mappings
         */
        OntModel m_w1_1 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(m_w1_1, Namespaces.sup.val()+"InfoMonitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"LagRatio");
        RDFUtil.addTriple(m_w1_1, Namespaces.sup.val()+"Monitor", Namespaces.sup.val()+"generatesQoS", Namespaces.sup.val()+"InfoMonitor");
        RDFUtil.addTriple(m_w1_1, Namespaces.sup.val()+"Monitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idMonitor");
        dataset.addNamedModel(Namespaces.sup.val()+"W1_1",m_w1_1);

        OntModel m_w1_2 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(m_w1_2, Namespaces.sup.val()+"InfoMonitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"LagRatio");
        RDFUtil.addTriple(m_w1_2, Namespaces.sup.val()+"Monitor", Namespaces.sup.val()+"generatesQoS", Namespaces.sup.val()+"InfoMonitor");
        RDFUtil.addTriple(m_w1_2, Namespaces.sup.val()+"Monitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idMonitor");
        dataset.addNamedModel(Namespaces.sup.val()+"W1_2",m_w1_2);

        OntModel m_w2 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(m_w2,Namespaces.duv.val()+"UserFeedback", GlobalOntology.HAS_FEATURE.val(), Namespaces.dct.val()+"description");
        RDFUtil.addTriple(m_w2,Namespaces.sup.val()+"FeedbackGathering", Namespaces.sup.val()+"generatesOpinion", Namespaces.duv.val()+"UserFeedback");
        RDFUtil.addTriple(m_w2,Namespaces.sup.val()+"FeedbackGathering", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idFeedbackGathering");
        dataset.addNamedModel(Namespaces.sup.val()+"W2",m_w2);

        OntModel m_w3 = ModelFactory.createOntologyModel();
        RDFUtil.addTriple(m_w3,Namespaces.sc.val()+"SoftwareApplication", Namespaces.sup.val()+"hasMonitor", Namespaces.sup.val()+"Monitor");
        RDFUtil.addTriple(m_w3,Namespaces.sc.val()+"SoftwareApplication", Namespaces.sup.val()+"hasFG", Namespaces.sup.val()+"FeedbackGathering");
        RDFUtil.addTriple(m_w3,Namespaces.sc.val()+"SoftwareApplication", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idApplication");
        RDFUtil.addTriple(m_w3,Namespaces.sup.val()+"Monitor", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idMonitor");
        RDFUtil.addTriple(m_w3,Namespaces.sup.val()+"FeedbackGathering", GlobalOntology.HAS_FEATURE.val(), Namespaces.sup.val()+"idFeedbackGathering");
        dataset.addNamedModel(Namespaces.sup.val()+"W3",m_w3);

        return dataset;
    }

    public static String getQuery1() {
        // Lag ratio for SUPERSEDE app
        String Q = Main.prefixes +
                "SELECT ?w ?t\n" +
                "WHERE {\n" +
                "\t?t rdf:type sup:LagRatio .\n" +
                "\t?x G:hasFeature ?t .\n" +
                "\t?x rdf:type sup:InfoMonitor .\n" +
                "\t?y sup:generatesQoS ?x .\n" +
                "\t?y rdf:type sup:Monitor .\n" +
                "\t?z sup:hasMonitor ?y .\n" +
                "\t?z rdf:type sc:SoftwareApplication .\n" +
                "\t?z G:hasFeature ?w .\n" +
                "\t?w rdf:type sup:idApplication .\n" +
                "\tFILTER (?w = \"SUPERSEDE\")\n" +
                "}";
        return Q;
    }
}
