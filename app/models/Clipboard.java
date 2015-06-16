package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToMany;

import models.GameParts.*;

public class Clipboard  {
	@ManyToMany
    private List<Action> actions;
	@ManyToMany
    private List<Attribute> attributes;
	@ManyToMany
    private List<Condition> conditions;
	@ManyToMany
    private List<Content> contents;
	@ManyToMany
    private List<Hotspot> hotspots;
    @ManyToMany
    private List<Part> parts;
    @ManyToMany
    private List<Rule> rules;
    
    

    public Clipboard(){
    	
     actions = new ArrayList<Action>();
     attributes = new ArrayList<Attribute>();
     conditions = new ArrayList<Condition>();
     contents = new ArrayList<Content>();
     hotspots = new ArrayList<Hotspot>();
     parts = new ArrayList<Part>();
     rules = new ArrayList<Rule>();

    }


	public List<Action> getActions() {
		return actions;
	}



	public void addActions(List<Action> actions) {
		this.actions.addAll(actions);
	}



	public List<Rule> getRules() {
		return rules;
	}



	public void addRules(List<Rule> rules) {
		this.rules.addAll(rules);
	}



	public List<Hotspot> getHotspots() {
		return hotspots;
	}



	public void addHotspots(List<Hotspot> hotspots) {
		this.hotspots.addAll(hotspots);
	}



	public List<Part> getParts() {
		return parts;
	}



	public void addParts(List<Part> parts) {
		this.parts.addAll(parts);
	}



	public List<Condition> getConditions() {
		return conditions;
	}



	public void addConditions(List<Condition> conditions) {
		this.conditions.addAll(conditions);
	}



	public List<Content> getContents() {
		return contents;
	}



	public void addContents(List<Content> contents) {
		this.contents.addAll(contents);
	}



	public List<Attribute> getAttributes() {
		return attributes;
	}



	public void addAttributes(List<Attribute> attributes) {
		this.attributes.addAll(attributes);
	}






}
