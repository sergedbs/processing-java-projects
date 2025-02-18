package org.sergedb.processing.motion;

import org.sergedb.processing.core.events.EventBus;
import org.sergedb.processing.core.events.KeyEvent;
import org.sergedb.processing.core.managers.Screen;
import processing.core.PApplet;

import static processing.core.PApplet.*;

public class AnimatedTeddyBearScreen implements Screen {

    private final PApplet app;

    private float headRotationAngle, feetRotationAngle;
    private boolean eyesOpen, bearClicked;
    private int lastBlinkFrame, lastMouseClickFrame;

    public AnimatedTeddyBearScreen(PApplet app) {
        this.app = app;
    }

    private void animate() {
        EventBus.getInstance().subscribe(KeyEvent.class, event -> {
            if (event.keyCode() == 32 && event.isPressed()) {
                bearClicked = true;
                lastMouseClickFrame = app.frameCount;
            }
        });
    }

    private void animateTeddyBear() {
        headRotationAngle = (float) (sin((float) (app.frameCount * (bearClicked ? 0.1 : 0.05))) * (bearClicked ? 0.05 : 0.02));
        feetRotationAngle = (float) (sin((float) (app.frameCount * 0.08)) * 0.02);

        if (!bearClicked && app.frameCount - lastBlinkFrame >= (eyesOpen ? 150 : 9)) {
            eyesOpen = !eyesOpen;
            lastBlinkFrame = app.frameCount;
        }

        if (bearClicked && app.frameCount - lastMouseClickFrame >= 180) {
            bearClicked = false;
        }

        if (bearClicked) {
            eyesOpen = false;
            animateHeart();
        }
    }

    private void animateHeart() {
        float x = map(sin((float) (app.frameCount - lastMouseClickFrame) * 0.05f), -1, 1, -10, 10);
        float y = lerp(-265, -400, (float) (app.frameCount - lastMouseClickFrame) / 180);
        float size = lerp(40, 80, (float) (app.frameCount - lastMouseClickFrame) / 180);

        heart(x, y, size);
    }

    private void heart(float x, float y, float size) {
        float halfSize = size / 2;
        float intersectionY = sqrt(sq((float) (halfSize * 0.5)) - sq((float) (halfSize * 0.4)));

        app.fill(255, 0, 0);
        app.noStroke();

        app.circle((float) (x - halfSize * 0.4), y - halfSize / 2, halfSize);

        app.circle((float) (x + halfSize * 0.4), y - halfSize / 2, halfSize);

        app.triangle(
                (float) (x - (halfSize * 0.5) - intersectionY), (float) (y - (halfSize * 0.5) + intersectionY),
                (float) (x + (halfSize * 0.5) + intersectionY), (float) (y - (halfSize * 0.5) + intersectionY),
                x, y + halfSize / 2
        );
    }

    private void drawTeddyBear() {
        app.noFill();
        app.stroke(0);
        app.strokeWeight(2);

        // Body elements

        // Hands
        app.strokeWeight(2.5f);
        app.fill(180, 120, 85);
        app.ellipse(-60, -10, 150, 140);
        app.ellipse(60, -10, 150, 140);

        // Tummy
        app.strokeWeight(2);
        app.fill(180, 120, 85);
        app.ellipse(0, 20, 240, 230);
        app.fill(220, 186, 146);
        app.ellipse(0, 30, 160, 150);

        // Left foot
        app.pushMatrix();
        app.rotate(-feetRotationAngle);
        drawLeftFoot();
        app.popMatrix();

        // Right foot
        app.pushMatrix();
        app.rotate(feetRotationAngle);
        drawRightFoot();
        app.popMatrix();

        // Head
        app.pushMatrix();
        app.rotate(headRotationAngle);
        drawHead();
        app.popMatrix();
    }

    private void drawLeftFoot() {
        app.strokeWeight(2.5f);
        app.fill(180, 120, 85);
        app.ellipse(-90, 100, 100, 110);

        app.strokeWeight(2);
        app.fill(220, 186, 146);

        app.ellipse(-90, 115, 50, 40);
        app.ellipse(-90, 65, 28, 23);
        app.ellipse(-115, 85, 23, 21);
        app.ellipse(-65, 85, 23, 21);
    }

    private void drawRightFoot() {
        app.strokeWeight(2.5f);
        app.fill(180, 120, 85);
        app.ellipse(90, 100, 100, 110);

        app.strokeWeight(2);
        app.fill(220, 186, 146);

        app.ellipse(90, 115, 50, 40);
        app.ellipse(90, 65, 28, 23);
        app.ellipse(115, 85, 23, 21);
        app.ellipse(65, 85, 23, 21);
    }

    void drawHead() {
        // Ears
        app.strokeWeight(2.5f);
        app.fill(180, 120, 85);
        app.ellipse(-80, -210, 90, 85);
        app.ellipse(80, -210, 90, 85);

        app.strokeWeight(2);
        app.fill(220, 186, 146);
        app.ellipse(-80, -210, 40, 35);
        app.ellipse(80, -210, 40, 35);

        // Head structure with nose and mounth
        app.strokeWeight(2.5f);
        app.fill(180, 120, 85);
        app.ellipse(0, -150, 210, 190);

        app.fill(220, 186, 146);
        app.ellipse(0, -105, 65, 75);

        app.strokeWeight(2);
        app.fill(160, 96, 60);
        app.ellipse(0, -105, 30, 20);

        app.strokeWeight(0);
        app.fill(255);
        app.ellipse(6, -108, 8, 6);

        if (!eyesOpen && bearClicked) {
            app.fill(209, 144, 142);
            app.strokeWeight(2.5f);
            app.arc(0, -86, 10, 8, 0, PI);
            app.strokeWeight(0);
            app.triangle(10, -86, 0, -90, -10, -86);
        }

        app.noFill();
        app.strokeWeight(2.5f);
        app.arc(-10, -94, 20, 15, 0, PI - QUARTER_PI);
        app.arc(10, -94, 20, 15, QUARTER_PI, PI);

        // Eyes
        if (eyesOpen) {
            app.fill(35, 35, 35);
            app.ellipse(-50, -140, 38, 36); // Left eye
            app.ellipse(50, -140, 38, 36);  // Right eye

            app.strokeWeight(0);
            app.fill(255);
            app.ellipse(-43, -143, 12, 15); // Left eye highlight
            app.ellipse(57, -143, 12, 15);  // Right eye highlight
        }
        if (!eyesOpen && !bearClicked) {
            app.strokeWeight(4);
            app.arc(-50, -140, 38, 10, QUARTER_PI, PI - QUARTER_PI); // Left eye closed
            app.arc(50, -140, 38, 10, QUARTER_PI, PI - QUARTER_PI); // Left eye closed
        }
        if (!eyesOpen && bearClicked) {
            app.strokeWeight(4);
            app.line(-55, -150, -35, -140);
            app.line(-55, -130, -35, -140);

            app.line(35, -140, 55, -150);
            app.line(35, -140, 55, -130);
        }
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
        app.scale(1.5f);
        app.translate(0, app.height / 10f);
        animate();
        animateTeddyBear();
        drawTeddyBear();
        app.popMatrix();
    }

    @Override
    public void onDispose() {

    }
}
