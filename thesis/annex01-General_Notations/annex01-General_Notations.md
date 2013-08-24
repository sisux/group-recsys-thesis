Annex I - Mathematical Notations
================================

* Let $U = \lbrace u_{1},\ldots,u_{n}\rbrace$ be a set of _users_, where $u_{i}$ represents the *i*th user and $n$ the number of users, $\left\vert{U}\right\vert = n$.
* Let $P = \lbrace p_{1},\ldots,p_{m}\rbrace$ be a set of _products_ or _items_ for recommendation, where $p_{j}$ is the *j*th product and $m$ the number of items, $\left\vert{P}\right\vert = m$. 
Depending on the recommender algorithm, $P$ will be the whole set of products or a subset of them. 
* Let $R$ be the _Utility_ or _Rating Matrix_; defined as an $n \times m$ matrix of _ratings_ or _degrees of preference_ $r_{i,j}$, with $i \in \lbrace 1,\ldots,n\rbrace$ and $j \in \lbrace 1,\ldots,m\rbrace$. Values come from an ordered set (e.g., integers 1–5, representing the number of stars that the user gave as a rating for that item). We assume that the matrix is **sparse**, meaning that most entries are “unknown.” An unknown rating $r_{i,j}$ implies that the user $i$ has no explicit information about preference for the item $j$.
* _Sparsity Problem_: In any recommender system, the number of ratings already obtained is usually very 
small compared to the number of ratings that need to be predicted.
* _Cold-Start Problem_: problem that occurs in content-based filtering and in social filtering due to no information about a new user.
