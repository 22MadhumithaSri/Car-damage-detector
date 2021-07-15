
import os
os.environ['GLOG_minloglevel'] = '2'
import  os, sys, cv2
import argparse
import shutil
import sys ,os
from os import listdir
from os.path import isfile, join
#from tqdm import tqdm
import json

CLASSES = ('__background__', "scratch", "dent", "broken", "crack", "chip", "stain")

def read_json(jsondict):
    flag=True
    #jsonfile=open(jsonpath)
    #jsondict=json.loads(jsonfile.read())
    fname=jsondict['images'][0]['source']['filename']
    width = int(jsondict['images'][0]['dimensions']['width'])
    height = int(jsondict['images'][0]['dimensions']['height'])
    labelledObjects=dict()
    print(fname)
    if len(jsondict['images'][0]['objects'])==0:
         return False
    objects=jsondict['images'][0]['objects']['collections'][0]['objects']
    for obj in objects:
         name=obj['object']
         top=obj['location']['top']
         left = obj['location']['left']
         obj_width = obj['location']['width']
         obj_height = obj['location']['height']
         xmin = left
         ymin = top
         ymax = ymin+obj_height
         xmax = xmin+obj_width
       
         if int(ymin) >= int(height) or int(ymax) >= int(height) or int(xmin) >= int(xmax) or int(ymin) >= int(ymax) or int(xmin) >= int(width) or int(xmax) >= int(width) :
              flag = False
              continue

         if int(xmin) <=0 or int(ymin) <=0 or int(xmax) <=0 or int(ymax) <=0:
              flag = False
              continue

         if (int(xmax)- int(xmin))*(int(ymax)-int(ymin)) <= 0:
              flag = False
              continue

         box = [xmin, ymin, xmax, ymax]
         bb = []
         for b in box:
            if int(b)  ==0:
                b = 1
            bb.append(b)
         box = (bb[0], bb[1], bb[2] , bb[3])
         if name not in labelledObjects:
            labelledObjects[name] = []
            labelledObjects[name].append(box)
         else:
            labelledObjects[name].append(box)
      
    if len(labelledObjects.keys())== 0:
        return False

    return labelledObjects

def drawObjects(labelledObjects, im):
    for obj in labelledObjects:
        print(labelledObjects[obj])
        for box in labelledObjects[obj]:
            print(box)
            x1, y1, x2, y2 = [int(b) for b in box]
            im = cv2.rectangle(im, (x1, y1), (x2, y2), (255,0,0), 2)
            font = cv2.FONT_HERSHEY_SIMPLEX 
            fontScale = 1
            color = (255, 0, 0) 
            thickness = 2
            im = cv2.putText(im, obj, (x1, y1), font,  
                   fontScale, color, thickness, cv2.LINE_AA) 
        
    return im
        


def drawing_json(filepath,result):
    image_path = filepath
    labelledObjects = read_json(result)
    if labelledObjects is False:
        im = cv2.imread(image_path)
        print(image_path)
        print(filepath)
        cv2.imwrite("E:/SRP/data1a/training/00-damage/0006.jpeg", im)
        result_path = "E:/SRP/data1a/training/00-damage/0006.jpeg"
        return result_path
    else:
        im = cv2.imread(image_path)
        im = drawObjects(labelledObjects, im)
        cv2.imwrite("E:/SRP/data1a/training/00-damage/0006.jpeg", im)
        result_path = "E:/SRP/data1a/training/00-damage/0006.jpeg"
        return result_path




