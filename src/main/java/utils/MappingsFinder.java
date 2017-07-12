package utils;

import com.google.common.collect.*;
import model.*;
import ontology.GlobalOntology;
import ontology.Namespaces;
import ontology.SourceOntology;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.*;
import org.apache.jena.sparql.algebra.*;
import org.apache.jena.sparql.algebra.op.OpBGP;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import scala.Tuple2;
import scala.Tuple3;
import java.util.*;

/**
 * Created by snadal on 18/06/17.
 */
public class MappingsFinder extends OpVisitorBase {

    private Dataset O;

    public MappingsFinder(Dataset ds) {
        O = ds;
    }

    public void walker(Op op) {
        OpWalker.walk(op, this);
    }

    /**
     * This method returns the corresponding attribute to the feature (following the sameAs relationship from global
     * to source levels) in the given wrapper
     */
    private String getAttributeFromSourceLevel(String URI_Feature, Wrapper w) {
        String attribute = null;
        String query = main.Main.prefixes + "SELECT ?a FROM <"+Namespaces.sup.val()+"bdi_ontology> WHERE { " +
                "?a <"+ Namespaces.owl.val()+"sameAs"+"> <"+URI_Feature+"> . " +
                "<"+w.getName()+"> <"+ SourceOntology.HAS_ATTRIBUTE.val()+"> ?a }";
        try (QueryExecution qExec = QueryExecutionFactory.create(QueryFactory.create(query), O)) {
            ResultSet rs = qExec.execSelect();
            attribute = rs.hasNext() ? rs.next().get("a").toString() : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attribute;
    }

    /**
     * This method converts the WHERE clause of a SPARQL query (including variables and rdf:type properties)
     * to a graph pattern (i.e., no variables and no rdf:type)
     */
    private List<Triple> getGraphPattern(List<Triple> triples) {
        // First get the types of the variables
        HashMap<String, String> variables = Maps.newHashMap();
        for (final Triple triple : triples) {
            if (triple.getMatchPredicate().getURI().equals(Namespaces.rdf.val() + "type")) {
                variables.put(triple.getMatchSubject().getName(), triple.getMatchObject().getURI());
            }
        }
        //Now let's contruct the graph pattern for the query (i.e., the triples without rdf:type and variables)
        List<Triple> graphPattern = Lists.newArrayList();
        for (final Triple triple : triples) {
            if (!triple.getMatchPredicate().getURI().equals(Namespaces.rdf.val() + "type")) {
                graphPattern.add(Utils.createTripleObject(variables.get(triple.getMatchSubject().getName()),triple.getMatchPredicate().getURI(),variables.get(triple.getMatchObject().getName())));
            }
        }
        return graphPattern;
    }

    /**
     * This method returns the list of wrappers (i.e., named graphs) that contain a given triple
     */
    private Set<Wrapper> getWrappersPerTriple(Triple t) {
        // There are other methods to build SPARQL queries in Java (https://jena.apache.org/documentation/query/manipulating_sparql_using_arq.html)
        // but for simplicity here we encode directly the triples in a String
        String composed = "<" + t.getMatchSubject().getURI() + ">"+ " <" + t.getMatchPredicate().getURI() + "> <" + t.getMatchObject().getURI() + ">";
        String query = main.Main.prefixes + "SELECT ?g WHERE { GRAPH ?g { " + composed + " } }";
        try (QueryExecution qExec = QueryExecutionFactory.create(QueryFactory.create(query), O)) {
            ResultSet rs = qExec.execSelect();
            Set<Wrapper> wrappers = Sets.newHashSet();
            while (rs.hasNext()) {
                String named_graph = rs.next().get("g").toString();
                if (!named_graph.contains("bdi_ontology")) {
                    wrappers.add(new Wrapper(named_graph));
                }
            }
            return wrappers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void visit(OpBGP opBGP) {
        // This first part is necessary as the Algebra class gives us variables in the
        // WHERE clause, so we need to convert it to proper triples
        List<Triple> graphPattern = getGraphPattern(opBGP.getPattern().getList());
        /**
         * The actual algorithm starts here
         */
        //Extract the target schema of the query (i.e., those triples with p == hasFeature)
        Set<String> targetSchema = Sets.newHashSet();
        graphPattern.forEach(t -> {
            if (t.getPredicate().getLocalName().equals("hasFeature")) {
                targetSchema.add(t.getObject().getURI());
            }
        });

        // We need to add ALL features that are IDs for each Concept in the graph pattern
        // if they are not in the target schema, they will finally be projected out
        Set<Triple> idTriplestoAdd = Sets.newHashSet();
        Set<String> conceptsInPattern = Sets.newHashSet();
        for (Triple t : graphPattern) {
            // We assume that all non-hasFeature edges are between concepts
            if (!t.getPredicate().getLocalName().equals("hasFeature")) {
                conceptsInPattern.add(t.getSubject().getURI());
                conceptsInPattern.add(t.getObject().getURI());
            }
        }
        // Now let's ask for the IDs of concepts
        conceptsInPattern.forEach(c -> {
            // This query asks for features that are subclass of sc:identifier
            String query = main.Main.prefixes + "SELECT ?t FROM <"+Namespaces.sup.val()+"bdi_ontology> WHERE { " +
                    "<"+c+"> <"+ GlobalOntology.HAS_FEATURE.val()+"> ?t . " +
                    "?t <"+ Namespaces.rdfs.val()+"subClassOf> <"+ Namespaces.sc.val()+"identifier" + "> }";
            try (QueryExecution qExec = QueryExecutionFactory.create(QueryFactory.create(query), O)) {
                ResultSet rs = qExec.execSelect();
                while (rs.hasNext()) {
                    idTriplestoAdd.add(Utils.createTripleObject(c,GlobalOntology.HAS_FEATURE.val(), rs.next().get("t").toString()));
                }
            }
        });
        // Final check to see that they are not already considered (i.e., they are not already in the target schema)
        idTriplestoAdd.forEach(t -> {
            if (!targetSchema.contains(t.getObject().getURI())) {
                graphPattern.add(t);
            }
        });

        // Find for each triple in the graph pattern which wrappers contribute to it
        // as each wrapper has a named graph, we look for it with a SPARQL query
        List<Tuple2<Triple,Set<Wrapper>>> wrappersPerTriple = Lists.newArrayList();
        for (Triple t : graphPattern) {
            wrappersPerTriple.add(new Tuple2<>(t,getWrappersPerTriple(t)));
        }
        // Now, we want to group for each concept its features (only those requested in targetSchema), and
        // for each feature we want its list of wrappers and the candidate wrappers to join concepts

        // To this end we use a graph structure (JGraphT). Concepts contain list of Features, which contain lists of Wrappers.
        // Edges contain list of wrappers too.

        // It is likely that this can be widely reduced in pseudocode notation
        DirectedGraph<Concept,WrapperEdge> G = new DefaultDirectedGraph<Concept, WrapperEdge>(WrapperEdge.class);
        // This map tells us, for each concept its features and for each feature its wrappers
        Map<String,Set<Tuple2<String,Set<Wrapper>>>> featuresPerConcept = Maps.newHashMap();
        // This list tells us, the path between concepts and which wrappers are in the edge
        List<Tuple3<String,Set<Wrapper>,String>> conceptsPath = Lists.newArrayList();
        wrappersPerTriple.forEach(t -> {
            if (t._1().getPredicate().getLocalName().equals("hasFeature")) {
                if (!featuresPerConcept.containsKey(t._1().getSubject().getURI())) {
                    featuresPerConcept.put(t._1().getSubject().getURI(),Sets.newHashSet());
                }
                featuresPerConcept.get(t._1().getSubject().getURI()).add(new Tuple2<>(t._1().getObject().getURI(),t._2()));
            } else {
                if (!featuresPerConcept.containsKey(t._1().getSubject().getURI())) {
                    featuresPerConcept.put(t._1().getSubject().getURI(),Sets.newHashSet());
                }
                conceptsPath.add(new Tuple3<>(t._1().getSubject().getURI(),t._2(),t._1().getObject().getURI()));
            }
        });
        // Now lets make the conversion to classes and add the data to the graph
        Map<String,Concept> conceptsURIs = Maps.newHashMap();
        featuresPerConcept.keySet().forEach(strConcept -> {
            Concept c = new Concept(strConcept);
            // Check if the feature is ID, if it is so we will consider it separately
            // in the Concept class for easier handling later
            final String URIforIDFeature ;
            String query = main.Main.prefixes + "SELECT ?t FROM <"+Namespaces.sup.val()+"bdi_ontology> WHERE { " +
                    "<"+c.getURI()+"> <"+ GlobalOntology.HAS_FEATURE.val()+"> ?t . " +
                    "?t <"+ Namespaces.rdfs.val()+"subClassOf> <"+ Namespaces.sc.val()+"identifier" + "> }";
            try (QueryExecution qExec = QueryExecutionFactory.create(QueryFactory.create(query), O)) {
                ResultSet rs = qExec.execSelect();
                URIforIDFeature = rs.hasNext() ? rs.next().get("t").toString() : null;
                c.setURI_idFeature(URIforIDFeature);
            }
            featuresPerConcept.get(strConcept).forEach(strFeature -> {
                Feature f = new Feature(strFeature._1());
                strFeature._2().forEach(w -> f.getWrappers().add(w));
                c.getFeatures().add(f);
            });
            G.addVertex(c);
            conceptsURIs.put(c.getURI(),c);
        });
        // List of wrappers in the edge between concepts
        conceptsPath.forEach(p -> G.addEdge(conceptsURIs.get(p._1()),conceptsURIs.get(p._3()),new WrapperEdge(Lists.newArrayList(p._2()))));

        // Once the graph is ready we can start to look for all ways to access the wrappers.
        // For each concept, we perform the cartesian product of the list of wrappers in its features, this will indicate
        // all possible ways to project a feature from the wrappers, but we must ensure that the wrappers can be joined
        GraphIterator<Concept,WrapperEdge> iterator = new BreadthFirstIterator<Concept, WrapperEdge>(G);
        // This structure will tell us, for each concept, the associated (partial) conjunctive queries that are valid
        List<Tuple2<Concept,List<ConjunctiveQuery>>> partialQueriesPerConcept = Lists.newArrayList();
        while (iterator.hasNext()) {
            Concept c = iterator.next();
            Map<Wrapper, ConjunctiveQuery> m = Maps.newHashMap();
            for (Feature f : c.getFeatures()) {
                for (Wrapper w : f.getWrappers()) {
                    if (!m.containsKey(w)) m.put(w,new ConjunctiveQuery());
                    m.get(w).getSources().add(w);
                    m.get(w).getProjections().add(new Tuple2<>(f.getURI(),new Attribute(w,Utils.nn(getAttributeFromSourceLevel(f.getURI(),w)))));
                }
            }
            partialQueriesPerConcept.add(new Tuple2<>(c,Lists.newArrayList(m.values())));
        }
        /**
        while (iterator.hasNext()) {
            Concept c = iterator.next();
            List<ConjunctiveQuery> partialQueries = Lists.newArrayList();
            List<Set<Wrapper>> wrappersForC = Lists.newArrayList();
            c.getFeatures().forEach(f -> wrappersForC.add(Sets.newHashSet(f.getWrappers())));
            // Execute the cartesian product for the list of wrappers per feature
            if (!wrappersForC.isEmpty()) {
                Sets.cartesianProduct(wrappersForC).forEach(cp -> {
                    Set<Wrapper> wrappersToJoin = Sets.newHashSet();
                    cp.forEach(w -> wrappersToJoin.add(w));

                    // To check whether I can join a set of wrappers, I only need to check that ALL of them appear in the set of the
                    // wrappers for the feature that is ID. If this Concept does not have an ID Feature we are doomed and this expression is not valid
                    if (c.getURI_idFeature() == null || wrappersToJoin.size() == 1) {
                        ConjunctiveQuery Q = new ConjunctiveQuery();
                        Q.setSources(wrappersToJoin);
                        for (int i = 0; i < cp.size(); ++i) {
                            Q.getProjections().add(new Tuple2<>(c.getFeatures().get(i).getURI(),
                                    new Attribute(cp.get(i),Utils.nn(getAttributeFromSourceLevel(c.getFeatures().get(i).getURI(), cp.get(i))))));
                        }
                        partialQueries.add(Q);
                    } else {
                        if (getWrappersPerTriple(Utils.createTripleObject(c.getURI(), GlobalOntology.HAS_FEATURE.val(), c.getURI_idFeature())).containsAll(wrappersToJoin)) {
                            ConjunctiveQuery Q = new ConjunctiveQuery();
                            List<Wrapper> listOfWrappersToJoin = Lists.newArrayList(wrappersToJoin);
                            Q.setSources(wrappersToJoin);
                            Wrapper current = listOfWrappersToJoin.get(0);
                            for (int i = 1; i < listOfWrappersToJoin.size(); ++i) {
                                Wrapper next = listOfWrappersToJoin.get(i);
                                Q.getSelections().add(new Tuple2<>(
                                        new Attribute(current,getAttributeFromSourceLevel(c.getURI_idFeature(), current)),
                                        new Attribute(next,getAttributeFromSourceLevel(c.getURI_idFeature(), next))));
                                current = next;
                            }
                            for (int i = 0; i < cp.size(); ++i) {
                                Q.getProjections().add(new Tuple2<>(Utils.nn(c.getFeatures().get(i).getURI()),
                                        new Attribute(cp.get(i),getAttributeFromSourceLevel(c.getFeatures().get(i).getURI(), cp.get(i)))));
                            }
                            partialQueries.add(Q);
                        }
                    }
                });
                partialQueriesPerConcept.add(new Tuple2<>(c, partialQueries));
            }
        }
        **/
        // So, now we have the partial CQs to fetch the attributes at the concept level.
        // The next step is to find the inter-concept combinations of CQs that can be joined
        // Recall we have our graph structure that tells us which wrappers can be used to join concepts, hence we need to
        // see for all combinations of partial queries in pairs of concepts (e.g., C1-C2), whether any of the selected wrappers
        // is in the list of wrappers in the edge between C1 and C2.
        Tuple2<Concept,List<ConjunctiveQuery>> current = partialQueriesPerConcept.get(0);
        for (int i = 1; i < partialQueriesPerConcept.size(); ++i) {

            Tuple2<Concept,List<ConjunctiveQuery>> next = partialQueriesPerConcept.get(i);
            Set<List<ConjunctiveQuery>> cartesianProduct = Sets.cartesianProduct(Sets.newHashSet(current._2()),Sets.newHashSet(next._2()));

            List<ConjunctiveQuery> combined = Lists.newArrayList();
            for (List<ConjunctiveQuery> cp : cartesianProduct) {
                ConjunctiveQuery cq = new ConjunctiveQuery(
                        Sets.newHashSet(Sets.union(cp.get(0).getProjections(),cp.get(1).getProjections())),
                        Sets.newHashSet(Sets.union(cp.get(0).getSelections(),cp.get(1).getSelections())),
                        Sets.newHashSet(Sets.union(cp.get(0).getSources(),cp.get(1).getSources())));
                // a) They share a source, no need to do anything

                // b) They don't share sources, must join them using IDs
                if (Collections.disjoint(cp.get(0).getSources(),cp.get(1).getSources())) {
                    // ID is in "current", I have to try all ways to obtain the FK fromedd
                    // "next" and join it with the ID I already projected from "currents"
                    if (G.getAllEdges(current._1(), next._1()).size() > 0) {
                        List<Wrapper> listOfWrappersForConcepts = Iterables.getOnlyElement(G.getAllEdges(current._1(), next._1())).getWrappers();
                        for (Wrapper w : listOfWrappersForConcepts) {
                            for (Tuple2<String,Attribute> p : cq.getProjections()) {
                                if (p._1().equals(current._1().getURI_idFeature())) {
                                    cq.getSelections().add(new Tuple2<>(new Attribute(w,getAttributeFromSourceLevel(current._1().getURI_idFeature(), w)),
                                            new Attribute(p._2().getWrapper(),getAttributeFromSourceLevel(current._1().getURI_idFeature(),p._2().getWrapper()))));
                                }
                            }
                        }
                    // ID is in "next", I have to try all ways to obtain the FK from "current" and join it with the ID I already projected from "next"
                    } else if (G.getAllEdges(next._1(), current._1()).size() > 0) {
                        List<Wrapper> listOfWrappersForConcepts = Iterables.getOnlyElement(G.getAllEdges(next._1(), current._1())).getWrappers();
                        for (Wrapper w : listOfWrappersForConcepts) {
                            for (Tuple2<String,Attribute> p : cq.getProjections()) {
                                if (p._1().equals(next._1().getURI_idFeature())) {
                                    cq.getSelections().add(new Tuple2<>(new Attribute(w,getAttributeFromSourceLevel(next._1().getURI_idFeature(), w)),
                                            new Attribute(p._2().getWrapper(),getAttributeFromSourceLevel(next._1().getURI_idFeature(),p._2().getWrapper()))));
                                }
                            }
                        }
                    }
                }
                combined.add(cq);
            }
            current = new Tuple2<>(next._1(),combined);
        }
        List<ConjunctiveQuery> finalList = Lists.newArrayList();
        current._2().forEach(cq -> {
            Set<String> features = Sets.newHashSet();
            cq.getProjections().forEach(proj -> {
                features.add(proj._1());
            });
            if (features.containsAll(targetSchema)) finalList.add(cq);
        });

        // Final, output the UNION of CQs
        System.out.println(finalList.get(0).relationalAlgebraPrint());
        for (int i = 1; i < finalList.size(); ++i) {
            System.out.println(" UNION "+finalList.get(i).relationalAlgebraPrint());
        }
        System.out.println("");
    }

}
