package com.empresa.model;

public class Material {
    private int id;
    private String tipo;
    private String descripcion;
    private double existencia;
    private double minimo;
    private String unidad;
    private String lote;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getExistencia() { return existencia; }
    public void setExistencia(double existencia) { this.existencia = existencia; }
    public double getMinimo() { return minimo; }
    public void setMinimo(double minimo) { this.minimo = minimo; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }
}

