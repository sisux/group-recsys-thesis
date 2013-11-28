
-- Item i núm de gent que l'ha votat dins del grup 40
SELECT r."itemId" as "Id", COUNT(gU."userId") as "popularity"
FROM "groupUsers" gU, "ratings" r
WHERE gU."groupId"=40 AND r."userId"=gU."userId"
GROUP BY r."itemId" ORDER BY "popularity" DESC
LIMIT 920;

-- => El grup 40 té 41 usuaris
SELECT COUNT(gU."userId")
FROM "groupUsers" gU
WHERE gU."groupId"=40

