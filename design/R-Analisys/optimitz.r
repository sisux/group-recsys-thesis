#******* Libraries required ************
library(RColorBrewer)
library(lattice)

#******* Preprocessing *****************

#Read the CSV group results file
gresults = read.csv("./data/test.csv", header = TRUE, sep = ";", dec = ",")

#Create 'type' and 'size' columns from the original 'kind' (ej: S3, S5, S7, D3, R3)
gresults$type = ifelse(substr(gresults$kind, 1, 1) != "S", ifelse(substr(gresults$kind, 1, 1) == "D", 'Dissimilar', 'Random'), 'Similar')
gresults$size = as.numeric(substr(gresults$kind, 2, 2))

#Factorize the both new attributes
gresults$size <- as.factor(gresults$size)
gresults$type <- as.factor(gresults$type)

#Remove unused columns
gresults <- gresults[,!(names(gresults) %in% c("kind","service","itemstorec"))]

#******* Plottering ********************

# *** Plottering - General properties

mycolorpalette <- brewer.pal(4, "PuBu")

# *** Plottering - Functions

allMetricsAnalisys <- function() {
	# Existing check
	if(!exists("gresults", envir=.GlobalEnv)) {
        stop("Variable 'gresults' does not exist in the global environment\n") 
    } 
	
	gtypes <- levels(gresults$type)
	for (itype in gtypes) {
		#metricBarplot("mae", itype)
		metricXYplot("rmse", itype)
	}
}

metricBarplot <- function(theMetric, theType) {
	# Local variables
	plot.title <- paste(toupper(theMetric),"for",theType,"Groups", sep=' ')
	plot.filename <- paste("bp-",toupper(theMetric),"-",theType,".png", sep='')

	#Summarize and split data by 'type' and 'size'
	m_agg <- aggregate(list(value=gresults[,theMetric]), by=list(type=gresults$type, size=gresults$size, strategy=gresults$strategy), FUN=mean) 

	#Creation of typed data frames, one for each
	m_agg_type = subset(m_agg, type == theType, select=c(strategy, size, value))

	#Convert data frame to matrix, summarizing by 'value' field
	m_agg_type.table <- xtabs(m_agg_type$value ~ ., m_agg_type)

	#Initialize png export
	png(filename=plot.filename, res=120)

	#Plotting
	o<-par(xpd=T, mar=par()$mar+c(0,0,0,4))
	barplot(m_agg_type.table, ylim=c(0,1.0), xlab="Group Size", beside=T, col=mycolorpalette)
	legend(9.5,0.7, legend=rownames(m_agg_type.table), cex=0.6, bty="n", fill=mycolorpalette)
	title(plot.title, cex.main = 1, font.main= 2, col.main= "blue")
	par(o)

	#Finalize plot and png export
	dev.off()
}

metricXYplot <- function(theMetric, theType) {
	# Local variables
	plot.title <- paste(toupper(theMetric),"for",theType,"Groups", sep=' ')
	plot.filename <- paste("xy-",toupper(theMetric),"-",theType,".png", sep='')

	#Summarize and split data by 'type' and 'size'
	m_agg <- aggregate(list(value=gresults[,theMetric]), by=list(type=gresults$type, size=gresults$size, strategy=gresults$strategy), FUN=mean) 

	#Creation of typed data frames, one for each
	m_agg_type <- subset(m_agg, type == theType, select=c(strategy, size, value))

	#Initialize png export
	png(filename=plot.filename)

	#Plotting
	o<-par(xpd=T, mar=par()$mar+c(0,0,0,4))
	xyplot( value ~ size, m_agg_type, group=strategy, type='b', xlab="Group Size", col=mycolorpalette, lwd=3, pch=20, cex=3.0)
	par(o)

	#Finalize plot and png export
	dev.off()
}

allMetricsAnalisys()
