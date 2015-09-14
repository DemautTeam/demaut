package ch.vd.demaut.commons.fk;

import org.apache.commons.lang3.ClassUtils;

/**
 * Object (Entity or VO) with Functional Key
 */
public abstract class ObjectFunctionalKeyAware implements FunctionalKeyAware {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
//        if (getClass() != o.getClass()) {
//            return false;
//        }

        return this.getFunctionalKey().equals(((ObjectFunctionalKeyAware) o).getFunctionalKey());
    }

    @Override
    public int hashCode() {
        return this.getFunctionalKey().hashCode();
    }

    @Override
    public String toString() {
        String functionKeyString = this.getFunctionalKey().toString();
        int indexOfCrochet = functionKeyString.indexOf('[');
        String fctKeyPropsStr = functionKeyString.substring(indexOfCrochet);
        return ClassUtils.getShortClassName(this.getClass()) + fctKeyPropsStr;
    }

    public String toStringShort() {
        return this.getFunctionalKey().toStringShort();
    }

}
