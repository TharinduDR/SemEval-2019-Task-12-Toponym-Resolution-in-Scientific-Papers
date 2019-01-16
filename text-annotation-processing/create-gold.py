#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Create gold standard file for GATE
"""
import glob, os

path = '../GATE/resources/traindata/annotation' # path to annotation files
path_out = '../GATE/resources/traindata/LocationGold.txt' # path to gold standard output



for filename in glob.glob(os.path.join(path, '*.ann')):
    print(filename)
    with open(filename, 'r') as i, open(path_out, 'a') as o:
        for line in i:
            l = line.split('\t')
            if 'Location' in l[1]:
                ll = l[1].split(' ')
                if 'PMC' in filename.split('/')[-1]:
                    begin = int(ll[1])
                    end = int(ll[-1])
                else:
                    begin = int(ll[1]) - 1
                    end = int(ll[-1]) - 1
                o.write(filename.split('/')[-1] +'\t'+ str(begin) +'\t'+ 
                        str(end) +'\t'+ l[2].replace('\n', '') +'\t'+ ll[0] +'\n')
