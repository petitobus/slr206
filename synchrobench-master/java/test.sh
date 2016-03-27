#!/bin/bash
nthreads="hello";
lscpu > reseltat.txt;
for nt in 1 2 4 6 8;
do
    for ur in 0 10 100;
    do
        for lsi in 100 1000 10000
        do
            echo Number of threads: $nt;
            echo Update ratios: $ur;
            echo List sizes: $lsi;
            lsid=$((lsi+lsi));
            echo Ranges: $lsid;
            java -cp bin contention.benchmark.Test -b linkedlists.lockbased.CoarseGrainedListBasedSet -d 3000 -t $nt -u $ur -i $lsi -r $lisd >>reseltat.txt;
            java -cp bin contention.benchmark.Test -b linkedlists.lockbased.OptimisticListBasedSet -d 3000 -t $nt -u $ur -i $lsi -r $lisd >>reseltat.txt;
            java -cp bin contention.benchmark.Test -b linkedlists.lockbased..LazyLinkedListSortedSet -d 3000 -t $nt -u $ur -i $lsi -r $lisd >>reseltat.txt;
        done
    done;
done;

echo $nthreads;
