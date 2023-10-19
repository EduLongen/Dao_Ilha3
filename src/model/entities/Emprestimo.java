package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String entidade;
    private String lugar;
    private String estado;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Usuario usuario;
    private Item item;
    public Emprestimo() {
    }

    public Emprestimo(Integer id, String entidade, String lugar, String estado, Date dataEmprestimo, Date dataDevolucao, Usuario usuario, Item item) {
        this.id = id;
        this.entidade = entidade;
        this.lugar = lugar;
        this.estado = estado;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.usuario = usuario;
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
        this.entidade = entidade; // Corrigi o erro aqui
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar; // Corrigi o erro aqui
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado; // Corrigi o erro aqui
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo; // Corrigi o erro aqui
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao; // Corrigi o erro aqui
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        Emprestimo other = (Emprestimo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Emprestimo [id=" + id + ", entidade=" + entidade + ", lugar=" + lugar + ", estado=" + estado + ", dataEmprestimo=" + dataEmprestimo + ", dataDevolucao=" + dataDevolucao + ", usuario=" + usuario + ", item=" + item + "]";
    }
}