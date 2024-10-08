module spaceshooter {
    requires hanyaeger;

    exports org.spacex;

    opens backgrounds;
    opens audios;
    opens sprites;
    exports org.spacex.entities;
}