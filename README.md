This archive was built starting with:

	./distribute.sh -m "sdl m2crypto netifaces libswift"

using

	https://github.com/d3vgru/python-for-android

from within the resulting dist/default/ (the core of this repo), execute:

	./build-apk.sh

to build an apk in bin/ such as AndroidTGS-0.1-debug.apk .You can install this onto a device using:

	adb install bin/AndroidTGS-0.1-debug.apk

or:

	adb install bin/AndroidTGS-0.1-debug.apk

to reinstall.
