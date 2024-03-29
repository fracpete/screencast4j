# screencast4j

Simple Java frontend for creating screencasts by recording the following:

* sound (WAV)
* desktop (H.264)
* webcam (H.264)

Each of the sources, sound, webcam and screen, get stored in separate files.
These files you can then combine using a 
[video editing software](https://en.wikipedia.org/wiki/List_of_video_editing_software) 
like [OpenShot](http://openshot.org/), [Kdenlive](https://kdenlive.org/),
[Shotcut](http://www.shotcut.org/) or [Pitivi](http://pitivi.org/).

## Usage

Use the following class to get a simple user interface:

```
com.github.fracpete.screencast4j.gui.Main
```

## Credits

* Icons by [ionicons](https://www.iconfinder.com/iconsets/ionicons)
* Webcam handling by [sarxos](https://github.com/sarxos/webcam-capture)
* Video processing by [xuggler](http://www.xuggle.com/xuggler/)

## Maven

Use the following dependency to include it in your Maven project:

```
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>screencast4j</artifactId>
      <version>0.1.5</version>
    </dependency>
```
