package com.miles;

public class Vertex {
    private final int NUM_NEIGHBORS = 3;

    private Building building;
    private Vertex[] neighbors;

    public Vertex() {
        this.building = new Building();
        this.neighbors = new Vertex[NUM_NEIGHBORS];
        
        for (int i = 0; i < NUM_NEIGHBORS; i++) {
            this.neighbors[i] = null;
        }
    }

    public void addNeighbor(int index, Vertex newNeighbor) {
        neighbors[index] = newNeighbor;
    }
}