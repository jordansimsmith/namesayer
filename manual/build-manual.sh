#!/bin/bash

cd manual/

# delete existing build
rm -rf build/

# make new build folder
mkdir build/

# copy source
cp user-manual.tex build/
cp -r images/ build/

cd build/

# compile latex file
pdflatex -shell-escape user-manual.tex
pdflatex -shell-escape user-manual.tex

# open pdf
xdg-open user-manual.pdf
