import java.util.*;

public class BinPacking3D_LAFF {

	public Map<String, Integer> containerDimension = new HashMap<String, Integer>();
	public Integer containerVolume;
	public List<Box> boxes = new ArrayList<Box>();

	public List<Level> levels = new ArrayList<Level>();

	public BinPacking3D_LAFF(List<Box> boxes) {

		this.boxes = boxes;

		calculateContainerDimension(calculateTwoLongestSide());
		containerVolume = 0;

		packLevel();
	}

	public Integer[] calculateTwoLongestSide() {
		Integer[] maxValue = new Integer[2];

		Set<Integer> value = new TreeSet<Integer>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return -o1.compareTo(o2);
			}

		});

		for (Box box : boxes) {
			value.addAll(box.dimension.values());
		}

		Iterator<Integer> itr = value.iterator();
		maxValue[0] = itr.next();
		maxValue[1] = itr.next();
		System.out.println("Longest 1 : " + maxValue[0] + "Longest 2nd : "
				+ maxValue[1]);

		return maxValue;

	}

	public void calculateContainerDimension(Integer[] longestEdges) {

		containerDimension.put("length", longestEdges[0]);
		containerDimension.put("breadth", longestEdges[1]);
		containerDimension.put("height", 0);

	}

	public void fillSpace(Box space, Level level) {

		Integer volume = space.volume;
		Box boxSelected = null;
		Integer volumeSelectedBox = Integer.MIN_VALUE;

		for (Box box : boxes) {

			if (box.volume > volume) {
				continue;
			} else {

				if (fitPack(space, box)) {
					if (box.volume > volumeSelectedBox) {

						boxSelected = box;
						volumeSelectedBox = box.volume;
					}
				}
			}
		}

		if (boxSelected != null) {
			boxes.remove(boxSelected);
			level.add(boxSelected);

			if (space.dimension.get("length")
					- boxSelected.dimension.get("length") > 0) {

				fillSpace(
						new Box(space.dimension.get("length")
								- boxSelected.dimension.get("length"),
								space.dimension.get("breadth"),
								space.dimension.get("height")), level);
			}
			if (space.dimension.get("breadth")
					- boxSelected.dimension.get("breadth") > 0) {
				fillSpace(
						new Box(space.dimension.get("length"),
								space.dimension.get("breadth")
										- boxSelected.dimension.get("breadth"),
								space.dimension.get("height")), level);
			}
		}
	}

	public void packLevel() {

		Box boxBiggest = null;
		Integer surfaceAreaBiggest = Integer.MIN_VALUE;
		Level level = new Level();
		levels.add(level);

		for (Box box : boxes) {

			int surfaceArea = box.dimension.get("length")
					* box.dimension.get("breadth");
			if (surfaceArea > surfaceAreaBiggest) {

				boxBiggest = box;
				surfaceAreaBiggest = surfaceArea;

			}

		}
		containerDimension.put("height", containerDimension.get("height")
				+ boxBiggest.dimension.get("height"));
		System.out.println("container Height: "
				+ containerDimension.get("height"));

		level.add(boxBiggest);
		boxes.remove(boxBiggest);

		if (boxes.isEmpty())
			return;
		Integer containerArea = containerDimension.get("length")
				* containerDimension.get("breadth");
		Integer boxArea = boxBiggest.dimension.get("length")
				* boxBiggest.dimension.get("breadth");

		if (containerArea - boxArea > 0) {

			if (containerDimension.get("length")
					- boxBiggest.dimension.get("length") > 0) {

				fillSpace(
						new Box(containerDimension.get("length")
								- boxBiggest.dimension.get("length"),
								containerDimension.get("breadth"),
								boxBiggest.dimension.get("height")), level);
			}
			if (containerDimension.get("breadth")
					- boxBiggest.dimension.get("breadth") > 0) {
				fillSpace(new Box(boxBiggest.dimension.get("length"),
						containerDimension.get("breadth")
								- boxBiggest.dimension.get("breadth"),
						boxBiggest.dimension.get("height")), level);
			}

			if (!boxes.isEmpty())
				packLevel();

		} else {
			packLevel();

		}

	}

	public boolean fitPack(Box space, Box box) {

		if (tryToFit(space, box)) {
			return true;

		} else {

			for (int i = 0; i < 3; i++) {
				if (tryToFit(space, box.rotateBox())) {
					return true;
				}
			}
		}

		return false;

	}

	public void printLevel() {
		int levelNumber = 0;
		for (Level level : levels) {
			System.out.print("\nLevel #" + levelNumber++ + " :: ");
			for (Box b : level.packedBoxes) {
				System.out.print("  Box#" + b.boxNumber);

			}

		}

	}

	public boolean tryToFit(Box space, Box box) {
		if (space.dimension.get("length") >= box.dimension.get("length")
				&& space.dimension.get("breadth") >= box.dimension
						.get("breadth")
				&& space.dimension.get("height") >= box.dimension.get("height"))
			return true;
		return false;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Box> boxes = new ArrayList<Box>();
		boxes.add(new Box("1", 50, 50, 8));
		boxes.add(new Box("2", 33, 8, 8));
		boxes.add(new Box("3", 16, 20, 8));
		boxes.add(new Box("4", 3, 18, 8));
		boxes.add(new Box("5", 14, 2, 8));

		BinPacking3D_LAFF packing3d = new BinPacking3D_LAFF(boxes);
		packing3d.printLevel();
		System.out.println("\n Box Container Dimension: "+packing3d.containerDimension);
	}

}
