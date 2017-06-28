package model;

/**
 * Created by snadal on 28/06/17.
 */
public class Attribute {

    public Attribute(Wrapper wrapper, String URI) {
        this.wrapper = wrapper;
        this.URI = URI;
    }

    private Wrapper wrapper;
    private String URI;

    public Wrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "wrapper=" + wrapper +
                ", URI='" + URI + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (getWrapper() != null ? !getWrapper().equals(attribute.getWrapper()) : attribute.getWrapper() != null)
            return false;
        return getURI() != null ? getURI().equals(attribute.getURI()) : attribute.getURI() == null;
    }

    @Override
    public int hashCode() {
        int result = getWrapper() != null ? getWrapper().hashCode() : 0;
        result = 31 * result + (getURI() != null ? getURI().hashCode() : 0);
        return result;
    }
}
