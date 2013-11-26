
-- Report of UserGroups
SELECT COUNT("Id"), description
  FROM groups where centroid is null
  GROUP BY description;

-- Remove all UserGroups
DELETE FROM "groupUsers" WHERE "groupId" IN (SELECT "Id" FROM "groups" WHERE "centroid" IS NULL);
DELETE FROM groups WHERE "centroid" is null;

-- Get the clusters with more than 9 items
SELECT COUNT("userId") > 9, "groupId"
  FROM "groupUsers" 
  WHERE "groupId" IN (SELECT "Id" FROM "groups" WHERE "centroid" IS NOT NULL)
  GROUP BY "groupId";