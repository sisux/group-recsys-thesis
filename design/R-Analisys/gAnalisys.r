#******* Libraries required ************
library(RColorBrewer)
library(lattice)

#******* Preprocessing *****************

#Read the CSV group results file
gresults = read.csv("./data/test.csv", header = TRUE, sep = ";", dec = ",")

#Create 'type' and 'size' columns from the original 'kind' (ej: S3, S5, S7, D3, R3)
gresults$type = ifelse(substr(gresults$kind, 1, 1) != "S", ifelse(substr(gresults$kind, 1, 1) == "D", 'Dissimilar', 'Random'), 'Similar')
gresults$size = as.numeric(substr(gresults$kind, 2, 2))

#Factorize the attribute 'size'
gresults$size <- as.factor(gresults$size)

#Remove unused columns
gresults <- gresults[,!(names(gresults) %in% c("kind","service"))]

#Summarize and split data by 'type' and 'size'
gresults.MAE <- aggregate(list(value=gresults$mae), by=list(type=gresults$type, size=gresults$size, strategy=gresults$strategy), FUN=mean) 
gresults.RMSE <- aggregate(list(value=gresults$rmse), by=list(type=gresults$type, size=gresults$size, strategy=gresults$strategy), FUN=mean) 

#******* Plottering ********************

# *** Plottering - General properties

mycolorpalette <- brewer.pal(4, "PuBu")

# *** Plottering - Bar chart of MAE by group Sizes and Strategies for only Similar groups

#Creation of typed data frames, one for each
gresults.MAE.Similar = subset(gresults.MAE, type == "Similar", select=c(strategy, size, value))
gresults.MAE.Dissimilar = subset(gresults.MAE, type == "Dissimilar", select=c(strategy, size, value))
gresults.MAE.Random = subset(gresults.MAE, type == "Random", select=c(strategy, size, value))

#Convert data framte to mateix, summarizing by 'value' field
gresults.MAE.Similar.table <- xtabs(gresults.MAE.Similar$value ~ ., gresults.MAE.Similar)

#Initialize png export
png(filename="plot.png", res=120)

#Plotting
o<-par(xpd=T, mar=par()$mar+c(0,0,0,4))
barplot(gresults.MAE.Similar.table, ylim=c(0,1.0), xlab="Group Size", beside=T, col=mycolorpalette)
legend(9.5,0.7, legend=rownames(gresults.MAE.Similar.table), cex=0.6, bty="n", fill=mycolorpalette)
title("MAE for Similar Groups", cex.main = 2, font.main= 2, col.main= "blue")
par(o)

#Finalize plot and png export
dev.off()

# *** Plottering - Line plot of MAE by group Sizes and Strategies for only Similar groups

#Initialize png export
png(filename="plot2.png", res=120)

#Plotting
o<-par(xpd=T, mar=par()$mar+c(0,0,0,4))
xyplot( value ~ size, gresults.MAE.Similar, group=strategy, type='b', xlab="Group Size", col=mycolorpalette, lwd=3, pch=20, cex=3.0)
par(o)

#Finalize plot and png export
dev.off()
