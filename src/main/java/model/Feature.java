package model;

import com.google.common.collect.Lists;
import utils.Utils;

import java.util.List;

/**
 * Created by snadal on 25/06/17.
 */
public class Feature {

    private String URI;
    private List<Wrapper> wrappers;

    public Feature(String URI) {
        this.URI = URI;
        wrappers = Lists.newArrayList();
    }


    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public List<Wrapper> getWrappers() {
        return wrappers;
    }

    public void setWrappers(List<Wrapper> wrappers) {
        this.wrappers = wrappers;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "URI='" + Utils.nn(URI) + '\'' +
                ", wrappers=" + wrappers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature)) return false;

        Feature feature = (Feature) o;

        if (getURI() != null ? !getURI().equals(feature.getURI()) : feature.getURI() != null) return false;
        return getWrappers() != null ? getWrappers().equals(feature.getWrappers()) : feature.getWrappers() == null;
    }

    @Override
    public int hashCode() {
        int result = getURI() != null ? getURI().hashCode() : 0;
        result = 31 * result + (getWrappers() != null ? getWrappers().hashCode() : 0);
        return result;
    }
}
