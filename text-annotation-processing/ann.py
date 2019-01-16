#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Create .ann files for evaluation from predictions.tsv
"""

path_preds = '/predictions-lstm.tsv' # path to predicitons file
path_out = '/ann/' # path to .ann files

with open(path_preds, 'r') as preds:
    fnam, c = '', 0
    for line in preds:
        line, c = line.split('\t'), c + 1
        if fnam != line[5]:
            fnam, c = line[5], 0
        if line[-1].replace('\n', '') == '1':
            with open(path_out + fnam + '.ann', 'a') as out:
                out.write('T' + str(c) + '\t' + 'Location' + ' ' + line[2] + 
                          ' ' + line[3] + '\t' + line[4].replace(' ', '', 1) + '\n')
                out.write('#' + str(c) + '\t' + 'AnnotatorNotes T' + str(c) + 
                          '\t' + '<latlng></latlng><pop></pop><geoID></geoID>' + '\n')
