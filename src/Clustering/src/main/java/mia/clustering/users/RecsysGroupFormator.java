package mia.clustering.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mia.clustering.users.dao.UserGroupDAO;
import mia.clustering.users.helper.BidiMatrix;
import mia.clustering.users.model.UserGroup;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

/**
 * From a list of 30 created clusters, this class can create
 *  several groups of similar/dissimilar users; depending on same/different cluster
 * @author sisux
 *
 */
public class RecsysGroupFormator {

	private static int GROUP_PER_TYPE = 28;
	private static List<Integer> GROUP_SIZES = new ArrayList<Integer>(Arrays.asList(3,5,7));
	private static int MIN_GROUP_SIZE = 9; //comes from the minimum n on C(9,7)=36
	
	private static String SIMILAR_PREFIX = "S";
	private static String DISSIMILAR_PREFIX = "D";
	
	private UserGroupDAO _userGroupDAO;
	private List<Long> _userClusters;
	private BidiMatrix<Long> _dissimilarMatrix;
	
	public RecsysGroupFormator()  {
		_userGroupDAO = new UserGroupDAO();
		_userClusters = _userGroupDAO.getAllClusters();
		_dissimilarMatrix = new BidiMatrix<Long>(_userClusters.size());
	}

	public void createUserGroups() {
		createSimilarUserGroups();
		createDissimilarUserGroups();
	}

	private void updateDissimilarMatrix(UserGroup tmpCurrentClusterGroup,
			int tmpClusterNum) {
		_dissimilarMatrix.addColumn(tmpClusterNum, tmpCurrentClusterGroup.get_userIds());
	}

	private UserGroup createGeneratedGroup(Long theId, List<Long> theUserList, String theDescription) {
		UserGroup tmpUserGroup = new UserGroup();
		
		tmpUserGroup.set_id(theId);
		tmpUserGroup.set_userIds(theUserList);
		tmpUserGroup.set_description(theDescription);
		
		return tmpUserGroup;
	}

	private void createDissimilarUserGroups() {
		for (int i = 0; i < _dissimilarMatrix.size(); i++) {
			ICombinatoricsVector<Long> initialVector = Factory.createVector(_dissimilarMatrix.getRow(i));
			createCombinatorialUserGroup(initialVector, DISSIMILAR_PREFIX);
		}
	}
	
	private void createSimilarUserGroups() {
		UserGroup tmpCurrentClusterGroup = null;
		int tmpClusterNum = 0;
		
		for (Long tmpCurrentClusterId : _userClusters) { // foreach cluster
			tmpCurrentClusterGroup = _userGroupDAO.findGroupById(tmpCurrentClusterId);
			updateDissimilarMatrix(tmpCurrentClusterGroup, tmpClusterNum);
			
			if (MIN_GROUP_SIZE <= tmpCurrentClusterGroup.get_userIds().size()) {
				// Create the initial vector
				ICombinatoricsVector<Long> initialVector = Factory.createVector(tmpCurrentClusterGroup.get_userIds());
				createCombinatorialUserGroup(initialVector, SIMILAR_PREFIX);
			}
			tmpClusterNum++;
		}
	}
	
	private void createCombinatorialUserGroup(ICombinatoricsVector<Long> theInitialVector, String theDescriptionPrefix) {
		Map<Long, UserGroup> tmpGeneratedUserGroups = new HashMap<Long, UserGroup>();
		UserGroup tmpGeneratedUserGroup = null;
		Long tmpGeneratedGroupId = (long) 0;
		
		for (Integer tmpSize : GROUP_SIZES) {

			// Create a simple combination generator to generate
			// Combinations of the initial vector
			Generator<Long> gen = Factory.createSimpleCombinationGenerator(theInitialVector, tmpSize);

			// Get some possible combinations
			Iterator<ICombinatoricsVector<Long>> tmpIterator = gen.iterator();
			ICombinatoricsVector<Long> tmpCombination;

			for (int j = 0; j < GROUP_PER_TYPE; j++) {
				tmpCombination = tmpIterator.next();
				System.out.println(tmpCombination);
				tmpGeneratedUserGroup = createGeneratedGroup(tmpGeneratedGroupId, tmpCombination.getVector(), theDescriptionPrefix + tmpSize);
				tmpGeneratedUserGroups.put(tmpGeneratedGroupId,tmpGeneratedUserGroup);
				tmpGeneratedGroupId++;
			}
		}
		_userGroupDAO.insertGroups(tmpGeneratedUserGroups);
	}
}
