module spaceshooter {
    requires hanyaeger;
    requires com.google.guice;
    requires org.checkerframework.checker.qual;

    exports org.spacex;
    
    opens backgrounds;
    opens audios;
    opens sprites;
    opens gifs;
}