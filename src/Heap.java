import java.lang.reflect.Array;

public class Heap<T extends IHeapItem<T> & Comparable<T>> { 
	public T[] array;
	public int size;

	public Heap(Class<T> c, int maxSize) {
		array = (T[]) Array.newInstance(c, maxSize);
		size = 0;
	}

	public int Count() {
		return size;
	}

	public boolean Contains(T value) {
		return value.equals(array[value.getHeapIndex()]);
	}
	
	public void Add(T value) {
		value.setHeapIndex(size);
		array[size] = value;
		SortUp(value);
		size++;
	}

	public T RemoveFirst() {
		T result = array[0];
		size--;
		array[0] = array[size];
		array[0].setHeapIndex(0);
		SortDown(array[0]);
		return result;
	}

	public void SortDown(T value) {
		int index = value.getHeapIndex();
		int nextIndex = 0;
		while (true) {
			int leftChildIndex = index * 2 + 1;
			int rightChildIndex = index * 2 + 2;

			if (leftChildIndex < size) {
				nextIndex = leftChildIndex;
				if (rightChildIndex < size) {
					if (array[leftChildIndex].compareTo(array[size]) < 0) {
						nextIndex = rightChildIndex;
					}
				}
				if (value.compareTo(array[nextIndex]) < 0) {
					Swap(value, array[nextIndex]);
				} else {
					return;
				}
			} else {
				return;
			}
		}
	}

	public void SortUp(T value) {
		int parentIndex = (value.getHeapIndex()-1) / 2;

        while (true) {
            T parentValue = array[parentIndex];
            if (value.compareTo(parentValue) > 0) {
                Swap(value, parentValue);
            }
            else {
                break;
            }
            parentIndex = (value.getHeapIndex() - 1) / 2;
        }
	}

	public void Swap(T value1, T value2) {
		array[value1.getHeapIndex()] = value2;
		array[value2.getHeapIndex()] = value1;
		int itemAIndex = value1.getHeapIndex();
		value1.setHeapIndex(value2.getHeapIndex());
		value2.setHeapIndex(itemAIndex);
	}
}
