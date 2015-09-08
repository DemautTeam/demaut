package ch.vd.demaut.cucumber.converters.commons;

import cucumber.api.Transformer;

public class OuiNonConverter extends Transformer<Boolean> {

    private String positive = "oui";
    private boolean caseSensitive = false;

    @Override
    public Boolean transform(String str) {
        if (caseSensitive) {
            return positive.equals(str) ? Boolean.TRUE : Boolean.FALSE;
        }
        return positive.equalsIgnoreCase(str) ? Boolean.TRUE : Boolean.FALSE;
    }

}
