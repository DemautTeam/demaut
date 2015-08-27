package ch.vd.ses.demaut.poc.jpa.entity;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Table(name = "ANNEXE")
public class Annexe implements Serializable {

	private static final long serialVersionUID = -585189894722223746L;

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "SIZE")
    private Long size;

    @Column(name="TYPE", length = 100, nullable = false)
    private String type;

    @Lob
    @Column(name = "FILE", nullable = false)
    protected  byte[] file;


    protected Annexe() {
        /* Reflection instantiation */
    }

    public Annexe(String name, Long size, String type, byte[] file) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.file = (file == null) ? null : file.clone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", this.id)
                .add("name", this.name)
                .add("type", this.type)
                .add("size", this.size)
                .toString();
    }

}