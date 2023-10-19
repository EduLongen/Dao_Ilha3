package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Anexos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String anexo;
    private String descricao;

    public Anexos() {
    }

    public Anexos(Integer id, String nome, String anexo, String descricao) {
        this.id = id;
        this.nome = nome;
        this.anexo = anexo;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Anexos other = (Anexos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Anexos [id=" + id + ", nome=" + nome + ", anexo=" + anexo + ", descricao=" + descricao + "]";
    }

}

/* CREATE TABLE anexos(
	Id  INT(11) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (Id),
    Nome VARCHAR(50) NOT NULL,
	Anexo VARCHAR(250) NOT NULL,
    Descricao VARCHAR(250) NOT NULL

); */
