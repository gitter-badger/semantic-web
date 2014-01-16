Denigma SemanticWeb project
============================

This is Denigma SemanticWeb app where for all data bigdata triple store is used

Most of the code that deals with bigdata is inside subproject

Setting Up
----------

To set up the project you need to (most of instructions are for Deiban/Ubuntu based Linux, but for Windows it will be somehow similar):
* Have scala 2.10.x, play 2.2.x and sbt 0.13 installed
    - Make sure you use JDK 1.7+ and have JAVA_HOME variable setup
    - Install Scala ( http://scala-lang.org/ ) and sbt ( http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html )
    - Download play and add PLAY_HOME to your Path
* Install Coffeescript
    - install nodejs ( https://github.com/joyent/node/wiki/Installing-Node.js-via-package-manager )
    - install coffeescript with
        $  sudo npm install -g coffee-script
* Install Sass and Compass:
    - you may need to install ruby2 and gems first ( see http://gorails.com/setup/ubuntu/13.10 ), and then:
    $ sudo gem install compass --pre
* Install following Compass libs:
    $ sudo gem install semantic-ui-sass
    $ sudo gem install singularitygs
* run tests:
    $ play test
* Check if app runs without errors and with semantic-ui styles. I added index.png pic to show how the styles may look like