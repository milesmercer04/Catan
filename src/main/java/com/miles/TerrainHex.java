package com.miles;

public class TerrainHex {
    private final int NUM_VERTICES = 6;
    
    private int terrainType;
    private int chitNumber;
    private Vertex[] vertices;
    
    public TerrainHex() {
        this.terrainType = 0;
        this.chitNumber = 0;
        this.vertices = new Vertex[NUM_VERTICES];
    }

    public TerrainHex(int terrainType) {
        this.terrainType = terrainType;
        this.chitNumber = 0;
        this.vertices = new Vertex[NUM_VERTICES];
    }

    public void setVertex(int index, Vertex newVertex) {
        this.vertices[index] = newVertex;
    }
}