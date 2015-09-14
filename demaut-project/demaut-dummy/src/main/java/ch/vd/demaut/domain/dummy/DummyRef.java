package ch.vd.demaut.domain.dummy;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class DummyRef extends BaseValueObject {

	private Long ref;
	
	public DummyRef() {
	}
	
	public DummyRef(long l) {
		this.ref = l;
	}

	public void setRef(Long ref) {
		this.ref = ref;
	}
	
	public Long getRef() {
		return ref;
	}

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String toStringShort() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.SIMPLE_STYLE);
    }
}
