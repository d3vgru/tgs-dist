This archive was built starting with:

	./distribute-tgs.sh

using

	https://github.com/d3vgru/python-for-android

From within the resulting dist/default/ (the core of this repo), execute:

	./build-apk.sh

to build an apk in bin/ such as AndroidTGS-0.1-debug.apk. You can install this onto a device using:

	adb install bin/AndroidTGS-0.1-debug.apk

or:

	adb install -r bin/AndroidTGS-0.1-debug.apk

to reinstall.

The dist/default dir from the first step was modified by manually merging

	https://github.com/rauljim/tgs-android

and

	https://github.com/whirm/tgs-pc

and adding a native Android UI and Java<->Python bindings. Also, the Python side was implemented as a service so the native Android UI could be employed. Probably.

Currently, .egg files produced by setuptools are unsupported, so they need to be manually extracted in the same directory, ie private/lib/python2.7/site-packages -- mainly for m2crypto and netifaces. For m2crypto, the M2Crypto dir needs to be moved to site-packages or ssl_dispatcher error if you

	import M2Crypto
