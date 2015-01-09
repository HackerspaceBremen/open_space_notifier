Open Space Notifier
============================

This repository holds the source code of the open space notifier, which is a backend for serving the [SpaceAPI](http://spaceapi.net). The backend is hosted on an Google App Engine instance and written with Java. 

This project is used in the following spaces:
* [Hackerspace Bremen](https://hackerspacehb.appspot.com)

There are several clients which use the Open Space Notifier as its backend:
* [hsbh_schedule_app_ios](https://github.com/HackerspaceBremen/hsbh_schedule_app_ios)
* [hshb_notifier_android](https://github.com/HackerspaceBremen/hshb_notifier_android)
* [hsp](https://github.com/HackerspaceBremen/hsp)
* [hshb_notifier_win](https://github.com/HackerspaceBremen/hshb_notifier_win)
* [osn_chrome_ext](https://github.com/HackerspaceBremen/osn_chrome_ext)
* [hshb_aastra](https://github.com/HackerspaceBremen/hshb_aastra)

Be aware that the most of these clients are created for a specific space, so you need to change the code to your needs.

Projectinformation
------------------

- Build with maven

Dependencies
------------

- [Objectify](https://code.google.com/p/objectify-appengine) 
- [Guice](https://code.google.com/p/google-guice)

Continuous Integration
----------------------

[![Build Status](http://tools.steveliedtke.de/jenkins/job/open%20space%20notifier%20-%20master/badge/icon)](http://tools.steveliedtke.de/jenkins/job/open%20space%20notifier%20-%20master/)

The project is build and tested by a Jenkins service on a private server. You can access it as an 
read-only anonymous user on the [Jenkins of Steve Liedtke](http://tools.steveliedtke.de/jenkins/view/Hackerspace%20Projekte).

Contributors
------------

* [dragondagda](https://github.com/dragondagda)
* [mkochenough](https://github.com/mkochenough)


License
-------

  Copyright (C) 2013-15 Hackerspace Bremen e.V.
  
	This program is free software; you can redistribute it and/or modify it under the terms of the 
	GNU General Public License as published by the Free Software Foundation; either version 3 of 
	the License, or (at your option) any later version.
	
	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
	without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
	the GNU General Public License for more details.
	
	You can find a copy of the GNU General Public License on http://www.gnu.org/licenses/gpl.html.
