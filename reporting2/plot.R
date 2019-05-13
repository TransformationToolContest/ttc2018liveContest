library(tidyverse)
library(plyr)

gm_mean = function(x, na.rm=TRUE) {
  exp(sum(log(x[x > 0]), na.rm=na.rm) / length(x))
}

nice_y_axis = function() {
  options(scipen=999)
  
  # y axis labels
  longticks = c(F, T, F, F, F, F, F, F, T)
  shortticks = 2:10
  range = -6:6
  
  ooms = 10^range
  
  ybreaks = as.vector(shortticks %o% ooms)
  ylabels = as.character(ybreaks * longticks)
  ylabels = gsub("^0$", "", ylabels)
  
  list(ybreaks = ybreaks, ylabels = ylabels)
}

## loading data

df = read_delim("output.csv", delim = ";")
df = df[df$MetricName == "Time", ]
df = df[df$PhaseName != "Initialization", ]
df$MetricValue = as.numeric(df$MetricValue)
# convert from nanoseconds to seconds
df$MetricValue = df$MetricValue / 10^9

# drop most tools for now
#df = df[df$Tool == "EMFSolutionViatra" | df$Tool == "SQLSolution", ]
df = df[df$Tool != "Naiad-1-Thread" &
        df$Tool != "Naiad-2-Thread" &
        df$Tool != "Naiad-4-Thread" &
        df$Tool != "Naiad-6-Thread", ]
#df$tool = factor(df$tool, levels=c(pgx, postgres, sparksee, sparql))
df$View = factor(df$View, levels=c("Q1", "Q2"))

# todo: aggregate RunIndex
df.aggregated = ddply(
  .data = df,
  .variables = c("Tool", "View", "ChangeSet", "PhaseName", "MetricName"),
  #.fun = colwise(mean),
  .fun = colwise(gm_mean),
  .progress = "text"
)

xbreaks = unique(df.aggregated$ChangeSet)
xlabels = paste(xbreaks, sep = "")
yaxis = nice_y_axis()
ggplot(df.aggregated, aes(x=ChangeSet, y=MetricValue, fill=Tool)) +
    geom_point(aes(col=Tool, shape=Tool)) +
    geom_line(aes(col=Tool, group=Tool)) +
    scale_x_log10(breaks=xbreaks) +  
    scale_y_log10(breaks=yaxis$ybreaks, labels=yaxis$ylabels) +
    scale_shape_manual(values = seq(0,24)) +
    facet_grid(View~PhaseName, drop=FALSE) +   #, scales="free_y") +
    xlab("Model size") +
    ylab("Execution time [s]") +
    theme_bw()
ggsave(file="results.pdf", width=250, height=170, units="mm")
