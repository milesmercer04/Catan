package com.miles;

public class TerrainHex {
    private final int NUM_VERTICES = 6;

    private int resourceType;
    private int chitNumber;
    private Vertex[] vertices;

    private TerrainHex() {
        this.resourceType = 0;
        this.chitNumber = 0;
        this.vertices = new Vertex[NUM_VERTICES];
    }
}