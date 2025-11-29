package com.empresa.model;

import java.sql.Timestamp;

public class Movimiento {

    private int id;
    private int materialId;
    private Integer usuarioId;
    private String tipo;
    private double cantidad;
    private String referencia;
    private String proveedor;
    private String lote;
    private Integer ordenId;
    private Timestamp fecha;

    public Movimiento() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public Integer getOrdenId() { return ordenId; }
    public void setOrdenId(Integer ordenId) { this.ordenId = ordenId; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
}
