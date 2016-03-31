#! /bin/bash
paper=paper
pdflatex $paper && bibtex $paper && pdflatex $paper && evince "paper.pdf"
