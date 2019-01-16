#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'''
Create gazetteer.lst files for GATE from Geonames.txt
'''

path_in = '/cities.txt' # path to geoname file
path_out = '/gaz.lst' # path to gazetteer.lst file for GATE


with open(path_in, 'r') as r, open(path_out, 'a') as w:
# with open('/home/alistair/Desktop/semeval-test/countryInfo.txt', 'r') as r, open('/home/alistair/Desktop/semeval-test/countries.txt', 'a') as cou, open('/home/alistair/Desktop/semeval-test/capitals.txt', 'a') as cap:
    for line in r:
        line = line.split('\t')
        # this is for all.txt
        # line = [line[i] for i in [0,1,2,4,5,17]] # ID, Name1, Name2, Long, Lat, Country
        line = [line[i] for i in [1,2]] # Name1, Name2
        # o = line[0] + '\t' + line[2] + '\t' + line[1] + '\t' + line[3] + '\t' + line[4] + '\t' + line[5] + '\t'
        o = line[0] + '\n' + line[1]
        # cou.write(line[4] + '\n')
        # cap.write(line[5] + '\n')
        w.write(o + '\n')
        
