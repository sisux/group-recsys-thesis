package mia.clustering.users.helper;

import java.util.List;
import java.util.Vector;

/**
 * Bidimensional matrix
 * @author sisux
 *
 * @param <T>
 */
public class BidiMatrix<T> {

	private Vector<Vector<T>> _data;
	
	public BidiMatrix(int the1dSize) {
		_data = new Vector<Vector<T>>(the1dSize);
		initializeMatrix(the1dSize);
	}
	
	private void initializeMatrix(int the1dSize) {
		for(int i = 0; i < the1dSize; i++) {
			_data.add(new Vector<T>());
		}
	}

	public void addColumn(int theColumnIdx, List<T> theColumn) {
		T value;
		int tmpCellIdx;
		for(int i = 0; i < _data.size(); i++) {
			tmpCellIdx = (i % theColumn.size());
			value = theColumn.get(tmpCellIdx);
			_data.get(i).add(theColumnIdx, value);
		}
	}
	
	public List<T> getRow(int theRowIdx) {
		return _data.get(theRowIdx);
	}

	public void clear() {
		_data.clear();
	}

	public boolean isEmpty() {
		return _data.isEmpty();
	}

	public int size() {
		return _data.size();
	}
}
 