#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Extremely ugly code to extract the classified locations withtin a window of 3 
words L/R. The input is a manually created file that is a list of tokens and
locations including their respective indexes.
"""

path_in = '/list' # path to manually created file
path_cor = '/correct' # path to correctly classified instances
path_inc = '/incorrect' # path to incorrectly classified instances

import pandas as pd
with open(path_in, 'r') as f:
    data = f.readlines()
    data = [line.split('\t') for line in data]
    for item in data:
        if item[0] == 'Filen':
            continue
        meta = item[5].split('=')
        typ = meta[1].split(',')
        item[5] = meta[-1].replace('}', '').replace('\n', '')
        item.append(typ[0])
    df = pd.DataFrame(data)

df = df.loc[df[6]!='punctuation']
df = df.loc[df[6]!='symbol']

tmp = ['', '', '', '', '', '']
stack = [tmp for i in range(20)]
file = ''

with open(path_cor, 'a') as c, open(path_inc, 'a') as i:
    c.write('Window' + '\t' + 'Start' + '\t' + 'End' + '\t' + 'Word' + '\t' + 'File' + '\n')
    i.write('Window' + '\t' + 'Start' + '\t' + 'End' + '\t' + 'Word' + '\t' + 'File' + '\n')
    for index, row in df.iterrows():
        if row[0] == 'Filen':
            file = row[5].replace('\n', '')
            continue
        stack.append(row)
        del stack[0]
        if stack[9][0] == 'Location' and stack[9][1] == '':
            loc = stack[10]
            pos = stack[9]
            out_left = ''
            out_right = ''
            c = 0
            # if 2+ word loc
            if loc[3] < pos[3]:
                loc2 = ''
                for i in range(8, -1, -1):
                    if stack[i][0] == 'Token':
                        out_left = stack[i][5] + ' ' + out_left
                        c += 1
                    if c >= 3:
                        c = 0
                        break
                for i in range(11, 21):
                    if stack[i][0] == 'Token':
                        if c == 0:
                            if stack[i][3] >= pos[3]:
                                c = 1
                            loc2 = loc2 + ' ' + stack[i][5]
                            continue
                        out_right = out_right + ' ' + stack[i][5]
                        c += 1
                    if c >= 4:
                        c = 0
                        break
                loc2 = loc2.replace(' ', '', 1)
                if stack[11][1] == 'Evaluation':
                    c.write(out_left + loc[5] + ' '+ loc2 + out_right + '\t' + 
                            pos[2] + '\t' + pos[3] + '\t '+ loc[5] + ' ' + loc2 + '\t' + file + '\n')
                else:
                    i.write(out_left + loc[5] + ' '+ loc2 + out_right + '\t' + 
                            pos[2] + '\t' + pos[3] + '\t '+ loc[5] + ' ' + loc2 + '\t' + file + '\n')
            # if 1 word loc
            elif loc[3] == pos[3]:
                for i in range(8, -1, -1):
                    if stack[i][0] == 'Token':
                        out_left = stack[i][5] + ' ' + out_left
                        c += 1
                    if c >= 3:
                        c = 0
                        break
                for i in range(11, 21):
                    if stack[i][0] == 'Token':
                        out_right = out_right + ' ' + stack[i][5]
                        c += 1
                    if c >= 3:
                        c = 0
                        break
                if stack[11][1] == 'Evaluation':
                    c.write(out_left + loc[5] + out_right + '\t' + pos[2] + '\t' + 
                            pos[3] + '\t '+ loc[5] + '\t' + file + '\n')
                else:
                    i.write(out_left + loc[5] + out_right + '\t' + pos[2] + '\t' + 
                            pos[3] + '\t '+ loc[5] + '\t' + file + '\n')
        