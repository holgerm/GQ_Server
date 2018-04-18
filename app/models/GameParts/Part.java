package models.GameParts;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.Game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;

@Entity
public class Part extends Model {

	@Id
	private Long id;

	private Long gqid;

	private boolean is_scene;

	@OneToOne
	private Mission mission;

	@OneToOne
	private Scene scene;

	private int sort;

	@OneToOne
	private Part parent;

	private boolean candelete;

	public Part(Mission m) {
		is_scene = false;
		mission = m;
		candelete = true;
		save();

	}

	public Part(Scene s) {

		is_scene = true;
		scene = s;
		candelete = true;
		save();

	}

	public void setParent(Part x) {
		parent = x;
	}

	public boolean isScene() {
		return is_scene;
	}

	public Mission getMission() {
		return mission;
	}

	public Scene getScene() {
		return scene;
	}

	public boolean isDeletable() {
		return candelete;
	}

	public void setDeleteable(boolean x) {
		candelete = x;

	}

	public Long getId() {
		return id;
	}

	/// CREATION

	public Part copyMe(String n, Map<Mission, Mission> missionbinder, Map<Hotspot, Hotspot> hotspotbinder) {

		Part p;
		System.out.println("starting copy");

		if (is_scene == true) {
			p = new Part(scene.copyMe(n, missionbinder, hotspotbinder));
		} else {

			p = new Part(mission.copyMe(n, missionbinder));

		}

		p.setParent(this);
		// System.out.println("parent set");

		p.save();
		System.out.println("copy saved");

		return p;
	}

	public static final Finder<Long, Part> find = new Finder<Long, Part>(Long.class, Part.class);

	public void removeMe() {

		if (is_scene == true) {

			try {
				Scene s = scene;
				this.update();
				scene.removeMe();
				scene.delete();
				scene = null;
			} catch (RuntimeException e) {

				System.out.println("Can't delete Part->Scene.");
				e.printStackTrace();

			}
		} else {

			try {
				Mission m = mission;
				this.update();
				mission.removeMe();
				mission.delete();
				mission = null;

				this.update();
			} catch (RuntimeException e) {

				System.out.println("Can't delete Part->Mission.");
				e.printStackTrace();

			}

		}

	}

	public List<Element> createXML(Document doc, Game g, ZipOutputStream zout) {

		List<Element> e = new ArrayList<Element>();

		if (is_scene == true) {
			e = scene.createXML(doc, g, zout);
		} else {

			e = mission.createXML(doc, g, zout);

		}
		return e;

	}

	public List<Element> createXMLForWeb(Document doc, Game g) {

		List<Element> e = new ArrayList<Element>();

		if (is_scene == true) {
			e = scene.createXMLForWeb(doc, g);
		} else {

			e = mission.createXMLForWeb(doc, g);

		}
		return e;
	}

	public Mission getLastMission() {

		if (is_scene) {

			if (!scene.getParts().isEmpty()) {
				Part lastpart = scene.getParts().get(scene.getParts().size() - 1);

				Mission m = lastpart.getLastMission();

				return m;

			} else {

				return null;

			}
		} else {

			return mission;

		}
	}

	public Part getParent() {
		return parent;
	}

	// public Part migrateTo(SceneType sceneType, Map<Mission, Mission>
	// missionbinder, Map<Hotspot, Hotspot> hotspotbinder) {
	// if(isScene()){
	//
	// Part p = new
	// Part(getScene().migrateTo(sceneType,missionbinder,hotspotbinder));
	// p.save();
	//
	// return p;
	//
	//
	// } else {
	//
	// return null;
	//
	// }
	// }

	public Part migrateTo(MissionType missionType, Map<Mission, Mission> missionbinder) {
		if (!isScene()) {

			Mission x = getMission().migrateTo(missionType);

			Part p = new Part(x);

			p.save();

			missionbinder.put(getMission(), x);

			return p;

		} else {

			return null;

		}
	}

}
