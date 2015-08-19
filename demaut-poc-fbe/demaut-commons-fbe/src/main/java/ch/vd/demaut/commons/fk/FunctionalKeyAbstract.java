package ch.vd.demaut.commons.fk;


import ch.vd.demaut.commons.vo.BaseValueObject;

public abstract class FunctionalKeyAbstract<T extends FunctionalKeyAware> extends BaseValueObject implements
        FunctionalKey<T> {

}
