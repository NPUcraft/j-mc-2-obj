package org.jmc.models;

import javax.annotation.Nonnull;

import org.jmc.BlockData;
import org.jmc.geom.UV;
import org.jmc.registry.NamespaceID;
import org.jmc.threading.ChunkProcessor;
import org.jmc.threading.ThreadChunkDeligate;


/**
 * Model for wood logs (and, accidentally, also hay blocks).
 */
public class WoodLog extends BlockModel
{

	@Nonnull
	protected NamespaceID[] getMtlSides(BlockData data, int biome, int dir)
	{
		NamespaceID[] mat = materials.get(data.state, biome);
		
		if (dir == 1)
			return new NamespaceID[] { mat[1], mat[1], mat[1], mat[0], mat[0], mat[1] };
		else if (dir == 2)
			return new NamespaceID[] { mat[1], mat[0], mat[0], mat[1], mat[1], mat[1] };
		else if (dir >= 3)
			return new NamespaceID[] { mat[1], mat[1], mat[1], mat[1], mat[1], mat[1] };
		else
			return new NamespaceID[] { mat[0], mat[1], mat[1], mat[1], mat[1], mat[0] };
	}
	
	protected UV[][] getUvSides(int dir)
	{
		UV[] uv1 = new UV[] { new UV(0,0), new UV(1,0), new UV(1,1), new UV(0,1) };
		UV[] uv2 = new UV[] { new UV(1,0), new UV(1,1), new UV(0,1), new UV(0,0) };
		
		if (dir == 1)
			return new UV[][] { uv2, uv2, uv2, uv2, uv2, uv2 };
		else if (dir == 2)
			return new UV[][] { uv1, uv2, uv2, uv2, uv2, uv1 };
		else
			return new UV[][] { uv1, uv1, uv1, uv1, uv1, uv1 };
	}


	@Override
	public void addModel(ChunkProcessor obj, ThreadChunkDeligate chunks, int x, int y, int z, BlockData data, int biome)
	{
		int dir;
		// 0 - upright
		// 1 - lying east-west
		// 2 - lying north-south
		// 3 - Anything greater (aka: 3) will return an all bark block
		String axis = data.state.get("axis");
		switch (axis) {
		case "x":
			dir = 1;
			break;
		case "y":
			dir = 0;
			break;
		case "z":
			dir = 2;
			break;

		default:
			dir = 0;
			throw new RuntimeException("invalid WoodLog axis!");
		}
		
		addBox(obj,
				x - 0.5f, y - 0.5f, z - 0.5f,
				x + 0.5f, y + 0.5f, z + 0.5f, 
				null, 
				getMtlSides(data, biome, dir), 
				getUvSides(dir), 
				drawSides(chunks, x, y, z, data));
	}

}
