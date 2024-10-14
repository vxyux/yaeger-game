module spaceshooter {
    requires hanyaeger;
    requires com.google.guice;

    exports org.spacex;
    
    opens backgrounds;
    opens audios;
    opens sprites;
    opens gifs;
}