package com.miles;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private static final int DESERT_VALUE = 0;
    // private static final int HILLS_VALUE = 1;
    // private static final int FOREST_VALUE = 2;
    // private static final int MOUNTAINS_VALUE = 3;
    // private static final int FIELDS_VALUE = 4;
    // private static final int PASTURE_VALUE = 5;
    private static final int NUM_TERRAIN_TYPES = 6;
    private static final int NUM_DESERT_TILES = 1;
    private static final int NUM_HILLS_TILES = 3;
    private static final int NUM_FOREST_TILES = 4;
    private static final int NUM_MOUNTAINS_TILES = 3;
    private static final int NUM_FIELDS_TILES = 4;
    private static final int NUM_PASTURE_TILES = 4;
    private static final int[] TERRAIN_TILE_NUMBERS = {NUM_DESERT_TILES, NUM_HILLS_TILES, NUM_FOREST_TILES,
                                                       NUM_MOUNTAINS_TILES, NUM_FIELDS_TILES, NUM_PASTURE_TILES};

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

    public void setUpBoard() {
        this.setTileTerrainTypes();
    }

    private void setTileTerrainTypes() {
        ArrayList<Integer> tileIndices = new ArrayList<>(NUM_TILES);
        Random rand = new Random();
        Integer selectedIndex;

        // Put every tile index in tileIndices arraylist
        for (Integer i = 0; i < NUM_TILES; i++) {
            tileIndices.add(i);
        }

        // Place the tiles of each type, removing available indices as they're placed
        for (int terrainType = 0; terrainType < NUM_TERRAIN_TYPES; terrainType++) {
            // Repeat for number of tiles of this type
            for (int i = 0; i < TERRAIN_TILE_NUMBERS[terrainType]; i++) {
                selectedIndex = tileIndices.get(rand.nextInt(tileIndices.size()));
                this.tiles[selectedIndex] = new TerrainHex(terrainType);
                tileIndices.remove(selectedIndex);
            }
        }

        System.out.println(tileIndices.size());
    }
}