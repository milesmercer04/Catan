package com.miles;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    public static final int DESERT_VALUE = 0;
    // public static final int HILLS_VALUE = 1;
    // public static final int FOREST_VALUE = 2;
    // public static final int MOUNTAINS_VALUE = 3;
    // public static final int FIELDS_VALUE = 4;
    // public static final int PASTURE_VALUE = 5;
    public static final int NUM_TERRAIN_TYPES = 6;
    public static final int NUM_DESERT_TILES = 1;
    public static final int NUM_HILLS_TILES = 3;
    public static final int NUM_FOREST_TILES = 4;
    public static final int NUM_MOUNTAINS_TILES = 3;
    public static final int NUM_FIELDS_TILES = 4;
    public static final int NUM_PASTURE_TILES = 4;
    private static final int[] TERRAIN_TILE_NUMBERS = {NUM_DESERT_TILES, NUM_HILLS_TILES, NUM_FOREST_TILES,
                                                       NUM_MOUNTAINS_TILES, NUM_FIELDS_TILES, NUM_PASTURE_TILES};

    private final int NUM_TILES = 19;
    private final int NUM_VERTICES = 54;
    private final int BOARD_HEIGHT = 5;

    private TerrainHex[] tiles;
    private Vertex[] vertices;
    private int[][] ownershipMatrix;

    public Board() {
        this.tiles = new TerrainHex[NUM_TILES];
        this.vertices = new Vertex[NUM_VERTICES];
        this.ownershipMatrix = new int[NUM_VERTICES][NUM_VERTICES];
    }

    public void setUpBoard() {
        // Fill vertices array
        for (int i = 0; i < NUM_VERTICES; i++) {
            this.vertices[i] = new Vertex();
        }

        // Fill ownership matrix with zeros (no owner)
        for (int i = 0; i < NUM_VERTICES; i++) {
            for (int j = 0; j < NUM_VERTICES; j++) {
                this.ownershipMatrix[i][j] = 0;
            }
        }

        this.setTileTerrainTypes();
        this.setTileVertices();
        this.setVertexNeighbors();
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

    private void setTileVertices() {
        int rowWidths[] = this.calculateRowWidths();
        int row, tile, firstUpper, firstLower, currUpper, currLower;

        // Using row widths, find vertices of each row
        row = 0;
        tile = 0;
        firstUpper = 0;

        // Find tile vertices for rows above middle row
        while (row < BOARD_HEIGHT / 2) {
            // Find first lower vertex of row
            firstLower = firstUpper + 2 * rowWidths[row + 1];
            currUpper = firstUpper;
            currLower = firstLower;

            // Iterate through tiles on row, filling in vertices
            for (int rowTile = 0; rowTile < rowWidths[row]; rowTile++) {
                for (int i = 0; i < 3; i++) {
                    this.tiles[tile].setVertex(i, this.vertices[currUpper + i]);
                    this.tiles[tile].setVertex(i + 3, this.vertices[currLower + i]);
                }

                // Step to next tile
                currUpper += 2;
                currLower += 2;
                tile++;
            }

            // Set first upper vertex for next row
            firstUpper = firstLower - 1;
            row++;
        }

        // Find tile vertices for middle row
        firstLower = firstUpper + 2 * rowWidths[row] + 1;
        currUpper = firstUpper;
        currLower = firstLower;

        for (int rowTile = 0; rowTile < rowWidths[row]; rowTile++) {
            for (int i = 0; i < 3; i++) {
                this.tiles[tile].setVertex(i, this.vertices[currUpper + i]);
                this.tiles[tile].setVertex(i + 3, this.vertices[currLower + i]);
            }

            currUpper += 2;
            currLower += 2;
            tile++;
        }

        firstUpper = firstLower + 1;
        row++;

        // Find tile vertices for lower rows
        while (row < BOARD_HEIGHT) {
            firstLower = firstUpper + 2 * rowWidths[row - 1];
            currUpper = firstUpper;
            currLower = firstLower;

            for (int rowTile = 0; rowTile < rowWidths[row]; rowTile++) {
                for (int i = 0; i < 3; i++) {
                    this.tiles[tile].setVertex(i, this.vertices[currUpper + i]);
                    this.tiles[tile].setVertex(i + 3, this.vertices[currLower + i]);
                }

                currUpper += 2;
                currLower += 2;
                tile++;
            }

            firstUpper = firstLower + 1;
            row++;
        }
    }

    private void setVertexNeighbors() {
        int[] rowWidths = this.calculateRowWidths();
        int row, currVertexIndex, firstUpper, offset;

        row = 0;
        firstUpper = 0;

        while (row < this.BOARD_HEIGHT / 2) {
            offset = 2 * rowWidths[row + 1];

            for (currVertexIndex = firstUpper; currVertexIndex <= firstUpper + 2 * rowWidths[row]; currVertexIndex++) {
                if ((currVertexIndex - firstUpper) % 2 == 0) {
                    if (currVertexIndex > firstUpper) {
                        this.vertices[currVertexIndex].addNeighbor(0, this.vertices[currVertexIndex - 1]);
                    }

                    if (currVertexIndex < firstUpper + 2 * rowWidths[row]) {
                        this.vertices[currVertexIndex].addNeighbor(1, this.vertices[currVertexIndex + 1]);
                    }

                    this.vertices[currVertexIndex].addNeighbor(2, this.vertices[currVertexIndex + offset]);
                    this.vertices[currVertexIndex + offset].addNeighbor(0, this.vertices[currVertexIndex]);
                } else {
                    this.vertices[currVertexIndex].addNeighbor(1, this.vertices[currVertexIndex - 1]);
                    this.vertices[currVertexIndex].addNeighbor(2, this.vertices[currVertexIndex + 1]);
                }
            }
        }
    }

    private int[] calculateRowWidths() {
        int[] rowWidths = new int[this.BOARD_HEIGHT];
        int currRowWidth;

        for (int i = 0; i < this.BOARD_HEIGHT / 2 + 1; i++) {
            currRowWidth = BOARD_HEIGHT / 2 + i + 1;
            rowWidths[i] = currRowWidth;
            rowWidths[this.BOARD_HEIGHT - i - 1] = currRowWidth;
        }

        return rowWidths;
    }
}