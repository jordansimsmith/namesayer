#!/bin/bash

# delete existing build
rm -rf build/

# make new build folder
mkdir build/

# copy files
cp -r src/ build/
cp out/artifacts/namesayer_part03_jar/namesayer-part03.jar build/namesayer.jar
rm -rf build/src/test
cp README.md build/

# copy names database
cp -r names/ build/

# zip files
cd build/
zip -r namesayer.zip *
