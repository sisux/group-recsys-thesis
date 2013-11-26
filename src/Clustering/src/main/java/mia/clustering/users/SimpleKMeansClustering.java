package mia.clustering.users;

/*
 * Invocation using Java involves supplying the following arguments:

input: a file path string to a directory containing the input data set a SequenceFile(WritableComparable, VectorWritable). The sequence file key is not used.
clusters: a file path string to a directory containing the initial clusters, a SequenceFile(key, Cluster | Canopy). Both KMeans clusters and Canopy canopies may be used for the initial clusters.
output: a file path string to an empty directory which is used for all output from the algorithm.
distanceMeasure: the fully-qualified class name of an instance of DistanceMeasure which will be used for the clustering.
convergenceDelta: a double value used to determine if the algorithm has converged (clusters have not moved more than the value in the last iteration)
maxIter: the maximum number of iterations to run, independent of the convergence specified
runClustering: a boolean indicating, if true, that the clustering step is to be executed after clusters have been determined.
runSequential: a boolean indicating, if true, that the k-means sequential implementation is to be used to process the input data.
After running the algorithm, the output directory will contain:

clusters-N: directories containing SequenceFiles(Text, Cluster) produced by the algorithm for each iteration. The Text key is a cluster identifier string.
clusteredPoints: (if --clustering enabled) a directory containing SequenceFile(IntWritable, WeightedVectorWritable). The IntWritable key is the clusterId. The WeightedVectorWritable value is a bean containing a double weight and a VectorWritable vector where the weight indicates the probability that the vector is a member of the cluster. For k-Means clustering, the weights are computed as 1/(1+distance) where the distance is between the cluster center and the vector using the chosen DistanceMeasure.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mia.clustering.users.helper.ClusterHelper;
import mia.clustering.users.model.ClusterManager;
import mia.clustering.users.model.UserGroup;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.Vector;

public class SimpleKMeansClustering {

		private void createClusters() throws IOException, ClassNotFoundException, InterruptedException {
			List<Vector> sampleData = new ArrayList<Vector>();

            VectorManager tmpVectorManager = new VectorManager();
            sampleData = tmpVectorManager.getUserVectors();
            
            double k = Math.sqrt(943);
            File testData = new File("input");
            if (!testData.exists()) {
              testData.mkdir();
            }

            DistanceMeasure measure = new EuclideanDistanceMeasure();
            Path samples = new Path("input/file1");
            Path output = new Path("output");
            Configuration conf = new Configuration();
            HadoopUtil.delete(conf, samples);
            HadoopUtil.delete(conf, output);
            
            ClusterHelper.writePointsToFile(sampleData, conf, samples);
            Path clustersIn = new Path(output, "random-seeds");
            RandomSeedGenerator.buildRandom(conf, samples, clustersIn, (int) k, measure);

            KMeansDriver.run(samples, clustersIn, output, measure, 0.01, 10, true, 0.0, true);

            List<List<Cluster>> Clusters = ClusterHelper.readClusters(conf, output);
            ClusterManager tmpClusterManager = new ClusterManager();
            Map<Integer, UserGroup> tmpUserGroups = tmpClusterManager.get_userGroups();
            
            UserGroup tmpUserGroup;
            for (Cluster cluster : Clusters.get(Clusters.size() - 1)) {
                    System.out.println("Cluster id: " + cluster.getId() + " center: "
                                    + cluster.getCenter().asFormatString());
                    System.out.println("Num observations: " + cluster.getNumObservations());
                    tmpUserGroup = new UserGroup();
                    tmpUserGroup.set_id(cluster.getId());
                    tmpUserGroup.set_centroid(cluster.getCenter().asFormatString());
                    tmpUserGroup.set_description("Initial Clustering Group");
                    tmpUserGroups.put(Integer.valueOf(cluster.getId()), tmpUserGroup);
            }

            writeClusterRelations(conf, tmpUserGroups);
            tmpClusterManager.saveGroups();
            System.out.println(tmpUserGroups.toString());

		}
	
		private void createRecSysUserGroups() {
        	RecsysGroupFormator tmpGroupFormator = new RecsysGroupFormator();
        	tmpGroupFormator.createUserGroups();
		}


        public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        	SimpleKMeansClustering tmpMain = new SimpleKMeansClustering();
        	
        	// Create the initial user clusters from their profile
        	//tmpMain.createClusters();
        	
        	// Create the user groups from both similar and dissimilar clusters
        	//tmpMain.createRecSysUserGroups();
        }


		private static void writeClusterRelations(Configuration conf, Map<Integer, UserGroup> theUserGroups)
				throws IOException {
			FileSystem fs = FileSystem.get(conf);
            SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path("output/" + Cluster.CLUSTERED_POINTS_DIR
            							+ "/part-m-0"), conf);
            IntWritable clusterId = new IntWritable();
            WeightedVectorWritable vector = new WeightedVectorWritable();
            NamedVector nVec;
            UserGroup tmpUserGroup;
            while (reader.next(clusterId, vector)) {
            	nVec = (NamedVector)vector.getVector();
            	System.out.println(nVec.getName() + " belongs to cluster " + clusterId.toString());
            	tmpUserGroup = theUserGroups.get(Integer.valueOf(clusterId.get()));
            	tmpUserGroup.get_userIds().add(Integer.valueOf(nVec.getName()));
            }
            reader.close();
		}
}