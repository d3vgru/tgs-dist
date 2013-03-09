'''
import kivy
kivy.require('1.0.9')
from kivy.uix.floatlayout import FloatLayout
from kivy.app import App
from kivy.properties import ObjectProperty, StringProperty
'''

import os
import M2Crypto

#class Controller(FloatLayout):
class Controller():
    '''Create a controller that receives a custom widget from the kv lang file.

    Add an action to be called from the kv lang file.
    '''
    
    ''' or not
    label_wid = ObjectProperty()
    otherlabel_wid = ObjectProperty()
    info = StringProperty()
    moreinfo = StringProperty()
    '''
    
    # handle button press
    def do_action(self):
		M2Crypto.Rand.rand_seed (os.urandom (1024))
		priv = "adfsdfsdfsdfsd"
		pub = "dfdffffffffffffffff"
		combinedkey = priv + '\n' + pub
		# You don’t need these for this code, but they’re faster in other instances
		privkey_bio = M2Crypto.BIO.MemoryBuffer(priv.encode('utf8'))
		pubkey_bio = M2Crypto.BIO.MemoryBuffer(pub.encode('utf8'))
		combinedkey_bio = M2Crypto.BIO.MemoryBuffer(combinedkey)
		SignEVP = M2Crypto.EVP.load_key_string(combinedkey)
		SignEVP.sign_init()
		SignEVP.sign_update('signing string')
		
		'''
		self.moreinfo = SignEVP.sign_final().encode('base64')
		
		self.label_wid.text = 'My label after button press'
		self.otherlabel_wid.text = self.moreinfo
		self.info = 'New info text'
	    '''

class ControllerApp():
    def build(self):
        return Controller(info='Hello world')

'''
if __name__ == '__main__':
    ControllerApp().run()
'''