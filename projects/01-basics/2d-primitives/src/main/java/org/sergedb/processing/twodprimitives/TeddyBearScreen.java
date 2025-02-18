package org.sergedb.processing.twodprimitives;

import org.sergedb.processing.core.managers.Screen;
import processing.core.PApplet;

import static processing.core.PConstants.*;

public class TeddyBearScreen implements Screen {

    private final PApplet app;

    public TeddyBearScreen(PApplet app) {
        this.app = app;
    }

    @Override
    public void onInitialize() {
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
        app.background(255, 249, 229);
        app.pushMatrix();
        app.scale(1.5F);
        drawTeddyBear();
        app.popMatrix();
    }

    @Override
    public void onDispose() {
    }

    private void drawTeddyBear() {

          app.noFill();
          app.stroke(0);
          app.strokeWeight(2);

          // Body elements

          // Hands
          app.strokeWeight(2.5F);
          app.fill(180, 120, 85);
          app.arc(-60, -10, 150, 140, HALF_PI, PI+HALF_PI);
          app.arc(60, -10, 150, 140, PI+HALF_PI, TWO_PI+HALF_PI);

          // Tummy
          app.strokeWeight(2);
          app.fill(180, 120, 85);
          app.ellipse(0, 20, 240, 230);
          app.fill(220, 186, 146);
          app.ellipse(0, 30, 160, 150);

          // Feet
          app.strokeWeight(2.5F);
          app.fill(180, 120, 85);
          app.ellipse(-90, 100, 100, 110);
          app.ellipse(90, 100, 100, 110);

          app.strokeWeight(2);
          app.fill(220, 186, 146);

          app.ellipse(-90, 115, 50, 40);
          app.ellipse(-90, 65, 28, 23);
          app.ellipse(-115, 85, 23, 21);
          app.ellipse(-65, 85, 23, 21);

          app.ellipse(90, 115, 50, 40);
          app.ellipse(90, 65, 28, 23);
          app.ellipse(115, 85, 23, 21);
          app.ellipse(65, 85, 23, 21);

          // Head and face elements

          // Ears
          app.strokeWeight(2.5F);
          app.fill(180, 120, 85);
          app.ellipse(-80, -210, 90, 85);
          app.ellipse(80, -210, 90, 85);

          app.strokeWeight(2);
          app.fill(220, 186, 146);
          app.ellipse(-80, -210, 40, 35);
          app.ellipse(80, -210, 40, 35);

          // Head structure with nose and mounth
          app.strokeWeight(2.5F);
          app.fill(180, 120, 85);
          app.ellipse(0, -150, 210, 190);

          app.fill(220, 186, 146);
          app.ellipse(0, -105, 65, 75);

          app.strokeWeight(2);
          app.fill(160,96,60);
          app.ellipse(0, -105, 30, 20);

          app.strokeWeight(0);
          app.fill(255);
          app.ellipse(6, -108, 8, 6);

          app.noFill();
          app.strokeWeight(2.5F);
          app.arc(-10, -94, 20, 15, 0, PI-QUARTER_PI);
          app.arc(10, -94, 20, 15, QUARTER_PI, PI);

          // Eyes
          app.fill(35, 35, 35);
          app.ellipse(-50, -140, 38, 36);
          app.ellipse(50, -140, 38, 36);

          app.strokeWeight(0);
          app.fill(255);
          app.ellipse(-43, -143, 12, 15);
          app.ellipse(57, -143, 12, 15);
    }
}
