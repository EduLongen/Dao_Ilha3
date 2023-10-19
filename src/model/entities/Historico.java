package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Historico implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String entidade;
    private String acao;
    private Date dataHistorico;
    private String hora;
    private Item item;

    public Historico() {
    }

    public Historico(Integer id, String entidade, String acao, Date dataHistorico, String hora, Item item) {
        this.id = id;
        this.entidade = entidade;
        this.acao = acao;
        this.dataHistorico = dataHistorico;
        this.hora = hora;
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
        Historico other = (Historico) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Historico [id=" + id + ", entidade=" + entidade + ", acao=" + acao + ", dataHistorico=" + dataHistorico + ", hora=" + hora + ", item=" + item +"]";
    }
}