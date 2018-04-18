package models.GameParts;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.Game;
import play.db.ebean.Model;

@Entity
public class PartType extends Model {

	@Id
	private Long id;

	private boolean is_scene;

	@OneToOne
	private MissionType mission;
	@OneToOne
	private SceneType scene;

	public PartType(MissionType m) {
		is_scene = false;
		mission = m;
	}

	public PartType(SceneType s) {
		is_scene = true;
		scene = s;
	}

	public boolean isSceneType() {
		return is_scene;
	}

	public MissionType getMissionType() {
		return mission;
	}

	public SceneType getSceneType() {
		return scene;
	}

	public Long getId() {
		return id;
	}

	public Part createMe(MissionType m, String n) {

		Part p = new Part(m.createMe(n));
		p.save();
		return p;

	}

	public Part createMe(SceneType s, String n, Game g) {

		Part p = new Part(s.createMe(n, g));
		p.save();
		return p;

	}

	public static final Finder<Long, PartType> find = new Finder<Long, PartType>(Long.class, PartType.class);

}
