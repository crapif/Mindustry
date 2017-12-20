package io.anuke.mindustry.world.blocks.types.defense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.world.Layer;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Angles;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Tmp;

public class LaserTurret extends PowerTurret{
	protected Color beamColor = Color.WHITE.cpy();
	protected Effect hiteffect = Fx.laserhit;
	protected int damage = 4;
	protected float cone = 15f;

	public LaserTurret(String name) {
		super(name);
		shootsound = null;
		layer2 = Layer.laser;
		soundReload = 20;
	}
	
	@Override
	public void shoot(Tile tile){
		TurretEntity entity = tile.entity();
		Enemy enemy = entity.target;
		
		if(Angles.angleDist(entity.rotation, Angles.angle(tile.worldx(), tile.worldy(), enemy.x, enemy.y)) < cone){
			enemy.damage(damage);
			Effects.effect(hiteffect, enemy.x + Mathf.range(3), enemy.y + Mathf.range(3));
		}
	}
	
	@Override
	public void drawLayer2(Tile tile){
		TurretEntity entity = tile.entity();
		
		if(entity.target != null &&
				Angles.angleDist(entity.rotation, Angles.angle(tile.worldx(), tile.worldy(), entity.target.x, entity.target.y)) <= cone){
			Angles.translation(entity.rotation, 4f);
			
			float x = tile.worldx() + Angles.x(), y = tile.worldy() + Angles.y();
			float x2 = entity.target.x, y2 = entity.target.y;

			float lighten = (MathUtils.sin(Timers.time()/1.2f) + 1f) / 10f;
			
			Draw.color(Tmp.c1.set(beamColor).mul(1f + lighten, 1f + lighten, 1f + lighten, 1f));
			Draw.alpha(0.3f);
			Draw.thickness(4f);
			Draw.line(x, y, x2, y2);
			Draw.thickness(2f);
			Draw.rect("circle", x2, y2, 7f, 7f);
			Draw.alpha(1f);
			Draw.thickness(2f);
			Draw.line(x, y, x2, y2);
			Draw.thickness(1f);
			Draw.rect("circle", x2, y2, 5f, 5f);
			
		}
		
		Draw.reset();
	}
}
