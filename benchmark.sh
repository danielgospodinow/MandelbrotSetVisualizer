#!/usr/bin/env bash

# Remove '\r from script
# "sed -i 's/\r$//' filename"

currentDir=$(pwd)
imageSize=$1
benchmarkFilePath=${currentDir}/benchmarkResults_${imageSize}.txt

cd ./out/artifacts/MandelbrotFractal_jar/

java -jar MandelbrotFractal.jar -t 1 > ${benchmarkFilePath}

for i in {2..32..2}
do
   java -jar MandelbrotFractal.jar -t ${i} >> ${benchmarkFilePath}
done