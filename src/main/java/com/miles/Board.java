package com.miles;

public class Board {
    private final int NUM_TILES = 19;
    private final int NUM_VERTICES = 54;

    private TerrainHex[] tiles;
    private Vertex[] vertices;
    private int[][] ownershipMatrix;

    public Board() {
        this.tiles = new TerrainHex[NUM_TILES];
        this.vertices = new Vertex[NUM_VERTICES];
        this.ownershipMatrix = new int[NUM_VERTICES][NUM_VERTICES];
    }
}