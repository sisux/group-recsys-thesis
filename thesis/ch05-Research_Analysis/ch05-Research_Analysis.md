Research Analysis
=================

http://www.contentwise.tv/files/An-evaluation-Methodology-for-Collaborative-Recommender-Systems.pdf
 
 
The pair $(p_i, a_i)$ refers to the prediction on the i-th test instance and the corresponding actual value given by the active user
 
 
Mean Squared Error (MSE)
 
$$MSE = \frac{1}{n}\sum_{i}^{n} (p_i - a_i)^2 $$
 
 
Root Mean Squared Error (RMSE)
 
$$RMSE = \sqrt{\frac{1}{n}\sum_{i}^{n} (p_i - a_i)^2} $$
 
Mean Absolute Error (MAE):
 
$$MAE = \frac{1}{n}\sum_{i}^{n} |p_i - a_i| $$
 
 
 
With classification metrics we can classify each recommendation such as:
a) true positive (TP, an interesting item is recommended to the user)
b) true negative (TN, an uninteresting item is not recommended to the user)
c) false negative (FN, an interesting item is not recommended to the user)
d) false positive (FP, an uninteresting item is recommended to the user)
 
Precision and recall are the most popular metrics in the information retrieval field. They have been adopted, among the others, by Sarwar et al. [17, 18] and Billsus and Pazzani [5]. According to [1], information retrieval applications are characterized by a large amount of negative data so it could be suitable to measure the performance of the model by ignoring instances which are correctly “not recommended” (i.e., TN).
 
It is possible to compute the metrics as follows:
 
 
$$precision = \frac{TP}{TP+FP}$$
 
$$recall = \frac{TP}{TP+FN}$$
 
 
These metrics are suitable for evaluating tasks such as top-N recommendation.
When a recommender algorithm predicts the top-N items that a user is expected to find interesting, by using the recall we can compute the
percentage of known relevant items from the test set that appear in the N predicted items. Basu et al. [2] describe
a better way to approximate precision and recall in top-N recommendation by considering only rated items.
According to Herlocker et al. [12], we must consider that:
 
• usually the number of items rated by each user is much smaller than the items available in the entire dataset;
• the number of relevant items in the test set may be much smaller than that one in the whole dataset.
 
Therefore, the value of the precision and the recall depend heavily on the number of rated items per user and,
thus, their values should not be interpreted as absolute measures, but only to compare different algorithms on
the same dataset.
 
F-measure, used in [18, 17], allows a single measure that combines precision and recall by means of the following
relations:
 
$$F-mesure = \frac{2 \cdot recall \cdot precision}{recall + precision}$$
 
Receiver Operating Characteristic (ROC) is a graphical technique that uses two metrics, true positve rate (TPR) and
false positive rate (FPR) defined as
 
$$TPR = \frac{TP}{TP + FN}$$
 
$$FPR = \frac{FP}{FP + TN}$$
 
to visualize the trade-off between TPR and FPR by varying the length $N$ of the list returned to the user. On the
vertical axis the ROC curves plot the TPR, i.e., the number of the instances recommended related to the total
number of relevant ones, against the FPR, i.e., the ratio between positively misclassified instances and all the not
relevant instances. Thus, by gradually varing the threshold and by repeating the classification process, we can
obtain the ROC curve which visualizes the continuous trade-off between TPR and FPR.