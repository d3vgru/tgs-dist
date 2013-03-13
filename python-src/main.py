import kivy
kivy.require('1.0.9')
from kivy.app import App
from kivy.properties import ObjectProperty, StringProperty

import nulllayout

import os
import M2Crypto

class ControllerApp(App):
    def build(self):
        return nulllayout.NullLayout()

if __name__ == '__main__':
    ControllerApp().run()
