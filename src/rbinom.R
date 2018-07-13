
x<-c(rbinom(1000000,100,0.5))
sink("a.txt")
cat(x)
sink()
