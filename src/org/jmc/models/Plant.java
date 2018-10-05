package org.jmc.models;
 
import org.jmc.geom.Transform;
import org.jmc.geom.Vertex;
import org.jmc.threading.ChunkProcessor;
import org.jmc.threading.ThreadChunkDeligate;
import java.util.Random;
 
/**
 * Updated model for plants rendered as 2 crossed polygons, like grass, dead bushes, small flowers.
 */
public class Plant extends BlockModel
{
 
    @Override
    public void addModel(ChunkProcessor obj, ThreadChunkDeligate chunks, int x, int y, int z, byte data, int biome)
    {
        Transform move = new Transform();
        move.translate(x, y, z);
       
        // Generates a random number to offset the grass in the x and y.
        Random r = new Random();
        float randomX = -0.15f + r.nextFloat() * 0.3f;
        float randomZ = -0.15f + r.nextFloat() * 0.3f;
       
        // Flip half of the faces horizontally to avoid repeating patterns.
        boolean flip = r.nextBoolean();
        float flipValue = 1.0f;
        if (flip){
            flipValue = -1.0f; }
 
        Vertex[] vertices = new Vertex[4];
        vertices[0] = new Vertex((+0.43f+randomX)*flipValue,-0.5f,(-0.43f+randomZ)*flipValue);
        vertices[1] = new Vertex((-0.43f+randomX)*flipValue,-0.5f,(+0.43f+randomZ)*flipValue);
        vertices[2] = new Vertex((-0.43f+randomX)*flipValue,+0.45f,(+0.43f+randomZ)*flipValue);
        vertices[3] = new Vertex((+0.43f+randomX)*flipValue,+0.45f,(-0.43f+randomZ)*flipValue);
        obj.addFace(vertices, null, move, materials.get(data,biome)[0]);
       
        vertices[0] = new Vertex((-0.43f+randomX)*flipValue,-0.5f,(-0.43f+randomZ)*flipValue);
        vertices[1] = new Vertex((+0.43f+randomX)*flipValue,-0.5f,(+0.43f+randomZ)*flipValue);
        vertices[2] = new Vertex((+0.43f+randomX)*flipValue,+0.45f,(+0.43f+randomZ)*flipValue);
        vertices[3] = new Vertex((-0.43f+randomX)*flipValue,+0.45f,(-0.43f+randomZ)*flipValue);
        obj.addFace(vertices, null, move, materials.get(data,biome)[0]);
    }
 
}