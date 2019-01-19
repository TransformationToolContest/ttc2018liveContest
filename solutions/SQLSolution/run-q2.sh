# setting this to 1 displays post/comment scores (to ease validation)
export SHOW_SCORES=1
# setting this to 1 hides running time (to ease validation)
export HIDE_RUNTIME=1

QUERY_NUM=2

OUTFILE_NAME="q${QUERY_NUM}.out"
OUTFILE_PATH=$(dirname $0)/build/${OUTFILE_NAME}
rm $OUTFILE_PATH

for s in 1 2 4 8 16 32 64 128 256 512 1024 ; do
  export MODEL_SIZE=$s
  echo "MODEL SIZE: $MODEL_SIZE" >>${OUTFILE_PATH}
  echo "==================" >>${OUTFILE_PATH}
  ./gradlew runContestDriver -PChangePath=../../models/$MODEL_SIZE -PQuery=Q${QUERY_NUM} -PSequences=20 -PRedirectOutput=${OUTFILE_NAME}
done
