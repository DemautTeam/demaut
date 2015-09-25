package ch.vd.demaut.microbiz.commons.json;

/**
 * Classe Utilitaire de sérialisation des enums pour le parsing JSon.<br/>
 */
abstract public class EnumJsonSerializer<T> extends BaseJsonSerializer<T> {

    public EnumJsonSerializer() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> handledType() {
        return (Class<T>) Enum.class;
    }
}
