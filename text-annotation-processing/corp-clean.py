#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Replace matched regular expressions with spaces
"""

import re, glob, os

path = '../GATE/resources/testdata/text' # path for text input files
path_out = '../GATE/resources/testdata/textClean/' # path for outputting the cleaned text

# replaces the matched string with spaces 
def spacr(matchobj):
    s = ''
    for char in matchobj.group(0):
        if char == '\n':
            s = s + '\n'
        else: 
            s = s + ' '
    return s


for filename in glob.glob(os.path.join(path, '*.txt')):
    print(filename)
    try:
        f = open(filename, 'r').read()
    except UnicodeDecodeError:
        print('Skipped: ' + filename)
        continue
    # uncomment if processing the files named below
    #if 'PMC2828076.txt' in filename or '18214200' in filename or 'PMC4861906.txt' in filename:
     #   o = open(path_out + filename.split('/')[-1], 'w')
      #  o.write(re.sub(r'(\b\w\w?\/((\w*\s?\w*?)\/){1,6}\w*)|([A-Z]{2}(\/|-)?\d{2})|(\w*\set\sal\.)', spacr, f))
       # continue
    o = open(path_out + filename.split('/')[-1], 'w')
    o.write(re.sub(r'(\b\w\w?\/((\w*\s?\w*?)\/){1,6}\w*)|([A-Z]{2}(\/|-)?\d{2})|' +
                   '(\w*\set\sal\.)|' + 
                   '((REFERENCES|References)\s*(\[?\d\d?\d?(\.|\])(\s|.)*)*)|' +
                   '((REFERENCES)\s*((\s|.)*))', spacr, f))
    
