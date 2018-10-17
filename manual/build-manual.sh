#!/bin/bash

cd manual/

# delete existing build
rm -rf build/

# make new build folder
mkdir build/

# compile latex file
pdflatex -output-directory build/ user-manual.tex
pdflatex -output-directory build/ user-manual.tex

# open pdf
xdg-open build/user-manual.pdf
