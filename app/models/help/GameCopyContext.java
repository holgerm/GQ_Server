package models.help;

import models.GameParts.Attribute;
import models.GameParts.Hotspot;
import models.GameParts.Mission;
import models.GameParts.ObjectReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameCopyContext {

    public Map<Mission, Mission> missionMap = new HashMap<Mission, Mission>();
    public Map<Hotspot, Hotspot> hotspotMap = new HashMap<Hotspot, Hotspot>();
    public Map<Attribute, Attribute> attributeMap = new HashMap<Attribute, Attribute>();
    public List<ObjectReference> objRefsWithTargetToRenew = new ArrayList<ObjectReference>();

}
