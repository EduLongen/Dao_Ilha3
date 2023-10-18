package model.entities;

import java.io.Serializable;
import java.util.Date;
public class Item implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String descricao;
    private String categoria;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String potencia;
    private String localizacao;
    private String enviado;
    private String notaFiscal;
    private Date dataEntrada;
    private Date ultimaQualificacao;
    private Date proximaQualifacao;
    private TipoItem tipoItem;
    public Item(){}

    public Item(Integer id, String descricao, String categoria, String marca, String modelo, String numeroSerie, String potencia, String localizacao, String enviado, String notaFiscal, Date dataEntrada, Date ultimaQualificacao, Date proximaQualifacao, TipoItem tipoItem) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.potencia = potencia;
        this.localizacao = localizacao;
        this.enviado = enviado;
        this.notaFiscal = notaFiscal;
        this.dataEntrada = dataEntrada;
        this.ultimaQualificacao = ultimaQualificacao;
        this.proximaQualifacao = proximaQualifacao;
        this.tipoItem = tipoItem;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getUltimaQualificacao() {
        return ultimaQualificacao;
    }

    public void setUltimaQualificacao(Date ultimaQualificacao) {
        this.ultimaQualificacao = ultimaQualificacao;
    }

    public Date getProximaQualifacao() {
        return proximaQualifacao;
    }

    public void setProximaQualifacao(Date proximaQualifacao) {
        this.proximaQualifacao = proximaQualifacao;
    }
    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
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
        Item other = (Item) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", descricao=" + descricao + ", categoria=" + categoria + ", marca=" + marca +  ", modelo=" + modelo + ", numeroSerie=" + numeroSerie + ", potencia=" + potencia + ", localizacao=" + localizacao + ", enviado=" + enviado + ", notaFiscal=" + notaFiscal + ", dataEntrada=" + dataEntrada + ", ultimaQualificacao=" + ultimaQualificacao + ", proximaQualifacao=" + proximaQualifacao + ", tipoItem=" + tipoItem + "]";
    }
}






/*
    private Integer id;
    private String descricao;
    private String categoria;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String potencia;
    private String localizacao;
    private String enviado;
    private String notaFiscal;
    private Date dataEntrada;
    private Date ultimaQualificacao;
    private Date proximaQualifacao;*/
