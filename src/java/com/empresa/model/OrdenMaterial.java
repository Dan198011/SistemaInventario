package com.empresa.model;

public class OrdenMaterial {
    private int id;
    private int ordenId;
    private int materialId;
    private double cantidad;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrdenId() { return ordenId; }
    public void setOrdenId(int ordenId) { this.ordenId = ordenId; }
    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
}
