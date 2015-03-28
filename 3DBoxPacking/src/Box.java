import java.util.*;

public class Box {

	String boxNumber;
	public Map<String, Integer> dimension = new HashMap<String, Integer>();
	public Integer volume;

	public Box(Integer l, Integer b, Integer h) {
		volume = l * b * h;
		dimension.put("length", l);
		dimension.put("breadth", b);
		dimension.put("height", h);

	}

	public Box(String boxName, Integer l, Integer b, Integer h) {
		boxNumber = boxName;
		volume = l * b * h;
		dimension.put("length", l);
		dimension.put("breadth", b);
		dimension.put("height", h);

	}

	public Box rotateBox() {

		Set<String> keySet = this.dimension.keySet();
		String[] sides = keySet.toArray(new String[3]);
		dimension.put(sides[1],
				dimension.put(sides[2], dimension.get(sides[1])));
		dimension.put(sides[0],
				dimension.put(sides[2], dimension.get(sides[0])));

		return this;
	}

	public Integer getVolume() {
		// TODO Auto-generated method stub
		return dimension.get("length") * dimension.get("breadth")
				* dimension.get("height");
	}
}
