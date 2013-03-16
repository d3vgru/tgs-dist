#!/bin/bash
export TGS_VERSION=0.1
rm -f bin/AndroidTGS-$TGS_VERSION-debug.apk && ./build.py --package org.theglobalsquare.app --name "Android TGS" --orientation portrait --version $TGS_VERSION --dir python-src --sdk 14 --minsdk 9 debug | grep Sorry
