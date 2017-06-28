package model;

import com.google.common.collect.Lists;
import utils.Utils;

import java.util.List;

/**
 * Created by snadal on 25/06/17.
 */
public class Concept {

    private String URI;
    // idFeature might be null when a concept has no id
    private String URI_idFeature;
    private List<Feature> features;

    public Concept(String URI) {
        this.URI = URI;
        URI_idFeature = null;
        features = Lists.newArrayList();
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getURI_idFeature() {
        return URI_idFeature;
    }

    public void setURI_idFeature(String URI_idFeature) {
        this.URI_idFeature = URI_idFeature;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "Concept{" +
                "URI='" + Utils.nn(URI) + '\'' +
                ", features=" + features +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Concept)) return false;

        Concept concept = (Concept) o;

        if (getURI() != null ? !getURI().equals(concept.getURI()) : concept.getURI() != null) return false;
        if (getURI_idFeature() != null ? !getURI_idFeature().equals(concept.getURI_idFeature()) : concept.getURI_idFeature() != null)
            return false;
        return getFeatures() != null ? getFeatures().equals(concept.getFeatures()) : concept.getFeatures() == null;
    }

    @Override
    public int hashCode() {
        int result = getURI() != null ? getURI().hashCode() : 0;
        result = 31 * result + (getURI_idFeature() != null ? getURI_idFeature().hashCode() : 0);
        result = 31 * result + (getFeatures() != null ? getFeatures().hashCode() : 0);
        return result;
    }
}
