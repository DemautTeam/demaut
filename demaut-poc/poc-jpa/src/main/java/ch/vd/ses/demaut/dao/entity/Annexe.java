package ch.vd.ses.demaut.dao.entity;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

@javax.persistence.Entity
public class Annexe implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column
    private Long size;

    @Column(length = 100, nullable = false)
    private String type;

    @Lob
    @Column(nullable = false)
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

    @Override
    public int hashCode() {
        return Hashing.md5().newHasher()
                .putLong(id)
                .putString(name, Charsets.UTF_8)
                .putObject(this, new Funnel<Annexe>() {
                    @Override
                    public void funnel(Annexe annexe, PrimitiveSink into) {
                        into.putLong(annexe.getId())
                                .putString(annexe.getName(), Charsets.UTF_8)
                                .putString(annexe.getType(), Charsets.UTF_8)
                                .putLong(annexe.getSize());
                    }
                })
                .hash()
                .asInt();
    }
}