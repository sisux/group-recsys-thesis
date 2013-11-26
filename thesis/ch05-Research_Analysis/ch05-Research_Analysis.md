Research Analysis
=================

http://www.contentwise.tv/files/An-evaluation-Methodology-for-Collaborative-Recommender-Systems.pdf
 

The pair $(p_i, a_i)$ refers to the prediction on the i-th test instance and the corresponding actual value given by the active user
 
## Mean Squared Error (MSE)
 
$$MSE = \frac{1}{n}\sum_{i}^{n} (p_i - a_i)^2 $$
 
## Root Mean Squared Error (RMSE)
 
$$RMSE = \sqrt{\frac{1}{n}\sum_{i}^{n} (p_i - a_i)^2} $$
 
## Mean Absolute Error (MAE):
 
$$MAE = \frac{1}{n}\sum_{i}^{n} |p_i - a_i| $$



With classification metrics we can classify each recommendation such as:
	a) true positive (TP, an interesting item is recommended to the user)
	b) true negative (TN, an uninteresting item is not recommended to the user)
	c) false negative (FN, an interesting item is not recommended to the user)
	d) false positive (FP, an uninteresting item is recommended to the user)
 
_Precision_ and _recall_ are the most popular metrics in the information retrieval field. 
They have been adopted, among the others, by Sarwar et al. [17, 18] and Billsus and Pazzani [5]. According to [1], information retrieval applications are characterized by a large amount of negative data so it could be suitable to measure the performance of the model by ignoring instances which are correctly “not recommended” (i.e., TN).
 
It is possible to compute the metrics as follows:
 
$$precision = \frac{TP}{TP+FP}$$
 
$$recall = \frac{TP}{TP+FN}$$
 
These metrics are suitable for evaluating tasks such as top-N recommendation.

When a recommender algorithm predicts the top-N items that a user is expected to find interesting, by using the *recall* we can compute the
percentage of known relevant items from the test set that appear in the N predicted items. Basu et al. [2] describe
a better way to approximate *precision* and *recall* in top-N recommendation by considering only rated items.
According to Herlocker et al. [12], we must consider that:
 
* usually the number of items rated by each user is much smaller than the items available in the entire dataset
* the number of relevant items in the test set may be much smaller than that one in the whole dataset
 
Therefore, the value of the _precision_ and the _recall_ depend heavily on the number of rated items per user and,
thus, their values should not be interpreted as absolute measures, but only to compare different algorithms on
the same dataset.

## F-mesure

F-measure, used in [18, 17], allows a single measure that combines precision and recall by means of the following
relations:
 
$$F-mesure = \frac{2 \cdot recall \cdot precision}{recall + precision}$$

## Receiver Operating Characteristic

Receiver Operating Characteristic (ROC) is a graphical technique that uses two metrics, true positive rate (TPR) and
false positive rate (FPR) defined as
 
$$TPR = \frac{TP}{TP + FN}$$
 
$$FPR = \frac{FP}{FP + TN}$$
 
to visualize the trade-off between TPR and FPR by varying the length $N$ of the list returned to the user. On the
vertical axis the ROC curves plot the TPR, i.e., the number of the instances recommended related to the total
number of relevant ones, against the FPR, i.e., the ratio between positively misclassified instances and all the not
relevant instances. Thus, by gradually varing the threshold and by repeating the classification process, we can
obtain the ROC curve which visualizes the continuous trade-off between TPR and FPR.

----------------------

\cite{Garcia:2012:PET:2109241.2109644}

Description of the measures for evaluating our GRS Recommender systems research has used several types of measures for evaluating the quality of the recommendations offered to individuals, such as _precision_, _recall_, or _mean absolute error_ (MAE).
However, to the best of our knowledge, there is not a widely accepted measure for evaluating GRSs. 

For this reason, we have adapted MAE to the group recommendation context because it is the most commonly used and is the easiest to interpret directly [56] when used for evaluating RSs. 

First, we define $MAE_{u}$, which gives a measure of the deviation of the recommendation for the group with respect to the
estimated values for a group member on his own. 

Given a recommendation list of $N$ items for a group $G$ such that $u \in G$, the mean absolute error for the user $u$ is defined as follows:

$$MAE^{u}=\frac
{\sum_{i=1}^{N}
	left\vert{
	d^{ui} - d^{Gi}
	}\right\vert
}
{N}$$

where $d^{ui}$ is the estimated degree of interest of the user u in the item i. 
This value is obtained by a single-user RS [21,60]. 

Therefore, $MAE^{u}$ indicates how adequate the group recommendation is for user $u$. 
The lower the $MAE^{u}$ is, the more accurate the group recommendation is for this user. 
Unlike individual recommendations, when dealing with groups, a very important issue is to obtain recommendations that
are as satisfactory as possible for all the group members, that is, the avoidance of misery. 

Therefore, our interest is to measure two aspects:

1. The *satisfaction of the group* as a whole, that is, the _accuracy_ of the group recommendation for all the group members. 
This is achieved by unifying the $MAE^{u}$ for each group member into a single measure; specifically, we define $MAE^{G}$ as the average of $MAE^{u}$ for all the members of the group. 
Therefore, a low $MAE^{G}$ indicates that the group as a whole is highly satisfied with the recommendation.

2. The degree to which the group members are equally satisfied with the recommendation. This is achieved by calculating the standard deviation (distance) on $MAE^{u}$ over all the group members, which we denote as $D^{G}$. 
A low distance represents that all the group members are equally satisfied. That is, this measure could be interpreted as the difference between the satisfaction of each group member with respect to the satisfaction of the other group members