package gametypes;

import java.util.ArrayList;
import java.util.List;

import play.api.Routes;
import models.Game;
import models.GameParts.*;
import util.Global;

public class BAGLernortFactory {


    public BAGLernortFactory() {


    }


    public GameType addGameToDatabase() {


        // GAME TYPE

        GeoQuestDefaultsFactory factory = new GeoQuestDefaultsFactory();
        GameType gt = factory.addGameToDatabase();
        gt.save();


        List<PartType> parts = new ArrayList<PartType>();
        parts.addAll(gt.getPossiblePartTypes());


        List<HotspotType> hotspots = new ArrayList<HotspotType>();
        hotspots.addAll(gt.getPossibleHotspotTypes());


        List<SceneType> scenes = new ArrayList<SceneType>();
        scenes.addAll(gt.getPossibleSceneTypes());


        List<AttributeType> attributes = new ArrayList<AttributeType>();
        attributes.addAll(gt.getAttributeTypes());


        gt.wipeClean();
        gt.update();


        gt.setName("BAG Lernort");


        /// BAG Lernort soll eine Szene beinhalten, welche nur einen NPCTalk autmatisch beinhaltet.


        SceneType st = new SceneType("Lernort");
        st.save();

        st.setIcon("icon-map-marker");
        st.setIconOpen("icon-map-marker");
        st.setSeeChildren(false);
        st.setSeeRules(false);


        st.setAddHotspots(false);
        st.setAddMissions(false);
        st.setAddRules(false);

        st.setSeeChildren(false);
        st.setSeeHotspots(true);
        st.setSeeMissions(false);
        st.setSeeRules(false);


        st.update();


        AttributeType att01 = new AttributeType("Bild", "image", "file");
        att01.setMimeType("image");
        att01.setOptional(true);
        att01.save();

        st.setAttributeType(att01);
        st.update();


        AttributeType att02 = new AttributeType("Textausgabe", "content", "String");
        att02.setOptional(false);
        att02.setMimeType("area");
        att02.save();

        st.setAttributeType(att02);
        st.update();


        AttributeType att04 = new AttributeType("Autor", "content2", "String");
        att04.setOptional(false);
        att04.setMimeType("field");
        att04.save();

        st.setAttributeType(att04);
        st.update();


        AttributeType att03 = new AttributeType("Audio-Datei", "sound", "file");
        att03.setOptional(true);
        att03.save();

        st.setAttributeType(att03);
        st.update();


        /// FIND NPCTALK
        MissionType npctalk = null;

        for (PartType p : parts) {

            if (!p.isSceneType()) {

                if (p.getMissionType().getXMLType().equals("NPCTalk")) {


                    npctalk = p.getMissionType();


                }


            }


        }


        /// CREATE DEFAULT NPCTALK

        if (npctalk != null) {


            Mission m1 = npctalk.createMe("NPCTalk");
            m1.save();


            PartType ptnpc = new PartType(npctalk);
            ptnpc.save();

            st.addPossiblePartTypes(ptnpc);
            st.save();


            // Standard-Attributte erstellen und evtl. darauf verlinken


            for (AttributeType ar : npctalk.getAttributeTypes()) {

                if (ar.getXMLType().equals("image")) {

                    // Bild


                    Attribute att01m1 = new Attribute(ar);
                    att01m1.save();

                    m1.setAttribute(att01m1);
                    m1.update();

                    ObjectReference att01m1or = new ObjectReference(att01m1);
                    att01m1or.save();


                    att01.setLink(att01m1or);
                    att01.update();


                }


                if (ar.getXMLType().equals("blocking")) {

                    // Nicht blocken

                    Attribute att02m1 = new Attribute(ar);
                    att02m1.save();

                    att02m1.setValue("false");
                    att02m1.update();

                    m1.setAttribute(att02m1);
                    m1.update();


                }


            }


            // Content

            boolean donedialog = false;
            for (ContentType ct : npctalk.getPossibleContentTypes()) {

                if (ct.getXMLType().equals("dialogitem")) {

                    donedialog = true;

                    Content c01 = ct.createMe();
                    c01.save();


                    ObjectReference c01or = new ObjectReference(c01);
                    c01or.save();

                    att02.setLink(c01or);
                    att02.update();

                    m1.addContent(c01);
                    m1.update();


                    Content c02 = ct.createMe();
                    c02.save();


                    ObjectReference c02or = new ObjectReference(c02);
                    c02or.save();

                    att04.setLink(c02or);
                    att04.update();

                    m1.addContent(c02);
                    m1.update();


                    // Sound Attribute


                    for (AttributeType attc : c01.getAllAttributes()) {


                        if (attc.getXMLType().equals("sound")) {


                            Attribute att01c01 = attc.createMe();
                            att01c01.save();

                            c01.setAttribute(att01c01);
                            c01.update();

                            ObjectReference att01c01or = new ObjectReference(att01c01);
                            att01c01or.save();


                            att03.setLink(att01c01or);
                            att03.update();


                        }


                    }


                }


            }

            // DEFAULT ACTIONS: onEnd: EndGame
            for (RuleType rt : npctalk.getPossibleRuleTypes()) {
                if (rt.getTrigger().equals("onEnd")) {
                    for (ActionType act : rt.getPossibleActionTypes()) {
                        if (act.getXMLType().equals("EndGame")) {
                            Action a1 = new Action("Spiel beenden", act);
                            a1.save();
                            Rule mr2 = new Rule();
                            mr2.save();
                            mr2.addAction(a1);
                            mr2.update();
                            Condition mcond1 = new Condition("onEnd");
                            mcond1.save();
                            Rule mr1 = new Rule(mcond1, mr2);
                            mr1.save();
                            m1.addRule(mr1);
                            m1.update();
                        }
                    }
                }
            }

            Part pm1 = new Part(m1);
            pm1.save();
            st.addDefaultPart(pm1);
            st.update();

            PartType pst = new PartType(st);
            pst.save();
            gt.addPossiblePartType(pst);
            pst.update();

            // CREATE DEFAULT HOTSPOT
            HotspotType hpt1 = new HotspotType("Standard", "hotspot");
            hpt1.save();

            // GET RULE TYPES
            for (RuleType rt : factory.getHotspotRuleTypes()) {
                hpt1.addPossibleRuleType(rt);
            }

            hpt1.update();

            AttributeType ha1 = new AttributeType("MarkerImage", "img", "file");
            ha1.setDefaultValue(Global.SERVER_URL_2 + "/assets/img/marker_bag_small.png");
            ha1.setVisibility(false);
            ha1.save();
            hpt1.setAttributeType(ha1);

            AttributeType ha2 = new AttributeType("Radius", "radius", "int");
            ha2.setDefaultValue("30");
            ha2.setVisibility(false);
            ha2.save();

            hpt1.setAttributeType(ha2);

            AttributeType ha3 = new AttributeType("Sichtbarkeit zu Beginn", "initialVisibility", "boolean");
            ha3.setDefaultValue("true");
            ha3.setVisibility(false);
            ha3.save();


            hpt1.setAttributeType(ha3);

            AttributeType ha4 = new AttributeType("Aktivität zu Beginn", "initialActivity", "boolean");
            ha4.setDefaultValue("true");
            ha4.setVisibility(false);
            ha4.save();

            hpt1.setAttributeType(ha4);
            hpt1.update();

            st.addPossibleHotspotTypes(hpt1);
            st.update();

            Float x = Float.valueOf(0);
            Float y = Float.valueOf(0);

            Attribute hax = ha1.createMe();
            hax.save();
            hax.setValue(Global.SERVER_URL_2 + "/assets/img/marker_blue.png");

            Hotspot h1 = new Hotspot(hpt1, x, y, "Hotspot");
            h1.save();
            h1.setAttribute(hax);
            h1.update();

            st.addDefaultHotspots(h1);
            st.update();

            // DEFAULT ACTIONS: onEnter & onTap: m1 aufrufen
            ObjectReference or1 = new ObjectReference(pm1);
            or1.save();

            for (RuleType rt : hpt1.getPossibleRuleTypes()) {
                if (rt.getTrigger().equals("onEnter") || rt.getTrigger().equals("onTap")) {
                    for (ActionType act : rt.getPossibleActionTypes()) {
                        if (act.getXMLType().equals("StartMission")) {
                            Action hsact1 = new Action("Mission aufrufen", act);
                            hsact1.save();
                            for (AttributeType ath01 : act.getAttributeTypes()) {


                                if (ath01.getXMLType().equals("id")) {

                                    Attribute ath01a = new Attribute(ath01);
                                    ath01a.save();

                                    ath01a.setAbstractValue(or1);
                                    ath01a.update();


                                    hsact1.setAttribute(ath01a);
                                    hsact1.update();
                                }
                            }


                            Rule hsdr2 = new Rule();
                            hsdr2.save();


                            Condition hscond1 = new Condition(rt.getTrigger());
                            hscond1.save();


                            Rule hsdr1 = new Rule(hscond1, hsdr2);
                            hsdr1.save();


                            hsdr2.addAction(hsact1);
                            hsdr2.update();


                            h1.addRule(hsdr1);
                            h1.update();
                        }
                    }
                }
            }

            gt.addPossibleSceneType(st);
            gt.update();

        }

        gt.setOnlySceneType(st);
        gt.update();

        return gt;
    }
}
