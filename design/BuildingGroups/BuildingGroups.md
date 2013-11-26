Building Groups
===============

The first step to be able to recommend to groups, is to actually have groups.

As our test datasets do not incorporate the *groups* notion, we must be able to create them from the available user information.

--------MIA
Clustering helps uncover interesting groups of information in a large volume of data.
What we consider as a cluster depends entirely on the traits we choose for measuring the similarity between items.

Clustering is all about organizing items from a given collection into groups of similar items. 
These clusters could be thought of as sets of items similar to each other in some ways but dissimilar from the items belonging to other clusters.

Clustering a collection involves three things:
1. An algorithm
2. A notion of both similarity and dissimilarity
3. A stopping condition

Clusters are also defined by a center point and radius. The center of the circle is called the *centroid*, or *mean* (average), of that cluster. It’s the point whose coordinates are the average of the x and y coordinates of all points in the cluster.


--------END MIA

## Mahout Clustering

The full list of clustering algorithms available in Mahout are K-Means, Canopy, Fuzzy K-Means, LDA and Dirichlet. All these algorithms expect data in the form of vectors, so the first step is to convert the input data into this format, a process known as vectorization.

Essentially, clustering is the process of finding nearby points in n-dimensional space, where each vector represents a point in this space, and each element of a vector represents a dimension in this space.

Once vectorized, one can invoke the appropriate algorithm. All the algorithms require the initial centroids to be provided, and the algorithm iteratively perturbes the centroids until they converge. One can either guess randomly or use the Canopy clusterer to generate the initial centroids.

Finally, the output of the clustering algorithm can be read using the Mahout cluster dumper subcommand. To check the quality, take a look at the top terms in each cluster to see how "believable" they are. Another way to measure the quality of clusters is to measure the intercluster and intracluster distances. A lower spread of intercluster and intracluster distances generally imply "good" clusters. Here is code to calculate inter-cluster distance based on code from the MIA book.

Cluster quality is also dependent on the measure used to calculate similarity between two feature vectors. Once again, Mahout supplies a large number of Distance Measure implementations (Chebyshev, Cosine, Mahalanobis, Manhattan, Minkowski, SquaredEuclidean, Euclidean, Tanimoto, Weighted Euclidean and Weighted Manhattan) and also allows you to specify your own if these don't suit your purposes. Within each dimension, points can be normalized to remove the effect of outliers - the normalization p-norm should match the p-norm used by the distance measure. Finally, if the dimensions are not comparable, such as number of bedrooms and price in dollars for a house, then one should normalize across dimensions, a process known as weighting (this should be done during the vectorization process, which you control fully).

Clustering Steps
1. Prepare Input: Convert the input it to a vector of doubles and normalize
	* DenseVector(if most values for most vectors are non-zero)
	* SparseVector(generally text)
2. Run Clustering: •Run the clustering algorithm
3. Evaluate Results
	* Evaluate the results
	* Iterate if necessary

-----MIA chpt 8

Vectorization: the process of representing objects as Vectors. A Vector is a very simplified representation of data that can help clustering algorithms understand the object and help compute its similarity with other objects.

The process of selecting the features of an object and mapping them to numbers is known as *feature selection*.
The process of encoding features as a vector is *vectorization*.

Normalization, here, is a process of cleaning up edge cases—data with unusual characteristics
that skew results disproportionately
This process of decreasing the magnitude of large
vectors and increasing the magnitude of smaller vectors is called normalization

For best results, the normalization ought to relate to the notion
of distance used in the similarity metric.

---- END MIA

## Clustering movieLens users

The aim is to create groups of users based on a similarity degree and constrained for a parametrized size value.

In order to compute a similarity function, the system must previously contain information of the users in a vectorized way.

This information, is somehow, a profile of the users that enables to classify them in groups.

### MovieLens User profiles

The information provided by movielens is the following:

* u.item     -- Information about the items (movies); this is a tab separated
              list of
              movie id | movie title | release date | video release date |
              IMDb URL | unknown | Action | Adventure | Animation |
              Children's | Comedy | Crime | Documentary | Drama | Fantasy |
              Film-Noir | Horror | Musical | Mystery | Romance | Sci-Fi |
              Thriller | War | Western |
              The last 19 fields are the genres, a 1 indicates the movie
              is of that genre, a 0 indicates it is not; movies can be in
              several genres at once.
              The movie ids are the ones used in the u.data data set.
* u.user     -- Demographic information about the users; this is a tab
              separated list of
              user id | age | gender | occupation | zip code
              The user ids are the ones used in the u.data data set.

We build a profile based on:

create a view:

user x rating x item



An then, built a vector using:

---------------------------

## Similarity, Dissimilarity and Distance

Similarity is a characterization of the ratio of the number of attributes two objects share in common compared to the total list of attributes between them. 
Objects which have everything in common are identical, and have a similarity of 1.0. 
Objects which have nothing in common have a similarity of 0.0. 

Dissimilarity is the complement of similarity, and is a characterization of the number of attributes two objects have uniquely 
compared to the total list of attributes between them. In general, dissimilarity can be calculated as 1 - similarity.

Distance is a geometric conception of the proximity of objects in a high dimensional space defined by measurements on the attributes.

In practice, distances and dissimilarities are sometimes used interchangeably.
They have quite distinct properties, however. 
Dissimilarities are bounded [0,1]; Distances are unbounded on the upper end;.

Shorter distances indicate more similarity between the vectors and vice versa; similarity and distance are related concepts.

-----------------------------

I want to cluster my data to say 5 clusters, then we need to select 50 individuals with most dissimilar relationship from all the data. 
That means if :
 - cluster one contains 100 (10%)
 - two contains 200         (20%)
 - three contains 400       (40%)
 - four contains 200        (20%)
 - and five 100             (10%)

I have to select:
 - 5 from the first cluster   (50 individuals x 10%) 
 - 10 from the second cluster (50 individuals x 20%) 
 - 20 from the third          (50 individuals x 40%) 
 - 10 from the fourth         (50 individuals x 20%) 
 - 5 from the fifth           (50 individuals x 10%) 
 

=> x grups disimilars de y persones
 => fer y clusters
 => per crear un grup => agafar 1 user de cada cluster
 
-------------------------------------
Carvalho, L. A. M. C., Cristóvão, S., & Macedo, H. T. (2013). Users ’ Satisfaction in Recommendation Systems for Groups : an Approach Based on Noncooperative Games, 951–958.

In order to deal with the absence of groups in the MovieLens dataset, 
the users have been automatically grouped with K-Means clustering, with $K = \sqrt{943} = 30 aprox $ and randomly generated initial centroid. 
Users of these clusters were randomly selected to form two kind of groups: 
(i) homogeneous group, with users from same cluster
(ii) heterogeneous group, with users from different clusters. 

--------------------------------------

From 30 clusters, we get:
	* 784 Similar groups of size 3 (28 clusters x 28 combinations of 3 items)
	* 784 Similar groups of size 5 (28 clusters x 28 combinations of 5 items)
	* 784 Similar groups of size 7 (28 clusters x 28 combinations of 7 items)
	* 840 Dissimilar groups of size 3 (30 clusters x 28 combinations of 3 items)
	* 840 Dissimilar groups of size 5 (30 clusters x 28 combinations of 5 items)
	* 840 Dissimilar groups of size 7 (30 clusters x 28 combinations of 7 items)
