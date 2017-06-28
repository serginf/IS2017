package model;

import utils.Utils;

/**
 * Created by snadal on 25/06/17.
 */
public class Wrapper {

    public Wrapper(String name) {
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "name='" + Utils.nn(name) + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wrapper wrapper = (Wrapper) o;

        return name.equals(wrapper.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
